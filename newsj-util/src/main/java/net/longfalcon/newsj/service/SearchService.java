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

package net.longfalcon.newsj.service;

import net.longfalcon.newsj.CategoryService;
import net.longfalcon.newsj.Releases;
import net.longfalcon.newsj.model.Binary;
import net.longfalcon.newsj.model.ConsoleInfo;
import net.longfalcon.newsj.model.Genre;
import net.longfalcon.newsj.model.MovieInfo;
import net.longfalcon.newsj.model.MusicInfo;
import net.longfalcon.newsj.model.Release;
import net.longfalcon.newsj.model.Site;
import net.longfalcon.newsj.model.TvRage;
import net.longfalcon.newsj.model.User;
import net.longfalcon.newsj.persistence.BinaryDAO;
import net.longfalcon.newsj.persistence.ConsoleInfoDAO;
import net.longfalcon.newsj.persistence.GenreDAO;
import net.longfalcon.newsj.persistence.MovieInfoDAO;
import net.longfalcon.newsj.persistence.MusicInfoDAO;
import net.longfalcon.newsj.persistence.SiteDAO;
import net.longfalcon.newsj.persistence.TvRageDAO;
import net.longfalcon.newsj.util.DateUtil;
import net.longfalcon.newsj.util.FormatUtil;
import net.longfalcon.newsj.util.ValidatorUtil;
import net.longfalcon.newsj.ws.atom.Link;
import net.longfalcon.newsj.ws.newznab.NewzNabAttribute;
import net.longfalcon.newsj.ws.newznab.Response;
import net.longfalcon.newsj.ws.rss.Category;
import net.longfalcon.newsj.ws.rss.Enclosure;
import net.longfalcon.newsj.ws.rss.Guid;
import net.longfalcon.newsj.ws.rss.Image;
import net.longfalcon.newsj.ws.rss.ObjectFactory;
import net.longfalcon.newsj.ws.rss.Rss;
import net.longfalcon.newsj.ws.rss.RssChannel;
import net.longfalcon.newsj.ws.rss.RssItem;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBElement;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * wrapper in case we move to a better search impl than the db
 * User: Sten Martinez
 * Date: 3/24/16
 * Time: 1:44 PM
 */
@Service
public class SearchService {

    private Releases releases;
    private BinaryDAO binaryDAO;
    private ConsoleInfoDAO consoleInfoDAO;
    private GenreDAO genreDAO;
    private MovieInfoDAO movieInfoDAO;
    private MusicInfoDAO musicInfoDAO;
    private SiteDAO siteDAO;
    private TvRageDAO tvRageDAO;

    public List<Release> getSearchReleases(String search, List<Integer> categoryIds, int maxAgeDays,
                                           List<Integer> userExCatIds, long groupId, String orderByFieldName, boolean orderByDesc, int offset, int pageSize) {
        String[] searchTokens = splitSearchQueryString(search);
        return releases.getSearchReleases(searchTokens, categoryIds, maxAgeDays, userExCatIds, groupId, orderByFieldName, orderByDesc, offset, pageSize);
    }

    public long getSearchCount(String search, List<Integer> categoryIds, int maxAgeDays, List<Integer> userExCatIds, long groupId) {
        String[] searchTokens = splitSearchQueryString(search);
        return releases.getSearchCount(searchTokens, categoryIds, maxAgeDays, userExCatIds, groupId);
    }

    public List<Binary> searchBinaries(String search, List<Integer> userExCatIds) {
        String[] searchTokens = splitSearchQueryString(search);
        return binaryDAO.searchByNameAndExcludedCats(searchTokens, 1000, userExCatIds);
    }

