package dk.easv.pmc.gui.model;

import dk.easv.pmc.be.Movie;
import dk.easv.pmc.bll.MovieLogic;
import dk.easv.pmc.gui.controller.HelloController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class MovieModel {
    private final MovieLogic movieLogic;
    private final HelloController hc;
    private ObservableList<Movie> movies;

    public MovieModel(HelloController hc) throws Exception {
        this.hc = hc;
        movieLogic = new MovieLogic(this);
    }

    public boolean deleteMovie(Movie movie) throws Exception {
        movies.remove(movie);
        return this.movieLogic.deleteMovie(movie);
    }

    public Movie createMovie(Movie movie) throws Exception {
        Movie m = movieLogic.createMovie(movie);
        movies.add(m);
        return m;
    }

    public ArrayList<Movie> getAllMoviesFromDb() throws Exception {
        return this.movieLogic.getAllMovies();
    }
    public double getSelectedOfficialRating(){
        return hc.getSelectedOfficialRating();
    }

    public ObservableList<Movie> getAllMovies() throws Exception {
        if (this.movies == null) {
            movies = FXCollections.observableArrayList(movieLogic.getAllMovies());
        }
        return this.movies;
    }

        public static void main(String[] args) throws Exception {
        MovieModel movieModel = new MovieModel(null);

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
