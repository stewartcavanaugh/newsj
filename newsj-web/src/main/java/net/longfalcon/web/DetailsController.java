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

package net.longfalcon.web;

import net.longfalcon.SessionKeys;
import net.longfalcon.newsj.Releases;
import net.longfalcon.newsj.model.ConsoleInfo;
import net.longfalcon.newsj.model.MovieInfo;
import net.longfalcon.newsj.model.MusicInfo;
import net.longfalcon.newsj.model.Release;
import net.longfalcon.newsj.model.ReleaseComment;
import net.longfalcon.newsj.model.ReleaseNfo;
import net.longfalcon.newsj.model.TvRage;
import net.longfalcon.newsj.model.User;
import net.longfalcon.newsj.persistence.ConsoleInfoDAO;
import net.longfalcon.newsj.persistence.MovieInfoDAO;
import net.longfalcon.newsj.persistence.MusicInfoDAO;
import net.longfalcon.newsj.persistence.ReleaseCommentDAO;
import net.longfalcon.newsj.persistence.ReleaseDAO;
import net.longfalcon.newsj.persistence.ReleaseNfoDAO;
import net.longfalcon.newsj.persistence.TvRageDAO;
import net.longfalcon.newsj.persistence.UserExCatDAO;
import net.longfalcon.newsj.service.ReleaseCommentService;
import net.longfalcon.newsj.util.ValidatorUtil;
import net.longfalcon.web.exception.NoSuchResourceException;
import net.longfalcon.web.exception.PermissionDeniedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * User: longfalcon
 * Date: 3/21/2016
 * Time: 3:49 PM
 */
@Controller
public class DetailsController extends BaseController {

    @Autowired
    ConsoleInfoDAO consoleInfoDAO;

    @Autowired
    Releases releases;

    @Autowired
    ReleaseDAO releaseDAO;

    @Autowired
    ReleaseNfoDAO releaseNfoDAO;

    @Autowired
    ReleaseCommentDAO releaseCommentDAO;

    @Autowired
    ReleaseCommentService releaseCommentService;

    @Autowired
    TvRageDAO tvRageDAO;

    @Autowired
    MovieInfoDAO movieInfoDAO;

    @Autowired
    MusicInfoDAO musicInfoDAO;

    @Autowired
    UserExCatDAO userExCatDAO;

    @RequestMapping("/details/{guid}/{releaseName}")
    public String detailsView(@PathVariable("guid") String guid,
                              @PathVariable("releaseName") String releaseName,
                              Model model) throws NoSuchResourceException {
        title = "View NZB";
        setPageMetaKeywords("view,nzb,description,details");


        Release release = releases.findByGuid(guid);
        if (release == null) {
            throw new NoSuchResourceException();
        }

        long releaseId = release.getId();
        releaseName = release.getSearchName();
        Long rageId = release.getRageId();
        Integer imdbId = release.getImdbId();
        Integer musicInfoId = release.getMusicInfoId();
        Integer consoleInfoId = release.getConsoleInfoId();

        ReleaseNfo releaseNfo = releaseNfoDAO.findByReleaseId(releaseId);

        List<ReleaseComment> commentsList = releaseCommentDAO.findByReleaseId(releaseId);

        TvRage tvInfo = null;
        if (rageId != null && rageId > 0) {
            tvInfo = tvRageDAO.findByTvRageId(rageId);
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

        List<Integer> categoryIds = userExCatDAO.getUserExCatIds(getUserId());

        // TODO: change this if we change search impl
        List<Release> similars = releases.searchSimilar(release, 6, categoryIds);
        String searchTokens = releases.getReleaseNameSearchTokens(releaseName).stream().collect(Collectors.joining(" ")).trim();

        model.addAttribute("release", release);
        model.addAttribute("nfo", releaseNfo);
        model.addAttribute("tvInfo", tvInfo);
        model.addAttribute("movieInfo", movieInfo);
        model.addAttribute("musicInfo", musicInfo);
        model.addAttribute("gameInfo", gameInfo);
        model.addAttribute("commentsList", commentsList);
        model.addAttribute("similars", similars);
        model.addAttribute("searchTokens", searchTokens);
        setPageMetaDescription("View NZB for " + release.getSearchName());
        return "details";
    }

    @RequestMapping(value = "/details/{guid}/{releaseName}", method = RequestMethod.POST)
    public View detailsPost(@PathVariable("guid") String guid,
                            @PathVariable("releaseName") String releaseName,
                            @RequestParam("txtAddComment") String commentText,
                            HttpSession httpSession,
                            HttpServletRequest httpServletRequest) throws PermissionDeniedException, NoSuchResourceException {

        Release release = releases.findByGuid(guid);
        if (release == null) {
            throw new NoSuchResourceException();
        }

        String userIdString = String.valueOf(httpSession.getAttribute(SessionKeys.USER_ID));

        if (ValidatorUtil.isNotNull(userIdString) && ValidatorUtil.isNumeric(userIdString)) {
            long userId = Long.parseLong(userIdString);
            User user = userDAO.findByUserId(userId);
            if (user != null) {
                //String rssToken = user.getRssToken();
                // int roleId = user.getRole();
                ReleaseComment releaseComment = new ReleaseComment();
                releaseComment.setCreateDate(new Date());
                String host = httpServletRequest.getRemoteHost();
                releaseComment.setHost(host);
                releaseComment.setRelease(release);
                releaseComment.setText(commentText);
                releaseComment.setUser(user);
                releaseCommentService.addReleaseComment(releaseComment);
            } else {
                throw new PermissionDeniedException();
            }
        }

        return safeRedirect("/details/" + guid + "/" + releaseName);
    }
}
