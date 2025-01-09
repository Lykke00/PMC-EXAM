package dk.easv.pmc.be;

import java.sql.Date;
import java.util.List;

public class Movie {
    private int id;
    private String name;
    private double IMDBrating;
    private double personalRating;
    private String fileLink;
    private Date lastView;
    private int duration;
    private List<Category> categories;

    public Movie(int id, String name, double IMDBrating, double personalRating, String fileLink, Date lastView, int duration, List<Category> categories) {
        this.id = id;
        this.name = name;
        this.IMDBrating = IMDBrating;
        this.personalRating = personalRating;
        this.fileLink = fileLink;
        this.lastView = lastView;
        this.duration = duration;
        this.categories = categories;
    }

    public Movie(String name, double IMDBrating, double personalRating, String fileLink, int duration, List<Category> categories) {
        this.name = name;
        this.IMDBrating = IMDBrating;
        this.personalRating = personalRating;
        this.fileLink = fileLink;
        this.duration = duration;
        this.categories = categories;

        updateLastView();
    }

    public void updateLastView() {
        java.util.Date now = new java.util.Date();
        this.lastView = new Date(now.getTime());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getIMDBrating() {
        return IMDBrating;
    }

    public void setIMDBrating(double IMDBrating) {
        this.IMDBrating = IMDBrating;
    }

    public double getPersonalRating() {
        return personalRating;
    }

    public void setPersonalRating(double personalRating) {
        this.personalRating = personalRating;
    }

    public String getFileLink() {
        return fileLink;
    }

    public void setFileLink(String fileLink) {
        this.fileLink = fileLink;
    }

    public Date getLastView() {
        return lastView;
    }

    public void setLastView(Date lastView) {
        this.lastView = lastView;
    }

    public int getDuration() {
        return duration;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Category> getCategories() {
        return categories;
    }
}
