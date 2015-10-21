package net.longfalcon.newsj;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * User: Sten Martinez
 * Date: 10/10/15
 * Time: 7:30 PM
 */
public class UpdateReleases {
    public static void main(String[] args) {
        ApplicationContext context =
                new ClassPathXmlApplicationContext(new String[] {"application-context.xml"});
        Releases releases = (Releases) context.getBean("releases");
        releases.processReleases();
    }
}
