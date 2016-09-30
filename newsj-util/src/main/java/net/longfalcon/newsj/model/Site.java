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

import java.util.Date;

/**
 * User: Sten Martinez
 * Date: 10/5/15
 * Time: 9:57 PM
 */
public class Site {
    private long id;
    private String code;
    private String title;
    private String strapLine;
    private String metaTitle;
    private String metaDescription;
    private String metaKeywords;
    private String footer;
    private String email;
    private Date lastUpdate;
    private String googleAdSenseSearch;
    private String googleAdSenseSidePanel;
    private String googleAnalyticsAcc;
    private String googleAdSenseAcc;
    private String siteSeed;
    private String tandc;
    private int registerStatus;
    private String style;
    private int menuPosition;
    private String deReferrerLink;
    private String nzbPath;
    private int rawRetentionDays;
    private int attemtpGroupBinDays;
    private int lookupTvRage;
    private int lookupImdb;
    private int lookupNfo;
    private int lookupMusic;
    private int lookupGames;
    private String amazonPubKey;
    private String amazonPrivKey;
    private String tmdbKey;
    private boolean compressedHeaders;
    private int maxMessages;
    private int newGroupsScanMethod;
    private int newGroupDaysToScan;
    private int newGroupMsgsToScan;
    private int storeUserIps;
    private int minFilesToFormRelease;
    private String reqIdUrl;
    private String latestRegexUrl;
    private int latestRegexRevision;
    private int releaseRetentionDays;
    private int checkPasswordedRar;
    private int showPasswordedRelease;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isCompressedHeaders() {
        return compressedHeaders;
    }

