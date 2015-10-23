package net.longfalcon.newsj.persistence;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * User: Sten Martinez
 * Date: 10/9/15
 * Time: 3:15 PM
 */
public class DaoTester {

    @Test
    @Ignore
    public void testDAO(){
        ApplicationContext context =
                new ClassPathXmlApplicationContext(new String[] {"application-context.xml"});
        PartDAO partDAO = (PartDAO) context.getBean("partDAO");
        Long size = partDAO.sumPartsSizeByBinaryId(29993);


        System.out.println("count: " + size);
    }
}
