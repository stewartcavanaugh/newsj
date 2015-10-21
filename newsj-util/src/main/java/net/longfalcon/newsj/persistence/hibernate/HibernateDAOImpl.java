package net.longfalcon.newsj.persistence.hibernate;

import org.hibernate.SessionFactory;

/**
 * User: Sten Martinez
 * Date: 10/8/15
 * Time: 8:06 AM
 */
public class HibernateDAOImpl {
    protected SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
