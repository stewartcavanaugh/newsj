/*
 * Copyright (c) 2015. Sten Martinez
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package net.longfalcon.newsj.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: Sten Martinez
 * Date: 10/7/15
 * Time: 5:08 PM
 */
public class RegexTester {

    String exampleSubject = "*MetalDept@Usenetdepartment.info*>>>Best Metal Ever<<< \"Sweet - Action! The Ultimate Story (2015).part02.rar\">[04/31] 382,97 MB yEnc (1/40)";
    String exampleSubject2 = "\"qiny2t_iqxH4lPUoenuf.part05.rar\" [06/23] yEnc (224/418)";

    @Test
    public void testBinaryMatcher() {
        Pattern pattern = Pattern.compile("\\((\\d+)\\/(\\d+)\\)$");
        Matcher matcher = pattern.matcher(exampleSubject);

        while(matcher.find()){
            //System.out.print(matcher.start()+matcher.group());
            System.out.println("Group 1:" + matcher.group(1));
            System.out.println("Group 1:" + matcher.group(2));
        }
    }

    @Test
    public void testSubjectMatcher() {
        Matcher matcher = Defaults.PARTS_SUBJECT_REGEX.matcher(exampleSubject2);
        if (ValidatorUtil.isNull(exampleSubject2) || !matcher.find()) {
            System.out.println("this shouldnt be ignored.");
        }

        Matcher matcher2 = Defaults.PARTS_SUBJECT_REGEX.matcher(exampleSubject);
        if (ValidatorUtil.isNull(exampleSubject2) || !matcher2.find()) {
            System.out.println("this shouldnt be ignored.");
        }
    }

    @Test
    public void testFixRegexes() {
        String badRegex = "^(\\[|\\()(PC|ISO).*?(\\]|\\)) (?P<name>.*?) \\- \\[(?P<parts>\\d{1,3}\\/\\d{1,3})";
        String goodRegex = "^(\\[|\\()(PC|ISO).*?(\\]|\\)) (?<name>.*?) \\- \\[(?<parts>\\d{1,3}\\/\\d{1,3})";
        String findBadNamesRegex = "\\?P\\<(\\w+)\\>";
        Pattern pattern = Pattern.compile(findBadNamesRegex);
        Matcher matcher = pattern.matcher(badRegex);
        String answer = "didnt work";
        if (matcher.find()) {
            answer = matcher.replaceAll("?<$1>");
        }
        Assert.assertEquals(goodRegex, answer);
        Pattern pattern1 = Pattern.compile(answer);
    }
}
