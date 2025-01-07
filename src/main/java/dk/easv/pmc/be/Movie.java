package dk.easv.pmc.be;

import java.sql.Date;

public class Movie {
    private int id;
    private String name;
    private double IMDBrating;
    private double personalRating;
    private String fileLink;
    private Date lastView;

    public Movie(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }

    private String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    private double getIMDBrating() {
        return IMDBrating;
    }

    private void setIMDBrating(double IMDBrating) {
        this.IMDBrating = IMDBrating;
    }

    private double getPersonalRating() {
        return personalRating;
    }

    private void setPersonalRating(double personalRating) {
        this.personalRating = personalRating;
    }

    private String getFileLink() {
        return fileLink;
    }

    private void setFileLink(String fileLink) {
        this.fileLink = fileLink;
    }

    private Date getLastView() {
        return lastView;
    }

    private void setLastView(Date lastView) {
        this.lastView = lastView;
    }
}