    /**
     * Search for WS API call
     * @param serverBaseUrl
     * @param search
     * @param categoryIds
     * @param maxAgeDays
     * @param userExCatIds
     * @param groupId
     * @param orderByFieldName
     * @param orderByDesc
     * @param offset
     * @param pageSize
     * @return
     */
    public Rss searchReleasesApi(User user, String serverBaseUrl, String search, List<Integer> categoryIds, int maxAgeDays,
                                 List<Integer> userExCatIds, long groupId, String orderByFieldName, boolean orderByDesc, int offset, int pageSize, boolean extended) {
        List<Release> releaseList;
        long total;
        if (ValidatorUtil.isNotNull(search)) {
            total = getSearchCount(search, categoryIds, maxAgeDays, userExCatIds, groupId);
            releaseList = getSearchReleases(search, categoryIds, maxAgeDays, userExCatIds, groupId, orderByFieldName, orderByDesc, offset, pageSize);
        } else {
            total = releases.getBrowseCount(categoryIds, maxAgeDays, userExCatIds, groupId);
            releaseList = releases.getBrowseReleases(categoryIds, maxAgeDays, userExCatIds, groupId, orderByFieldName, orderByDesc, offset, pageSize);
        }

        return buildSearchResponse(user, serverBaseUrl, offset, extended, releaseList, total);
    }

    public Rss searchTvReleasesApi(User user, String serverBaseUrl, String search, long rageId, String season, String episode, List<Integer> categoryIds, int maxAgeDays,
                                 List<Integer> userExCatIds, long groupId, String orderByFieldName, boolean orderByDesc, int offset, int pageSize, boolean extended) {
        String[] searchTokens = splitSearchQueryString(search);
        List<Release> releaseList;
        long total = releases.getSearchCount(searchTokens, -1, rageId, season, episode, categoryIds, maxAgeDays, userExCatIds, groupId);
        releaseList = releases.getSearchReleases(searchTokens, -1, rageId, season, episode, categoryIds, maxAgeDays, userExCatIds, groupId, orderByFieldName, orderByDesc, offset, pageSize);

        return buildSearchResponse(user, serverBaseUrl, offset, extended, releaseList, total);
    }

    public Rss searchMovieReleasesApi(User user, String serverBaseUrl, String search, long imdbId, List<Integer> categoryIds, int maxAgeDays,
                                   List<Integer> userExCatIds, long groupId, String orderByFieldName, boolean orderByDesc, int offset, int pageSize, boolean extended) {
        String[] searchTokens = splitSearchQueryString(search);
        List<Release> releaseList;
        long total = releases.getSearchCount(searchTokens, imdbId, -1, null, null, categoryIds, maxAgeDays, userExCatIds, groupId);
        releaseList = releases.getSearchReleases(searchTokens, imdbId, -1, null, null, categoryIds, maxAgeDays, userExCatIds, groupId, orderByFieldName, orderByDesc, offset, pageSize);

        return buildSearchResponse(user, serverBaseUrl, offset, extended, releaseList, total);
    }

    public Rss getSingleRelease(User user, String serverBaseUrl, Release release, boolean extended) {
        List<Release> releaseList = new ArrayList<>(1);
        releaseList.add(release);

        return buildSearchResponse(user, serverBaseUrl, 0, extended, releaseList, 1);
    }

    public Rss getRssFeed(User user, String serverBaseUrl, List<Integer> userExCatIds, List<Integer> categoryIds, int limit, boolean download) {
        List<Release> releaseList = releases.getBrowseReleases(categoryIds, -1, userExCatIds, -1, "postDate", true, 0, limit);

        ObjectFactory rssObjectFactory = new ObjectFactory();

        Rss rssRoot = generateRssRoot(serverBaseUrl, siteDAO.getDefaultSite(), true, rssObjectFactory);
        RssChannel rssChannelMain = rssRoot.getChannel();

        List<RssItem> rssItems = rssChannelMain.getItem();
        for (Release releaseItem : releaseList) {
            rssItems.add(createRssItem(releaseItem, user, serverBaseUrl, true, download, true, rssObjectFactory));
        }

        return rssRoot;
    }

    private Rss buildSearchResponse(User user, String serverBaseUrl, int offset, boolean extended, List<Release> releaseList, long total) {
        ObjectFactory rssObjectFactory = new ObjectFactory();

        Rss rssRoot = generateRssRoot(serverBaseUrl, siteDAO.getDefaultSite(), true, rssObjectFactory);
        RssChannel rssChannelMain = rssRoot.getChannel();
        Response response = new Response();
        response.setOffset(offset);
        response.setTotal(total);
        rssChannelMain.setNewznabResponse(response);

        List<RssItem> rssItems = rssChannelMain.getItem();
        for (Release releaseItem : releaseList) {
            rssItems.add(createRssItem(releaseItem, user, serverBaseUrl, extended, true, false, rssObjectFactory));
        }

        return rssRoot;
    }

