package net.longfalcon.newsj.persistence.hibernate;

import net.longfalcon.newsj.model.Site;
import net.longfalcon.newsj.persistence.SiteDAO;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * User: Sten Martinez
 * Date: 10/5/15
 * Time: 11:12 PM
 */
@Repository
public class SiteDAOImpl extends HibernateDAOImpl implements SiteDAO {

    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ, propagation = Propagation.NOT_SUPPORTED)
    public Site getDefaultSite() {
        Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(Site.class);
        criteria.setMaxResults(1);
        return (Site) criteria.list().get(0);
    }
}
