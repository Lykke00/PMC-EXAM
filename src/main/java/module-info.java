module dk.easv.pmc {
    requires javafx.controls;
    requires javafx.fxml;


    opens dk.easv.pmc to javafx.fxml;
    exports dk.easv.pmc;
}