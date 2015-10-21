package net.longfalcon.newsj.persistence;

import net.longfalcon.newsj.model.BinaryBlacklistEntry;

import java.util.List;

/**
 * User: Sten Martinez
 * Date: 10/14/15
 * Time: 4:48 PM
 */
public interface BinaryBlacklistDAO {

    List<BinaryBlacklistEntry> findAllBinaryBlacklistEntries(boolean activeOnly);
}
