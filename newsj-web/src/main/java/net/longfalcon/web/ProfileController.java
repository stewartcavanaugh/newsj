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
import net.longfalcon.newsj.model.Category;
import net.longfalcon.newsj.model.ReleaseComment;
import net.longfalcon.newsj.model.User;
import net.longfalcon.newsj.persistence.CategoryDAO;
import net.longfalcon.newsj.persistence.ReleaseCommentDAO;
import net.longfalcon.newsj.persistence.UserDAO;
import net.longfalcon.newsj.persistence.UserExCatDAO;
import net.longfalcon.newsj.util.EncodingUtil;
import net.longfalcon.newsj.util.ValidatorUtil;
import net.longfalcon.view.CategoryVO;
import net.longfalcon.view.ProfileVO;
import net.longfalcon.web.auth.PasswordHash;
import org.apache.commons.lang3.StringEscapeUtils;
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

import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * User: Sten Martinez
 * Date: 6/30/16
 * Time: 2:32 PM
 */
@Controller
@SessionAttributes({"profile"})
public class ProfileController extends BaseController {
    private static final Log _log = LogFactory.getLog(ProfileController.class);

    @Autowired
    CategoryService categoryService;

    @Autowired
    CategoryDAO categoryDAO;

    @Autowired
    ReleaseCommentDAO releaseCommentDAO;

    @Autowired
    UserDAO userDAO;

    @Autowired
    UserExCatDAO userExCatDAO;

    @RequestMapping("/profile")
    public String profileView(@RequestParam(value = "name", required = false)String username,
                              @RequestParam(value = "id", required = false)Long userId,
                              @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
                              Model model) {
        User user;
        if (ValidatorUtil.isNotNull(userId)) {
            user = userDAO.findByUserId(userId);
        } else if (ValidatorUtil.isNotNull(username)) {
            user = userDAO.findByUsername(username);
        } else {
            user = userDAO.findByUserId(getUserId());
        }
        userId = user.getId();

        User invitedByUser = null;
        Long invitedByUserId = user.getInvitedBy();
        if (invitedByUserId != null && invitedByUserId > 0) {
            invitedByUser = userDAO.findByUserId(invitedByUserId);
        }

        List<Integer> exCatIds = userExCatDAO.getUserExCatIds(userId);
        List<String> exCatNames = categoryService.getCategoryDisplayNames(exCatIds);

        long pagerTotalItems = releaseCommentDAO.countReleaseCommentsByUser(userId);
        List<ReleaseComment> userReleaseComments = releaseCommentDAO.getReleaseCommentsByUser(userId, offset, PAGE_SIZE);

        model.addAttribute("user", user);
        model.addAttribute("invitedByUser", invitedByUser);
        model.addAttribute("exCatNames", exCatNames);
        model.addAttribute("pagerTotalItems", pagerTotalItems);
        model.addAttribute("pagerOffset", offset);
        model.addAttribute("pagerItemsPerPage", PAGE_SIZE);
        model.addAttribute("userReleaseComments", userReleaseComments);
        model.addAttribute("pageMetaTitle",  "View User Profile");
        model.addAttribute("pageMetaKeywords", "view,profile,user,details");
        model.addAttribute("pageMetaDescription", "View User Profile for " + user.getUsername());
        return "profile";
    }

    @RequestMapping("/profileedit")
    public String profileEditView(Model model) {
        User user = userDAO.findByUserId(getUserId());
        ProfileVO profileVO = new ProfileVO(user);
        profileVO.setExCatIds(userExCatDAO.getUserExCatIds(user.getId()));

        List<Category> categoryList = categoryDAO.getAllCategories(false);
        List<CategoryVO> categoryVOList = new ArrayList<>(categoryList.size());

        for(Category category : categoryList) {
            String parentTitle;
            if (category.getParentId() != null && category.getParentId() > 0) {
                Category parentCategory = categoryService.getCategory(category.getParentId());
                parentTitle = parentCategory.getTitle();
                CategoryVO categoryVO = new CategoryVO(category.getId(), category.getTitle(), parentTitle,
                        category.getStatus() == CategoryService.STATUS_ACTIVE);
                categoryVOList.add(categoryVO);
            }
        }


        model.addAttribute("user", user);
        model.addAttribute("profile", profileVO);
        model.addAttribute("categoryVOList", categoryVOList);
        model.addAttribute("pageMetaTitle", "Edit User Profile");
        model.addAttribute("pageMetaKeywords", "edit,profile,user,details");
        model.addAttribute("pageMetaDescription", "Edit User Profile for " + user.getUsername());
        return "profile-edit";
    }

    @RequestMapping(value = "/profileedit", method = RequestMethod.POST)
    public View profileEditPost(@ModelAttribute("profile")ProfileVO profileVO, HttpSession httpSession, Model model) {
        String error = "";
        User user = userDAO.findByUserId(profileVO.getUserId());
        String email = profileVO.getEmail();
        if (ValidatorUtil.isValidEmail(email)) {
            if (userDAO.findByEmail(email) == null) {
                user.setEmail(email);
            } else {
                error += "Email address " + StringEscapeUtils.escapeHtml4(email) + " is taken or unavailable<br/>";
            }
        } else {
            error += "Email address " + StringEscapeUtils.escapeHtml4(email) + " is invalid<br/>";
        }

        String newPassword = profileVO.getPassword();
        String newPasswordConfirm = profileVO.getConfirmPassword();
        if (ValidatorUtil.isNotNull(newPassword)) {
            if (newPassword.equals(newPasswordConfirm)) {
                try {
                    user.setPassword(PasswordHash.createHash(newPassword));
                } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                    _log.error(e);
                }
            } else {
                error += "Password Mismatch<br/>";
            }
        }
        user.setMovieView(profileVO.isMovieView() ? 1 : 0);
        user.setMusicView(profileVO.isMusicView() ? 1 : 0);
        user.setConsoleView(profileVO.isConsoleView() ? 1 : 0);
        List<Integer> exCatIds = profileVO.getExCatIds();
        // TODO update excats

        if (ValidatorUtil.isNull(error)) {
            userDAO.update(user);
        }
        httpSession.setAttribute("errors", error);

        return safeRedirect("/profileedit");
    }

    @RequestMapping("/profileedit/newapikey")
    public View generateNewRssToken(Model model) {
        User user = userDAO.findByUserId(getUserId());
        String rssToken = UUID.randomUUID().toString();
        user.setRssToken(EncodingUtil.md5Hash(rssToken));
        userDAO.update(user);

        return safeRedirect("/profileedit");
    }
}
