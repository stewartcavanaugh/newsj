/*
 * Copyright (c) 2015. Sten Martinez
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

package net.longfalcon.newsj.persistence;

import net.longfalcon.newsj.CategoryService;
import net.longfalcon.newsj.model.Category;
import net.longfalcon.newsj.util.ArrayUtil;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * User: Sten Martinez
 * Date: 10/9/15
 * Time: 3:15 PM
 */
public class DaoTester {
    private long userId = -1L;

    @Test
    @Ignore
    public void testDAO(){
        ApplicationContext context =
                new ClassPathXmlApplicationContext(new String[] {"application-context.xml"});
        CategoryService categoryService = (CategoryService) context.getBean("categoryService");
        List<Category> categoriesList = categoryService.getCategoriesForMenu(0);


        System.out.println("cats: " + ArrayUtil.stringify(categoriesList, "\n"));
    }
}
