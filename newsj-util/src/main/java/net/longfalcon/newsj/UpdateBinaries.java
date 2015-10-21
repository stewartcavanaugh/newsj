package net.longfalcon.newsj;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class UpdateBinaries
{
    public static void main( String[] args )
    {
        ApplicationContext context =
                new ClassPathXmlApplicationContext(new String[] {"application-context.xml"});
        Binaries binaries = (Binaries) context.getBean("binaries");
        binaries.updateAllGroups();
    }
}
