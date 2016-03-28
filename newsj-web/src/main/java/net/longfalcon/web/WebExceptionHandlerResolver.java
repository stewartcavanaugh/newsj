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

import net.longfalcon.web.exception.FlagrantSystemException;
import net.longfalcon.web.exception.NoSuchResourceException;
import net.longfalcon.web.exception.PermissionDeniedException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.Ordered;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
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
            } else if (ex instanceof PermissionDeniedException) {
                return handlePermissionDenied((PermissionDeniedException) ex, request, response, handler);
            } else if (ex instanceof FlagrantSystemException) {
                return handleFlagrantSystemError((FlagrantSystemException) ex, request, response, handler);
            } else if (ex instanceof BindException) {
                return handleBindException((BindException) ex, request, response, handler);
            }

        } catch (Exception handlerException) {
            _log.warn("Handling of [" + ex.getClass().getName() + "] resulted in Exception", handlerException);
        }
        return null;
    }

    private ModelAndView handleNoSuchResource(NoSuchResourceException ex, HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
        _log.error(ex, ex);
        return new ModelAndView();
    }

    private ModelAndView handlePermissionDenied(PermissionDeniedException ex, HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        _log.error(ex, ex);
        return new ModelAndView();
    }

    private ModelAndView handleFlagrantSystemError(FlagrantSystemException ex, HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        _log.error(ex, ex);
        return new ModelAndView();
    }

    /**
     * Handle the case where an {@linkplain ModelAttribute @ModelAttribute} method
     * argument has binding or validation errors and is not followed by another
     * method argument of type {@link BindingResult}.
     * By default an HTTP 400 error is sent back to the client.
     * @param request current HTTP request
     * @param response current HTTP response
     * @param handler the executed handler
     * @return an empty ModelAndView indicating the exception was handled
     * @throws IOException potentially thrown from response.sendError()
     */
    protected ModelAndView handleBindException(BindException ex, HttpServletRequest request,
                                               HttpServletResponse response, Object handler) throws IOException {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        _log.error(ex, ex);
        return new ModelAndView();
    }
}
