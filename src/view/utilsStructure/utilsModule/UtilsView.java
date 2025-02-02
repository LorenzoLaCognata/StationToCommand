package view.utilsStructure.utilsModule;

import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import model.locationStructure.locationModule.Location;
import model.locationStructure.locationModule.LocationManager;
import view.View;

import java.util.ArrayList;
import java.util.List;

public class UtilsView {

    public UtilsView() {
    }

    public List<Node> resetAndAddToPane(Pane pane, List<Node> nodes, Node newNode) {
        pane.getChildren().clear();
        pane.getChildren().addAll(nodes);
        pane.getChildren().add(newNode);
        List<Node> newNodeList = new ArrayList<>(nodes);
        newNodeList.add(newNode);
        return newNodeList;
    }

    public List<Node> resetPane(Pane pane, List<Node> nodes) {
        pane.getChildren().clear();
        pane.getChildren().addAll(nodes);
        return nodes;
    }

    public List<Node> clearPane(Pane pane) {
        pane.getChildren().clear();
        return new ArrayList<>();
    }

    public void addToSidebar(Pane pane, Button button, String text1, String text2) {
        button.setText(text1);
        button.setAlignment(Pos.CENTER_LEFT);
        button.setMinWidth(200);
        Label label = new Label(text2);
        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.getChildren().addAll(button, label);
        pane.getChildren().addAll(hBox);
    }

    public void addToMap(Pane pane, Node node) {
        pane.getChildren().addAll(node);
    }

    double normalize(double value, double min, double max) {
        return (value - min) / (max - min);
    }

    public Point2D locationToPoint(Location location) {
        double x = normalize(location.longitude(), LocationManager.MIN_LONGITUDE, LocationManager.MAX_LONGITUDE) * View.SCENE_WIDTH;
        double y = (1 - normalize(location.latitude(), LocationManager.MIN_LATITUDE, LocationManager.MAX_LATITUDE)) * View.SCENE_HEIGHT;
        return new Point2D(x, y);
    }

}