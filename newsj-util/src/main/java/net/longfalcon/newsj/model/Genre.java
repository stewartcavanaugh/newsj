package net.longfalcon.newsj.model;

/**
 * User: Sten Martinez
 * Date: 11/6/15
 * Time: 4:04 PM
 */
public class Genre {
    private long id;
    private String title;
    private Integer type;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
