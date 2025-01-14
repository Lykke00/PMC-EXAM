package dk.easv.pmc.gui.controller;

import dk.easv.pmc.be.Category;
import dk.easv.pmc.be.Movie;
import dk.easv.pmc.gui.utils.ShowAlerts;
import dk.easv.pmc.bll.MetadataExtractor;
import dk.easv.pmc.gui.model.CategoryModel;
import dk.easv.pmc.gui.model.MovieModel;

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
    private Movie editMovie = null;


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
    public void setEditedMovie(Movie movie){
        // fyld tingene ud
        txtTitle.setText(movie.getName());

        chosenCategories = movie.getCategories();
        for (Category cat : chosenCategories){
            changeCategory(cat.getName());
        }

        txtDuration.setText(MetadataExtractor.getDuration(movie.getCompleteFileLink()));
        txtRating.setText(movie.getPersonalRating() + "");
        txtRatingOff.setText(movie.getIMDBrating() + "");
        txtFilePath.setText(movie.getFileLink());
        editMovie = movie;
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
        double duration = 0;
        double ratingPers = 0;
        double ratingOff = 0;
        try{
            ratingOff = Double.parseDouble(txtRatingOff.getText());
        }
        catch (Exception e) {
           // den officielle rating sættes til 0 hvis intet er udfyldt - hvis dette udkom ikke er ønsket - så sæt noget her (ShowAlerts el. lign.) med return statement
        }
        try {
            ratingPers = Double.parseDouble(txtRating.getText());
        } catch (Exception e) {
            //e.printStackTrace(); // Dette er det samme eksempel om ovenfor ^^^^^
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

        // opdatere filmen
        if (editMovie != null){
            try{
                editMovie.setName(title);
                editMovie.setCategories(chosenCategories);
                editMovie.setIMDBrating(ratingOff);
                editMovie.setPersonalRating(ratingPers);
                editMovie.setFileLink(path);
                editMovie.setDuration(duration);
                mm.updateMovie(editMovie);
            } catch (Exception e) {
                ShowAlerts.displayError("Kunne ikke opdatere filmen!");
            }

        }
        // "Create" film
        else{
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
