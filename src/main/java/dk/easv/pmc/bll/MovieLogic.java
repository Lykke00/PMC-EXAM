package dk.easv.pmc.bll;

import dk.easv.pmc.be.Movie;
import dk.easv.pmc.dal.dao.IMovieDAO;
import dk.easv.pmc.dal.dao.MovieDAO;
import dk.easv.pmc.gui.model.MovieModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class MovieLogic {
    private final IMovieDAO movieDAO;
    private final MovieModel movieModel;

    public MovieLogic(MovieModel movieModel) throws Exception {
        this.movieModel = movieModel;
        this.movieDAO = new MovieDAO();
    }

    public boolean deleteMovie(Movie movie) throws Exception {
        return this.movieDAO.deleteMovie(movie);
    }

    public Movie createMovie(Movie movie) throws Exception {
        return this.movieDAO.createMovie(movie);
    }

    public ArrayList<Movie> getAllMovies() throws Exception {
        return this.movieDAO.getAllMovies();
    }
    public ObservableList<Movie> getMoviesbyOfficialRating(ObservableList<Movie> movies) {
        if (movies == null) {
            return null;
        }
        ObservableList<Movie> filteredOfficialRating = FXCollections.observableArrayList();
        double selectedOfficialRating = movieModel.getSelectedOfficialRating();
        for(Movie m: movies){
            if(m.getIMDBrating()>=selectedOfficialRating){
                filteredOfficialRating.add(m);
            }
        }
        movies.setAll(filteredOfficialRating);
      return movies;
    }

    public List<Movie> getOldLowRatedMovies() throws Exception {
        return movieDAO.getOldLowRatedMovies();
    }

    public boolean updateMovie(Movie movie) throws Exception{
        return movieDAO.updateMovie(movie);
    }

}

