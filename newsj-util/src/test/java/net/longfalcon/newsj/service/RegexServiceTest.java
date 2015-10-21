package net.longfalcon.newsj.service;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * User: Sten Martinez
 * Date: 10/15/15
 * Time: 1:11 PM
 */
public class RegexServiceTest {

    @Test
    public void testCheckRegexesUptoDate() throws Exception {
        ApplicationContext context =
                new ClassPathXmlApplicationContext(new String[] {"application-context.xml"});
        RegexService regexService = (RegexService) context.getBean("regexService");


        regexService.checkRegexesUptoDate("http://www.newznab.com/getregex.php", 1);
    }
}