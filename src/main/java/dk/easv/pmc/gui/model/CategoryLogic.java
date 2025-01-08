package dk.easv.pmc.gui.model;


import dk.easv.pmc.be.Category;
import dk.easv.pmc.dal.dao.CategoryDAO;
import dk.easv.pmc.dal.dao.ICategoryDAO;

import java.util.List;

public class CategoryLogic {
    private final ICategoryDAO categoryDAO;

    public CategoryLogic() throws Exception {
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
}
