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

import net.longfalcon.newsj.fs.FileSystemService;
import net.longfalcon.newsj.fs.model.Directory;
import net.longfalcon.newsj.model.Binary;
import net.longfalcon.newsj.model.Group;
import net.longfalcon.newsj.model.Part;
import net.longfalcon.newsj.model.Release;
import net.longfalcon.newsj.model.ReleaseNfo;
import net.longfalcon.newsj.nntp.NntpConnectionFactory;
import net.longfalcon.newsj.nntp.client.NewsClient;
import net.longfalcon.newsj.persistence.BinaryDAO;
import net.longfalcon.newsj.persistence.GroupDAO;
import net.longfalcon.newsj.persistence.PartDAO;
import net.longfalcon.newsj.persistence.ReleaseNfoDAO;
import net.longfalcon.newsj.util.ParseUtil;
import net.longfalcon.newsj.util.StreamUtil;
import net.longfalcon.newsj.util.ValidatorUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.yenc.YDecoder;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: Sten Martinez
 * Date: 10/14/15
 * Time: 9:14 AM
 */
public class Nfo {
    private static final Log _log = LogFactory.getLog(Nfo.class);

    private BinaryDAO binaryDAO;
    private GroupDAO groupDAO;
    private PartDAO partDAO;
    private ReleaseNfoDAO releaseNfoDAO;
    private NntpConnectionFactory nntpConnectionFactory;
    private FileSystemService fileSystemService;

    /**
     * only returns one NFO. dunno about releases with two nfos.
     * @param release
     * @return
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS, isolation = Isolation.READ_UNCOMMITTED)
    public ReleaseNfo determineReleaseNfo(Release release) {
        Pattern nfoPattern = Pattern.compile(".*\\.nfo[ \"\\)\\]\\-]?.*", Pattern.CASE_INSENSITIVE);

        List<Binary> binaryList = binaryDAO.findBinariesByReleaseId(release.getId());
        for (Binary binary : binaryList) {
            Matcher matcher = nfoPattern.matcher(binary.getName());
            if (matcher.matches()) {
                ReleaseNfo releaseNfo = new ReleaseNfo();
                releaseNfo.setBinary(binary);
                releaseNfo.setRelease(release);

                return releaseNfo;
            }
        }

        return null;
    }

    public void addReleaseNfo(ReleaseNfo releaseNfo) {
        releaseNfoDAO.updateReleaseNfo(releaseNfo);
    }

    @Transactional
    public void processNfoFiles(int lookupImdb, int lookupTvRage) {
        List<ReleaseNfo> releaseNfos = releaseNfoDAO.findReleaseNfoWithNullNfoByAttempts(5);
        if (!releaseNfos.isEmpty()) {
            int ret = 0;
            _log.info("Processing " + releaseNfos.size() + " nfos");
            NewsClient newsClient = nntpConnectionFactory.getNntpClient();
            for (ReleaseNfo releaseNfo : releaseNfos) {
                String nfoText = getSmallBinaryNfo(newsClient, releaseNfo);


                if (ValidatorUtil.isNotNull(nfoText)) {
                    releaseNfo.setNfo(nfoText.getBytes());

                    ret++;

                    // check imdb/movie
                    String imdbString = ParseUtil.parseImdb(nfoText);
                    if (ValidatorUtil.isNotNull(imdbString) && ValidatorUtil.isNumeric(imdbString)) {
                        releaseNfo.getRelease().setImdbId(Integer.parseInt(imdbString));

                        if (lookupImdb == 1) {
                            // update movie info TODO
                        }
                    }

                    // check TVRage / TVDB
                    String rageIdString = ParseUtil.parseRageId(nfoText);
                    if (ValidatorUtil.isNotNull(rageIdString) && ValidatorUtil.isNumeric(rageIdString)) {
                        // update tv info TODO
                    }
                    releaseNfoDAO.updateReleaseNfo(releaseNfo);
                } else {
                    int attempts = releaseNfo.getAttemtps() + 1;
                    releaseNfo.setAttemtps(attempts);
                    _log.warn(String.format("NFO download failed - release %s on attempt %s", releaseNfo.getRelease().getId(), attempts));
                    releaseNfoDAO.updateReleaseNfo(releaseNfo);
                }

                if (ret != 0 && (ret % 5 == 0)) {
                    _log.info(String.format("-processed %s nfos", ret));
                }
            }

        }

    }

    private String getSmallBinaryNfo(NewsClient newsClient, ReleaseNfo releaseNfo) {
        Binary binary = releaseNfo.getBinary();
        Group group = groupDAO.findGroupByGroupId(binary.getGroupId());
        try {
            newsClient.selectNewsgroup(group.getName());
        } catch (IOException e) {
            _log.error(e);
            return null;
        }
        String nfo;
        List<Part> partList = partDAO.findPartsByBinaryId(binary.getId());
        if (partList.size() > 1) {
            _log.error("NFO is more than one part, skipping " + binary.getName());
            return null;
        } else {
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                Directory tempDir = fileSystemService.getDirectory("/temp");
                File tempFile = tempDir.getTempFile(String.valueOf(System.currentTimeMillis()));
                for (Part part : partList) {
                    BufferedReader bufferedReader = newsClient.retrieveArticleBody(part.getNumber());
                    if (bufferedReader != null) {
                        YDecoder.decode(bufferedReader, tempFile);
                    } else {
                        return null;
                    }
                }

                FileReader fileReader = new FileReader(tempFile);
                String enc = fileReader.getEncoding();
                InputStream inputStream = new FileInputStream(tempFile);
                StreamUtil.transferByteArray(inputStream, byteArrayOutputStream, 1024);
                nfo = byteArrayOutputStream.toString(enc);


            } catch (IOException e) {
                _log.error(e);
                return null;
            }
        }

        return nfo;
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

    public ReleaseNfoDAO getReleaseNfoDAO() {
        return releaseNfoDAO;
    }

    public void setReleaseNfoDAO(ReleaseNfoDAO releaseNfoDAO) {
        this.releaseNfoDAO = releaseNfoDAO;
    }

    public NntpConnectionFactory getNntpConnectionFactory() {
        return nntpConnectionFactory;
    }

    public void setNntpConnectionFactory(NntpConnectionFactory nntpConnectionFactory) {
        this.nntpConnectionFactory = nntpConnectionFactory;
    }

    public FileSystemService getFileSystemService() {
        return fileSystemService;
    }

    public void setFileSystemService(FileSystemService fileSystemService) {
        this.fileSystemService = fileSystemService;
    }

    public GroupDAO getGroupDAO() {
        return groupDAO;
    }

    public void setGroupDAO(GroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }
}
