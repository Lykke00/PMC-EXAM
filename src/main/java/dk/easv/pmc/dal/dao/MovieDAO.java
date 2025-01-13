package dk.easv.pmc.dal.dao;

import dk.easv.pmc.be.Category;
import dk.easv.pmc.be.Movie;
import dk.easv.pmc.be.ShowAlerts;
import dk.easv.pmc.dal.DBConnector;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO implements IMovieDAO {
    private final DBConnector dbConnector;

    public MovieDAO() throws Exception {
        try {
            this.dbConnector = new DBConnector();
        } catch (Exception e) {
            throw new Exception("Der skete en fejl ved forbindelse til databasen");
        }
    }

    public boolean doesMovieExist(String filePath) {
        if (filePath.contains(":")){
            return Files.exists(Paths.get(filePath));
        }
        String projectFolder = System.getProperty("user.dir");
        filePath = filePath.replace("\\", "/");

        Path path = Paths.get(projectFolder + "/" + filePath);

        return Files.exists(path);
    }

    @Override
    public ArrayList<Movie> getAllMovies() throws Exception {
        ArrayList<Movie> movies = new ArrayList<>();

        String query = "SELECT * FROM Movie ORDER by Id";

        try (Connection conn = dbConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("Id");
                String name = rs.getString("Name");
                double IMDBRating = rs.getDouble("IMDBRating");
                double PersonalRating = rs.getDouble("PersonalRating");
                String FileLink = rs.getString("FileLink");
                Date LastView = rs.getDate("LastView");
                double duration = rs.getDouble("Duration");

                if (!doesMovieExist(FileLink))
                    continue;

                ArrayList<Category> categories = getCategoriesForMovie(id);
                Movie movie = new Movie(id, name, IMDBRating, PersonalRating, FileLink, LastView, duration, categories);

                movies.add(movie);
            }

            return movies;
        } catch (Exception e) {
            throw new Exception("Kunne ikke få fat i alle film fra databasen");
        }
    }

    private ArrayList<Category> getCategoriesForMovie(int movieId) throws Exception {
        ArrayList<Category> categories = new ArrayList<>();

        String query = """ 
                SELECT CategoryId, Name FROM CatMovie
                        JOIN dbo.Category C on C.Id = CatMovie.CategoryId
                        where MovieId =  ?
                        """;

        try (Connection conn = dbConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, movieId);

            ResultSet rs = stmt.executeQuery();


            while (rs.next()) {
                int id = rs.getInt("CategoryId");
                String name = rs.getString("Name");

                Category category = new Category(id, name);
                categories.add(category);


                //playlists.add(new (id, name));
            }

            return categories;
        } catch (Exception e) {
            throw new Exception("Kunne ikke få fat i alle katagorierne til fom fra databasen. Film id: " + movieId);
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
                INSERT INTO Movie (Name, IMDBRating, PersonalRating, FileLink, LastView, Duration)
                       VALUES(?, ?, ?, ?, ?, ?);
                """;

        try (Connection conn = dbConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, movie.getName());
            stmt.setDouble(2, movie.getIMDBrating());
            stmt.setDouble(3, movie.getPersonalRating());
            stmt.setString(4, movie.getFileLink());
            stmt.setDate(5, movie.getLastView());
            stmt.setDouble(6, movie.getDuration());


            int rowsAffected = stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    movie.setId(rs.getInt(1));
                }
            }

            setMovieCategories(movie);
            return movie;
        } catch (Exception e) {
            throw new Exception("Kunne ikke tilføje din film i databasen");
        }
    }




    public boolean setMovieCategories(Movie m) throws Exception {
        if (m == null || m.getCategories() == null) {
            return false;
        }
        List<Category> categories = m.getCategories();
        String SQL_del = "DELETE FROM CatMovie WHERE MovieId = ?;";
        String SQL_add = "INSERT INTO CatMovie (CategoryId, MovieId) VALUES (?, ?);";

        try(Connection conn = dbConnector.getConnection();
            PreparedStatement delStmt = conn.prepareStatement(SQL_del);
            PreparedStatement addStmt = conn.prepareStatement(SQL_add)) {

            delStmt.setInt(1, m.getId());
            delStmt.executeUpdate();

            for (Category cat : categories) {
                addStmt.setInt(1, cat.getId());
                addStmt.setInt(2, m.getId());
                addStmt.executeUpdate();
            }
            return true;
        }
        catch (Exception e) {
            ShowAlerts.displayError(e.getMessage());
            throw new Exception("Kunne ikke tilføje kategorierne");
        }
    }
}