    private Rss generateRssRoot(String serverBaseUrl, Site site, boolean isApi, ObjectFactory rssObjectFactory) {

        Rss rssRoot = new Rss();
        rssRoot.setVersion(new BigDecimal(2.0));
        RssChannel rssChannelMain = new RssChannel();
        rssRoot.setChannel(rssChannelMain);

        Link atomLink = new Link();
        String path;
        if (isApi) {
            path = "/api";
        } else {
            path = "/rss";
        }
        atomLink.setHref(serverBaseUrl + path);
        atomLink.setRel("self");
        atomLink.setType("application/rss+xml");
        rssChannelMain.setAtomLink(atomLink);

        JAXBElement<String> titleElement = rssObjectFactory.createRssChannelTitle("Newznab");
        String channelDesc;
        if (isApi) {
            channelDesc = "Newznab API Results";
        } else {
            channelDesc = "Newznab Nzb Feed";
        }
        JAXBElement<String> descElement = rssObjectFactory.createRssChannelDescription(channelDesc);
        JAXBElement<String> linkElement = rssObjectFactory.createRssChannelLink(serverBaseUrl);
        List<Object> titleOrLinkOrDescription = rssChannelMain.getTitleOrLinkOrDescription();
        titleOrLinkOrDescription.add(titleElement);
        titleOrLinkOrDescription.add(descElement);
        titleOrLinkOrDescription.add(linkElement);

        Image image = new Image();
        image.setUrl(serverBaseUrl + "/images/banner.jpg");
        image.setTitle(site.getTitle());
        image.setLink(serverBaseUrl);
        image.setDescription("Visit " + site.getTitle() + " - " + site.getStrapLine());
        titleOrLinkOrDescription.add(image);

        return rssRoot;
    }

