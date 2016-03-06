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
import net.longfalcon.newsj.model.Binary;
import net.longfalcon.newsj.model.Category;
import net.longfalcon.newsj.model.Release;
import net.longfalcon.newsj.persistence.BinaryDAO;
import net.longfalcon.newsj.persistence.ReleaseDAO;
import net.longfalcon.newsj.util.ValidatorUtil;
import net.longfalcon.web.exception.NoSuchResourceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Sten Martinez
 * Date: 3/4/16
 * Time: 2:18 PM
 */
@Controller
@SessionAttributes({"release"})
public class AdminReleaseController extends BaseController {

    @Autowired
    BinaryDAO binaryDAO;

    @Autowired
    ReleaseDAO releaseDAO;

    @Autowired
    Releases releases;

    @RequestMapping("/admin/release-list")
    public String listReleaseView(@RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset, Model model) {
        title = "Release List";
        setPageMetaTitle(title);

        List<Release> releaseList = releaseDAO.getReleases(offset, PAGE_SIZE);

        int pagerTotalItems = Math.toIntExact(releaseDAO.getReleasesCount());

        model.addAttribute("title", title);
        model.addAttribute("releaseList", releaseList);
        model.addAttribute("pagerTotalItems", pagerTotalItems);
        model.addAttribute("pagerOffset", offset);
        model.addAttribute("pagerItemsPerPage", PAGE_SIZE);

        return "admin/release-list";
    }

    @RequestMapping("/admin/release-edit")
    public String editReleaseView(@RequestParam(value = "id")Long id, Model model) throws NoSuchResourceException {
        title = "Edit Release";
        setPageMetaTitle(title);
        Release release = releases.findByReleaseId(id);
        if (release == null) {
            throw new NoSuchResourceException();
        }

        List<Category> categories = categoryService.getCategories();
        Map<Integer,String> categoriesMap = new HashMap<>();
        for (Category category : categories) {
            // TODO: replace with hbm mapping for parentCategory
            Category parentCategory = categoryService.getCategory(category.getParentId());
            categoriesMap.put(category.getId(), parentCategory.getTitle() + " > " + category.getTitle());
        }

        model.addAttribute("title", title);
        model.addAttribute("release", release);
        model.addAttribute("categoriesMap", categoriesMap);
        return "admin/release-edit";
    }

    @RequestMapping(value = "/admin/release-edit", method = RequestMethod.POST)
    public String editReleasePost(@ModelAttribute("release")Release release, Model model) throws NoSuchResourceException {
        releases.updateRelease(release);

        return editReleaseView(release.getId(), model);
    }

    @RequestMapping(value = "/admin/release-delete", method = RequestMethod.POST)
    public String deleteReleasePost(@RequestParam(value = "id")Long id, Model model) throws NoSuchResourceException {
        Release release = releases.findByReleaseId(id);
        if (release == null) {
            throw new NoSuchResourceException();
        }
        releaseDAO.deleteRelease(release);
        Integer offset = (Integer) model.asMap().get("pagerOffset");
        if (!ValidatorUtil.isNotNull(offset)) {
            offset = 0;
        }
        model.asMap().clear();

        return listReleaseView(offset, model);
    }

    @RequestMapping("/admin/release-files")
    public String listReleaseFilesView(@RequestParam(value = "guid")String guid, Model model) throws NoSuchResourceException {
        Release release = releases.findByGuid(guid);
        if (release == null) {
            throw new NoSuchResourceException();
        }
        List<Binary> binaries = binaryDAO.findBinariesByReleaseId(release.getId());

        title = "File List";
        setPageMetaTitle("View Nzb file list");
        setPageMetaKeywords("view,nzb,file,list,description,details");
        setPageMetaDescription("View Nzb File List");

        model.addAttribute("title", title);
        model.addAttribute("release", release);
        model.addAttribute("binaries", binaries);
        return "admin/release-files";
    }

    @RequestMapping(value = "/admin/release-rebuild", method = RequestMethod.POST)
    public String rebuildReleasePost(@RequestParam(value = "id")Long id, Model model) throws NoSuchResourceException {
        Release release = releases.findByReleaseId(id);
        if (release == null) {
            throw new NoSuchResourceException();
        }

        releases.resetRelease(release.getId());
        Integer offset = (Integer) model.asMap().get("pagerOffset");
        if (!ValidatorUtil.isNotNull(offset)) {
            offset = 0;
        }
        model.asMap().clear();

        return listReleaseView(offset, model);
    }
}
