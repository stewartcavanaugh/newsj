package net.longfalcon.newsj.persistence;

import net.longfalcon.newsj.model.Category;

import java.util.List;

/**
 * User: Sten Martinez
 * Date: 10/16/15
 * Time: 4:50 PM
 */
public interface CategoryDAO {
    Category findByCategoryId(int categoryId);

    List<Category> findByParentId(int parentId);
}
