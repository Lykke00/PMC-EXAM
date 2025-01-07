package dk.easv.pmc.bll;

import dk.easv.pmc.be.Movie;
import dk.easv.pmc.dal.dao.IMovieDAO;
import dk.easv.pmc.dal.dao.MovieDAO;

public class MovieLogic {
    private final IMovieDAO movieDAO;

    public MovieLogic() throws Exception {
        this.movieDAO = new MovieDAO();
    }

    public boolean deleteMovie(Movie movie) throws Exception {
        return this.movieDAO.deleteMovie(movie);
    }
}
