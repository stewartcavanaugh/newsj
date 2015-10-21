package net.longfalcon.newsj.persistence;

import net.longfalcon.newsj.model.Group;

import java.util.Collection;
import java.util.List;

/**
 * User: Sten Martinez
 * Date: 10/5/15
 * Time: 9:48 PM
 */
public interface GroupDAO {
    Collection<Group> getActiveGroups();

    void update(Group group);

    Group getGroupByName(String name);

    List<Group> findGroupsByName(String name);

    Group findGroupByGroupId(long groupId);

    public List<String> getGroupsForSelect();
}
