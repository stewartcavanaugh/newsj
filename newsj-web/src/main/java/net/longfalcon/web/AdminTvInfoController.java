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

import net.longfalcon.newsj.model.TvRage;
import net.longfalcon.newsj.persistence.ReleaseDAO;
import net.longfalcon.newsj.persistence.TvRageDAO;
import net.longfalcon.newsj.util.StreamUtil;
import net.longfalcon.newsj.util.ValidatorUtil;
import net.longfalcon.taglib.TextFunctions;
import net.longfalcon.web.exception.NoSuchResourceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.View;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * User: Sten Martinez
 * Date: 3/17/16
 * Time: 4:44 PM
 */
@Controller
@SessionAttributes({"tvRage"})
public class AdminTvInfoController extends BaseController {

    @Autowired
    ReleaseDAO releaseDAO;

    @Autowired
    TvRageDAO tvRageDAO;

    @RequestMapping("/admin/rage-list")
    public String listTvInfoView(@RequestParam(value = "offset", required = false, defaultValue = "0")Integer offset,
                                 @RequestParam(value = "rageSearch", required = false, defaultValue = "")String rageSearch,
                                 Model model) {
        title = "TV Rage List";
        if (offset == null) {
            offset = 0;
        }

        int pagerTotalItems = Math.toIntExact(tvRageDAO.countTvRage());
        List<TvRage> tvRageList;
        String searchUrlFragment = "";
        if (ValidatorUtil.isNotNull(rageSearch)) {
            tvRageList = tvRageDAO.searchTvRage(offset, PAGE_SIZE, rageSearch);
            searchUrlFragment = "rageSearch=" + TextFunctions.urlEncode(rageSearch) + "&";
        } else {
            tvRageList = tvRageDAO.getTvRage(offset, PAGE_SIZE);
        }

        model.addAttribute("title", title);
        model.addAttribute("rageSearch", rageSearch);
        model.addAttribute("searchUrlFragment", searchUrlFragment);
        model.addAttribute("tvRageList", tvRageList);
        model.addAttribute("pagerTotalItems", pagerTotalItems);
        model.addAttribute("pagerOffset", offset);
        model.addAttribute("pagerItemsPerPage", PAGE_SIZE);
        return "admin/rage-list";
    }

    @RequestMapping("/admin/rage-edit")
    public String editTvInfoView(@RequestParam(value = "id", required = false, defaultValue = "0") Long id,
                                 @RequestParam(value = "from", required = false)String from,
                                 Model model) throws NoSuchResourceException {
        TvRage tvRage;
        if (id != null && id > 0) {
            tvRage = tvRageDAO.findByTvRageId(id);
            if (tvRage == null) {
                throw new NoSuchResourceException();
            }
            title = "TV Rage Edit";
        } else {
            tvRage = new TvRage();
            title = "Add/Edit TV Rage Show Data";
        }



        setPageMetaTitle("Add/Edit TV Rage Show Data");
        model.addAttribute("title", title);
        model.addAttribute("tvRage", tvRage);
        model.addAttribute("from", from);
        return "admin/rage-edit";
    }

    @RequestMapping(value = "/admin/rage-edit", method = RequestMethod.POST)
    public View editTvInfoPost(@ModelAttribute("tvRage") TvRage tvRage,
                               @RequestParam("imagedata") MultipartFile imageData,
                               @RequestParam(value = "from", required = false) String from,
                               Model model) throws IOException {
        InputStream imageStream = null;

        if (imageData != null && !imageData.isEmpty()) {
            imageStream = imageData.getInputStream();
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        StreamUtil.transferByteArray(imageStream, byteArrayOutputStream, 1024);
        tvRage.setImgData(byteArrayOutputStream.toByteArray());

        tvRageDAO.update(tvRage);

        if (ValidatorUtil.isNull(from)) {
            return safeRedirect("/admin/rage-list");
        } else {
            return safeRedirect(from);
        }
    }

    @RequestMapping(value = "/admin/rage-delete", method = RequestMethod.POST)
    public View deleteTvInfoPost(@RequestParam("id") long id) throws NoSuchResourceException {
        TvRage tvRage = tvRageDAO.findByTvRageId(id);
        if (tvRage == null) {
            throw new NoSuchResourceException();
        }

        tvRageDAO.delete(tvRage);

        return safeRedirect("/admin/rage-list");
    }

    @RequestMapping(value = "/admin/rage-remove", method = RequestMethod.POST)
    public View removeTvInfoPost(@RequestParam("id") long id) throws NoSuchResourceException {
        TvRage tvRage = tvRageDAO.findByTvRageId(id);
        if (tvRage == null) {
            throw new NoSuchResourceException();
        }

        releaseDAO.resetReleaseTvRageId(id);

        return safeRedirect("/admin/rage-list");
    }
}
