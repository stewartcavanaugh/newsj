package net.longfalcon.newsj.persistence;

import net.longfalcon.newsj.model.ReleaseRegex;
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
        ReleaseRegexDAO releaseRegexDAO = (ReleaseRegexDAO) context.getBean("releaseRegexDAO");
        ReleaseRegex releaseRegex = releaseRegexDAO.findById(643);


        System.out.println("regex: " + releaseRegex.getRegex());
    }
}
