package net.longfalcon.newsj.persistence.hibernate;

import net.longfalcon.newsj.model.Group;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: Sten Martinez
 * Date: 10/5/15
 * Time: 5:13 PM
 */
@Repository
public class GroupDAOImpl extends HibernateDAOImpl implements net.longfalcon.newsj.persistence.GroupDAO {

    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ, propagation = Propagation.SUPPORTS)
    public List<Group> getActiveGroups() {
        return this.sessionFactory.getCurrentSession()
                .createQuery("from Group group where group.active = true")
                .list();
    }

    @Transactional
    public void update(Group group) {
        Session currentSession = this.sessionFactory.getCurrentSession();
        currentSession.saveOrUpdate(group);
        //currentSession.flush();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ, propagation = Propagation.SUPPORTS)
    public Group getGroupByName(String name) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Group.class);
        criteria.add(Restrictions.eq("name", name));

        return (Group) criteria.uniqueResult();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ, propagation = Propagation.SUPPORTS)
    public List<Group> findGroupsByName(String name) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Group.class);
        criteria.add(Restrictions.like("name", name + "%"));

        return criteria.list();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ, propagation = Propagation.SUPPORTS)
    public Group findGroupByGroupId(long groupId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Group.class);
        criteria.add(Restrictions.eq("id", groupId));

        return (Group) criteria.uniqueResult();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ, propagation = Propagation.SUPPORTS )
    public List<String> getGroupsForSelect() {
        Query query = sessionFactory.getCurrentSession().createQuery("select distinct coalesce(rr.groupName,'all') as _groupname from ReleaseRegex rr order by rr.groupName");

        return query.list();
    }
}
