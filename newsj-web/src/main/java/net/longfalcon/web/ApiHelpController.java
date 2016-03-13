package net.longfalcon.web;

import net.longfalcon.newsj.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * User: longfalcon
 * Date: 3/12/2016
 * Time: 7:35 PM
 */
@Controller
public class ApiHelpController extends BaseController {

    @RequestMapping("/apihelp")
    public String apiHelpView(Model model) {
        title = "Api";
        setPageMetaTitle("Api Help Topics");
        setPageMetaKeywords("view,nzb,api,details,help,json,rss,atom");
        setPageMetaDescription("View description of the site Nzb Api.");

        User user = userDAO.findByUserId(getUserId());

        model.addAttribute("title", title);
        model.addAttribute("userData", user);
        return "apihelp";
    }
}
