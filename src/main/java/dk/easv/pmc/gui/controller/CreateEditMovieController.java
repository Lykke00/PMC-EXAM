package dk.easv.pmc.gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class CreateEditMovieController {


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

    @FXML
    private void saveMovie(ActionEvent actionEvent) {
    }

    @FXML
    private void choosePath(ActionEvent actionEvent) {
    }
}
