/*
 * Copyright (c) 2016. Sten Martinez
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package net.longfalcon.newsj;

import net.longfalcon.newsj.fs.FileSystemService;
import net.longfalcon.newsj.fs.model.Directory;
import net.longfalcon.newsj.model.Binary;
import net.longfalcon.newsj.model.Category;
import net.longfalcon.newsj.model.Group;
import net.longfalcon.newsj.model.MatchedReleaseQuery;
import net.longfalcon.newsj.model.Release;
import net.longfalcon.newsj.model.ReleaseNfo;
import net.longfalcon.newsj.model.ReleaseRegex;
import net.longfalcon.newsj.model.Site;
import net.longfalcon.newsj.persistence.BinaryDAO;
import net.longfalcon.newsj.persistence.GroupDAO;
import net.longfalcon.newsj.persistence.PartDAO;
import net.longfalcon.newsj.persistence.ReleaseDAO;
import net.longfalcon.newsj.persistence.ReleaseRegexDAO;
import net.longfalcon.newsj.persistence.SiteDAO;
import net.longfalcon.newsj.service.GameService;
import net.longfalcon.newsj.service.MovieService;
import net.longfalcon.newsj.service.MusicService;
import net.longfalcon.newsj.util.DateUtil;
import net.longfalcon.newsj.util.Defaults;
import net.longfalcon.newsj.util.ValidatorUtil;
import org.apache.commons.lang3.text.StrMatcher;
import org.apache.commons.lang3.text.StrTokenizer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.joda.time.format.PeriodFormat;
import org.joda.time.format.PeriodFormatter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * User: Sten Martinez
 * Date: 10/8/15
 * Time: 9:13 PM
 */
@Service
public class Releases {
    private static final Log _log = LogFactory.getLog(Releases.class);
    private static PeriodFormatter _periodFormatter = PeriodFormat.wordBased();
    private static Pattern _wildcardPattern = Pattern.compile("(\\D)+\\*$"); // this should match bin.name.* wildcard group names
    private Binaries binaries;
    private BinaryDAO binaryDAO;
    private CategoryService categoryService;
    private FileSystemService fileSystemService;
    private GameService gameService;
    private GroupDAO groupDAO;
    private MovieService movieService;
    private MusicService musicService;
    private Nfo nfo;
    private Nzb nzb;
    private PartDAO partDAO;
    private ReleaseDAO releaseDAO;
    private ReleaseRegexDAO releaseRegexDAO;
    private SiteDAO siteDAO;
    private PlatformTransactionManager transactionManager;
    private TVRageService tvRageService;

    public Release findByGuid(String guid) {
        Release release = releaseDAO.findByGuid(guid);
        populateTransientFields(release);

        return release;
    }

    public Release findByReleaseId(long releaseId) {
        Release release = releaseDAO.findByReleaseId(releaseId);
        populateTransientFields(release);

        return release;
    }

    public Long getBrowseCount(Collection<Integer> categoryIds, int maxAgeDays, List<Integer> excludedCategoryIds, long groupId) {
        Date maxAge = null;
        if (maxAgeDays > 0) {
            maxAge = DateTime.now().minusDays(maxAgeDays).toDate();
        }

        Long groupIdObj = null;
        if (groupId > 0) {
            groupIdObj = groupId;
        }

        return releaseDAO.countByCategoriesMaxAgeAndGroup(categoryIds, maxAge, excludedCategoryIds, groupIdObj);
    }

    public List<Release> getBrowseReleases(Collection<Integer> categoryIds, int maxAgeDays, List<Integer> excludedCategoryIds,
                                           long groupId, String orderByField, boolean descending,
                                           int offset, int pageSize) {
        Date maxAge = null;
        if (maxAgeDays > 0) {
            maxAge = DateTime.now().minusDays(maxAgeDays).toDate();
        }

        Long groupIdObj = null;
        if (groupId > 0) {
            groupIdObj = groupId;
        }

        List<Release> releases = releaseDAO.findByCategoriesMaxAgeAndGroup(categoryIds, maxAge, excludedCategoryIds, groupIdObj,
                orderByField, descending, offset, pageSize);
        for (Release release : releases) {
            Group group = groupDAO.findGroupByGroupId(release.getGroupId());
            if (group != null) {
                release.setGroupName(group.getName());
            }
            Category category = release.getCategory();
            if (category != null) {
                release.setCategoryDisplayName(categoryService.getCategoryDisplayName(category.getId()));
            }
        }
        return releases;
    }

