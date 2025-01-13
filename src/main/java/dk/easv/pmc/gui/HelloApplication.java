package dk.easv.pmc.gui;

import dk.easv.pmc.gui.controller.HelloController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/dk/easv/pmc/hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Private Movie Collector!");
        stage.setScene(scene);
        stage.show();

        // Tjek for gamle film med lav rating
       HelloController controller = fxmlLoader.getController();
        controller.checkOldLowRatedMovies(); // Tilføjet kald til advarselsmetoden
    }

    public static void main(String[] args) {
        launch();
    }
}
