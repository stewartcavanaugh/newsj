package net.longfalcon.web;

import net.longfalcon.newsj.Releases;
import net.longfalcon.newsj.model.User;
import net.longfalcon.newsj.persistence.ReleaseDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * User: longfalcon
 * Date: 3/12/2016
 * Time: 8:06 PM
 */
@Controller
public class BrowseController extends BaseController {

    @Autowired
    ReleaseDAO releaseDAO;

    @Autowired
    Releases releases;

    @RequestMapping("/browse")
    public String browseView(@RequestParam(value = "t", required = false) Integer categoryId,
                             @RequestParam(value = "g", required = false) String groupName,
                             @RequestParam(value = "offset", required = false, defaultValue = "0") String offset,
                             @RequestParam(value = "ob", required = false) String orderBy,
                             Model model) {
        if (categoryId != null) {

        }
        User user = userDAO.findByUserId(getUserId());

        model.addAttribute("pagerTotalItems", pagerTotalItems);
        model.addAttribute("pagerOffset", offset);
        model.addAttribute("pagerItemsPerPage", PAGE_SIZE);
        model.addAttribute("orderBy", orderBy);
        return "browse";
    }
}
