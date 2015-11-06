package net.longfalcon.newsj.persistence;

import net.longfalcon.newsj.model.TvRage;

/**
 * User: Sten Martinez
 * Date: 11/5/15
 * Time: 10:31 PM
 */
public interface TvRageDAO {
    void update(TvRage tvRage);

    void delete(TvRage tvRage);

    TvRage findByTvRageId(long id);

    TvRage findByReleaseTitle(String title);
}
