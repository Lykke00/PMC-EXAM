package dk.easv.pmc.dal.dao;

import dk.easv.pmc.be.Movie;

import java.util.ArrayList;
import java.util.List;

public interface IMovieDAO {
    ArrayList<Movie> getAllMovies() throws Exception;
    boolean deleteMovie(Movie movie) throws Exception;
    Movie createMovie(Movie movie) throws Exception;
    boolean setMovieCategories(Movie m) throws Exception;
    List<Movie> getOldLowRatedMovies() throws Exception;
    boolean updateMovie(Movie movie) throws Exception;
}
