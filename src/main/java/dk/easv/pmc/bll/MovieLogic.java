package dk.easv.pmc.bll;

import dk.easv.pmc.be.Category;
import dk.easv.pmc.be.Movie;
import dk.easv.pmc.dal.dao.IMovieDAO;
import dk.easv.pmc.dal.dao.MovieDAO;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jdk.jshell.execution.FailOverExecutionControlProvider;

import java.util.ArrayList;
import java.util.List;

public class MovieLogic {
    private final IMovieDAO movieDAO;

    public MovieLogic() throws Exception {
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

}

