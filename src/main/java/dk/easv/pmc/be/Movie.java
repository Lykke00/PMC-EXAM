package dk.easv.pmc.be;

import dk.easv.pmc.bll.MetadataExtractor;
import javafx.application.Platform;
import org.apache.tika.Tika;
import org.apache.tika.metadata.Metadata;

import java.io.File;
import java.io.IOException;
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
    private String durationString;
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

        this.durationString = MetadataExtractor.getDuration(fileLink);
    }

    public Movie(String name, double IMDBrating, double personalRating, String fileLink, int duration, List<Category> categories) {
        this.name = name;
        this.IMDBrating = IMDBrating;
        this.personalRating = personalRating;
        this.fileLink = fileLink;
        this.duration = duration;
        this.categories = categories;

        this.durationString = MetadataExtractor.getDuration(fileLink);

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

    public String getGenresString() {
        String genres = "";
        int length = categories.size();

        for (int i = 0; i < length; i++) {
            Category category = categories.get(i);
            if (length == i + 1)
                genres = genres + category.getName();
            else
                genres = genres + category.getName() + ", ";
        }
        return genres;
    }

    public String getDurationString() {
        return this.durationString;
    }
}
