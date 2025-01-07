package dk.easv.pmc.dal.dao;

import dk.easv.pmc.be.Movie;
import dk.easv.pmc.dal.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class MovieDAO implements IMovieDAO {
    private final DBConnector dbConnector;

    public MovieDAO() throws Exception {
        try {
            this.dbConnector = new DBConnector();
        } catch (Exception e) {
            throw new Exception("Der skete en fejl ved forbindelse til databasen");
        }
    }

    @Override
    public boolean deleteMovie(Movie movie) throws Exception {
        String sql = "DELETE FROM Movie WHERE Id = ?";
        try (Connection conn = dbConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, movie.getId());
            stmt.executeUpdate();

            return true;
        } catch (Exception e) {
            throw new Exception("Kunne ikke slette filmen");
        }
    }
}
