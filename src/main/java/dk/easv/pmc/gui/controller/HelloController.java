package dk.easv.pmc.gui.controller;

import dk.easv.pmc.gui.HelloApplication;
import dk.easv.pmc.gui.view.PlaybackView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class HelloController {
    private final PlaybackView playbackView;

    public HelloController() {
        this.playbackView = new PlaybackView();
    }

    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");

    }

    @FXML
    private void playMovie(ActionEvent actionEvent) {
        try {
            playbackView.play("movies/sample.mp4");
        } catch (Exception e) {
            //TODO: håndter i alert
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void onManageCategoryClick(ActionEvent actionEvent) {
    try {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("/dk/easv/pmc/Manage-Category.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Manage Categories");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    } catch (IOException e) {
        e.printStackTrace();

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }
    }
}