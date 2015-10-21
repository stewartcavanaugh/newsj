package net.longfalcon.newsj.persistence;

import net.longfalcon.newsj.model.MatchedReleaseQuery;
import net.longfalcon.newsj.util.Defaults;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
        BinaryDAO binaryDAO = (BinaryDAO) context.getBean("binaryDAO");
        List<MatchedReleaseQuery> returnList = binaryDAO.findBinariesByProcStatAndTotalParts(Defaults.PROCSTAT_TITLEMATCHED);

        Map<String, MatchedReleaseQuery> queryMap = new LinkedHashMap<>(returnList.size());
        for (MatchedReleaseQuery matchedReleaseQuery : returnList) {
            String releaseName = matchedReleaseQuery.getReleaseName();
            long numberOfBinaries = matchedReleaseQuery.getNumberOfBinaries();
            int totalParts = matchedReleaseQuery.getReleaseTotalParts();
            if (queryMap.containsKey(releaseName)) {
                MatchedReleaseQuery currentQuery = queryMap.get(releaseName);
                currentQuery.setNumberOfBinaries(currentQuery.getNumberOfBinaries() + numberOfBinaries);
                currentQuery.setReleaseTotalParts(currentQuery.getReleaseTotalParts() + totalParts);
            } else {
                queryMap.put(releaseName, matchedReleaseQuery);
            }
            Object thing = binaryDAO.findMaxDateAddedBinaryByReleaseNameProcStatGroupIdFromName(matchedReleaseQuery.getReleaseName(), Defaults.PROCSTAT_TITLEMATCHED, matchedReleaseQuery.getGroup(), matchedReleaseQuery.getFromName());
            //System.out.println(thing);
        }



        //System.out.println(ArrayUtil.stringify(returnList, "\n"));
    }
}
