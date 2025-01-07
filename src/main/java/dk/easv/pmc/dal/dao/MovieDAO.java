package dk.easv.pmc.dal.dao;

import dk.easv.pmc.be.Movie;
import dk.easv.pmc.dal.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

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

    @Override
    public Movie createMovie(Movie movie) throws Exception {
        String query = """
                INSERT INTO Movie (Name, IMDBRating, PersonalRating, FileLink, LastView)
                       VALUES(?, ?, ?, ?, ?);
                """;

        try (Connection conn = dbConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, movie.getName());
            stmt.setDouble(2, movie.getIMDBrating());
            stmt.setDouble(3, movie.getPersonalRating());
            stmt.setString(4, movie.getFileLink());
            stmt.setDate(5, movie.getLastView());

            int rowsAffected = stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    movie.setId(rs.getInt(1));
                }
            }

            return movie;
        } catch (Exception e) {
            throw new Exception("Kunne ikke tilføje din film i databasen");
        }
    }
}
