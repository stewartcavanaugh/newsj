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

package net.longfalcon.web;

import net.longfalcon.newsj.Nzb;
import net.longfalcon.newsj.fs.FileSystemService;
import net.longfalcon.newsj.fs.model.Directory;
import net.longfalcon.newsj.fs.model.FsFile;
import net.longfalcon.newsj.model.Release;
import net.longfalcon.newsj.model.User;
import net.longfalcon.newsj.persistence.ReleaseDAO;
import net.longfalcon.newsj.persistence.UserDAO;
import net.longfalcon.newsj.util.StreamUtil;
import net.longfalcon.newsj.util.ValidatorUtil;
import net.longfalcon.web.exception.FlagrantSystemException;
import net.longfalcon.web.exception.NoSuchResourceException;
import net.longfalcon.web.exception.PermissionDeniedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * User: Sten Martinez
 * Date: 3/28/16
 * Time: 2:46 PM
 */
@Controller
public class GetNzbController extends BaseController {

    @Autowired
    FileSystemService fileSystemService;

    @Autowired
    Nzb nzb;

    @Autowired
    ReleaseDAO releaseDAO;

    @Autowired
    UserDAO userDAO;

    @RequestMapping(value = "/getnzb/{releaseGuid}/{searchName}", produces = "application/x-nzb")
    @Transactional
    public ResponseEntity<?> getNzb(@RequestParam(value = "i", required = false) Long remoteUserId,
                                    @RequestParam(value = "r", required = false) String rssToken,
                                    @PathVariable("releaseGuid") String releaseGuid,
                                    @PathVariable("searchName") String searchName,
                                    Model model) throws PermissionDeniedException, NoSuchResourceException, FlagrantSystemException {
        User user = checkUserCredentials(remoteUserId, rssToken);

        Release release = releaseDAO.findByGuid(releaseGuid);
        if ( release == null) {
            throw new NoSuchResourceException();
        }

        int grabs = release.getGrabs();
        release.setGrabs(grabs + 1);
        int userGrabs = user.getGrabs();
        user.setGrabs(userGrabs + 1);
        // TODO: delete from cart?

        releaseDAO.updateRelease(release);
        userDAO.update(user);

        try {
            Directory nzbBaseDir = fileSystemService.getDirectory("/nzbs");
            FsFile nzbFileHandle = nzb.getNzbFileHandle(release, nzbBaseDir);
            MultiValueMap<String, String> headerMap = new HttpHeaders();
            String releaseSearchName = release.getSearchName();
            headerMap.set("X-DNZB-Name", releaseSearchName);
            headerMap.set("X-DNZB-Category", release.getCategoryDisplayName());
            headerMap.set("X-DNZB-MoreInfo", ""); // todo
            headerMap.set("X-DNZB-NFO", "");      // todo
            String fileName;
            if (ValidatorUtil.isNotNull(searchName)) {
                fileName = searchName.replace(" ", "_");
            } else {
                fileName = releaseSearchName.replace(" ", "_");
            }
            headerMap.set("Content-type", "application/x-nzb");
            headerMap.set("Content-Disposition", "attachment; filename=" + fileName + ".nzb");
            return new ResponseEntity<>(new InputStreamResource(nzbFileHandle.getInputStream()), headerMap, HttpStatus.OK);
        } catch (IOException e) {
            throw new FlagrantSystemException(e);
        }
    }

    @RequestMapping(value = "/getnzb", produces = "application/octet-stream")
    @Transactional
    public ResponseEntity<?> getNzbs(@RequestParam(value = "i", required = false) Long remoteUserId,
                                     @RequestParam(value = "r", required = false) String rssToken,
                                     @RequestParam("id") String ids,
                                     @RequestParam(value = "zip", required = false, defaultValue = "false") boolean zip,
                                     Model model) throws NoSuchResourceException, PermissionDeniedException, FlagrantSystemException {

        if (ValidatorUtil.isNotNull(ids) && !zip) {
            return getNzb(remoteUserId, rssToken, ids, "", model);
        }

        User user  = checkUserCredentials(remoteUserId, rssToken);
        String[] guids = ids.split(",");

        try {
            Directory directory = fileSystemService.getDirectory("temp", true);
            File tempFile = directory.getTempFile(String.valueOf(System.currentTimeMillis()) + "zip.nzb.temp");
            ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(tempFile));

            for (String guid : guids) {
                Release release = releaseDAO.findByGuid(guid);

                if ( release == null) {
                    throw new NoSuchResourceException();
                }

                int grabs = release.getGrabs();
                release.setGrabs(grabs + 1);
                int userGrabs = user.getGrabs();
                user.setGrabs(userGrabs + 1);
                // TODO: delete from cart?

                releaseDAO.updateRelease(release);
                userDAO.update(user);

                Directory nzbBaseDir = fileSystemService.getDirectory("/nzbs");
                FsFile nzbFileHandle = nzb.getNzbFileHandle(release, nzbBaseDir);

                String fileName = release.getSearchName().replace(" ", "_");
                ZipEntry zipEntry = new ZipEntry(fileName + ".nzb");
                zipOutputStream.putNextEntry(zipEntry);

                StreamUtil.transferByteArray(nzbFileHandle.getInputStream(), zipOutputStream, 1024);

                zipOutputStream.closeEntry();
            }

            zipOutputStream.close();

            MultiValueMap<String, String> headerMap = new HttpHeaders();
            String fileName = System.currentTimeMillis() + ".nzb.zip";
            headerMap.set("Content-type", "application/octet-stream");
            headerMap.set("Content-Disposition", "attachment; filename=" + fileName);
            return new ResponseEntity<>(new InputStreamResource(new FileInputStream(tempFile)), headerMap, HttpStatus.OK);
        } catch (IOException e) {
            throw new FlagrantSystemException(e);
        }
    }

    private User checkUserCredentials(Long remoteUserId, String rssToken) throws PermissionDeniedException {
        long userId = getUserId();
        if (userId < 1) {
            if (ValidatorUtil.isNotNull(remoteUserId) && ValidatorUtil.isNotNull(rssToken)) {
                User remoteUser = userDAO.findByUserId(remoteUserId);
                if (!rssToken.equals(remoteUser.getRssToken())) {
                     throw new PermissionDeniedException();
                }
                return remoteUser;
            } else {
                throw new PermissionDeniedException();
            }
        }

        User user = userDAO.findByUserId(userId);

        if (user == null) {
            throw new PermissionDeniedException();
        }

        return user;
    }


}
