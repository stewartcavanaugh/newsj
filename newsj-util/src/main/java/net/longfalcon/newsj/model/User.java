package net.longfalcon.newsj.model;

import java.util.Date;

/**
 * User: Sten Martinez
 * Date: 11/6/15
 * Time: 4:57 PM
 */
public class User {
    private long id;
    private String username;
    private String email;
    private String password;
    private int role;
    private String host;
    private int grabs;
    private String rssToken;
    private Date createDate;
    private String resetGuid;
    private Date lastLogin;
    private Date apiAccess;
    private int invites;
    private Long invitedBy;
    private int movieView;
    private int musicView;
    private int consoleView;
    private String userseed;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getGrabs() {
        return grabs;
    }

    public void setGrabs(int grabs) {
        this.grabs = grabs;
    }

    public String getRssToken() {
        return rssToken;
    }

    public void setRssToken(String rssToken) {
        this.rssToken = rssToken;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getResetGuid() {
        return resetGuid;
    }

    public void setResetGuid(String resetGuid) {
        this.resetGuid = resetGuid;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Date getApiAccess() {
        return apiAccess;
    }

    public void setApiAccess(Date apiAccess) {
        this.apiAccess = apiAccess;
    }

    public int getInvites() {
        return invites;
    }

    public void setInvites(int invites) {
        this.invites = invites;
    }

    public Long getInvitedBy() {
        return invitedBy;
    }

    public void setInvitedBy(Long invitedBy) {
        this.invitedBy = invitedBy;
    }

    public int getMovieView() {
        return movieView;
    }

    public void setMovieView(int movieView) {
        this.movieView = movieView;
    }

    public int getMusicView() {
        return musicView;
    }

    public void setMusicView(int musicView) {
        this.musicView = musicView;
    }

    public int getConsoleView() {
        return consoleView;
    }

    public void setConsoleView(int consoleView) {
        this.consoleView = consoleView;
    }

    public String getUserseed() {
        return userseed;
    }

    public void setUserseed(String userseed) {
        this.userseed = userseed;
    }
}
