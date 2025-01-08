package dk.easv.pmc.gui.controller;

import dk.easv.pmc.gui.model.MovieModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateEditMovieController{
    private MovieModel mm;


    @FXML
    private ComboBox btnCategory;
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

    public void setMovieModel(MovieModel movieModel){
        this.mm = movieModel;
    }

    @FXML
    private void saveMovie(ActionEvent actionEvent) {
        String title = txtTitle.getText().trim();
        if (title.it)
    }

    @FXML
    private void choosePath(ActionEvent actionEvent) {
    }
}
