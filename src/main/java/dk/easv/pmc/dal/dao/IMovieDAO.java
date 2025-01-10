package dk.easv.pmc.dal.dao;

import dk.easv.pmc.be.Movie;

import java.util.ArrayList;

public interface IMovieDAO {
    ArrayList<Movie> getAllMovies() throws Exception;
    boolean deleteMovie(Movie movie) throws Exception;
    Movie createMovie(Movie movie) throws Exception;
    boolean setMovieCategories(Movie m) throws Exception;
}
