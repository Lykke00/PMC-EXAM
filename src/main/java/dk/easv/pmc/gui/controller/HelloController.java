package dk.easv.pmc.gui.controller;

import dk.easv.pmc.be.Category;
import dk.easv.pmc.be.Movie;
import dk.easv.pmc.be.ShowAlerts;
import dk.easv.pmc.bll.MovieLogic;
import dk.easv.pmc.gui.HelloApplication;
import dk.easv.pmc.gui.model.CategoryModel;
import dk.easv.pmc.gui.model.MovieModel;
import dk.easv.pmc.gui.view.PlaybackView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.controlsfx.control.CheckComboBox;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    private final CategoryModel catModel = new CategoryModel(this);
    private final PlaybackView playbackView;
    public MenuButton officialRating;
    @FXML private CheckComboBox<Category> ccbGenres;
    private final BigDecimal RATING_INC = new BigDecimal("0.5");
    private MovieModel movieModel;
    @FXML
    private TableColumn<Movie, String> tblColTitel;
    @FXML
    private TableColumn<Movie, String> tblColGenre;
    @FXML
    private TableColumn<Movie, Integer> tblColDuration;
    @FXML
    private TableColumn<Movie, Double> tblColRating;
    @FXML
    private TableColumn<Movie, Double> tblColOfficialRating;
    @FXML
    private TableColumn tblColPlay;
    @FXML
    private TableColumn tblColEdit;
    @FXML
    private TableView<Movie> movieListView;
    @FXML
    private TextField txtSearchBar;


    public HelloController() throws Exception {
        this.playbackView = new PlaybackView();
        try {
            this.movieModel = new MovieModel(this);
        } catch (Exception e) {
            ShowAlerts.displayError(e.getMessage());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        populateMovies();
        populateCategories();
    }

    private void populateMovies() {
        tblColTitel.setCellValueFactory(new PropertyValueFactory<>("name"));
        tblColGenre.setCellValueFactory(new PropertyValueFactory<>("GenresString"));
        tblColDuration.setCellValueFactory(new PropertyValueFactory<>("durationString"));
        tblColRating.setCellValueFactory(new PropertyValueFactory<>("PersonalRating"));
        tblColOfficialRating.setCellValueFactory(new PropertyValueFactory<>("IMDBrating"));

        tblColPlay.setCellFactory(new Callback<TableColumn<Movie, String>, TableCell<Movie, String>>() {
            @Override
            public TableCell<Movie, String> call(TableColumn<Movie, String> param) {
                return new TableCell<Movie, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty) {
                            setGraphic(null);
                        } else {
                            Button playButton = new Button("Play");
                            playButton.setOnAction(event -> {
                                Movie movie = getTableView().getItems().get(getIndex());
                                try {
                                    playbackView.play(movie.getCompleteFileLink());
                                } catch (Exception e) {

                                    ShowAlerts.displayError("Kunne ikke afspille film");
                                }
                            });
                            setGraphic(playButton);
                        }
                    }
                };
            }
        });


        try {
            movieListView.setItems(movieModel.getAllMovies());
        } catch (Exception e) {
            ShowAlerts.displayError(e.getMessage());
        }
    }

    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");

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
            controller.setModels(movieModel, catModel);
            stage.setTitle("Things");//TODO edit or add
            //stage.setTitle("Add/Edit Movie"); // TODO : få fat i selected items
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (Exception e) {
            ShowAlerts.displayError("Kan ikke åbne vinduet!");
        }

    }

    public void populateCategories() {
        try {
            List<Category> categories = catModel.getAllCategories();
            ccbGenres.getItems().addAll(categories);
            officialRating.getItems().clear();
            for(BigDecimal i = new BigDecimal(0); i.doubleValue() <= 10; i=i.add(RATING_INC)) {
                MenuItem item = new MenuItem(String.valueOf(i));
                item.setOnAction(event -> officialRating.setText(item.getText()));
                officialRating.getItems().add(item);
            }

        } catch (Exception e) {
            ShowAlerts.displayError("Kunne ikke hente kategorier");
        }


    }
    public List<Category> getSelectedCategories() {
        return ccbGenres.getCheckModel().getCheckedItems();
    }

    public double getSelectedOfficialRating() {
        if(officialRating.getText().equals("None"))
            return 0;
        try{
            return Double.parseDouble(officialRating.getText());
        } catch (NumberFormatException e) {
            return 0;
        }

    }

    @FXML
    private void onClear(ActionEvent event){
        txtSearchBar.clear();
        officialRating.setText("None");
        ccbGenres.getCheckModel().clearChecks();
    }
    @FXML
    private void onRemove(ActionEvent event) {
        try {
            Movie movie = movieListView.getSelectionModel().getSelectedItem();
            if (movie == null) {
                ShowAlerts.displayError("Vælg en film at slette");
                return;
            }
            movieModel.deleteMovie(movie);
            //movieListView.getItems().remove(movie);
        } catch (Exception e) {
            ShowAlerts.displayError("Kunne ikke slette film");
        }
    }
    @FXML
    public void checkOldLowRatedMovies() {
        try {
            List<Movie> oldLowRatedMovies = movieModel.getOldLowRatedMovies();

            if (!oldLowRatedMovies.isEmpty()) {
                StringBuilder warningMessage = new StringBuilder("The following movies have a low rating and haven't been opened in over 2 years:\n\n");

                for (Movie movie : oldLowRatedMovies) {
                    warningMessage.append("- ").append(movie.getName());
                }
                System.out.println(warningMessage.toString());
                ShowAlerts.displayOldMovies(warningMessage.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            ShowAlerts.displayError("Could not check for old low-rated movies.");
        }
    }
}