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

package net.longfalcon.newsj;

import net.longfalcon.newsj.fs.model.Directory;
import net.longfalcon.newsj.fs.model.FsFile;
import net.longfalcon.newsj.model.Binary;
import net.longfalcon.newsj.model.Category;
import net.longfalcon.newsj.model.Part;
import net.longfalcon.newsj.model.Release;
import net.longfalcon.newsj.persistence.BinaryDAO;
import net.longfalcon.newsj.persistence.CategoryDAO;
import net.longfalcon.newsj.persistence.GroupDAO;
import net.longfalcon.newsj.persistence.PartDAO;
import net.longfalcon.newsj.xml.File;
import net.longfalcon.newsj.xml.Group;
import net.longfalcon.newsj.xml.Groups;
import net.longfalcon.newsj.xml.Head;
import net.longfalcon.newsj.xml.Meta;
import net.longfalcon.newsj.xml.Segment;
import net.longfalcon.newsj.xml.Segments;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormat;
import org.joda.time.format.PeriodFormatter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * User: Sten Martinez
 * Date: 10/14/15
 * Time: 12:52 PM
 */
public class Nzb {
    private static final String _XMLNS = "http://www.newzbin.com/DTD/2003/nzb";
    private static final Log _log = LogFactory.getLog(Nzb.class);
    private static Marshaller _marshaller;
    private static PeriodFormatter _periodFormatter = PeriodFormat.getDefault();

    private Config config;
    private CategoryDAO categoryDAO;
    private BinaryDAO binaryDAO;
    private PartDAO partDAO;
    private GroupDAO groupDAO;

    private Marshaller getMarshaller() throws JAXBException {
        if (_marshaller == null) {
            JAXBContext jaxbContext = JAXBContext.newInstance(File.class, Group.class, Groups.class, Head.class,
                    Meta.class, net.longfalcon.newsj.xml.Nzb.class, Segment.class, Segments.class);
            _marshaller = jaxbContext.createMarshaller();
        }
        return _marshaller;
    }

    public void writeNZBforReleaseId(Release release, Directory nzbBaseDir, boolean debug) {
        try {
            _doWriteNZBforRelease(release, nzbBaseDir);
        } catch (Exception e) {
            _log.error(e,e);
        }
    }

    private void _doWriteNZBforRelease(Release release, Directory nzbBaseDir) throws IOException, JAXBException {
        long releaseId = release.getId();
        String releaseGuid = release.getGuid();
        String releaseName = release.getName();
        long startTime = System.currentTimeMillis();

        Category category = release.getCategory();
        String categoryName = null;
        if (category != null) {
            categoryName = category.getTitle();
        }

        net.longfalcon.newsj.xml.Nzb nzbRoot = new net.longfalcon.newsj.xml.Nzb();
        nzbRoot.setXmlns(_XMLNS);
        Head head = new Head();
        List<Meta> metaElements = head.getMeta();
        Meta categoryMeta = new Meta();
        categoryMeta.setType("category");
        categoryMeta.setvalue(StringEscapeUtils.escapeXml11(categoryName));
        Meta nameMeta = new Meta();
        nameMeta.setType("name");
        nameMeta.setvalue(StringEscapeUtils.escapeXml11(releaseName));
        metaElements.add(categoryMeta);
        metaElements.add(nameMeta);
        nzbRoot.setHead(head);

        List<File> files = nzbRoot.getFile();
        List<Binary> binaries = binaryDAO.findBinariesByReleaseId(releaseId);
        for (Binary binary : binaries) {
            File fileElement = new File();
            fileElement.setPoster(StringEscapeUtils.escapeXml11(binary.getFromName()));
            fileElement.setDate(String.valueOf(binary.getDate().getTime()));
            String subjectString = String.format("%s (1/%s)", StringEscapeUtils.escapeXml11(binary.getName()), binary.getTotalParts());
            fileElement.setSubject(subjectString);

            Groups groupsElement = new Groups();
            List<Group> groups = groupsElement.getGroup();
            net.longfalcon.newsj.model.Group group = groupDAO.findGroupByGroupId(binary.getGroupId());
            Group groupElement = new Group();
            groupElement.setvalue(group.getName());
            groups.add(groupElement);

            // TODO: add XRef groups
            fileElement.setGroups(groupsElement);
            Segments segmentsElement = new Segments();
            List<Segment> segments = segmentsElement.getSegment();

            // TODO: manually pull out messageID, size and partnumber from a faster query for distinct(messageID)
            List<Part> parts = partDAO.findPartsByBinaryId(binary.getId());
            Set<String> messageIds = new HashSet<>();
            for (Part part : parts) {
                String messageId = part.getMessageId();

                if (!messageIds.contains(messageId)) {
                    Segment segment = new Segment();
                    segment.setBytes(String.valueOf(part.getSize()));
                    segment.setNumber(String.valueOf(part.getPartNumber()));

                    segment.setvalue(messageId);
                    segments.add(segment);
                    messageIds.add(messageId);
                }
            }
            fileElement.setSegments(segmentsElement);

            files.add(fileElement);
        }


        long startFileWriteTime = System.currentTimeMillis();

        FsFile fileHandle = getNzbFileHandle(release, nzbBaseDir);
        Writer writer = new OutputStreamWriter(fileHandle.getOutputStream(), Charset.forName("UTF-8"));
        getMarshaller().marshal(nzbRoot, writer);
        writer.write(String.format("<!-- generated by NewsJ %s -->", config.getReleaseVersion()));
        writer.flush();
        writer.close();

        Period totalTimePeriod = new Period(startTime, System.currentTimeMillis());
        Period buildTimePeriod = new Period(startTime, startFileWriteTime);
        Period writeTimePeriod = new Period(startFileWriteTime, System.currentTimeMillis());
        _log.info(String.format("Wrote NZB for %s in %s;\n build time: %s write time: %s",
                releaseName, _periodFormatter.print(totalTimePeriod), _periodFormatter.print(buildTimePeriod), _periodFormatter.print(writeTimePeriod)));
    }

    public FsFile getNzbFileHandle(Release release, Directory nzbBaseDir) throws IOException {
        String releaseGuid = release.getGuid();
        String releaseName = release.getName();

        // nzbs are stored in a dir with the first char of their GUID.
        char subDirName = releaseGuid.charAt(0);
        Directory nzbFullPath = nzbBaseDir.getDirectory(subDirName + "/");
        String nzbFileName = releaseName + ".nzb";
        FsFile fsFile = nzbFullPath.getFile(nzbFileName);

        return fsFile;
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public CategoryDAO getCategoryDAO() {
        return categoryDAO;
    }

    public void setCategoryDAO(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    public BinaryDAO getBinaryDAO() {
        return binaryDAO;
    }

    public void setBinaryDAO(BinaryDAO binaryDAO) {
        this.binaryDAO = binaryDAO;
    }

    public PartDAO getPartDAO() {
        return partDAO;
    }

    public void setPartDAO(PartDAO partDAO) {
        this.partDAO = partDAO;
    }

    public GroupDAO getGroupDAO() {
        return groupDAO;
    }

    public void setGroupDAO(GroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }
}
