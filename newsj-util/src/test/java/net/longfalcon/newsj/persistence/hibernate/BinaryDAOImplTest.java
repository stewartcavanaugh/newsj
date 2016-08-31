package net.longfalcon.newsj.persistence.hibernate;

import net.longfalcon.newsj.model.Binary;
import net.longfalcon.newsj.persistence.BinaryDAO;
import net.longfalcon.newsj.test.BaseFsTestSupport;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

/**
 * User: Sten Martinez
 * Date: 4/30/16
 * Time: 5:40 PM
 */
public class BinaryDAOImplTest extends BaseFsTestSupport {

    @Autowired
    BinaryDAO binaryDAO;

    @Test
    @Sql("/sql/binaries/binaries-test-data.sql")
    public void testFindByBinaryHash() throws Exception {
        Binary binary = binaryDAO.findByBinaryHash("abcd1234");
        Assert.assertNotNull(binary);
    }
}