package net.longfalcon.newsj.persistence;

import net.longfalcon.newsj.model.ReleaseNfo;

import java.util.List;

/**
 * User: Sten Martinez
 * Date: 10/15/15
 * Time: 5:01 PM
 */
public interface ReleaseNfoDAO {
    void updateReleaseNfo(ReleaseNfo releaseNfo);

    void deleteReleaseNfo(ReleaseNfo releaseNfo);

    List<ReleaseNfo> findReleaseNfoWithNullNfoByAttempts(int attempts);
}
