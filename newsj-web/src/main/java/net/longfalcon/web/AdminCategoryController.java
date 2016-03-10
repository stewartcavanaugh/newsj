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
import net.longfalcon.newsj.persistence.CategoryDAO;
import net.longfalcon.view.CategoryVO;
import net.longfalcon.web.exception.NoSuchResourceException;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Sten Martinez
 * Date: 3/10/16
 * Time: 12:17 PM
 */
@Controller
@SessionAttributes({"category"})
public class AdminCategoryController extends BaseController {

    @Autowired
    CategoryDAO categoryDAO;


    @RequestMapping("/admin/category-list")
    public String listCategoryView(Model model) {
        title = "Category List";

        // TODO: replace with hbm mapping for parentCategory
        List<Category> categoryList = categoryDAO.getAllCategories(false);
        List<CategoryVO> categoryVOList = new ArrayList<>(categoryList.size());

        for(Category category : categoryList) {
            String parentTitle = "n/a";
            if (category.getParentId() != null && category.getParentId() > 0) {
                Category parentCategory = categoryService.getCategory(category.getParentId());
                parentTitle = parentCategory.getTitle();
            }
            CategoryVO categoryVO = new CategoryVO(category.getId(), category.getTitle(), parentTitle,
                    category.getStatus() == CategoryService.STATUS_ACTIVE);

            categoryVOList.add(categoryVO);
        }

        model.addAttribute("title", title);
        model.addAttribute("categoryVOList", categoryVOList);
        return "admin/category-list";
    }

    @RequestMapping("/admin/category-edit")
    public String editCategoryView(@RequestParam(value = "id")Integer id, Model model) throws NoSuchResourceException {
        title = "Category Edit";

        Category category = categoryDAO.findByCategoryId(id);
        if (category == null) {
            throw new NoSuchResourceException();
        }

        Map<Integer, String> statusMap = new HashMap<>();
        statusMap.put(CategoryService.STATUS_ACTIVE, "Yes");
        statusMap.put(CategoryService.STATUS_INACTIVE, "No");

        model.addAttribute("title", title);
        model.addAttribute("statusMap", statusMap);
        model.addAttribute("category", category);
        return "admin/category-edit";
    }

    @RequestMapping(value = "/admin/category-edit", method = RequestMethod.POST)
    public View editCategoryPost(@ModelAttribute(value = "category")Category category, Model model) {
        categoryDAO.updateCategory(category);

        return safeRedirect("/admin/category-edit?id=" + category.getId());
    }
}
