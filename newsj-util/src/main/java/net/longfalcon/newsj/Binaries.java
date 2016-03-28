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

import net.longfalcon.newsj.model.Group;
import net.longfalcon.newsj.model.Part;
import net.longfalcon.newsj.model.PartRepair;
import net.longfalcon.newsj.model.Site;
import net.longfalcon.newsj.nntp.NntpConnectionFactory;
import net.longfalcon.newsj.nntp.client.NewsClient;
import net.longfalcon.newsj.nntp.client.NewsgroupInfo;
import net.longfalcon.newsj.persistence.BinaryDAO;
import net.longfalcon.newsj.persistence.GroupDAO;
import net.longfalcon.newsj.persistence.PartDAO;
import net.longfalcon.newsj.persistence.PartRepairDAO;
import net.longfalcon.newsj.persistence.SiteDAO;
import net.longfalcon.newsj.util.ArrayUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormat;
import org.joda.time.format.PeriodFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Sten Martinez
 * Date: 10/5/15
 * Time: 5:07 PM
 */
@Service
public class Binaries {

    private static PeriodFormatter _periodFormatter = PeriodFormat.wordBased();
    private static final Log _log = LogFactory.getLog(Binaries.class);

    private Backfill backfill;
    private Blacklist blacklist;
    private FetchBinaries fetchBinaries;

    @Autowired
    private GroupDAO groupDAO;

    @Autowired
    private SiteDAO siteDAO;

    private BinaryDAO binaryDAO;

    private PartDAO partDAO;

    private PartRepairDAO partRepairDAO;

    private PlatformTransactionManager transactionManager;

    @Autowired
    private NntpConnectionFactory nntpConnectionFactory;

    private boolean newGroupScanByDays = false;
    private boolean compressedHeaders;
    private int messageBuffer = 20000;
    private int newGroupMsgsToScan = 50000;
    private int newGroupDaysToScan = 3;

    public void updateAllGroups() {
        // get all groups
        Collection<Group> groups = groupDAO.getActiveGroups();

        // get site config TODO: use config service
        Site site = siteDAO.getDefaultSite();

        newGroupScanByDays = site.getNewGroupsScanMethod() == 1; // TODO: create constants/enum
        compressedHeaders = site.isCompressedHeaders();
        messageBuffer = site.getMaxMessages();
        newGroupDaysToScan = site.getNewGroupDaysToScan();
        newGroupMsgsToScan = site.getNewGroupMsgsToScan();

        if (!groups.isEmpty()) {
            long startTime = System.currentTimeMillis();

            System.out.printf("Updating: %s groups - Using compression? %s%n", groups.size(), compressedHeaders ? "Yes" : "No");

            //get nntp client
            NewsClient nntpClient = nntpConnectionFactory.getNntpClient();
            if (nntpClient == null) {
                throw new RuntimeException("Connection error. Please check NNTP settings and try again.");
            }

            for (Group group : groups) {
                updateGroup(nntpClient, group);
                if (nntpClient == null) {
                    nntpClient = nntpConnectionFactory.getNntpClient();
                } else if (!nntpClient.isConnected() || !nntpClient.isAvailable()) {
                    try {
                        nntpClient.disconnect();
                    } catch (IOException e) {
                        _log.error(e.toString(), e);
                    }
                    nntpClient = nntpConnectionFactory.getNntpClient();
                }
            }
            try {
                nntpClient.logout();
                nntpClient.disconnect();
            } catch (IOException e) {
                _log.error(e.toString(), e);
            }
            long endTime = System.currentTimeMillis();

            String duration = _periodFormatter.print(new Period(endTime - startTime));
            System.out.println("Updating completed in " + duration);
        } else {
            System.out.println("No groups specified. Ensure groups are added to newznab's database for updating.");
        }
    }

