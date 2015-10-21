package net.longfalcon.newsj.fs;

import net.longfalcon.newsj.fs.model.Directory;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * User: Sten Martinez
 * Date: 10/14/15
 * Time: 5:45 PM
 */
public class FIleSystemServiceTester {

    @Test
    @Ignore
    public void tryFiles() {
        ApplicationContext context =
                new ClassPathXmlApplicationContext(new String[] {"application-context.xml"});
        FileSystemService fileSystemService = (FileSystemService) context.getBean("fileSystemService");

        Directory testDir = fileSystemService.getDirectory("test1/test3/test2");
        System.out.println(testDir);
    }
}