    public void setCompressedHeaders(boolean compressedHeaders) {
        this.compressedHeaders = compressedHeaders;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getMaxMessages() {
        return maxMessages;
    }

    public void setMaxMessages(int maxMessages) {
        this.maxMessages = maxMessages;
    }

    public int getNewGroupsScanMethod() {
        return newGroupsScanMethod;
    }

    public void setNewGroupsScanMethod(int newGroupsScanMethod) {
        this.newGroupsScanMethod = newGroupsScanMethod;
    }

    public int getNewGroupDaysToScan() {
        return newGroupDaysToScan;
    }

    public void setNewGroupDaysToScan(int newGroupDaysToScan) {
        this.newGroupDaysToScan = newGroupDaysToScan;
    }

    public int getNewGroupMsgsToScan() {
        return newGroupMsgsToScan;
    }

    public void setNewGroupMsgsToScan(int newGroupMsgsToScan) {
        this.newGroupMsgsToScan = newGroupMsgsToScan;
    }

    public int getMinFilesToFormRelease() {
        return minFilesToFormRelease;
    }

    public void setMinFilesToFormRelease(int minFilesToFormRelease) {
        this.minFilesToFormRelease = minFilesToFormRelease;
    }

    public String getNzbPath() {
        return nzbPath;
    }

    public void setNzbPath(String nzbPath) {
        this.nzbPath = nzbPath;
    }

    public String getStrapLine() {
        return strapLine;
    }

    public void setStrapLine(String strapLine) {
        this.strapLine = strapLine;
    }

    public String getMetaTitle() {
        return metaTitle;
    }

    public void setMetaTitle(String metaTitle) {
        this.metaTitle = metaTitle;
    }

    public String getMetaDescription() {
        return metaDescription;
    }

    public void setMetaDescription(String metaDescription) {
        this.metaDescription = metaDescription;
    }

    public String getMetaKeywords() {
        return metaKeywords;
    }

    public void setMetaKeywords(String metaKeywords) {
        this.metaKeywords = metaKeywords;
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getSiteSeed() {
        return siteSeed;
    }

    public void setSiteSeed(String siteSeed) {
        this.siteSeed = siteSeed;
    }

    public String getTandc() {
        return tandc;
    }

    public void setTandc(String tandc) {
        this.tandc = tandc;
    }

    public int getRegisterStatus() {
        return registerStatus;
    }

    public void setRegisterStatus(int registerStatus) {
        this.registerStatus = registerStatus;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public int getMenuPosition() {
        return menuPosition;
    }

    public void setMenuPosition(int menuPosition) {
        this.menuPosition = menuPosition;
    }

    public String getDeReferrerLink() {
        return deReferrerLink;
    }

    public void setDeReferrerLink(String deReferrerLink) {
        this.deReferrerLink = deReferrerLink;
    }

    public int getStoreUserIps() {
        return storeUserIps;
    }

    public void setStoreUserIps(int storeUserRips) {
        this.storeUserIps = storeUserRips;
    }

    public String getReqIdUrl() {
        return reqIdUrl;
    }

    public void setReqIdUrl(String reqIdUrl) {
        this.reqIdUrl = reqIdUrl;
    }

    public String getLatestRegexUrl() {
        return latestRegexUrl;
    }

    public void setLatestRegexUrl(String latestRegexUrl) {
        this.latestRegexUrl = latestRegexUrl;
    }

    public int getLatestRegexRevision() {
        return latestRegexRevision;
    }

    public void setLatestRegexRevision(int latestRegexRevision) {
        this.latestRegexRevision = latestRegexRevision;
    }

    public int getReleaseRetentionDays() {
        return releaseRetentionDays;
    }

    public void setReleaseRetentionDays(int releaseRetentionDays) {
        this.releaseRetentionDays = releaseRetentionDays;
    }

    public int getCheckPasswordedRar() {
        return checkPasswordedRar;
    }

    public void setCheckPasswordedRar(int checkPasswordedRar) {
        this.checkPasswordedRar = checkPasswordedRar;
    }

    public int getShowPasswordedRelease() {
        return showPasswordedRelease;
    }

    public void setShowPasswordedRelease(int showPasswordedRelease) {
        this.showPasswordedRelease = showPasswordedRelease;
    }

    public int getRawRetentionDays() {
        return rawRetentionDays;
    }

    public void setRawRetentionDays(int rawRetentionDays) {
        this.rawRetentionDays = rawRetentionDays;
    }

    public int getAttemtpGroupBinDays() {
        return attemtpGroupBinDays;
    }

    public void setAttemtpGroupBinDays(int attemtpGroupBinDays) {
        this.attemtpGroupBinDays = attemtpGroupBinDays;
    }

    public int getLookupTvRage() {
        return lookupTvRage;
    }

    public void setLookupTvRage(int lookupTvRage) {
        this.lookupTvRage = lookupTvRage;
    }

    public int getLookupImdb() {
        return lookupImdb;
    }

    public void setLookupImdb(int lookupImdb) {
        this.lookupImdb = lookupImdb;
    }

    public int getLookupNfo() {
        return lookupNfo;
    }

    public void setLookupNfo(int lookupNfo) {
        this.lookupNfo = lookupNfo;
    }

    public int getLookupMusic() {
        return lookupMusic;
    }

    public void setLookupMusic(int lookupMusic) {
        this.lookupMusic = lookupMusic;
    }

    public int getLookupGames() {
        return lookupGames;
    }

    public void setLookupGames(int lookupGames) {
        this.lookupGames = lookupGames;
    }

    public String getAmazonPubKey() {
        return amazonPubKey;
    }

    public void setAmazonPubKey(String amazonPubKey) {
        this.amazonPubKey = amazonPubKey;
    }

    public String getAmazonPrivKey() {
        return amazonPrivKey;
    }

    public void setAmazonPrivKey(String amazonPrivKey) {
        this.amazonPrivKey = amazonPrivKey;
    }

    public String getTmdbKey() {
        return tmdbKey;
    }

    public void setTmdbKey(String tmdbKey) {
        this.tmdbKey = tmdbKey;
    }

    public String getGoogleAdSenseSearch() {
        return googleAdSenseSearch;
    }

    public void setGoogleAdSenseSearch(String googleAdSenseSearch) {
        this.googleAdSenseSearch = googleAdSenseSearch;
    }

    public String getGoogleAdSenseSidePanel() {
        return googleAdSenseSidePanel;
    }

    public void setGoogleAdSenseSidePanel(String googleAdSenseSidePanel) {
        this.googleAdSenseSidePanel = googleAdSenseSidePanel;
    }

    public String getGoogleAnalyticsAcc() {
        return googleAnalyticsAcc;
    }

    public void setGoogleAnalyticsAcc(String googleAnalyticsAcc) {
        this.googleAnalyticsAcc = googleAnalyticsAcc;
    }

    public String getGoogleAdSenseAcc() {
        return googleAdSenseAcc;
    }

    public void setGoogleAdSenseAcc(String googleAdSenseAcc) {
        this.googleAdSenseAcc = googleAdSenseAcc;
    }
}
