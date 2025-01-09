package dk.easv.pmc.gui.controller;

import dk.easv.pmc.be.Category;
import dk.easv.pmc.be.Movie;
import dk.easv.pmc.be.ShowAlerts;
import dk.easv.pmc.gui.model.MovieModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CreateEditMovieController{
    private MovieModel mm;
    private CategoryModel cm;


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

    private void init(){
        List<Category> cats = cm.getAllCategories();
        for (Category cat : cats){
            btnCategory.getItems().add(cat.getName());
        }
    }

    @FXML
    private void saveMovie(ActionEvent actionEvent) {
        String title = txtTitle.getText().trim();
        if (title.isEmpty()) {
            ShowAlerts.displayError("Title is empty");
            return;
        }
        String genre = "None"; // TODO : FÅ FAT I GENRERNE
        // txtDuration - er disabled
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
            //e.printStackTrace();
        }

        String path = txtFilePath.getText().trim();
        if (path.isEmpty()){
            ShowAlerts.displayError("Path is empty");
            return;
        }

        Movie movie = new Movie(title, ratingOff, ratingPers, path, 0);
        try{
            mm.createMovie(movie);
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
        }
    }


   public static void main(String[] args){
        System.out.println(Double.parseDouble(""));
    }
}