    public void updateGroup(NewsClient nntpClient, Group group) {
        /*
        * */
        System.out.println("Processing  " + group.getName());
        try {
            NewsgroupInfo newsgroupInfo = new NewsgroupInfo();
            boolean exists = nntpClient.selectNewsgroup(group.getName(), newsgroupInfo);
            if (!exists) {
                System.out.println("Could not select group (bad name?): " + group.getName());
                return;
            }

            //Attempt to repair any missing parts before grabbing new ones
            partRepair(nntpClient, group);

            //Get first and last part numbers from newsgroup
            long firstArticle = newsgroupInfo.getFirstArticleLong();
            long lastArticle = newsgroupInfo.getLastArticleLong();
            long groupLastArticle = lastArticle; // this is to hold the true last article in the group

            // For new newsgroups - determine here how far you want to go back.
            if (group.getLastRecord() == 0) {
                if (newGroupScanByDays) {
                    firstArticle = backfill.dayToPost(nntpClient, group, newGroupDaysToScan, true);
                    if ( firstArticle == 0) {
                        _log.warn("Skipping group: " + group.getName());
                        return;
                    }
                } else {
                    if (firstArticle > (lastArticle - newGroupMsgsToScan)) {
                        firstArticle = firstArticle; //????
                    } else {
                        firstArticle = lastArticle - newGroupMsgsToScan;
                    }
                }
                DateTime firstRecordPostDate = backfill.postDate(nntpClient, firstArticle, false);
                // update group record
                group.setFirstRecord(firstArticle);
                group.setFirstRecordPostdate(firstRecordPostDate.toDate());
                updateGroupModel(group);
            } else {
                firstArticle = group.getLastRecord() + 1;
            }

            // Generate postdates for first and last records, for those that upgraded
            if ( (group.getFirstRecordPostdate() == null || group.getLastRecordPostdate() == null) && (group.getLastRecord() != 0 && group.getFirstRecord() != 0)) {
                DateTime groupFirstPostDate = backfill.postDate(nntpClient, group.getFirstRecord(), false);
                group.setFirstRecordPostdate(groupFirstPostDate.toDate());
                DateTime groupLastPostDate = backfill.postDate(nntpClient, group.getLastRecord(), false);
                group.setLastRecordPostdate(groupLastPostDate.toDate());
                updateGroupModel(group);
            }

            // Deactivate empty groups
            if ((lastArticle - firstArticle) <= 5) {
                group.setActive(false);
                group.setLastUpdated(new Date());
                updateGroupModel(group);
            }

            // Calculate total number of parts
            long totalParts = groupLastArticle - firstArticle + 1;

            // If total is bigger than 0 it means we have new parts in the newsgroup
            if(totalParts > 0) {
                _log.info(String.format("Group %s has %d new parts.", group.getName(), totalParts));
                _log.info(String.format("First: %d Last: %d Local last: %d", firstArticle, lastArticle, group.getLastRecord()));
                if (group.getLastRecord() == 0) {
                    _log.info("New group starting with " + (newGroupScanByDays ? newGroupDaysToScan + " days" : newGroupMsgsToScan + " messages") + " worth.");
                }

                boolean done = false;

                long startLoopTime = System.currentTimeMillis();
                while (!done) {

                    if (totalParts > messageBuffer) {
                        if (firstArticle + messageBuffer > groupLastArticle) {
                            lastArticle = groupLastArticle;
                        } else {
                            lastArticle = firstArticle + messageBuffer;
                        }
                    }

                    _log.info(String.format("Getting %d parts (%d to %d) - %d in queue", lastArticle - firstArticle + 1, firstArticle, lastArticle, groupLastArticle - lastArticle));

                    //get headers from newsgroup
                    long lastId = 0;
                    try {
                        lastId = fetchBinaries.scan(nntpClient, group, firstArticle, lastArticle, "update", compressedHeaders); // magic string
                    } catch (Exception e) {
                        _log.error(e.toString(), e);
                    }
                    if (lastId == 0) {
                        // scan failed - skip group
                        return;
                    }

                    group.setLastRecord(lastArticle);
                    group.setLastUpdated(new Date());
                    updateGroupModel(group);

                    if (lastArticle == groupLastArticle) {
                        done = true;
                    } else {
                        lastArticle = lastId;
                        firstArticle = lastArticle + 1;
                    }

                }

                DateTime lastRecordPostDate = backfill.postDate(nntpClient, lastArticle, false);
                group.setLastRecordPostdate(lastRecordPostDate.toDate());
                group.setLastUpdated(new Date());
                updateGroupModel(group);
                Period loopTime = new Period(startLoopTime, System.currentTimeMillis());
                _log.info(String.format("Group processed in %s seconds", _periodFormatter.print(loopTime)));
            }
        } catch (IOException e) {
            _log.error(e.toString(), e);
        }

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private void partRepair(NewsClient nntpClient, Group group) throws IOException {
        List<PartRepair> partRepairList = partRepairDAO.findByGroupIdAndAttempts(group.getId(), 5, true);
        long partsRepaired = 0;
        long partsFailed = 0;

        int partRepairListSize = partRepairList.size();
        if (partRepairListSize > 0) {
            _log.info("Attempting to repair " + partRepairListSize + " parts...");

            //loop through each part to group into ranges
            Map<Long,Long> ranges = new LinkedHashMap<>();
            long lastNum = 0;
            long lastPart = 0;
            for(PartRepair partRepair : partRepairList) {
                long partRepairNumberId = partRepair.getNumberId();
                if ((lastNum+1) == partRepairNumberId) {
                    ranges.put(lastPart, partRepairNumberId);
                } else {
                    lastPart = partRepairNumberId;
                    ranges.put(lastPart, partRepairNumberId);
                }
                lastNum = partRepairNumberId;
            }

            //download missing parts in ranges
            long startLoopTime = System.currentTimeMillis();
            for(Map.Entry<Long,Long> entry : ranges.entrySet()) {

                long partFrom = entry.getKey();
                long partTo = entry.getValue();

                _log.info("repairing " + partFrom + " to " + partTo );

                //get article from newsgroup
                fetchBinaries.scan(nntpClient, group, partFrom, partTo, "partrepair", compressedHeaders);

                //check if the articles were added
                List<Long> articlesRange = ArrayUtil.rangeList(partFrom, partTo);
                // This complete clusterf*ck is due to the Part table lacking a groupId column.
                // TODO: add a groupId column to Part table!!!!
                List<PartRepair> partRepairs = partRepairDAO.findByGroupIdAndNumbers(group.getId(), articlesRange);
                List<Long> groupBinaryIds = binaryDAO.findBinaryIdsByGroupId(group.getId());
                for (PartRepair partRepair : partRepairs) {
                    List<Part> partList = partDAO.findByNumberAndBinaryIds(partRepair.getNumberId(), groupBinaryIds);
                    Part part = partList.isEmpty() ? null : partList.get(0);
                    if (part != null && partRepair.getNumberId() == part.getNumber()) {
                        partsRepaired++;

                        //article was added, delete from partrepair
                        // May need to be stored for later to prevent modification
                        partRepairDAO.deletePartRepair(partRepair);
                    } else {
                        partsFailed++;

                        //article was not added, increment attempts
                        int attempts = partRepair.getAttempts();
                        partRepair.setAttempts(attempts+1);
                        partRepairDAO.updatePartRepair(partRepair);
                    }
                }
            }
            Period repairLoopTime = new Period(startLoopTime, System.currentTimeMillis());
            _log.info(partsRepaired + " parts repaired.");
            _log.info("repair took " + _periodFormatter.print(repairLoopTime));
        }

        //remove articles that we cant fetch after 5 attempts
        //change to HQL Delete?
        List<PartRepair> partRepairsToDelete = partRepairDAO.findByGroupIdAndAttempts(group.getId(), 5, false);
        for (PartRepair partRepair : partRepairsToDelete) {
            partRepairDAO.deletePartRepair(partRepair);
        }
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.NOT_SUPPORTED)
    public void updateGroupModel(Group group) {
        groupDAO.update(group);
    }

    public GroupDAO getGroupDAO() {
        return groupDAO;
    }

    public void setGroupDAO(GroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }

    public SiteDAO getSiteDAO() {
        return siteDAO;
    }

    public void setSiteDAO(SiteDAO siteDAO) {
        this.siteDAO = siteDAO;
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

    public PartRepairDAO getPartRepairDAO() {
        return partRepairDAO;
    }

    public void setPartRepairDAO(PartRepairDAO partRepairDAO) {
        this.partRepairDAO = partRepairDAO;
    }

    public NntpConnectionFactory getNntpConnectionFactory() {
        return nntpConnectionFactory;
    }

    public void setNntpConnectionFactory(NntpConnectionFactory nntpConnectionFactory) {
        this.nntpConnectionFactory = nntpConnectionFactory;
    }

    public Backfill getBackfill() {
        return backfill;
    }

    public void setBackfill(Backfill backfill) {
        this.backfill = backfill;
    }

    public PlatformTransactionManager getTransactionManager() {
        return transactionManager;
    }

    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public Blacklist getBlacklist() {
        return blacklist;
    }

    public void setBlacklist(Blacklist blacklist) {
        this.blacklist = blacklist;
    }

    public FetchBinaries getFetchBinaries() {
        return fetchBinaries;
    }

    public void setFetchBinaries(FetchBinaries fetchBinaries) {
        this.fetchBinaries = fetchBinaries;
    }
}