    private RssItem createRssItem(Release release, User user, String serverBaseUrl, boolean extended, boolean download, boolean rssFeed, ObjectFactory rssObjectFactory) {
        RssItem rssItem = new RssItem();
        String releaseDetailsUrl = serverBaseUrl + "/details/" + release.getGuid();
        String releasePermalink  = serverBaseUrl + "/getnzb/" + release.getGuid() + ".nzb?i=" + user.getId() + "&r=" + user.getRssToken();

        List<Object> titleOrDescOrLink = rssItem.getTitleOrDescriptionOrLink();

        JAXBElement<String> itemTitle = rssObjectFactory.createRssItemTitle(release.getSearchName());
        titleOrDescOrLink.add(itemTitle);

        Guid itemGuidValue = rssObjectFactory.createGuid();
        itemGuidValue.setValue(releaseDetailsUrl);
        itemGuidValue.setIsPermaLink(true);
        JAXBElement<Guid> itemGuid = rssObjectFactory.createRssItemGuid(itemGuidValue);
        titleOrDescOrLink.add(itemGuid);

        JAXBElement<String> itemLink;
        if (download) {
            itemLink = rssObjectFactory.createRssItemLink(releasePermalink);
        } else {
            itemLink = rssObjectFactory.createRssItemLink(releaseDetailsUrl);
        }
        titleOrDescOrLink.add(itemLink);

        JAXBElement<String> itemComments = rssObjectFactory.createRssItemComments(releaseDetailsUrl + "#comments");
        titleOrDescOrLink.add(itemComments);

        JAXBElement<String> itemPubDate = rssObjectFactory.createRssItemPubDate(DateUtil.RFC_822_dateFormatter.print(release.getAddDate().getTime()));
        titleOrDescOrLink.add(itemPubDate);

        Category categoryElement = rssObjectFactory.createCategory();
        categoryElement.setValue(release.getCategoryDisplayName());
        JAXBElement<Category> itemCategory = rssObjectFactory.createRssItemCategory(categoryElement);
        titleOrDescOrLink.add(itemCategory);

        JAXBElement<String> itemDesc;
        if (rssFeed) {
            String description = getRssFeedItemDescription(release, serverBaseUrl);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("<![CDATA[").append(description).append("]]>");
            itemDesc = rssObjectFactory.createRssItemDescription(stringBuilder.toString());
        } else {
            itemDesc = rssObjectFactory.createRssItemDescription(release.getSearchName());
        }
        titleOrDescOrLink.add(itemDesc);

        Enclosure enclosure = rssObjectFactory.createEnclosure();
        if (download) {
            enclosure.setUrl(releasePermalink);
        } else {
            enclosure.setUrl(releaseDetailsUrl);
        }
        enclosure.setLength(BigInteger.valueOf(release.getSize()));
        enclosure.setType("application/x-nzb");
        JAXBElement<Enclosure> itemEncl = rssObjectFactory.createRssItemEnclosure(enclosure);
        titleOrDescOrLink.add(itemEncl);

        List<NewzNabAttribute> newzNabAttributes = rssItem.getAttributes();
        net.longfalcon.newsj.model.Category releaseCategory = release.getCategory();
        Integer releaseCategoryParentId = releaseCategory.getParentId();
        if (ValidatorUtil.isNotNull(releaseCategoryParentId)) {

            NewzNabAttribute nzbAttrCategory1 = new NewzNabAttribute();
            nzbAttrCategory1.setName("category");
            nzbAttrCategory1.setValue(String.valueOf(releaseCategoryParentId));
            newzNabAttributes.add(nzbAttrCategory1);
        }

        NewzNabAttribute nzbAttrCategory2 = new NewzNabAttribute();
        nzbAttrCategory2.setName("category");
        nzbAttrCategory2.setValue(String.valueOf(releaseCategory.getId()));
        newzNabAttributes.add(nzbAttrCategory2);

        NewzNabAttribute newzNabSizeAttribute = createNewzNabAttributeElement("size", release.getSize());
        newzNabAttributes.add(newzNabSizeAttribute);

        if (extended) {
            NewzNabAttribute newzNabFilesAttribute = createNewzNabAttributeElement("files", release.getTotalpart());
            newzNabAttributes.add(newzNabFilesAttribute);
            NewzNabAttribute newzNabPosterAttribute = createNewzNabAttributeElement("poster", release.getFromName());
            newzNabAttributes.add(newzNabPosterAttribute);

            String releaseSeason = release.getSeason();
            if (ValidatorUtil.isNotNull(releaseSeason)) {
                NewzNabAttribute newzNabSeasonAttribute = createNewzNabAttributeElement("season", releaseSeason);
                newzNabAttributes.add(newzNabSeasonAttribute);
            }

            String releaseEpisode = release.getEpisode();
            if (ValidatorUtil.isNotNull(releaseEpisode)) {
                NewzNabAttribute newzNabEpisodeAttribute = createNewzNabAttributeElement("episode", releaseEpisode);
                newzNabAttributes.add(newzNabEpisodeAttribute);
            }

            Long tvRageId = release.getRageId();
            if (ValidatorUtil.isNotNull(tvRageId) && tvRageId > 0) {
                NewzNabAttribute newzNabRageIdAttribute = createNewzNabAttributeElement("rageid", tvRageId);
                newzNabAttributes.add(newzNabRageIdAttribute);
            }

            String releaseTvTitle = release.getTvTitle();
            if (ValidatorUtil.isNotNull(releaseTvTitle)) {
                NewzNabAttribute newzNabTvTitleAttribute = createNewzNabAttributeElement("tvtitle", releaseTvTitle);
                newzNabAttributes.add(newzNabTvTitleAttribute);
            }

            Date releaseTvAirDate = release.getTvAirDate();
            if (releaseTvAirDate != null) {
                NewzNabAttribute newzNabAirDateAttribute = createNewzNabAttributeElement("tvairdate", DateUtil.RFC_822_dateFormatter.print(releaseTvAirDate.getTime()));
                newzNabAttributes.add(newzNabAirDateAttribute);
            }

            Integer releaseImdbId = release.getImdbId();
            if (ValidatorUtil.isNotNull(releaseImdbId)) {
                NewzNabAttribute newzNabImdbIdAttribute = createNewzNabAttributeElement("imdb", "tt" + String.format("%07d", releaseImdbId));
                newzNabAttributes.add(newzNabImdbIdAttribute);
            }

            NewzNabAttribute newzNabGrabsAttribute = createNewzNabAttributeElement("grabs", release.getGrabs());
            newzNabAttributes.add(newzNabGrabsAttribute);
            NewzNabAttribute newzNabCommentsAttribute = createNewzNabAttributeElement("comments", release.getComments());
            newzNabAttributes.add(newzNabCommentsAttribute);
            NewzNabAttribute newzNabPasswordAttribute = createNewzNabAttributeElement("password", release.getPasswordStatus());
            newzNabAttributes.add(newzNabPasswordAttribute);
            NewzNabAttribute newzNabDateAttribute = createNewzNabAttributeElement("usenetdate", DateUtil.RFC_822_dateFormatter.print(release.getPostDate().getTime()));
            newzNabAttributes.add(newzNabDateAttribute);
            NewzNabAttribute newzNabGroupAttribute = createNewzNabAttributeElement("group", release.getGroupName());
            newzNabAttributes.add(newzNabGroupAttribute);
        }

        return rssItem;
    }

