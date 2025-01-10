package dk.easv.pmc.gui.controller;

import dk.easv.pmc.be.Category;
import dk.easv.pmc.be.ShowAlerts;
import dk.easv.pmc.bll.CategoryLogic;
import dk.easv.pmc.gui.HelloApplication;
import dk.easv.pmc.gui.model.CategoryModel;
import dk.easv.pmc.gui.model.MovieModel;
import dk.easv.pmc.gui.view.PlaybackView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import org.controlsfx.control.CheckComboBox;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    private final CategoryModel catModel = new CategoryModel(this);//Slet hvis lykke laver en
    private final PlaybackView playbackView;
    @FXML private CheckComboBox<Category> ccbGenres;

    public HelloController() throws Exception {
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
        /*
        e.printStackTrace();

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(e.getMessage());
        alert.showAndWait();

         */
        ShowAlerts.displayError("Kunne ikke åbne vinduet");
    }
    }


    @FXML
    private void onAddClick(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(HelloApplication.class.getResource("/dk/easv/pmc/Add-EditMovie.fxml"));

            Stage stage = new Stage();
            Parent scene = loader.load();
            stage.setScene(new Scene(scene));

            CreateEditMovieController controller = loader.getController();
            controller.setStage(stage);
            controller.setModels(new MovieModel(), new CategoryModel(this));
            stage.setTitle("Things");//TODO edit or add
            //stage.setTitle("Add/Edit Movie"); // TODO : få fat i selected items
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (Exception e) {
            ShowAlerts.displayError("Kan ikke åbne vinduet!");
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            List<Category> categories = catModel.getAllCategories();
            ccbGenres.getItems().addAll(categories);
        } catch (Exception e) {
            ShowAlerts.displayError("Kunne ikke hente kategorier");
        }


    }
    public List<Category> getSelectedCategories() {
        return ccbGenres.getCheckModel().getCheckedItems();
    }
}