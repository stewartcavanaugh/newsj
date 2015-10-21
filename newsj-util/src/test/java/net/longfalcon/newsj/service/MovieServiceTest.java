package net.longfalcon.newsj.service;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * User: Sten Martinez
 * Date: 10/20/15
 * Time: 12:47 PM
 */
public class MovieServiceTest {

    @Ignore
    @Test
    public void testProcessMovieReleases() {
        ApplicationContext context =
                new ClassPathXmlApplicationContext(new String[] {"application-context.xml"});
        MovieService movieService = (MovieService) context.getBean("movie");
        movieService.processMovieReleases();
    }
}
