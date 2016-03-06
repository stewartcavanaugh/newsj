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

import net.longfalcon.web.exception.NoSuchResourceException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User: Sten Martinez
 * Date: 3/4/16
 * Time: 6:15 PM
 */
public class WebExceptionHandlerResolver implements HandlerExceptionResolver, Ordered {

    private static final Log _log = LogFactory.getLog(WebExceptionHandlerResolver.class);

    @Override
    public int getOrder() {
        return Integer.MIN_VALUE;
    }

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        try {
            if (ex instanceof NoSuchResourceException) {
                return handleNoSuchResource((NoSuchResourceException) ex, request, response,
                        handler);
            }

        } catch (Exception handlerException) {
            _log.warn("Handling of [" + ex.getClass().getName() + "] resulted in Exception", handlerException);
        }
        return null;
    }

    private ModelAndView handleNoSuchResource(NoSuchResourceException ex, HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
        return new ModelAndView();
    }
}
