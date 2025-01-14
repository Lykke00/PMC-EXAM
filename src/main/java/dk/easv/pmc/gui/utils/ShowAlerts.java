package dk.easv.pmc.gui.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Region;

public class ShowAlerts {
    public static void displayError(String errorMessage){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(errorMessage);
        alert.setTitle("An error occurred");
        alert.showAndWait();
    }
    public static boolean displayWarning(String warningMessage){
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirm Deletion");
        confirmation.setContentText(warningMessage);
        var result = confirmation.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            return true;
        }
        return false;
    }

    public static void displayOldMovies(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Old Low-Rated Movies");
        alert.setHeaderText("Consider deleting these movies:");
        alert.setContentText(message);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }
}
