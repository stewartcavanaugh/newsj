package net.longfalcon.newsj.service;

import net.longfalcon.newsj.Nzb;
import net.longfalcon.newsj.fs.model.Directory;
import net.longfalcon.newsj.fs.model.DirectoryImpl;
import net.longfalcon.newsj.model.Release;
import net.longfalcon.newsj.persistence.ReleaseDAO;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;

/**
 * User: Sten Martinez
 * Date: 10/23/15
 * Time: 12:45 PM
 */
public class NzbServiceTest {

    Directory directory;

    @Before
    public void setUp() {
        directory = new DirectoryImpl(new File(""));
    }

    @Ignore
    @Test
    public void testWriteNZB() {
        ApplicationContext context =
                new ClassPathXmlApplicationContext(new String[] {"application-context.xml"});
        Nzb nzbService = (Nzb) context.getBean("nzb");
        ReleaseDAO releaseDAO = (ReleaseDAO) context.getBean("releaseDAO");
        Release release = releaseDAO.findByReleaseId(130);
        System.out.println("release name " + release.getName());
        nzbService.writeNZBforReleaseId(release, directory, false);

    }
}
