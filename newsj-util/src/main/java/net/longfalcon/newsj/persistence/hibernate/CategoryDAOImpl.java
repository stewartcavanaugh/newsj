package net.longfalcon.newsj.persistence.hibernate;

import net.longfalcon.newsj.model.Category;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: Sten Martinez
 * Date: 10/16/15
 * Time: 4:45 PM
 */
@Repository
public class CategoryDAOImpl extends HibernateDAOImpl implements net.longfalcon.newsj.persistence.CategoryDAO {

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.SUPPORTS)
    public Category findByCategoryId(int categoryId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Category.class);
        criteria.add(Restrictions.eq("id", categoryId));

        return (Category) criteria.uniqueResult();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.SUPPORTS)
    public List<Category> findByParentId(int parentId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Category.class);
        criteria.add(Restrictions.eq("parentId", parentId));

        return criteria.list();
    }
}
