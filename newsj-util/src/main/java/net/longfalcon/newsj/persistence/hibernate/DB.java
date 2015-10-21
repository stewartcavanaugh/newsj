package net.longfalcon.newsj.persistence.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * User: Sten Martinez
 * Date: 10/5/15
 * Time: 5:16 PM
 */
public class DB {
    private SessionFactory sessionFactory;

    public void init() {

        // A SessionFactory is set up once for an application
        sessionFactory = new Configuration()
                .configure() // configures settings from hibernate.cfg.xml
                .buildSessionFactory();

    }

    public void destroy() {
        if ( sessionFactory != null ) {
            sessionFactory.close();
        }
    }

    public Session getSession() {
         return sessionFactory.openSession();
    }
}
