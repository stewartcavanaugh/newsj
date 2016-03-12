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

import net.longfalcon.newsj.CategoryService;
import net.longfalcon.newsj.Releases;
import net.longfalcon.newsj.model.Binary;
import net.longfalcon.newsj.model.Category;
import net.longfalcon.newsj.model.Group;
import net.longfalcon.newsj.model.ReleaseRegex;
import net.longfalcon.newsj.persistence.BinaryDAO;
import net.longfalcon.newsj.persistence.GroupDAO;
import net.longfalcon.newsj.persistence.ReleaseRegexDAO;
import net.longfalcon.newsj.util.ArrayUtil;
import net.longfalcon.newsj.util.Defaults;
import net.longfalcon.newsj.util.ValidatorUtil;
import net.longfalcon.web.exception.NoSuchResourceException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: Sten Martinez
 * Date: 3/11/16
 * Time: 8:14 AM
 */
@Controller
@SessionAttributes("releaseRegex")
public class AdminRegexController extends BaseController {

    @Autowired
    Releases releases;

    @Autowired
    ReleaseRegexDAO releaseRegexDAO;

    @Autowired
    GroupDAO groupDAO;

    @Autowired
    BinaryDAO binaryDAO;

    @Autowired
    CategoryService categoryService;

    private static final Log _log = LogFactory.getLog(AdminRegexController.class);

    @RequestMapping("/admin/regex-list")
    public String listRegexView(@RequestParam(value = "group", required = false, defaultValue = "-1")String groupName, Model model) {
        title = "Release Regex List";

        List<ReleaseRegex> releaseRegexList = releases.getRegexesWithStatistics(false, groupName, true);
        List<String> groupNameList = groupDAO.getGroupsForSelect();

        model.addAttribute("title", title);
        model.addAttribute("releaseRegexList", releaseRegexList);
        model.addAttribute("groupNameList", groupNameList);
        model.addAttribute("groupName", groupName);
        return "admin/regex-list";
    }

    @RequestMapping("/admin/regex-edit")
    public String editRegexView(@RequestParam(value = "id", required = false, defaultValue = "0")Long id,
                                @RequestParam(value = "regex", required = false)String regex,
                                @RequestParam(value = "groupId", required = false)long groupId,
                                Model model) throws NoSuchResourceException {
        ReleaseRegex releaseRegex;
        if (id != null && id > 0) {
            title = "Release Regex Edit";
            releaseRegex = releaseRegexDAO.findById(id);
            if (releaseRegex == null) {
                throw new NoSuchResourceException();
            }
        } else {
            title = "Release Regex Add";
            releaseRegex = new ReleaseRegex();
            if (ValidatorUtil.isNotNull(regex)) {
                releaseRegex.setRegex(regex);
            }
            if (ValidatorUtil.isNotNull(groupId)) {
                Group group = groupDAO.findGroupByGroupId(groupId);
                if (group != null) {
                    releaseRegex.setGroupName(group.getName());
                }
            }
        }

        List<Category> categories = categoryService.getChildCategories();
        Map<Integer,String> categoriesMap = new HashMap<>();
        for (Category category : categories) {
            // TODO: replace with hbm mapping for parentCategory
            Category parentCategory = categoryService.getCategory(category.getParentId());
            categoriesMap.put(category.getId(), parentCategory.getTitle() + " > " + category.getTitle());
        }

        Map<Integer, String> statusMap = new HashMap<>();
        statusMap.put(CategoryService.STATUS_ACTIVE, "Yes");
        statusMap.put(CategoryService.STATUS_INACTIVE, "No");

        model.addAttribute("title", title);
        model.addAttribute("releaseRegex", releaseRegex);
        model.addAttribute("statusMap", statusMap);
        model.addAttribute("categoriesMap", categoriesMap);
        return "admin/regex-edit";
    }

    @RequestMapping(value = "/admin/regex-edit", method = RequestMethod.POST)
    public View editRegexPost(@ModelAttribute("releaseRegex")ReleaseRegex releaseRegex, Model model) {

        releaseRegexDAO.updateReleaseRegex(releaseRegex);

        return safeRedirect("/admin/regex-edit?id=" + releaseRegex.getId());
    }

