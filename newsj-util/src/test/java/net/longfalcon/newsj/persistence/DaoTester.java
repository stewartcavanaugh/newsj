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
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.ArrayList;
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
        PlatformTransactionManager transactionManager = (PlatformTransactionManager) context.getBean("transactionManager");
        CategoryDAO categoryDAO = (CategoryDAO) context.getBean("categoryDAO");
        ReleaseDAO releaseDAO = (ReleaseDAO) context.getBean("releaseDAO");

        TransactionStatus transaction = transactionManager.getTransaction(new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRED));
        List<Category> categories = categoryDAO.findByParentId(CategoryService.CAT_PARENT_MOVIE);
        List<Integer> ids = new ArrayList<>();
        for (Category category : categories) {
            ids.add(category.getId());
        }
        List returnList = releaseDAO.findReleasesByNoImdbIdAndCategoryId(ids);


        System.out.println("list: " + ArrayUtil.stringify(returnList, "\n"));
        transaction.flush();
    }
}
