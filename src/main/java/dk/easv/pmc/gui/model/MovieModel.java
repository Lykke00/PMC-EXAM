package dk.easv.pmc.gui.model;

import dk.easv.pmc.be.Movie;
import dk.easv.pmc.bll.MovieLogic;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class MovieModel {
    private final MovieLogic movieLogic;
    private ObservableList<Movie> movies;

    public MovieModel() throws Exception {
        movieLogic = new MovieLogic();
    }

    public boolean deleteMovie(Movie movie) throws Exception {
        return this.movieLogic.deleteMovie(movie);
    }

    public Movie createMovie(Movie movie) throws Exception {
        return this.movieLogic.createMovie(movie);
    }

    public ArrayList<Movie> getAllMoviesFromDb() throws Exception {
        return this.movieLogic.getAllMovies();
    }

    public ObservableList<Movie> getAllMovies() throws Exception {
        if (this.movies == null) {
            movies = FXCollections.observableArrayList(movieLogic.getAllMovies());
        }
        return this.movies;
    }

    public static void main(String[] args) throws Exception {
        MovieModel movieModel = new MovieModel();

        Movie m = new Movie("hej", 2.5, 6, "ggtg", 234, null);

        Movie sm = movieModel.createMovie(m);

        if (sm.getId() > 0)
            System.out.println("lykke er sej");

        //Movie movie = new Movie(1);

        //boolean n = movieModel.deleteMovie(movie);

        //if (n) {
        //    System.out.println("success");
        //}
}

}
