module stationtocommand {
    requires javafx.controls;
    requires javafx.fxml;

    opens stationtocommand to javafx.fxml;
    exports stationtocommand;
}