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

import net.longfalcon.newsj.Config;
import net.longfalcon.newsj.util.ValidatorUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.View;

/**
 * User: Sten Martinez
 * Date: 6/23/16
 * Time: 11:48 AM
 */
@Controller
public class ContactUsController extends BaseController {
    private static final Log _log = LogFactory.getLog(ContactUsController.class);

    String message = "";

    @Autowired
    Config config;

    @RequestMapping("/contact-us")
    public String contactUsView(Model model) {
        String siteTitle = config.getDefaultSite().getTitle();
        String title = "Contact " + siteTitle;
        String metaDescription = "Contact us at " + siteTitle + " and submit your feedback";

        model.addAttribute("title", title);
        model.addAttribute("pageMetaTitle", title);
        model.addAttribute("pageMetaDescription", metaDescription);
        model.addAttribute("thanksMessage", message);
        message = "";
        return "contact-us";
    }

    @RequestMapping(value = "/contact-us", method = RequestMethod.POST)
    public View contactFormPost(@RequestParam("username")String userName,
                                  @RequestParam("useremail")String email,
                                  @RequestParam("comment")String comment,
                                  Model model) {
        String thanksMessage = "";

        if (ValidatorUtil.isNull(userName)) {
            thanksMessage += "Please Enter a Name. ";
        }

        if (ValidatorUtil.isNull(email)) {
            thanksMessage += "Please Enter an Email address. ";
        }

        if (ValidatorUtil.isNull(comment)) {
            thanksMessage += "Please Enter a comment. ";
        }

        if (ValidatorUtil.isNotNull(userName) && ValidatorUtil.isNotNull(email) && ValidatorUtil.isNotNull(comment)) {
            thanksMessage = "Thanks for getting in touch with " + config.getDefaultSite().getTitle();
        }

        //TODO: do something with the message
        _log.warn(String.format("message sent from %s(%s) : %s", userName, email, comment));
        message = thanksMessage;

        return safeRedirect("/contact-us");
    }

    @Override
    public String getPageMetaKeywords() {
        return "contact us,contact,get in touch,email";
    }
}
