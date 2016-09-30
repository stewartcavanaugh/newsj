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

package net.longfalcon.newsj.model;

/**
 * User: Sten Martinez
 * Date: 10/14/15
 * Time: 8:26 AM
 */
public class ReleaseNfo {
    private long id;
    private Release release;
    private Binary binary;
    private int attemtps;
    private byte[] nfo = null;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getAttemtps() {
        return attemtps;
    }

    public void setAttemtps(int attemtps) {
        this.attemtps = attemtps;
    }

    public byte[] getNfo() {
        return nfo;
    }

    public void setNfo(byte[] nfo) {
        this.nfo = nfo;
    }

    public Release getRelease() {
        return release;
    }

    public void setRelease(Release release) {
        this.release = release;
    }

    public Binary getBinary() {
        return binary;
    }

    public void setBinary(Binary binary) {
        this.binary = binary;
    }
}
