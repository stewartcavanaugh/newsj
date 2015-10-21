package net.longfalcon.newsj.persistence;

import net.longfalcon.newsj.model.ReleaseRegex;

import java.util.List;

/**
 * User: Sten Martinez
 * Date: 10/9/15
 * Time: 2:33 PM
 */
public interface ReleaseRegexDAO {
    List<ReleaseRegex> getRegexes(boolean activeOnly, String groupName, boolean userReleaseRegexes);

    ReleaseRegex findById(long id);

    void updateReleaseRegex(ReleaseRegex releaseRegex);

    void deleteReleaseRegex(ReleaseRegex releaseRegex);
}
