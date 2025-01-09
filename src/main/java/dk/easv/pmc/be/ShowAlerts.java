package dk.easv.pmc.be;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

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
}
