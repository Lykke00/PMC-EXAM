package dk.easv.pmc.bll;


import dk.easv.pmc.be.Category;
import dk.easv.pmc.be.Movie;
import dk.easv.pmc.dal.dao.CategoryDAO;
import dk.easv.pmc.dal.dao.ICategoryDAO;
import dk.easv.pmc.gui.model.CategoryModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class CategoryLogic {
    private final ICategoryDAO categoryDAO;
    private final CategoryModel categoryModel;

    public CategoryLogic(CategoryModel categoryModel) throws Exception {
        this.categoryModel = categoryModel;
        this.categoryDAO = new CategoryDAO(); // Initialiser kategori-DAO
    }

    public Category createCategory(Category category) throws Exception {
        return categoryDAO.createCategory(category);
    }

    public boolean deleteCategory(Category category) throws Exception {
        return categoryDAO.deleteCategory(category);
    }

    public List<Category> getAllCategories() throws Exception {
        return categoryDAO.getAllCategories();
    }

    //Kan muligvis også lave via Databasen
    public ObservableList<Movie> getMoviesbySelectedCategory(ObservableList<Movie> movies) {
        if (movies == null) {
            return null;
        }
        List<Category> categories = categoryModel.getSelectedCategories();
        if(categories == null || categories.isEmpty())
            return movies;
        ObservableList<Movie> filteredMovies = FXCollections.observableArrayList();
        for (Movie m : movies) {
            String name = m.getCategories().toString();
            for (Category c : categories) {
                if (name.contains(c.getName() + ",")) {
                    filteredMovies.add(m);
                    break;
                }

            }

        }
        return filteredMovies;
    }
}
