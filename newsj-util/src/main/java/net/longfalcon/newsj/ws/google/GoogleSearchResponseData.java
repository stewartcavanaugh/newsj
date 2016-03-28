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

package net.longfalcon.newsj.ws.google;

import java.util.List;

/**
 * User: Sten Martinez
 * Date: 10/21/15
 * Time: 12:16 PM
 */
public class GoogleSearchResponseData {
    private List<GoogleSearchResult> results;
    private GoogleSearchCursor cursor;

    public List<GoogleSearchResult> getResults() {
        return results;
    }

    public void setResults(List<GoogleSearchResult> results) {
        this.results = results;
    }

    public GoogleSearchCursor getCursor() {
        return cursor;
    }

    public void setCursor(GoogleSearchCursor cursor) {
        this.cursor = cursor;
    }
}
