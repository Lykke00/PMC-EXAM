package dk.easv.pmc.gui.controller;

import dk.easv.pmc.be.Category;
import dk.easv.pmc.be.Movie;
import dk.easv.pmc.be.ShowAlerts;
import dk.easv.pmc.bll.MetadataExtractor;
import dk.easv.pmc.bll.MovieLogic;
import dk.easv.pmc.gui.model.CategoryModel;
import dk.easv.pmc.gui.model.MovieModel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.util.*;

public class CreateEditMovieController{
    private MovieModel mm;
    private CategoryModel cm;
    private Stage stage;
    private List<Category> chosenCategories = new ArrayList<>();
    private Map<String, Category> catMap = new HashMap<>();


    @FXML
    private ComboBox<String> btnCategory;
    @FXML
    private TextField txtTitle;
    @FXML
    private TextField txtGenre;
    @FXML
    private TextField txtDuration;
    @FXML
    private TextField txtRating;
    @FXML
    private TextField txtRatingOff;
    @FXML
    private TextField txtFilePath;

    public void setModels(MovieModel movieModel, CategoryModel catModel){
        this.mm = movieModel;
        this.cm = catModel;

        init();
    }
    public void setStage(Stage window){
        this.stage = window;
    }

    private void init(){
        try {
            List<Category> cats = cm.getAllCategories();
            for (Category cat : cats){
                catMap.put(cat.getName(), cat);
                btnCategory.getItems().add(cat.getName());
            }
            btnCategory.addEventHandler(ActionEvent.ACTION, event -> {
                try {
                    ComboBox cb = (ComboBox) event.getSource();
                    changeCategory((String) cb.getValue());
                } catch (Exception e) {
                    System.out.println("nej");
                    ShowAlerts.displayError("Kunne ikke få kategorien");
                }
            });
        } catch (Exception e) {
            ShowAlerts.displayError("Kunne ikke hente genrerne!");
            stage.close();
        }
    }

    @FXML
    private void saveMovie(ActionEvent actionEvent) {
        String title = txtTitle.getText().trim();
        if (title.isEmpty()) {
            ShowAlerts.displayError("Title is empty");
            return;
        }

        // txtDuration - er disabled
        double duration = 0; // TODO : get duration
        double ratingPers = 0;
        double ratingOff = 0;
        try{
            ratingOff = Double.parseDouble(txtRatingOff.getText());
        }
        catch (Exception e) {
            //e.printStackTrace(); - jeg er ikke sikker på at den returner - men man ved aldrig
        }
        try {
            ratingPers = Double.parseDouble(txtRating.getText());
        } catch (Exception e) {
            //e.printStackTrace(); // TODO : display errors - also up there ^^^^^^
        }

        String path = txtFilePath.getText().trim();
        if (path.isEmpty()){
            ShowAlerts.displayError("Path is empty");
            return;
        }
        else{
            try{
                duration = Double.parseDouble(MetadataExtractor.getDuration(path));
                txtDuration.setText(String.valueOf(duration));
            } catch (NumberFormatException e) {
                ShowAlerts.displayError("Kunne ikke hente længden af filen");
            }
        }

        Movie movie = new Movie(title, ratingOff, ratingPers, path, duration, chosenCategories);
        try{
            System.out.println(chosenCategories);
            mm.createMovie(movie);
            stage.close();
        }
        catch (Exception e) {
            ShowAlerts.displayError("Kunne ikke tilføje filmen. " + e.getMessage());
        }

    }

    @FXML
    private void choosePath(ActionEvent actionEvent) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Choose a file");
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Mp4 Files", "*.mp4"),
                new FileChooser.ExtensionFilter("Mpeg4 Files", "*.mpeg4")
        );
        File file = chooser.showOpenDialog(null);
        if (file != null) {
            txtFilePath.setText(file.getPath());
            //parent.getMyTunesModel().setDurationOfFile(file.getPath(), this);
            txtDuration.setText(MetadataExtractor.getDuration(file.getPath()));
        }
    }

    private void changeCategory(String catName) {
        if (chosenCategories.contains(catMap.get(catName))) {
            chosenCategories.remove(catMap.get(catName));
        }
        else{
            System.out.println(catName);
            chosenCategories.add(catMap.get(catName));
        }

        StringBuilder displayText = new StringBuilder();
        for (Category cat : chosenCategories){
            displayText.append(cat.getName()).append(", ");
        }
        displayText.setLength(displayText.length() - 2);

        txtGenre.setText(displayText.toString());
        System.out.println(catName);
    }


   public static void main(String[] args) throws Exception {

    }
}