    @RequestMapping("/admin/regex-test")
    public String testRegexView(@RequestParam(value = "action", required = false, defaultValue = "view")String action,
                                @RequestParam(value = "regex", required = false)String regex,
                                @RequestParam(value = "groupId", required = false)Long groupId,
                                @RequestParam(value = "groupName", required = false)String groupName,
                                @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
                                @RequestParam(defaultValue = "false") boolean unreleased,
                                Model model) {
        title = "Release Regex Test";

        if (ValidatorUtil.isNull(regex)) {
            regex = "/^(?P<name>.*)$/i";
        }

        if (ValidatorUtil.isNull(groupId)) {
            groupId = -1L;
        }

        if (ValidatorUtil.isNotNull(groupName)) {
            Group group = groupDAO.getGroupByName(groupName);
            if (group != null) {
                groupId = group.getId();
            }
        }

        List<Group> groupList = groupDAO.getActiveGroups();
        List<Binary> matchesList = new ArrayList<>();
        Map<String,Binary> matchesMap = new HashMap<>();
        int pagerTotalItems = 0;

        if (action != null && action.equals("submit")) {
            String realRegex = fixRegex(regex);
            List<Integer> procstats = Arrays.asList(Defaults.PROCSTAT_NEW, Defaults.PROCSTAT_READYTORELEASE, Defaults.PROCSTAT_WRONGPARTS);
            if (unreleased) {
                matchesList = binaryDAO.findByGroupIdProcStatsReleaseId(groupId, procstats, null);
            } else {
                matchesList = binaryDAO.findByGroupIdProcStatsReleaseId(groupId, null, 0L);
            }

            for (Binary binary : matchesList) {
                Pattern pattern = Pattern.compile(fixRegex(regex), Pattern.CASE_INSENSITIVE); // remove '/' and '/i'
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
                        _log.debug(String.format("regex applied which didnt return right number of capture groups - %s", regex));
                        _log.debug(String.format("regex matched: reqId = %s parts = %s and name = %s", reqIdGroup, partsGroup, nameGroup));
                        continue;
                    }

                    int relTotalPart = 0;
                    if (ValidatorUtil.isNotNull(partsGroup)) {
                        String partsStrings[] = partsGroup.split("/");
                        int relpart = Integer.parseInt(partsStrings[0]);
                        relTotalPart = Integer.parseInt(partsStrings[1]);
                    }

                    binary.setRelName(nameGroup);
                    binary.setReqId(Integer.valueOf(reqIdGroup));
                    binary.setRelPart(relTotalPart);

                    if (!matchesMap.containsKey(nameGroup)) {
                        binary.setRelTotalPart(1);
                        int categoryId = categoryService.determineCategory(groupId,nameGroup);
                        binary.setCategoryName(categoryService.getCategoryDisplayName(categoryId));
                        binary.setCategoryId(categoryId);
                        matchesMap.put(nameGroup, binary);
                    } else {
                        Binary prevMatch = matchesMap.get(nameGroup);
                        int count = prevMatch.getRelTotalPart();
                        prevMatch.setRelTotalPart(count + 1);
                    }
                }
            }
            List<Binary> valuesList = new ArrayList<>(matchesMap.values());
            pagerTotalItems = valuesList.size();
            matchesList = ArrayUtil.paginate(valuesList, offset, PAGE_SIZE);
        }

        model.addAttribute("title", title);
        model.addAttribute("regex", regex);
        model.addAttribute("groupId", groupId);
        model.addAttribute("groupList", groupList);
        model.addAttribute("unreleased", unreleased);
        model.addAttribute("matchesList", matchesList);
        model.addAttribute("pagerTotalItems", pagerTotalItems);
        model.addAttribute("pagerOffset", offset);
        model.addAttribute("pagerItemsPerPage", PAGE_SIZE);
        return "admin/regex-test";
    }
    /*@RequestMapping("/admin/regex-test")
    public String testRegexSubmit(Model model) {
        title = "Release Regex List";

        model.addAttribute("title", title);
        return "admin/regex-test";
    }*/

    // See: Releases
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
}