    public List<ReleaseRegex> getRegexesWithStatistics(boolean activeOnly, String groupName, boolean userReleaseRegexes) {
        List<ReleaseRegex> releaseRegexList = releaseRegexDAO.getRegexes(activeOnly, groupName, userReleaseRegexes);

        for (ReleaseRegex releaseRegex : releaseRegexList) {
            long releaseRegexId = releaseRegex.getId();
            int releaseCount = Math.toIntExact(releaseDAO.countReleasesByRegexId(releaseRegexId));
            releaseRegex.setNumberReleases(releaseCount);
            releaseRegex.setLastReleaseDate(releaseDAO.getLastReleaseDateByRegexId(releaseRegexId));
        }

        return releaseRegexList;
    }

    public Long getSearchCount(String[] searchTokens, Collection<Integer> categoryIds, int maxAgeDays, List<Integer> excludedCategoryIds, long groupId) {
        Date maxAge = null;
        if (maxAgeDays > 0) {
            maxAge = DateTime.now().minusDays(maxAgeDays).toDate();
        }

        Long groupIdObj = null;
        if (groupId > 0) {
            groupIdObj = groupId;
        }

        return releaseDAO.searchCountByCategoriesMaxAgeAndGroup(searchTokens, categoryIds, maxAge, excludedCategoryIds, groupIdObj);
    }

    public List<Release> getSearchReleases(String[] searchTokens, Collection<Integer> categoryIds, int maxAgeDays, List<Integer> excludedCategoryIds,
                                           long groupId, String orderByField, boolean descending,
                                           int offset, int pageSize) {
        Date maxAge = null;
        if (maxAgeDays > 0) {
            maxAge = DateTime.now().minusDays(maxAgeDays).toDate();
        }

        Long groupIdObj = null;
        if (groupId > 0) {
            groupIdObj = groupId;
        }

        List<Release> releases = releaseDAO.searchByCategoriesMaxAgeAndGroup(searchTokens, categoryIds, maxAge, excludedCategoryIds, groupIdObj,
                orderByField, descending, offset, pageSize);
        for (Release release : releases) {
            Group group = groupDAO.findGroupByGroupId(release.getGroupId());
            if (group != null) {
                release.setGroupName(group.getName());
            }
            Category category = release.getCategory();
            if (category != null) {
                release.setCategoryDisplayName(categoryService.getCategoryDisplayName(category.getId()));
            }
        }
        return releases;
    }

    public void processReleases() {
        String startDateString = DateUtil.displayDateFormatter.print(System.currentTimeMillis());
        _log.info(String.format("Starting release update process (%s)", startDateString));

        // get site config TODO: use config service
        Site site = siteDAO.getDefaultSite();

        int retcount = 0;

        Directory nzbBaseDir = fileSystemService.getDirectory("/nzbs");

        checkRegexesUptoDate(site.getLatestRegexUrl(), site.getLatestRegexRevision());

        // Stage 0

        // this is a hack - tx is not working ATM
        TransactionStatus transaction = transactionManager.getTransaction(new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRED));

