package dk.easv.pmc.be;

import javafx.scene.control.Alert;

public class ShowAlerts {
    public static void displayError(String errorMessage){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(errorMessage);
        alert.setHeaderText("An error occurred");
        alert.showAndWait();
    }
}
