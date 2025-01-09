package dk.easv.pmc.dal.dao;

import dk.easv.pmc.be.Category;
import dk.easv.pmc.dal.DBConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO implements ICategoryDAO {
    private final DBConnector dbConnector;

    public CategoryDAO() throws Exception {
        this.dbConnector = new DBConnector();
    }

    @Override
    public Category createCategory(Category category) throws Exception {
        String sql = "INSERT INTO Category (Name) VALUES (?);";

        try (Connection conn = dbConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, category.getName());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    category.setId(rs.getInt(1));
                }
            }

            return category;
        } catch (SQLException e) {
            throw new Exception("Kunne ikke tilføje kategorien: " + e.getMessage());
        }
    }

    @Override
    public boolean deleteCategory(Category category) throws Exception {
        String sql = "DELETE FROM Category WHERE Id = ?;";

        try (Connection conn = dbConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, category.getId());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new Exception("Kunne ikke slette kategorien: " + e.getMessage());
        }
    }

    @Override
    public List<Category> getAllCategories() throws Exception {
        String sql = "SELECT * FROM Category;";
        List<Category> categories = new ArrayList<>();

        try (Connection conn = dbConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                categories.add(new Category(rs.getInt("Id"), rs.getString("Name")));
            }
        } catch (SQLException e) {
            throw new Exception("Kunne ikke hente kategorier: " + e.getMessage());
        }

        return categories;
    }
}