        //
        // Get all regexes for all groups which are to be applied to new binaries
        // in order of how they should be applied
        //
        List<ReleaseRegex> releaseRegexList = releaseRegexDAO.getRegexes(true,"-1", false);
        for (ReleaseRegex releaseRegex : releaseRegexList) {

            String releaseRegexGroupName = releaseRegex.getGroupName();
            _log.info(String.format("Applying regex %d for group %s", releaseRegex.getId(), ValidatorUtil.isNull(releaseRegexGroupName) ? "all" : releaseRegexGroupName));

            // compile the regex early, to test them
            String regex = releaseRegex.getRegex();
            Pattern pattern = Pattern.compile(fixRegex(regex), Pattern.CASE_INSENSITIVE); // remove '/' and '/i'

            HashSet<Long> groupMatch = new LinkedHashSet<>();

            //
            // Groups ending in * need to be like matched when getting out binaries for groups and children
            //
            Matcher matcher = _wildcardPattern.matcher(releaseRegexGroupName);
            if (matcher.matches()) {
                releaseRegexGroupName = releaseRegexGroupName.substring(0,releaseRegexGroupName.length()-1);
                List<Group> groups = groupDAO.findGroupsByName(releaseRegexGroupName);
                for (Group group : groups) {
                    groupMatch.add(group.getId());
                }
            } else if ( !ValidatorUtil.isNull(releaseRegexGroupName) ) {
                Group group = groupDAO.getGroupByName(releaseRegexGroupName);
                if (group != null) {
                    groupMatch.add(group.getId());
                }
            }

            List<Binary> binaries = new ArrayList<>();
            if (groupMatch.size() > 0) {
                // Get out all binaries of STAGE0 for current group
                binaries = binaryDAO.findByGroupIdsAndProcStat(groupMatch, Defaults.PROCSTAT_NEW);
            }

            Map<String, String> arrNoPartBinaries = new LinkedHashMap<>();
            DateTime fiveHoursAgo = DateTime.now().minusHours(5);

            // this for loop should probably be a single transaction
            for (Binary binary : binaries) {
                String testMessage = "Test run - Binary Name " + binary.getName();

                Matcher groupRegexMatcher = pattern.matcher(binary.getName());
                if (groupRegexMatcher.find()) {
                    String reqIdGroup = null;
                    try {
                        reqIdGroup = groupRegexMatcher.group("reqid");
                    } catch (IllegalArgumentException e) {
                        _log.debug(e.toString());
                    }
                    String partsGroup = null;
                    try {
                        partsGroup = groupRegexMatcher.group("parts");
                    } catch (IllegalArgumentException e) {
                        _log.debug(e.toString());
                    }
                    String nameGroup = null;
                    try {
                        nameGroup = groupRegexMatcher.group("name");
                    } catch (Exception e) {
                        _log.debug(e.toString());
                    }
                    _log.debug(testMessage + " matches with: \n reqId = " + reqIdGroup + " parts = " + partsGroup + " and name = " + nameGroup);

                    if ((ValidatorUtil.isNotNull(reqIdGroup) && ValidatorUtil.isNumeric(reqIdGroup)) && ValidatorUtil.isNull(nameGroup)) {
                        nameGroup = reqIdGroup;
                    }

                    if (ValidatorUtil.isNull(nameGroup)) {
                        _log.warn(String.format("regex applied which didnt return right number of capture groups - %s", regex));
                        _log.warn(String.format("regex matched: reqId = %s parts = %s and name = %s", reqIdGroup, partsGroup, nameGroup));
                        continue;
                    }

                    // If theres no number of files data in the subject, put it into a release if it was posted to usenet longer than five hours ago.
                    if ( (ValidatorUtil.isNull(partsGroup) && fiveHoursAgo.isAfter(binary.getDate().getTime()))) {
                        //
                        // Take a copy of the name of this no-part release found. This can be used
                        // next time round the loop to find parts of this set, but which have not yet reached 3 hours.
                        //
                        arrNoPartBinaries.put(nameGroup, "1");
                        partsGroup = "01/01";
                    }

                    if (ValidatorUtil.isNotNull(nameGroup) && ValidatorUtil.isNotNull(partsGroup)) {

                        if (partsGroup.indexOf('/') == -1) {
                            partsGroup = partsGroup.replaceFirst("(-)|(~)|(\\sof\\s)", "/"); // replace weird parts delimiters
                        }

                        Integer regexCategoryId = releaseRegex.getCategoryId();
                        Integer reqId = null;
                        if (ValidatorUtil.isNotNull(reqIdGroup) && ValidatorUtil.isNumeric(reqIdGroup)) {
                            reqId = Integer.parseInt(reqIdGroup);
                        }

                        //check if post is repost
                        Pattern repostPattern = Pattern.compile("(repost\\d?|re\\-?up)", Pattern.CASE_INSENSITIVE);
                        Matcher binaryNameRepostMatcher = repostPattern.matcher(binary.getName());

                        if (binaryNameRepostMatcher.find() && !nameGroup.toLowerCase().matches("^[\\s\\S]+(repost\\d?|re\\-?up)")) {
                            nameGroup = nameGroup + (" " + binaryNameRepostMatcher.group(1));
                        }

                        String partsStrings[] = partsGroup.split("/");
                        int relpart = Integer.parseInt(partsStrings[0]);
                        int relTotalPart = Integer.parseInt(partsStrings[1]);

                        binary.setRelName(nameGroup.replace("_", " "));
                        binary.setRelPart(relpart);
                        binary.setRelTotalPart(relTotalPart);
                        binary.setProcStat(Defaults.PROCSTAT_TITLEMATCHED);
                        binary.setCategoryId(regexCategoryId);
                        binary.setRegexId(releaseRegex.getId());
                        binary.setReqId(reqId);
                        binaryDAO.updateBinary(binary);

                    }
                }
            }

        }

        transactionManager.commit(transaction);

