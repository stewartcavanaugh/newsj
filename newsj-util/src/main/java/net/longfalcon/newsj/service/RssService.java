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

package net.longfalcon.newsj.service;

import net.longfalcon.newsj.Config;
import net.longfalcon.newsj.model.Release;
import net.longfalcon.newsj.util.DateUtil;
import net.longfalcon.newsj.ws.atom.Link;
import net.longfalcon.newsj.ws.newznab.NewzNabAttribute;
import net.longfalcon.newsj.ws.newznab.Response;
import net.longfalcon.newsj.ws.rss.Category;
import net.longfalcon.newsj.ws.rss.Enclosure;
import net.longfalcon.newsj.ws.rss.Guid;
import net.longfalcon.newsj.ws.rss.Image;
import net.longfalcon.newsj.ws.rss.ObjectFactory;
import net.longfalcon.newsj.ws.rss.Rss;
import net.longfalcon.newsj.ws.rss.RssChannel;
import net.longfalcon.newsj.ws.rss.RssItem;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBElement;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 * User: Sten Martinez
 * Date: 8/5/16
 * Time: 1:47 PM
 */
@Service
public class RssService {

    Config config;

    /**
     * builds rss document from release list
     * @param releases
     * @return
     */
    Rss getRssDocument(List<Release> releases, String serverRoot, int offset, int total, boolean isApiResult) {
        ObjectFactory rssObjectFactory = new ObjectFactory();
        Rss rssRoot = new Rss();
        rssRoot.setVersion(new BigDecimal(2.0));
        RssChannel rssChannelMain = new RssChannel();
        rssRoot.setChannel(rssChannelMain);

        Link atomLink = new Link();
        atomLink.setHref(serverRoot + "/api");
        atomLink.setRel("self");
        atomLink.setType("application/rss+xml");
        rssChannelMain.setAtomLink(atomLink);

        JAXBElement<String> titleElement = rssObjectFactory.createRssChannelTitle("Newznab");
        JAXBElement<String> descElement = rssObjectFactory.createRssChannelDescription("Newznab API Results");
        JAXBElement<String> linkElement = rssObjectFactory.createRssChannelLink(serverRoot);
        List<Object> titleOrLinkOrDescription = rssChannelMain.getTitleOrLinkOrDescription();
        titleOrLinkOrDescription.add(titleElement);
        titleOrLinkOrDescription.add(descElement);
        titleOrLinkOrDescription.add(linkElement);
        Response response = new Response();
        response.setOffset(offset);
        response.setTotal(total);
        rssChannelMain.setNewznabResponse(response);

        Image image = new Image();
        image.setUrl(serverRoot + "/images/banner.jpg"); // todo: theme
        image.setTitle("Newznab");
        image.setLink(serverRoot);
        image.setDescription(config.getDefaultSite().getTitle());
        titleOrLinkOrDescription.add(image);

        List<RssItem> rssItems = rssChannelMain.getItem();
        RssItem rssItem = new RssItem();
        List<Object> titleOrDescOrLink = rssItem.getTitleOrDescriptionOrLink();
        JAXBElement<String> itemTitle = rssObjectFactory.createRssItemTitle("test1");
        titleOrDescOrLink.add(itemTitle);
        Guid itemGuidValue = rssObjectFactory.createGuid();
        itemGuidValue.setValue("test-abcd-1234");
        itemGuidValue.setIsPermaLink(true);
        JAXBElement<Guid> itemGuid = rssObjectFactory.createRssItemGuid(itemGuidValue);
        titleOrDescOrLink.add(itemGuid);
        JAXBElement<String> itemLink = rssObjectFactory.createRssItemLink("http://testserver/test1");
        titleOrDescOrLink.add(itemLink);
        JAXBElement<String> itemComments = rssObjectFactory.createRssItemComments("http://testserver/test1#comments");
        titleOrDescOrLink.add(itemComments);
        JAXBElement<String> itemPubDate = rssObjectFactory.createRssItemPubDate(DateUtil.RFC_822_dateFormatter.print(new DateTime()));
        titleOrDescOrLink.add(itemPubDate);
        Category category = rssObjectFactory.createCategory();
        category.setValue("TV > HD");
        JAXBElement<Category> itemCategory = rssObjectFactory.createRssItemCategory(category);
        titleOrDescOrLink.add(itemCategory);
        JAXBElement<String> itemDesc = rssObjectFactory.createRssItemDescription("blah blah blah");
        titleOrDescOrLink.add(itemDesc);
        Enclosure enclosure = rssObjectFactory.createEnclosure();
        enclosure.setUrl("http://testserver/test1");
        enclosure.setLength(BigInteger.valueOf(1234));
        JAXBElement<Enclosure> itemEncl = rssObjectFactory.createRssItemEnclosure(enclosure);
        titleOrDescOrLink.add(itemEncl);

        List<NewzNabAttribute> newzNabAttributes = rssItem.getAttributes();
        NewzNabAttribute nzbAttrCategory1 = new NewzNabAttribute();
        nzbAttrCategory1.setName("category");
        nzbAttrCategory1.setValue("5000");
        newzNabAttributes.add(nzbAttrCategory1);
        NewzNabAttribute nzbAttrCategory2 = new NewzNabAttribute();
        nzbAttrCategory2.setName("category");
        nzbAttrCategory2.setValue("5040");
        newzNabAttributes.add(nzbAttrCategory2);

        rssItems.add(rssItem);

        return rssRoot;
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }
}