    private NewzNabAttribute createNewzNabAttributeElement(String name, Object value) {
        NewzNabAttribute nzbAttrAttribute = new NewzNabAttribute();
        nzbAttrAttribute.setName(name);
        nzbAttrAttribute.setValue(String.valueOf(value));

        return nzbAttrAttribute;
    }

    private String[] splitSearchQueryString(String queryString) {
        if (ValidatorUtil.isNotNull(queryString)) {
            return queryString.split(" ");
        } else {
            return new String[]{};
        }
    }

    // TODO: UGHHHH MOVE THIS TO A VELOCITY TEMPLATE OR SOMETHING, FUCK
    private String getRssFeedItemDescription(Release release, String serverBaseUrl) {

        Long rageId = release.getRageId();
        Integer imdbId = release.getImdbId();
        Integer musicInfoId = release.getMusicInfoId();
        Integer consoleInfoId = release.getConsoleInfoId();

        TvRage tvInfo = null;
        if (rageId != null && rageId > 0) {
            tvInfo = tvRageDAO.findById(rageId);
        }

        MovieInfo movieInfo = null;
        if (imdbId != null && imdbId > 0) {
            movieInfo = movieInfoDAO.findByImdbId(imdbId);
        }

        MusicInfo musicInfo = null;
        if (musicInfoId != null && musicInfoId > 0) {
            musicInfo = musicInfoDAO.findByMusicInfoId(musicInfoId);
        }

        ConsoleInfo gameInfo = null;
        if (consoleInfoId != null && consoleInfoId > 0) {
            gameInfo = consoleInfoDAO.findByConsoleInfoId(consoleInfoId);
        }

        StringBuilder descriptionHtmlFragment = new StringBuilder();

        descriptionHtmlFragment.append("<div>\n");
        if (movieInfo != null && movieInfo.isCover()) {
            descriptionHtmlFragment.append("\t\t<img style=\"margin-left:10px;margin-bottom:10px;float:right;\" src=\"")
                    .append(serverBaseUrl)
                    .append("/images/covers/movies/")
                    .append(movieInfo.getId())
                    .append("-cover.jpg\" width=\"120\" border=\"0\" alt=\"")
                    .append(release.getSearchName()).append("\" />\n");
        }
        if (musicInfo != null && musicInfo.isCover()) {
            descriptionHtmlFragment.append("\t\t<img style=\"margin-left:10px;margin-bottom:10px;float:right;\" src=\"")
                    .append(serverBaseUrl).append("/images/covers/music/").append(musicInfo.getId())
                    .append(".jpg\" width=\"120\" border=\"0\" alt=\"").append(release.getSearchName())
                    .append("\" />\n");
        }

        if (gameInfo != null && gameInfo.isCover()) {
            descriptionHtmlFragment.append("\t\t<img style=\"margin-left:10px;margin-bottom:10px;float:right;\" src=\"")
                    .append(serverBaseUrl).append("/images/covers/console/").append(gameInfo.getId())
                    .append(".jpg\" width=\"120\" border=\"0\" alt=\"").append(release.getSearchName())
                    .append("\" />\n");
        }

        descriptionHtmlFragment.append("\t<ul>\n").append("\t<li>ID: <a href=\"").append(serverBaseUrl).append("details/")
                .append(release.getGuid()).append("\">").append(release.getGuid()).append("</a> (Size: ")
                .append(FormatUtil.formatFileSize(release.getSize(), true)).append(") </li>\n")
                .append("\t<li>Name: ").append(release.getSearchName()).append("</li>\n");

        String categoryName = release.getCategoryDisplayName();

        descriptionHtmlFragment.append("\t<li>Attributes: Category - ").append(categoryName).append("</li>\n").append("\t<li>Groups: ")
                .append(release.getGroupName()).append("</li>\n").append("\t<li>Poster: ").append(release.getFromName()).append("</li>\n")
                .append("\t<li>PostDate: ").append(DateUtil.RFC_822_dateFormatter.print(release.getPostDate().getTime())).append("</li>\n");

        String passwordStatus = "Unknown";
        if (release.getPasswordStatus() == 0) {
            passwordStatus = "None";
        } else if (release.getPasswordStatus() == 1) {
            passwordStatus = "Passworded Rar Archive";
        } else if (release.getPasswordStatus() == 2) {
            passwordStatus = "Contains Cab/Ace Archive";
        }

        descriptionHtmlFragment.append("\t<li>Password: " + passwordStatus + "</li>\n")
                .append("\t\n");
        if (release.getCategory().getParentId() == CategoryService.CAT_PARENT_MOVIE) {
            if (ValidatorUtil.isNotNull(imdbId) && movieInfo != null) {
                descriptionHtmlFragment.append("\t\t<li>Imdb Info: \n")
                        .append("\t\t\t<ul>\n")
                        .append("\t\t\t\t<li>IMDB Link: <a href=\"http://www.imdb.com/title/tt")
                        .append(String.format("%07d", imdbId)).append("/\">").append(release.getSearchName()).append("</a></li>\n");
                if (movieInfo.getRating() != null) {
                    descriptionHtmlFragment.append("\t\t\t\t<li>Rating: " + movieInfo.getRating() + "</li>\n");
                }
                if (movieInfo.getPlot() != null) {
                    descriptionHtmlFragment.append("\t\t\t\t<li>Plot: " + movieInfo.getPlot() + "</li>\n");
                }
                if (movieInfo.getYear() != null) {
                    descriptionHtmlFragment.append("\t\t\t\t<li>Year: " + movieInfo.getYear() +  "/li>\n");
                }
                if (movieInfo.getGenre() != null) {
                    descriptionHtmlFragment.append("\t\t\t\t<li>Genre: " + movieInfo.getGenre() + "</li>\n");
                }
                if (movieInfo.getDirector() != null) {
                    descriptionHtmlFragment.append("\t\t\t\t<li>Director: " + movieInfo.getDirector() +"</li>\n");
                }
                if (movieInfo.getActors() != null) {
                    descriptionHtmlFragment.append("\t\t\t\t<li>Actors: " + movieInfo.getActors() + "</li>\n");
                }
                descriptionHtmlFragment.append("\t\t\t</ul>\n").append("\t\t</li>\n");
            }
        }
        if (release.getCategory().getParentId() == CategoryService.CAT_PARENT_MUSIC) {
            if (musicInfo != null) {
                descriptionHtmlFragment.append("\t\t<li>Music Info: \n").append("\t\t\t<ul>\n");
                if (musicInfo.getUrl() != null) {
                    descriptionHtmlFragment.append("\t\t\t\t<li>Amazon: <a href=\"" + musicInfo.getUrl() + "\">" + musicInfo.getTitle() + "</a></li>\n");
                }
                if (musicInfo.getArtist() != null) {
                    descriptionHtmlFragment.append("\t\t\t\t<li>Artist: " + musicInfo.getArtist() + "</li>\n");
                }
                if (musicInfo.getGenreId() != null) {
                    Genre genre = genreDAO.getGenre(musicInfo.getGenreId());
                    descriptionHtmlFragment.append("\t\t\t\t<li>Genre: " + genre.getTitle() + "</li>\n");
                }
                if (musicInfo.getPublisher() != null) {
                    descriptionHtmlFragment.append("\t\t\t\t<li>Publisher: " + musicInfo.getPublisher() + "</li>\n");
                }
                if (musicInfo.getReleaseDate() != null) {
                    descriptionHtmlFragment.append("\t\t\t\t<li>Released: " + DateUtil.formatDefaultDate(musicInfo.getReleaseDate()) + "</li>\n");
                }
                if (musicInfo.getReview() != null) {
                    descriptionHtmlFragment.append("\t\t\t\t<li>Review: " + musicInfo.getReview() + "</li>\n");
                }
                if (musicInfo.getTracks() != null) {
                    descriptionHtmlFragment.append("\t\t\t\t<li>Track Listing:\n")
                            .append("\t\t\t\t\t<ol>\n");
                    String[] tracks = musicInfo.getTracks().split("|"); // tODO: check if this is how we want to do track listing going forward
                    for (String track : tracks) {
                        descriptionHtmlFragment.append("\t\t\t\t\t\t<li>" + track.trim() + "</li>\n");
                    }
                    descriptionHtmlFragment.append("\t\t\t\t\t</ol>\n").append("\t\t\t\t</li>\t\t\t\t\n");

                }
                descriptionHtmlFragment.append("\t\t\t</ul>\n").append("\t\t</li>\n");
            }
        }
        if (release.getCategory().getParentId() == CategoryService.CAT_PARENT_GAME) {
            if (gameInfo != null) {
                descriptionHtmlFragment.append("\t\t<li>Console Info: \n").append("\t\t\t<ul>\n");
                if (gameInfo.getUrl() != null) {
                    descriptionHtmlFragment.append("\t\t\t\t<li>Amazon: <a href=\"" + gameInfo.getUrl() + "\">" + gameInfo.getTitle() + "</a></li>\n");
                }
                if (gameInfo.getGenreId() != null) {
                    Genre genre = genreDAO.getGenre(gameInfo.getGenreId());
                    descriptionHtmlFragment.append("\t\t\t\t<li>Genre: " + genre.getTitle() + "</li>\n");
                }
                if (gameInfo.getPublisher() != null) {
                    descriptionHtmlFragment.append("\t\t\t\t<li>Publisher: " + gameInfo.getPublisher() + "</li>\n");
                }
                if (gameInfo.getReleaseDate() != null) {
                    descriptionHtmlFragment.append("\t\t\t\t<li>Released: " + DateUtil.formatDefaultDate(gameInfo.getReleaseDate()) + "</li>\n");
                }
                if (gameInfo.getReview() != null) {
                    descriptionHtmlFragment.append("\t\t\t\t<li>Review: " + gameInfo.getReview() + "</li>{/if}\n");
                }
                descriptionHtmlFragment.append("\t\t\t</ul>\n").append("\t\t</li>\n");
            }
        }

        descriptionHtmlFragment.append("\t</ul>\n").append("\t\n")
                .append("\t</div>\n").append("\t<div style=\"clear:both;\">");

        return descriptionHtmlFragment.toString();
    }