        // this is a hack - tx is not working ATM
        transaction = transactionManager.getTransaction(new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRED));
        //
        // Move all binaries from releases which have the correct number of files on to the next stage.
        //
        _log.info("Stage 2");
        List<MatchedReleaseQuery> matchedReleaseQueries = binaryDAO.findBinariesByProcStatAndTotalParts(Defaults.PROCSTAT_TITLEMATCHED);
        matchedReleaseQueries = combineMatchedQueries(matchedReleaseQueries);

        int siteMinFilestoFormRelease = site.getMinFilesToFormRelease();

        for (MatchedReleaseQuery matchedReleaseQuery : matchedReleaseQueries) {
            retcount++;

            //
            // Less than the site permitted number of files in a release. Dont discard it, as it may
            // be part of a set being uploaded.
            //
            int minFiles = siteMinFilestoFormRelease;
            String releaseName = matchedReleaseQuery.getReleaseName();
            long matchedReleaseQueryGroup = matchedReleaseQuery.getGroup();
            Long matchedReleaseQueryNumberOfBinaries = matchedReleaseQuery.getNumberOfBinaries();
            int matchecReleaseTotalParts = matchedReleaseQuery.getReleaseTotalParts();
            String fromName = matchedReleaseQuery.getFromName();
            Integer reqId = matchedReleaseQuery.getReqId();

            Group group = groupDAO.findGroupByGroupId(matchedReleaseQueryGroup);
            if (group != null && group.getMinFilesToFormRelease() != null) {
                minFiles = group.getMinFilesToFormRelease();
            }

            if (matchedReleaseQueryNumberOfBinaries < minFiles) {

                _log.warn(String.format("Number of files in release %s less than site/group setting (%s/%s)", releaseName, matchedReleaseQueryNumberOfBinaries, minFiles));

                binaryDAO.updateBinaryIncrementProcAttempts(releaseName, Defaults.PROCSTAT_TITLEMATCHED, matchedReleaseQueryGroup, fromName);
            } else if (matchedReleaseQueryNumberOfBinaries >= matchecReleaseTotalParts) {
                // Check that the binary is complete
                List<Binary> releaseBinaryList = binaryDAO.findBinariesByReleaseNameProcStatGroupIdFromName(releaseName, Defaults.PROCSTAT_TITLEMATCHED, matchedReleaseQueryGroup, fromName);

                boolean incomplete = false;
                for (Binary binary : releaseBinaryList) {
                    long partsCount = partDAO.countPartsByBinaryId(binary.getId());
                    if (partsCount < binary.getTotalParts()) {
                        float percentComplete = ((float) partsCount / (float) binary.getTotalParts()) * 100;
                        _log.warn(String.format("binary %s from %s has missing parts = %s/%s (%s%% complete)", binary.getId(), releaseName, partsCount, binary.getTotalParts(), percentComplete));

                        // Allow to binary to release if posted to usenet longer than four hours ago and we still don't have all the parts
                        DateTime fourHoursAgo = DateTime.now().minusHours(4);
                        if (fourHoursAgo.isAfter(new DateTime(binary.getDate()))) {
                            _log.info("allowing incomplete binary " + binary.getId());
                        } else {
                            incomplete = true;
                        }
                    }
                }

                if (incomplete) {
                    _log.warn(String.format("Incorrect number of parts %s-%s-%s", releaseName, matchedReleaseQueryNumberOfBinaries, matchecReleaseTotalParts));
                    binaryDAO.updateBinaryIncrementProcAttempts(releaseName, Defaults.PROCSTAT_TITLEMATCHED, matchedReleaseQueryGroup, fromName);
                }

                //
                // Right number of files, but see if the binary is a allfilled/reqid post, in which case it needs its name looked up
                // TODO: Does this even work anymore?
                else if ( ValidatorUtil.isNotNull(site.getReqIdUrl()) && ValidatorUtil.isNotNull(reqId) ) {

                        //
                        // Try and get the name using the group
                        //
                        _log.info("Looking up "+ reqId + " in " + group.getName() + "...");
                        String newTitle = getReleaseNameForReqId(site.getReqIdUrl(), group, reqId, true);

                        //
                        // if the feed/group wasnt supported by the scraper, then just use the release name as the title.
                        //
                        if (ValidatorUtil.isNull(newTitle) || newTitle.equals("no feed"))
                        {
                            newTitle = releaseName;
                            _log.warn( "Group not supported");
                        }

                        //
                        // Valid release with right number of files and title now, so move it on
                        //
                        if (ValidatorUtil.isNotNull(newTitle)) {
                            binaryDAO.updateBinaryNameAndStatus(newTitle, Defaults.PROCSTAT_READYTORELEASE, releaseName, Defaults.PROCSTAT_TITLEMATCHED, matchedReleaseQueryGroup, fromName);
                        } else {
                            //
                            // Item not found, if the binary was added to the index yages ago, then give up.
                            //
                            Timestamp timestamp = binaryDAO.findMaxDateAddedBinaryByReleaseNameProcStatGroupIdFromName(releaseName, Defaults.PROCSTAT_TITLEMATCHED, matchedReleaseQueryGroup, fromName);
                            DateTime maxAddedDate = new DateTime(timestamp);
                            DateTime twoDaysAgo = DateTime.now().minusDays(2);

                            if (maxAddedDate.isBefore(twoDaysAgo)) {
                                binaryDAO.updateBinaryNameAndStatus(releaseName, Defaults.PROCSTAT_NOREQIDNAMELOOKUPFOUND, releaseName, Defaults.PROCSTAT_TITLEMATCHED, matchedReleaseQueryGroup, fromName);
                                _log.warn("Not found in 48 hours");
                            }
                        }
                } else {
                    binaryDAO.updateBinaryNameAndStatus(releaseName, Defaults.PROCSTAT_READYTORELEASE, releaseName, Defaults.PROCSTAT_TITLEMATCHED, matchedReleaseQueryGroup, fromName);

                }
            } else {
                //
                // Theres less than the expected number of files, so update the attempts and move on.
                //

                _log.info(String.format("Incorrect number of files for %s (%d/%d)", releaseName, matchedReleaseQueryNumberOfBinaries, matchecReleaseTotalParts));
                binaryDAO.updateBinaryIncrementProcAttempts(releaseName, Defaults.PROCSTAT_TITLEMATCHED, matchedReleaseQueryGroup, fromName);
            }

            if (retcount % 10 == 0) {
                _log.info(String.format("-processed %d binaries stage two", retcount));
            }

        }
        transactionManager.commit(transaction);

        retcount = 0;
        int nfoCount = 0;

        // this is a hack - tx is not working ATM
        transaction = transactionManager.getTransaction(new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRED));
        //
        // Get out all distinct relname, group from binaries of STAGE2
        //
        _log.info("Stage 3");
        List<MatchedReleaseQuery> readyReleaseQueries = binaryDAO.findBinariesByProcStatAndTotalParts(Defaults.PROCSTAT_READYTORELEASE);
        readyReleaseQueries = combineMatchedQueries(readyReleaseQueries);
        for (MatchedReleaseQuery readyReleaseQuery : readyReleaseQueries) {
            retcount++;

            String releaseName = readyReleaseQuery.getReleaseName();
            int numParts = readyReleaseQuery.getReleaseTotalParts();
            long binaryCount = readyReleaseQuery.getNumberOfBinaries();
            long groupId = readyReleaseQuery.getGroup();
            //
            // Get the last post date and the poster name from the binary
            //
            String fromName = readyReleaseQuery.getFromName();
            Timestamp timestamp = binaryDAO.findMaxDateAddedBinaryByReleaseNameProcStatGroupIdFromName(releaseName, Defaults.PROCSTAT_READYTORELEASE, groupId, fromName);
            DateTime addedDate = new DateTime(timestamp);

            //
            // Get all releases with the same name with a usenet posted date in a +1-1 date range.
            //
            Date oneDayBefore = addedDate.minusDays(1).toDate();
            Date oneDayAfter = addedDate.plusDays(1).toDate();
            List<Release> relDupes = releaseDAO.findReleasesByNameAndDateRange(releaseName, oneDayBefore, oneDayAfter );

            if (!relDupes.isEmpty()) {
                binaryDAO.updateBinaryNameAndStatus(releaseName, Defaults.PROCSTAT_DUPLICATE, releaseName, Defaults.PROCSTAT_READYTORELEASE, groupId, fromName);
                continue;
            }

            //
            // Get total size of this release
            // Done in a big OR statement, not an IN as the mysql binaryID index on parts table
            // was not being used.
            //

            // SM: TODO this should be revisited, using hb mappings

            long totalSize = 0;
            int regexAppliedCategoryId = 0;
            long regexIdUsed = 0;
            int reqIdUsed = 0;
            int relTotalParts = 0;
            float relCompletion;
            List<Binary> binariesForSize = binaryDAO.findBinariesByReleaseNameProcStatGroupIdFromName(releaseName, Defaults.PROCSTAT_READYTORELEASE, groupId, fromName);

            long relParts = 0;
            for (Binary binary : binariesForSize) {
                if (ValidatorUtil.isNotNull(binary.getCategoryId()) && regexAppliedCategoryId == 0) {
                    regexAppliedCategoryId = binary.getCategoryId();
                }

                if (ValidatorUtil.isNotNull(binary.getRegexId()) && regexIdUsed == 0) {
                    regexIdUsed = binary.getRegexId();
                }

                if (ValidatorUtil.isNotNull(binary.getReqId()) && reqIdUsed == 0) {
                    reqIdUsed = binary.getReqId();
                }

                relTotalParts += binary.getTotalParts();
                relParts += partDAO.countPartsByBinaryId(binary.getId());
                totalSize += partDAO.sumPartsSizeByBinaryId(binary.getId());
            }
            relCompletion = ((float) relParts / (float) relTotalParts) * 100f;

            //
            // Insert the release
            //

            String releaseGuid = UUID.randomUUID().toString();
            int categoryId;
            Category category = null;
            Long regexId;
            Integer reqId;
            if (regexAppliedCategoryId == 0) {
                categoryId = categoryService.determineCategory(groupId, releaseName);
            } else {
                categoryId = regexAppliedCategoryId;
            }
            if (categoryId > 0) {
                category = categoryService.getCategory(categoryId);
            }

            if (regexIdUsed == 0) {
                regexId = null;
            } else {
                regexId = regexIdUsed;
            }

            if (reqIdUsed == 0) {
                reqId = null;
            } else {
                reqId = reqIdUsed;
            }

            //Clean release name of '#', '@', '$', '%', '^', '§', '¨', '©', 'Ö'
            String cleanReleaseName = releaseName.replaceAll("[^A-Za-z0-9-_\\ \\.]+", "");
            Release release = new Release();
            release.setName(cleanReleaseName);
            release.setSearchName(cleanReleaseName);
            release.setTotalpart(numParts);
            release.setGroupId(groupId);
            release.setAddDate(new Date());
            release.setGuid(releaseGuid);
            release.setCategory(category);
            release.setRegexId(regexId);
            release.setRageId((long) -1);
            release.setPostDate(addedDate.toDate());
            release.setFromName(fromName);
            release.setSize(totalSize);
            release.setReqId(reqId);
            release.setPasswordStatus(site.getCheckPasswordedRar() == 1 ? -1 : 0); // magic constants
            release.setCompletion(relCompletion);
            releaseDAO.updateRelease(release);
            long releaseId = release.getId();
            _log.info("Added release " + cleanReleaseName);

            //
            // Tag every binary for this release with its parent release id
            // remove the release name from the binary as its no longer required
            //
            binaryDAO.updateBinaryNameStatusReleaseID("",Defaults.PROCSTAT_RELEASED, releaseId, releaseName, Defaults.PROCSTAT_READYTORELEASE, groupId, fromName);

            //
            // Find an .nfo in the release
            //
            ReleaseNfo releaseNfo = nfo.determineReleaseNfo(release);
            if ( releaseNfo != null) {
                nfo.addReleaseNfo(releaseNfo);
                nfoCount++;
            }

            //
            // Write the nzb to disk
            //
            nzb.writeNZBforReleaseId(release, nzbBaseDir, true);

            if (retcount % 5 == 0) {
                _log.info("-processed " + retcount + " releases stage three");
            }

        }

        _log.info("Found " + nfoCount + " nfos in " + retcount + " releases");

        //
        // Process nfo files
        //
        if (site.getLookupNfo() != 1) {
            _log.info("Site config (site.lookupnfo) prevented retrieving nfos");
        } else {
            nfo.processNfoFiles(site.getLookupImdb(), site.getLookupTvRage());
        }

        //
        // Lookup imdb if enabled
        //
        if (site.getLookupImdb() == 1)
        {
            movieService.processMovieReleases();
        }

        //
        // Lookup music if enabled
        //
        if (site.getLookupMusic() == 1)
        {
            musicService.processMusicReleases();
        }

        //
        // Lookup games if enabled
        //
        if (site.getLookupGames() == 1)
        {
            gameService.processConsoleReleases();
        }

        //
        // Check for passworded releases
        //
        if (site.getCheckPasswordedRar() != 1)
        {
            _log.info("Site config (site.checkpasswordedrar) prevented checking releases are passworded");
        }
        else
        {
            processPasswordedReleases(true);
        }

        //
        // Process all TV related releases which will assign their series/episode/rage data
        //
        tvRageService.processTvReleases(site.getLookupTvRage() == 1);

        //
        // Get the current datetime again, as using now() in the housekeeping queries prevents the index being used.
        //
        DateTime now = new DateTime();

        //
        // Tidy away any binaries which have been attempted to be grouped into
        // a release more than x times (SM: or is it days?)
        //
        int attemtpGroupBinDays = site.getAttemtpGroupBinDays();
        _log.info(String.format("Tidying away binaries which cant be grouped after %s days", attemtpGroupBinDays));

        DateTime maxGroupBinDays = now.minusDays(attemtpGroupBinDays);
        binaryDAO.updateProcStatByProcStatAndDate(Defaults.PROCSTAT_WRONGPARTS, Defaults.PROCSTAT_NEW, maxGroupBinDays.toDate());

        //
        // Delete any parts and binaries which are older than the site's retention days
        //
        int maxRetentionDays = site.getRawRetentionDays();
        DateTime maxRetentionDate = now.minusDays(maxRetentionDays);
        _log.info(String.format("Deleting parts which are older than %d days", maxRetentionDays));
        partDAO.deletePartByDate(maxRetentionDate.toDate());

        _log.info(String.format("Deleting binaries which are older than %d days", maxRetentionDays));
        binaryDAO.deleteBinaryByDate(maxRetentionDate.toDate());

        //
        // Delete any releases which are older than site's release retention days
        //
        int releaseretentiondays = site.getReleaseRetentionDays();
        if(releaseretentiondays != 0)
        {
            _log.info("Determining any releases past retention to be deleted.");

            DateTime maxReleaseRetentionDate = DateTime.now().minusDays(releaseretentiondays);
            List<Release> releasesToDelete = releaseDAO.findReleasesBeforeDate(maxReleaseRetentionDate.toDate());
            for (Iterator<Release> iterator = releasesToDelete.iterator(); iterator.hasNext(); ) {
                Release release = iterator.next();
                releaseDAO.deleteRelease(release);
            }
        }
        transactionManager.commit(transaction);

        _log.info(String.format("Processed %d releases", retcount));

        //return retcount;
    }

    // TODO
    private void checkRegexesUptoDate(String latestRegexUrl, int latestRegexRevision) {
        // hook up once it works
    }

    // convert from PHP style regexes in legacy Newznab
    private String fixRegex(String badRegex) {
        badRegex = badRegex.trim();
        String findBadNamesRegex = "\\?P\\<(\\w+)\\>";  // fix bad grouping syntax
        Pattern pattern = Pattern.compile(findBadNamesRegex);
        Matcher matcher = pattern.matcher(badRegex);
        String answer = badRegex;
        if (matcher.find()) {
            answer = matcher.replaceAll("?<$1>");
        }

        if (answer.startsWith("/")) {
            answer = answer.substring(1);
        }

        if (answer.endsWith("/i")) {  // TODO: case insensitive regexes are not properly created
            answer = answer.substring(0,answer.length()-2);
        } else if (answer.endsWith("/")) {
            answer = answer.substring(0,answer.length()-1);
        }

        return answer;
    }

    // merge releases with same release name, similar to original messy query
    private List<MatchedReleaseQuery> combineMatchedQueries(List<MatchedReleaseQuery> matchedReleaseQueries) {
        Map<String, MatchedReleaseQuery> queryMap = new LinkedHashMap<>(matchedReleaseQueries.size());
        for (MatchedReleaseQuery matchedReleaseQuery : matchedReleaseQueries) {
            String releaseName = matchedReleaseQuery.getReleaseName();
            long numberOfBinaries = matchedReleaseQuery.getNumberOfBinaries();
            int totalParts = matchedReleaseQuery.getReleaseTotalParts();
            if (queryMap.containsKey(releaseName)) {
                MatchedReleaseQuery currentQuery = queryMap.get(releaseName);
                currentQuery.setNumberOfBinaries(currentQuery.getNumberOfBinaries() + numberOfBinaries);
                currentQuery.setReleaseTotalParts(currentQuery.getReleaseTotalParts() + totalParts);
            } else {
                queryMap.put(releaseName, matchedReleaseQuery);
            }
        }

        return new ArrayList<>(queryMap.values());
    }

    // TODO
    private String getReleaseNameForReqId(String reqIdUrl, Group group, Integer reqId, boolean debug) {
        return "";
    }

    private void populateTransientFields(Release release) {
        if (release != null && release.getCategory() != null) {
            int categoryId = release.getCategory().getId();
            release.setCategoryId(categoryId);
            Group group = groupDAO.findGroupByGroupId(release.getGroupId());
            release.setGroupName(group.getName());
            release.setCategoryDisplayName(categoryService.getCategoryDisplayName(categoryId));
        }
    }

    private void processPasswordedReleases(boolean debug) {
    }

    @Transactional
    public void resetRelease(long releaseId) {
        binaryDAO.resetReleaseBinaries(releaseId);
    }

    public List<Release> searchSimilar(Release release, int limit, List<Integer> excludedCategoryIds) {
        String releaseName = release.getName();
        List<String> searchTokens = getReleaseNameSearchTokens(releaseName);

        return releaseDAO.searchReleasesByNameExludingCats(searchTokens, limit, excludedCategoryIds);
    }

    public List<String> getReleaseNameSearchTokens(String releaseName) {
        StrTokenizer strTokenizer = new StrTokenizer(releaseName, StrMatcher.charSetMatcher('.', '_'));
        List<String> tokenList = strTokenizer.getTokenList();

        return tokenList.stream()
                .filter(s -> s.length() > 2)
                .limit(2)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateRelease(Release release) {
        if (release.getCategoryId() > 0) {
            Category newCategory = categoryService.getCategory(release.getCategoryId());
            release.setCategory(newCategory);
        }

        releaseDAO.updateRelease(release);
    }

    public List<Release> getTopDownloads() {
        return releaseDAO.findTopDownloads();
    }

    public List<Release> getTopComments() {
        return releaseDAO.findTopCommentedReleases();
    }

    /**
     *
     * @return List of Object[Category,Long]
     */
    public List<Object[]> getRecentlyAddedReleases() {
        return releaseDAO.findRecentlyAddedReleaseCategories();
    }

    public Binaries getBinaries() {
        return binaries;
    }

    public void setBinaries(Binaries binaries) {
        this.binaries = binaries;
    }

    public FileSystemService getFileSystemService() {
        return fileSystemService;
    }

    public void setFileSystemService(FileSystemService fileSystemService) {
        this.fileSystemService = fileSystemService;
    }

    public SiteDAO getSiteDAO() {
        return siteDAO;
    }

    public void setSiteDAO(SiteDAO siteDAO) {
        this.siteDAO = siteDAO;
    }

    public ReleaseRegexDAO getReleaseRegexDAO() {
        return releaseRegexDAO;
    }

    public void setReleaseRegexDAO(ReleaseRegexDAO releaseRegexDAO) {
        this.releaseRegexDAO = releaseRegexDAO;
    }

    public GroupDAO getGroupDAO() {
        return groupDAO;
    }

    public void setGroupDAO(GroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }

    public BinaryDAO getBinaryDAO() {
        return binaryDAO;
    }

    public void setBinaryDAO(BinaryDAO binaryDAO) {
        this.binaryDAO = binaryDAO;
    }

    public PartDAO getPartDAO() {
        return partDAO;
    }

    public void setPartDAO(PartDAO partDAO) {
        this.partDAO = partDAO;
    }

    public ReleaseDAO getReleaseDAO() {
        return releaseDAO;
    }

    public void setReleaseDAO(ReleaseDAO releaseDAO) {
        this.releaseDAO = releaseDAO;
    }

    public Nfo getNfo() {
        return nfo;
    }

    public void setNfo(Nfo nfo) {
        this.nfo = nfo;
    }

    public CategoryService getCategoryService() {
        return categoryService;
    }

    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public Nzb getNzb() {
        return nzb;
    }

    public void setNzb(Nzb nzb) {
        this.nzb = nzb;
    }

    public MovieService getMovieService() {
        return movieService;
    }

    public void setMovieService(MovieService movieService) {
        this.movieService = movieService;
    }

    public GameService getGameService() {
        return gameService;
    }

    public void setGameService(GameService gameService) {
        this.gameService = gameService;
    }

    public MusicService getMusicService() {
        return musicService;
    }

    public void setMusicService(MusicService musicService) {
        this.musicService = musicService;
    }

    public TVRageService getTvRageService() {
        return tvRageService;
    }

    public void setTvRageService(TVRageService tvRageService) {
        this.tvRageService = tvRageService;
    }

    public PlatformTransactionManager getTransactionManager() {
        return transactionManager;
    }

    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }
}
