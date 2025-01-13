package dk.easv.pmc.gui.model;

import dk.easv.pmc.be.Movie;
import dk.easv.pmc.bll.MovieLogic;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class MovieModel {
    private final MovieLogic movieLogic;
    private ObservableList<Movie> movies;

    public MovieModel() throws Exception {
        movieLogic = new MovieLogic();
        movies = FXCollections.observableArrayList(movieLogic.getAllMovies());
    }

    public boolean deleteMovie(Movie movie) throws Exception {
        boolean deleted = this.deleteMovie(movie);
        if (deleted)
            movies.remove(movie); // hold den cached liste i programmet opdateret

        return deleted;
    }

    public Movie createMovie(Movie movie) throws Exception {
        Movie created = this.movieLogic.createMovie(movie);
        if (created != null)
            movies.add(created); // hold den cached liste i programmet opdateret

        return created;
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

    public ObservableList<Movie> searchMovie(String searchWord) {
        List<Movie> searchMovies = new ArrayList<>();
        for(Movie movie : movies) {
            if (movie.getName().toLowerCase().contains(searchWord.toLowerCase())) {
                searchMovies.add(movie);
            }
        }
        return FXCollections.observableArrayList(searchMovies);
    }
}
