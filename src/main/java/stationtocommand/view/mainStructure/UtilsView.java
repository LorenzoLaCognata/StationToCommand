package stationtocommand.view.mainStructure;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.departmentStructure.Department;
import stationtocommand.model.locationStructure.Location;
import stationtocommand.model.locationStructure.LocationManager;
import stationtocommand.view.View;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UtilsView {

    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("E dd-MM-yyyy HH:mm");

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

    public DateTimeFormatter getDateFormat() {
        return dateFormat;
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

    public void addHeadingLabel(Pane pane, String text) {
        Label label = new Label(text);
        label.setStyle("""
            -fx-text-fill: white;
            -fx-font-size: 14px;
            -fx-font-weight: bold;
            -fx-padding: 5px 10px;
            -fx-background-color: rgba(60, 60, 60, 0.9);
            -fx-background-radius: 6px;
            -fx-border-color: #888;
            -fx-border-width: 1px;
            -fx-border-radius: 6px;
            -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 5, 0.3, 0, 0);
        """);
        pane.getChildren().add(label);
    }

    public void addLabel(Pane pane, String text) {
        Label label = new Label(text);
        label.setStyle("""
            -fx-text-fill: white;
            -fx-font-size: 12px;
            -fx-padding: 5px 10px;
            -fx-background-color: rgba(60, 60, 60, 0.9);
        """);
        pane.getChildren().add(label);
    }

    public void resetBreadCrumbBar(BreadCrumbBar<Object> breadCrumbBar) {
        breadCrumbBar.setSelectedCrumb(null);
    }

    public void addBreadCrumb(BreadCrumbBar<Object> breadCrumbBar, Object object) {
        TreeItem<Object> treeItem = new TreeItem<>(object);
        if (breadCrumbBar.getSelectedCrumb() == null) {
            breadCrumbBar.setSelectedCrumb(treeItem);
        }
        else if (breadCrumbBar.getSelectedCrumb().getValue() != object) {
            breadCrumbBar.getSelectedCrumb().getChildren().add(treeItem);
            breadCrumbBar.setSelectedCrumb(treeItem);
        }
    }

    public void addToMap(Pane pane, Node node) {
        pane.getChildren().addAll(node);
    }

    double normalize(double value, double min, double max) {
        return (value - min) / (max - min);
    }

    public Point2D locationToPoint(Location location) {
        double x = normalize(location.longitude(), LocationManager.MIN_LONGITUDE, LocationManager.MAX_LONGITUDE) * View.MAP_WIDTH;
        double y = (1 - normalize(location.latitude(), LocationManager.MIN_LATITUDE, LocationManager.MAX_LATITUDE)) * View.MAP_HEIGHT;
        return new Point2D(x, y);
    }

    public ImageView basicIcon(String iconPath) {
        return new ImageView(new Image(Objects.requireNonNull(getClass().getResource(iconPath)).toExternalForm()));
    }

    public ImageView mediumIcon(String iconPath) {
        ImageView imageView = basicIcon(iconPath);
        imageView.setFitWidth(48);
        imageView.setFitHeight(48);
        return imageView;
    }

    public ImageView mediumShadowIcon(String iconPath, String color) {
        ImageView imageView = mediumIcon(iconPath);
        imageView.setStyle("-fx-effect: dropshadow(gaussian, " + color + ", 20, 0.3, 0, 0);");
        return imageView;
    }

    public ImageView smallIcon(String iconPath) {
        ImageView imageView = basicIcon(iconPath);
        imageView.setFitWidth(32);
        imageView.setFitHeight(32);
        return imageView;
    }

    public ImageView smallShadowIcon(String iconPath, String color) {
        ImageView imageView = smallIcon(iconPath);
        imageView.setStyle("-fx-effect: dropshadow(gaussian, " + color + ", 20, 0.3, 0, 0);");
        return imageView;
    }

    public FadeTransition departmentIconTransition(ImageView stationIcon) {

        FadeTransition flash = new FadeTransition(Duration.seconds(0.5), stationIcon);
        flash.setFromValue(1.0);
        flash.setToValue(0.5);
        flash.setCycleCount(Animation.INDEFINITE);
        flash.setAutoReverse(true);

        return flash;
    }

    public ImageView generateIcon(String iconPath) {
        ImageView missionIcon = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(iconPath)).toExternalForm()));
        missionIcon.setFitWidth(40);
        missionIcon.setFitHeight(40);
        missionIcon.setOpacity(0.85);
        missionIcon.setStyle("-fx-effect: dropshadow(gaussian, yellow, 15, 0.7, 0, 0);");
        return missionIcon;
    }

    public void addImageToMap(Pane pane, ImageView imageView, Point2D point) {
        Group group = new Group();
        group.getChildren().add(imageView);
        group.setLayoutX(point.getX());
        group.setLayoutY(point.getY());
        addToMap(pane, group);
    }

    public String stationIconPath(Department department) {
        String iconPath;
        switch (department.getDepartmentType()) {
            case FIRE_DEPARTMENT -> iconPath = "/images/fireStation.png";
            case POLICE_DEPARTMENT -> iconPath = "/images/policeStation.png";
            case MEDIC_DEPARTMENT -> iconPath = "/images/medicStation.png";
            default -> iconPath = "/images/blank.png";
        }
        return iconPath;
    }

    public String departmentIconPath(Department department) {
        String iconPath;
        switch (department.getDepartmentType()) {
            case FIRE_DEPARTMENT -> iconPath = "/images/fireDepartment.png";
            case POLICE_DEPARTMENT -> iconPath = "/images/policeDepartment.png";
            case MEDIC_DEPARTMENT -> iconPath = "/images/medicDepartment.png";
            default -> iconPath = "/images/blank.png";
        }
        return iconPath;
    }

    public String departmentIconColor(Department department) {
        String iconColor;
        switch (department.getDepartmentType()) {
            case FIRE_DEPARTMENT -> iconColor = "#dc212a";
            case POLICE_DEPARTMENT -> iconColor = "#03132d";
            case MEDIC_DEPARTMENT -> iconColor = "#840705";
            default -> iconColor = "white";
        }
        return iconColor;
    }

}