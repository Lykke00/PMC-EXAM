package dk.easv.pmc.gui.view;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
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

public class PlaybackView {

    public void play(String mediaFilePath) throws Exception {
        try {
            Media media = new Media(new File(mediaFilePath).toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            MediaView mediaView = createMediaView(mediaPlayer);

            StackPane root = createView(mediaPlayer, mediaView);

            Scene scene = new Scene(root, 800, 600);
            Stage mediaStage = new Stage();
            mediaStage.setScene(scene);
            mediaStage.setTitle("Media Player");

            InvalidationListener resizeMediaView = observable -> {
                mediaView.setFitWidth(root.getWidth());
                mediaView.setFitHeight(root.getHeight());

                Bounds actualVideoSize = mediaView.getLayoutBounds();
                mediaView.setX((mediaView.getFitWidth() - actualVideoSize.getWidth()) / 2);
                mediaView.setY((mediaView.getFitHeight() - actualVideoSize.getHeight()) / 2);
            };

            root.heightProperty().addListener(resizeMediaView);
            root.widthProperty().addListener(resizeMediaView);

            mediaStage.show();

            /* DEBUG
            mediaPlayer.setOnReady(() -> System.out.println("MediaPlayer is ready"));
            mediaPlayer.setOnPlaying(() -> System.out.println("MediaPlayer is playing"));
            mediaPlayer.setOnPaused(() -> System.out.println("MediaPlayer is paused"));
            mediaPlayer.setOnStopped(() -> System.out.println("MediaPlayer is stopped"));
            mediaPlayer.setOnEndOfMedia(() -> System.out.println("MediaPlayer reached end of media"));
            mediaPlayer.setOnError(() -> System.out.println("MediaPlayer error: " + mediaPlayer.getError().getMessage()));
            */

        } catch (Exception e) {
            throw new Exception("Kunne ikke afspille film");
        }
    }

    private StackPane createView(MediaPlayer mediaPlayer, MediaView mediaView) {
        Button playButton = new Button("Play");
        playButton.setMinWidth(100);
        playButton.setMinHeight(50);

        playButton.setOnAction(event -> {
            boolean playing = mediaPlayer.getStatus().equals(MediaPlayer.Status.PLAYING);

            if (!playing) {
                mediaPlayer.play();
                playButton.setText("Pause");
            } else {
                mediaPlayer.pause();
                playButton.setText("Play");

            }
        });

        StackPane.setAlignment(playButton, javafx.geometry.Pos.BOTTOM_LEFT);

        Platform.runLater(() -> {
            javafx.animation.PauseTransition hideButtonTransition = new javafx.animation.PauseTransition(javafx.util.Duration.seconds(2));
            hideButtonTransition.setOnFinished(e -> playButton.setOpacity(0));
            hideButtonTransition.play();
        });

        // Show the button when hovering over the video
        mediaView.setOnMouseEntered(event -> playButton.setOpacity(1));
        mediaView.setOnMouseExited(event -> {
            if (playButton.getOpacity() == 1) {
                javafx.animation.PauseTransition hideButtonTransition = new javafx.animation.PauseTransition(javafx.util.Duration.seconds(2));
                hideButtonTransition.setOnFinished(e -> playButton.setOpacity(0));
                hideButtonTransition.play();
            }
        });

        return new StackPane(mediaView, playButton);
    }

    public MediaView createMediaView(MediaPlayer mediaPlayer) {
        MediaView mediaView = new MediaView(mediaPlayer);

        // Error handling
        mediaPlayer.setOnError(() -> {
            System.out.println("Media error: " + mediaPlayer.getError().getMessage());
        });

        return mediaView;
    }
}
