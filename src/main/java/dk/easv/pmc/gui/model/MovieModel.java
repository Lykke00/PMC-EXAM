package dk.easv.pmc.gui.model;

import dk.easv.pmc.be.Movie;
import dk.easv.pmc.bll.MovieLogic;

public class MovieModel {
    private final MovieLogic movieLogic;

    public MovieModel() throws Exception {
        movieLogic = new MovieLogic();
    }

    public boolean deleteMovie(Movie movie) throws Exception {
        return this.movieLogic.deleteMovie(movie);
    }

    public static void main(String[] args) throws Exception {
        MovieModel movieModel = new MovieModel();

        Movie movie = new Movie(1);

        boolean n = movieModel.deleteMovie(movie);

        if (n) {
            System.out.println("success");
        }
    }

}