    public Releases getReleases() {
        return releases;
    }

    public void setReleases(Releases releases) {
        this.releases = releases;
    }

    public BinaryDAO getBinaryDAO() {
        return binaryDAO;
    }

    public void setBinaryDAO(BinaryDAO binaryDAO) {
        this.binaryDAO = binaryDAO;
    }

    public SiteDAO getSiteDAO() {
        return siteDAO;
    }

    public void setSiteDAO(SiteDAO siteDAO) {
        this.siteDAO = siteDAO;
    }

    public ConsoleInfoDAO getConsoleInfoDAO() {
        return consoleInfoDAO;
    }

    public void setConsoleInfoDAO(ConsoleInfoDAO consoleInfoDAO) {
        this.consoleInfoDAO = consoleInfoDAO;
    }

    public GenreDAO getGenreDAO() {
        return genreDAO;
    }

    public void setGenreDAO(GenreDAO genreDAO) {
        this.genreDAO = genreDAO;
    }

    public MovieInfoDAO getMovieInfoDAO() {
        return movieInfoDAO;
    }

    public void setMovieInfoDAO(MovieInfoDAO movieInfoDAO) {
        this.movieInfoDAO = movieInfoDAO;
    }

    public MusicInfoDAO getMusicInfoDAO() {
        return musicInfoDAO;
    }

    public void setMusicInfoDAO(MusicInfoDAO musicInfoDAO) {
        this.musicInfoDAO = musicInfoDAO;
    }

    public TvRageDAO getTvRageDAO() {
        return tvRageDAO;
    }

    public void setTvRageDAO(TvRageDAO tvRageDAO) {
        this.tvRageDAO = tvRageDAO;
    }
}
