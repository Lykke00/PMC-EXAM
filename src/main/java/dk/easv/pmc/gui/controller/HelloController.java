package dk.easv.pmc.gui.controller;

import dk.easv.pmc.gui.view.PlaybackView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.io.File;

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
}