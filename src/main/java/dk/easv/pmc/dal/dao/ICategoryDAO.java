package dk.easv.pmc.dal.dao;

import dk.easv.pmc.be.Category;
import dk.easv.pmc.be.Movie;

import java.util.List;

public interface ICategoryDAO {
    Category createCategory(Category category) throws Exception;
    boolean deleteCategory(Category category) throws Exception;
    List<Category> getAllCategories() throws Exception;
}
