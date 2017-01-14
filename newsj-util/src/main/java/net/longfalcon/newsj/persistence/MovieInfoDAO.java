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

package net.longfalcon.newsj.persistence;

import net.longfalcon.newsj.model.MovieInfo;

import java.util.List;

/**
 * User: Sten Martinez
 * Date: 3/16/16
 * Time: 1:51 PM
 */
public interface MovieInfoDAO {
    public void update(MovieInfo movieInfo);
    public void delete(MovieInfo movieInfo);
    public Long countMovieInfos();
    public List<MovieInfo> getMovieInfos(int offset, int pageSize);

    MovieInfo findByMovieInfoId(long id);

    MovieInfo findByImdbId(int imdbId);

    List<MovieInfo> findByImdbId(List<Long> imdbIds);

    Long getMovieCount(List<Long> imdbIds, String titleSearch, String genreSearch, String actorsSearch,
                       String directorSearch, String yearSearch);

    List<MovieInfo> getMovies(List<Long> imdbIds, String titleSearch, String genreSearch, String actorsSearch,
                                  String directorSearch, String yearSearch,
                                  int offset, int pageSize, String orderByField, boolean descending);
}
