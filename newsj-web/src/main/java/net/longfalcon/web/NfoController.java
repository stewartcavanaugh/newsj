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

import net.longfalcon.newsj.Releases;
import net.longfalcon.newsj.model.Release;
import net.longfalcon.newsj.model.ReleaseNfo;
import net.longfalcon.newsj.persistence.ReleaseNfoDAO;
import net.longfalcon.newsj.util.EncodingUtil;
import net.longfalcon.web.exception.NoSuchResourceException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * User: Sten Martinez
 * Date: 3/28/16
 * Time: 9:32 AM
 */
@Controller
public class NfoController extends BaseController {
    private static final Log _log = LogFactory.getLog(NfoController.class);

    @Autowired
    Releases releases;

    @Autowired
    ReleaseNfoDAO releaseNfoDAO;

    @RequestMapping("/nfo/{releaseGuid}")
    public String displayNfoView(@PathVariable("releaseGuid") String releaseGuid,
                                 @RequestParam(value = "modal", required = false, defaultValue = "false") boolean modal,
                                 Model model) throws NoSuchResourceException {

        title = "NFO File";
        setPageMetaTitle("View Nfo");
        setPageMetaKeywords("view,nzb,nfo,description,details");
        setPageMetaDescription("View Nfo File");

        Release release = releases.findByGuid(releaseGuid);
        if (release == null) {
            throw new NoSuchResourceException();
        }

        ReleaseNfo releaseNfo = releaseNfoDAO.findByReleaseId(release.getId());

        String nfoUTF = EncodingUtil.cp437toUTF(releaseNfo.getNfo());

        model.addAttribute("title", title);
        model.addAttribute("release", release);

        model.addAttribute("nfo", nfoUTF);
        if (modal) {
            return "view-nfo-modal";
        } else {
            return "view-nfo";
        }
    }
}
