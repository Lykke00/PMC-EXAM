package dk.easv.pmc.dal.dao;

import dk.easv.pmc.be.Movie;

public interface IMovieDAO {
    boolean deleteMovie(Movie movie) throws Exception;
    Movie createMovie(Movie movie) throws Exception;
}
