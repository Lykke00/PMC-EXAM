package dk.easv.pmc.gui.controller;

import dk.easv.pmc.be.Category;
import dk.easv.pmc.gui.utils.ShowAlerts;
import dk.easv.pmc.gui.model.CategoryModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class CategoryManagerController {
    private CategoryModel categoryModel;

    private final ObservableList<Category> categoryList = FXCollections.observableArrayList();

    @FXML
    private TextField categoryNameField;

    @FXML
    private ListView<Category> categoryListView;

    @FXML
    private Button ManageCategory;

    @FXML
    public void initialize() {
        try {
            categoryModel = new CategoryModel(null);
            loadCategories();
        } catch (Exception e) {
            showAlert("Error", "Could not initialize MovieLogic: " + e.getMessage());
        }
    }

    @FXML
    private void onAddCategory() {
        String categoryName = categoryNameField.getText();

        if (categoryName == null || categoryName.isBlank()) {
            showAlert("Error", "Category name cannot be empty!");
            return;
        }

        try {
            Category newCategory = new Category(categoryName);
            Category addedCategory = categoryModel.createCategory(newCategory);

            categoryList.add(addedCategory);
            categoryListView.setItems(categoryList);

            categoryNameField.clear();
        } catch (Exception e) {
            showAlert("Error", "Could not add category: " + e.getMessage());
        }
    }
    @FXML
    private void onDeleteCategory() {
        Category selectedCategory = categoryListView.getSelectionModel().getSelectedItem();

        if (selectedCategory != null) {
            if(!ShowAlerts.displayWarning("Are you sure you want to delete this category?"))
                return;

        }

        if (selectedCategory == null) {
            showAlert("Error", "Please select a category to delete.");
            return;
        }

        try {
            boolean isDeleted = categoryModel.deleteCategory(selectedCategory);
            if (isDeleted) {
                categoryList.remove(selectedCategory);
                categoryListView.setItems(categoryList);
            } else {
                showAlert("Error", "Could not delete the selected category: " + selectedCategory.getName());
            }
        } catch (Exception e) {
            showAlert("Error", "Could not delete category: " + e.getMessage());
        }
    }

    private void loadCategories() {
        try {
            categoryList.setAll(categoryModel.getAllCategories());
            categoryListView.setItems(categoryList);
        } catch (Exception e) {
            showAlert("Error", "Could not load categories: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
