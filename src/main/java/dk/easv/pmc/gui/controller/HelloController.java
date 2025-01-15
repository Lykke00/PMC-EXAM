package dk.easv.pmc.gui.controller;

import dk.easv.pmc.be.Category;
import dk.easv.pmc.be.Movie;
import dk.easv.pmc.gui.utils.ShowAlerts;
import dk.easv.pmc.gui.HelloApplication;
import dk.easv.pmc.gui.model.CategoryModel;
import dk.easv.pmc.gui.model.MovieModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.controlsfx.control.CheckComboBox;


import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.math.BigDecimal;
import java.util.List;

public class HelloController implements Initializable {
    private final CategoryModel catModel = new CategoryModel(this);

    public MenuButton officialRating;
    @FXML private CheckComboBox<Category> ccbGenres;
    private final BigDecimal RATING_INC = new BigDecimal("0.5");
    private MovieModel movieModel;
    @FXML
    private TableColumn<Movie, String> tblColTitel;
    @FXML
    private TableColumn<Movie, String> tblColGenre;
    @FXML
    private TableColumn<Movie, String> tblColDuration;
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
    private TextField txtSearchField;

    private final Map<String, String> durationCache = new HashMap<>();


    public HelloController() throws Exception {
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
        searchHandler();

        // lad vær med at fokuser på tekstfelt når programmet starter
        txtSearchField.setFocusTraversable(false);
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
                                File movieFile = new File(movie.getCompleteFileLink());

                                if (!movieFile.exists()) {
                                    System.out.println(movie.getCompleteFileLink());
                                    ShowAlerts.displayError("File not found");
                                    return;
                                }

                                if (Desktop.isDesktopSupported()) {
                                    Desktop desktop = Desktop.getDesktop();
                                    try {
                                        desktop.open(movieFile);
                                    } catch (IOException e) {
                                        ShowAlerts.displayError(("kunne ikke åbne systemafspiller"));
                                    }
                                }
                            });
                            setGraphic(playButton);
                        }
                    }
                };
            }
        });

        tblColEdit.setCellFactory(new Callback<TableColumn<Movie, String>, TableCell<Movie, String>>() {
            @Override
            public TableCell<Movie, String> call(TableColumn<Movie, String> param) {
                return new TableCell<Movie, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty) {
                            setGraphic(null);
                        } else {
                            Button editButton = new Button("Edit");
                            editButton.setOnAction(event ->{
                                Movie movie = getTableView().getItems().get(getIndex());
                                editAddMovie(movie);
                            });
                            setGraphic(editButton);
                        }
                    }
                };
            }
        });

        Label noDataLabel = new Label("Ingen resultater fundet");
        movieListView.setPlaceholder(noDataLabel);

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
        }
        catch (IOException e) {
            ShowAlerts.displayError("Kunne ikke åbne vinduet");
        }
    }


    @FXML
    private void onAddClick(ActionEvent actionEvent) {
        editAddMovie(null);
    }

    private void editAddMovie(Movie movieToEdit){
        String title = "Add movie";
        try {
            // load vinduet
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(HelloApplication.class.getResource("/dk/easv/pmc/Add-EditMovie.fxml"));

            Stage stage = new Stage();
            Parent scene = loader.load();
            stage.setScene(new Scene(scene));


            // init ting
            CreateEditMovieController controller = loader.getController();
            controller.setStage(stage);
            controller.setModels(movieModel, catModel);
            // edit or add
            if (movieToEdit != null) {
                title = "Edit movie; " + movieToEdit.getName();
                controller.setEditedMovie(movieToEdit);
            }
            stage.setTitle(title);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            movieListView.refresh();
        } catch (Exception e) {
            e.printStackTrace();
            ShowAlerts.displayError("Kan ikke åbne vinduet!");
        }
    }

    // tydeligvis så er det både kategoprier og rating der bliver udfyldt her ;)
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
        txtSearchField.clear();
        officialRating.setText("None");
        ccbGenres.getCheckModel().clearChecks();
        try {
            movieListView.setItems(movieModel.getAllMovies());
        } catch (Exception e) {
            ShowAlerts.displayError("Kunne ikke hente filmene");
        }
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

        private void searchHandler() {
            txtSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
                movieListView.setItems(movieModel.searchMovie(newValue));
            });
            officialRating.addEventHandler(MenuButton.ON_HIDDEN, event -> {
                movieListView.setItems(movieModel.getMoviesByOfficialRating(movieListView.getItems()));
            });
            ccbGenres.addEventHandler(ComboBox.ON_HIDDEN, event -> {
                //Først så finder vi de film der er søgt på i søgefeltet. Derefter filtrerer vi på de valgt rating, hvor vi tilsidst filtrerer på de valgte kategorier.
                movieListView.setItems(catModel.getMoviesbySelectedCategory(movieModel.getMoviesByOfficialRating(movieModel.searchMovie(txtSearchField.getText()))));
            });
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