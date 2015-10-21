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
