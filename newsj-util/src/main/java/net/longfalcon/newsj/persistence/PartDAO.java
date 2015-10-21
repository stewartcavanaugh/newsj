package net.longfalcon.newsj.persistence;

import net.longfalcon.newsj.model.Part;

import java.util.Date;
import java.util.List;

/**
 * User: Sten Martinez
 * Date: 10/8/15
 * Time: 7:14 AM
 */
public interface PartDAO {
    void updatePart(Part part);

    void deletePart(Part part);

    void deletePartByDate(Date before);

    List<Part> findPartsByBinaryId(long binaryId);
}
