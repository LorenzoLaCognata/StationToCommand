package stationtocommand.view.mainStructure;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.locationStructure.Location;
import stationtocommand.model.locationStructure.LocationManager;
import stationtocommand.view.View;

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

    // TODO: remove the old method after all occurences are replaced
    public Node addToSidebarNew(Pane pane, String text1, String text2, EventHandler<MouseEvent> eventHandler) {

        Label label = new Label(text1);
        label.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 0;");

        StackPane stackPane = new StackPane(label);
        stackPane.setPrefSize(250, 35);
        stackPane.setMinHeight(35);
        stackPane.setMaxHeight(35);
        stackPane.setStyle("""
                -fx-background-color: #333;
                -fx-padding: 5px 10px;
                -fx-background-radius: 8px;
            """);

        stackPane.setOnMouseEntered(e -> stackPane.setStyle("""
                -fx-background-color: #555;
                -fx-padding: 5px 10px;
                -fx-background-radius: 8px;
            """));
        stackPane.setOnMouseExited(e -> stackPane.setStyle("""
                -fx-background-color: #333;
                -fx-padding: 5px 10px;
                -fx-background-radius: 8px;
            """));
        stackPane.setOnMousePressed(e -> stackPane.setStyle("""
                -fx-background-color: #666;
                -fx-padding: 5px 10px;
                -fx-background-radius: 8px;
            """));
        stackPane.setOnMouseReleased(e -> stackPane.setStyle("""
                -fx-background-color: #444;
                -fx-padding: 5px 10px;
                -fx-background-radius: 8px;
            """));
        stackPane.setOnMouseClicked(eventHandler);

        ImageView icon = new ImageView(new Image("file:C:\\Users\\vodev\\OneDrive\\Desktop\\station.png"));
        icon.setFitWidth(32);
        icon.setFitHeight(32);

        HBox content = new HBox(10, icon, label);
        content.setAlignment(Pos.CENTER_LEFT);
        content.setPadding(new Insets(5));

        stackPane.getChildren().add(content);

        pane.getChildren().addAll(stackPane);

        return stackPane;

    }

    public void resetBreadCrumbBar(BreadCrumbBar<Object> breadCrumbBar) {
        breadCrumbBar.setSelectedCrumb(null);
    }

    public void addBreadCrumb(BreadCrumbBar<Object> breadCrumbBar, Object object) {
        TreeItem<Object> treeItem = new TreeItem<>(object);
        if (breadCrumbBar.getSelectedCrumb() != null) {
            breadCrumbBar.getSelectedCrumb().getChildren().add(treeItem);
        }
        breadCrumbBar.setSelectedCrumb(treeItem);
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