package dk.easv.pmc.gui.model;

import dk.easv.pmc.be.Category;
import dk.easv.pmc.be.Movie;
import dk.easv.pmc.bll.CategoryLogic;
import dk.easv.pmc.dal.dao.CategoryDAO;
import dk.easv.pmc.dal.dao.ICategoryDAO;
import dk.easv.pmc.gui.controller.HelloController;
import javafx.collections.ObservableList;

import java.util.List;



public class CategoryModel {
    private final CategoryLogic categoryLogic;
    private final HelloController hc;

    public CategoryModel(HelloController hc) throws Exception {
        this.hc = hc;
        this.categoryLogic = new CategoryLogic(this); // Initialiser kategori-DAO
    }

    public Category createCategory(Category category) throws Exception {
        return categoryLogic.createCategory(category);
    }

    public boolean deleteCategory(Category category) throws Exception {
        return categoryLogic.deleteCategory(category);
    }

    public List<Category> getAllCategories() throws Exception {
        return categoryLogic.getAllCategories();
    }
    public ObservableList<Movie> getMoviesbySelectedCategory(ObservableList<Movie> movies) {
        return categoryLogic.getMoviesbySelectedCategory(movies);
    }
    public List<Category> getSelectedCategories() {
        return hc.getSelectedCategories();
    }
}

