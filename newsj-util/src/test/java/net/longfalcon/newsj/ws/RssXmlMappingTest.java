/*
 * Copyright (c) 2016. Sten Martinez
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

package net.longfalcon.newsj.ws;

import net.longfalcon.newsj.ws.atom.Link;
import net.longfalcon.newsj.ws.rss.Image;
import net.longfalcon.newsj.ws.rss.ObjectFactory;
import net.longfalcon.newsj.ws.rss.Rss;
import net.longfalcon.newsj.ws.rss.RssChannel;
import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.math.BigDecimal;
import java.util.List;

/**
 * User: Sten Martinez
 * Date: 7/28/16
 * Time: 3:47 PM
 */
public class RssXmlMappingTest {

    JAXBContext jaxbContext;
    Unmarshaller unmarshaller;
    Marshaller marshaller;

    @Before
    public void setUp() throws Exception {
        jaxbContext = JAXBContext.newInstance("net.longfalcon.newsj.ws.atom:net.longfalcon.newsj.ws.rss");
        unmarshaller = jaxbContext.createUnmarshaller();
        marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
    }

    @Test
    public void createRssTest() throws Exception {
        ObjectFactory rssObjectFactory = new ObjectFactory();
        Rss rssRoot = new Rss();
        rssRoot.setVersion(new BigDecimal(2.0));
        RssChannel rssChannelMain = new RssChannel();
        rssRoot.setChannel(rssChannelMain);

        Link atomLink = new Link();
        atomLink.setHref("http://fluffypanda/newznab/api");
        atomLink.setRel("self");
        atomLink.setType("application/rss+xml");
        rssChannelMain.setAtomLink(atomLink);

        JAXBElement<String> titleElement = rssObjectFactory.createRssChannelTitle("Newznab");
        JAXBElement<String> descElement = rssObjectFactory.createRssChannelDescription("Newznab API Results");
        JAXBElement<String> linkElement = rssObjectFactory.createRssChannelLink("http://fluffypanda/newznab/");
        List<Object> titleOrLinkOrDescription = rssChannelMain.getTitleOrLinkOrDescription();
        titleOrLinkOrDescription.add(titleElement);
        titleOrLinkOrDescription.add(descElement);
        titleOrLinkOrDescription.add(linkElement);

        Image image = new Image();
        image.setUrl("http://fluffypanda/newznab/images/banner.jpg");
        image.setTitle("Newznab");
        image.setLink("http://fluffypanda/newznab/");
        image.setDescription("Visit Newznab - A great usenet indexer");
        titleOrLinkOrDescription.add(image);

        marshaller.marshal(rssRoot, System.out);
    }
}
