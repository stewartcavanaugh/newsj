package net.longfalcon.newsj;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * User: Sten Martinez
 * Date: 10/23/15
 * Time: 9:53 PM
 */
public class BackfillGroups {
    public static void main(String[] args) {
        ApplicationContext context =
                new ClassPathXmlApplicationContext(new String[] {"application-context.xml"});
        Backfill backfill = (Backfill) context.getBean("backfill");
        backfill.backfillAllGroups();
    }
}
