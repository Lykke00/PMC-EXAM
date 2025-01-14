package dk.easv.pmc.be;

import dk.easv.pmc.bll.MetadataExtractor;

import java.sql.Date;
import java.util.List;

public class Movie {
    private int id;
    private String name;
    private double IMDBrating;
    private double personalRating;
    private String fileLink;
    private String completeFileLink;
    private Date lastView;
    private double duration;
    private String durationString;
    private List<Category> categories;

    public Movie(int id, String name, double IMDBrating, double personalRating, String fileLink, Date lastView, double duration, List<Category> categories) {
        this.id = id;
        this.name = name;
        this.IMDBrating = IMDBrating;
        this.personalRating = personalRating;
        this.fileLink = fileLink;
        this.lastView = lastView;
        this.duration = duration;
        this.categories = categories;

        //this.durationString = MetadataExtractor.getDuration(fileLink);
        setCompleteFileLink(fileLink);
    }

    public Movie(String name, double IMDBrating, double personalRating, String fileLink, double duration, List<Category> categories) {
        this.name = name;
        this.IMDBrating = IMDBrating;
        this.personalRating = personalRating;
        this.fileLink = fileLink;
        this.duration = duration;
        this.categories = categories;

        this.durationString = MetadataExtractor.getDuration(fileLink);

        updateLastView();
        setCompleteFileLink(fileLink);
    }

    private void setCompleteFileLink(String fileLink) {
        if (fileLink.contains(":")){
            this.completeFileLink = fileLink;
            return;
        }
        String projectFolder = System.getProperty("user.dir");
        fileLink = fileLink.replace("\\", "/");
        this.completeFileLink = projectFolder + "/" + fileLink;
    }

    public void updateLastView() {
        java.util.Date now = new java.util.Date();
        this.lastView = new Date(now.getTime());
    }

    public String getCompleteFileLink() {
        return this.completeFileLink;
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

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
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
        return duration + "";
    }
}
