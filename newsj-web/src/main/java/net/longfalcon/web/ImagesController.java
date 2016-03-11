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

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * User: Sten Martinez
 * Date: 3/10/16
 * Time: 5:48 PM
 */
@Controller
public class ImagesController {

    @RequestMapping("/images/**")
    @ResponseBody
    public ResponseEntity<InputStreamResource> getImage(HttpServletRequest httpServletRequest) {
        String url = httpServletRequest.getRequestURI();
        String[] urlParts = url.split("/images/");
        String urlPart = urlParts[1];
        System.out.println("url: " + urlPart);

        return new ResponseEntity<InputStreamResource>(HttpStatus.NO_CONTENT);
    }
}
