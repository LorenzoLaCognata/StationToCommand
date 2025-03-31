package stationtocommand.view.mainStructure;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Duration;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.departmentStructure.Department;
import stationtocommand.model.locationStructure.Location;
import stationtocommand.model.locationStructure.LocationManager;
import stationtocommand.model.utilsStructure.EnumWithResource;

import java.time.format.DateTimeFormatter;
import java.util.*;

public class UtilsView {

    public static final float SMALL_ICON_SIZE = 32;
    public static final float MEDIUM_ICON_SIZE = 48;

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

    public void clearPane(Pane pane) {
        pane.getChildren().clear();
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

    public void addMainTitleLabel(Pane pane, String text) {
        Label label = new Label(text);
        label.setStyle("""
            -fx-text-fill: white;
            -fx-font-size: 18px;
            -fx-font-weight: bold;
            -fx-padding: 10px 15px;
            -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 5, 0.3, 0, 0);
        """);
        pane.getChildren().add(label);
    }

    public void addSectionTitleLabel(Pane pane, String text) {
        Label label = new Label(text);
        label.setStyle("""
            -fx-text-fill: white;
            -fx-font-size: 14px;
            -fx-font-weight: bold;
            -fx-padding: 7px 12px;
            -fx-background-color: rgba(60, 60, 60, 0.9);
            -fx-background-radius: 6px;
            -fx-border-color: #777;
            -fx-border-width: 1px;
            -fx-border-radius: 6px;
            -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 5, 0.3, 0, 0);
        """);
        pane.getChildren().add(label);
    }

    public void addBodyLabel(Pane pane, String text) {
        Label label = new Label(text);
        label.setStyle("""
            -fx-text-fill: white;
            -fx-font-size: 13px;
            -fx-padding: 5px 10px;
            -fx-background-color: rgba(60, 60, 60, 0.9);
            -fx-background-radius: 4px;
            -fx-alignment: center;
        """);
        pane.getChildren().add(label);
    }

    public void addBodySmallLabel(Pane pane, String text) {
        Label label = new Label(text);
        label.setStyle("""
            -fx-text-fill: white;
            -fx-font-size: 13px;
            -fx-padding: 5px 10px;
            -fx-background-color: rgba(60, 60, 60, 0.9);
            -fx-background-radius: 4px;
            -fx-alignment: center;
        """);
        label.setPrefWidth(100);
        label.setMaxWidth(100);
        label.setAlignment(Pos.CENTER_RIGHT);
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

    public void addNodeToPane(Pane pane, Node node, Point2D point) {
        node.setLayoutX(point.getX());
        node.setLayoutY(point.getY());
        addNodeToPane(pane, node);
    }

    public void addNodeToPane(Pane pane, Node node) {
        pane.getChildren().addAll(node);
    }

    double normalize(double value, double min, double max) {
        return (value - min) / (max - min);
    }

    public Point2D locationToPoint(Location location, IconType iconType) {
        double iconSize = 0;
        switch (iconType) {
            case IconType.SMALL, IconType.SMALL_SHADOW -> iconSize = SMALL_ICON_SIZE;
            case IconType.MEDIUM, IconType.MEDIUM_SHADOW -> iconSize = MEDIUM_ICON_SIZE;
        }
        double x = normalize(location.longitude(), LocationManager.MIN_LONGITUDE, LocationManager.MAX_LONGITUDE) * WorldMap.MAP_WIDTH;
        double y = (1 - normalize(location.latitude(), LocationManager.MIN_LATITUDE, LocationManager.MAX_LATITUDE)) * WorldMap.MAP_HEIGHT;
        return new Point2D(x - (iconSize/2.0) , y  - (iconSize/2.0));
    }

    public ImageView basicIcon(String iconPath, String tooltipText) {
        ImageView imageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(iconPath)).toExternalForm()));
        if (!tooltipText.isEmpty()) {
            Tooltip tooltip = new Tooltip(tooltipText);
            tooltip.setShowDelay(javafx.util.Duration.millis(100));
            tooltip.setShowDuration(javafx.util.Duration.seconds(10));
            Tooltip.install(imageView, tooltip);
        }
        return imageView;
    }

    public ImageView mediumIcon(String iconPath, String tooltipText) {
        ImageView imageView = basicIcon(iconPath, tooltipText);
        imageView.setFitWidth(MEDIUM_ICON_SIZE);
        imageView.setFitHeight(MEDIUM_ICON_SIZE);
        return imageView;
    }

    public ImageView mediumShadowIcon(String iconPath, String tooltipText, IconColor iconColor) {
        ImageView imageView = mediumIcon(iconPath, tooltipText);
        imageView.setStyle("-fx-effect: dropshadow(gaussian, " + iconColor + ", 20, 0.3, 0, 0);");
        return imageView;
    }

    public ImageView mediumFadedIcon(String iconPath, String tooltipText) {
        ImageView imageView = mediumIcon(iconPath, tooltipText);
        imageView.setOpacity(0.2);
        return imageView;
    }

    public StackPane mediumBorderIcon(String iconPath, String tooltipText, IconColor iconColor) {
        ImageView imageView = mediumIcon(iconPath, tooltipText);
        StackPane imageContainer = new StackPane(imageView);
        imageContainer.setStyle("-fx-border-color: " + iconColor + "; -fx-border-width: 1px; -fx-border-radius: 2px;");
        return imageContainer;
    }

    public ImageView smallIcon(String iconPath, String tooltipText) {
        ImageView imageView = basicIcon(iconPath, tooltipText);
        imageView.setFitWidth(SMALL_ICON_SIZE);
        imageView.setFitHeight(SMALL_ICON_SIZE);
        return imageView;
    }

    public ImageView smallShadowIcon(String iconPath, String tooltipText, IconColor iconColor) {
        ImageView imageView = smallIcon(iconPath, tooltipText);
        imageView.setStyle("-fx-effect: dropshadow(gaussian, " + iconColor + ", 20, 0.3, 0, 0);");
        return imageView;
    }

    public ImageView smallFadedIcon(String iconPath, String tooltipText) {
        ImageView imageView = smallIcon(iconPath, tooltipText);
        imageView.setOpacity(0.2);
        return imageView;
    }

    public StackPane smallBorderIcon(String iconPath, String tooltipText, IconColor iconColor) {
        ImageView imageView = smallIcon(iconPath, tooltipText);
        StackPane imageContainer = new StackPane(imageView);
        imageContainer.setStyle("-fx-border-color: " + iconColor + "; -fx-border-width: 1px; -fx-border-radius: 2px;");
        return imageContainer;
    }

    public FadeTransition iconTransition(ImageView stationIcon) {
        FadeTransition flash = new FadeTransition(Duration.seconds(0.5), stationIcon);
        flash.setFromValue(1.0);
        flash.setToValue(0.5);
        flash.setCycleCount(Animation.INDEFINITE);
        flash.setAutoReverse(true);

        return flash;
    }

    public IconColor departmentIconColor(Department department) {
        IconColor iconColor;
        switch (department.getDepartmentType()) {
            case FIRE_DEPARTMENT -> iconColor = IconColor.PERSIAN_RED;
            case POLICE_DEPARTMENT -> iconColor = IconColor.DARK_BLUE;
            case MEDIC_DEPARTMENT -> iconColor = IconColor.CRIMSON_RED;
            default -> iconColor = IconColor.WHITE;
        }
        return iconColor;
    }

    public Pane createPane(Pane pane) {
        Pane pane2 = new Pane();
        pane.getChildren().add(pane2);
        return pane2;
    }

    public HBox createHBox(Pane pane) {
        HBox hBox = new HBox(10);
        pane.getChildren().add(hBox);
        return hBox;
    }

    public static Separator createSeparator() {
        Separator separator = new Separator();
        separator.setStyle("""
            -fx-border-color: #444444;
            -fx-border-width: 1px;
        """);
        return separator;
    }

    public void addSeparatorToPane(Pane pane) {
        Separator separator = createSeparator();
        pane.getChildren().add(separator);
    }

    /*
    public void addSeparatorBeforeNode(Pane pane, Node node) {
        Separator separator = createSeparator();
        int index = pane.getChildren().indexOf(node);
        if (index > -1) {
            pane.getChildren().add(index, separator);
        }
    }
    */

    public HBox createHBox(Pane pane, Pos pos) {
        HBox hBox = createHBox(pane);
        hBox.setAlignment(pos);
        return hBox;
    }

    public VBox createVBox(Pane pane) {
        VBox vBox = new VBox(15);
        pane.getChildren().add(vBox);
        return vBox;
    }

    public VBox createVBox(Pane pane, Pos pos) {
        VBox vBox = createVBox(pane);
        vBox.setAlignment(pos);
        return vBox;
    }

    public void addIconToPane(Pane pane, IconType iconType, IconColor iconColor, String imagePath, String tooltipText) {
        Node node;

        switch (iconType) {
            case IconType.SMALL -> node = smallIcon(imagePath, tooltipText);
            case IconType.SMALL_SHADOW -> node = smallShadowIcon(imagePath, tooltipText, iconColor);
            case IconType.SMALL_FADED -> node = smallFadedIcon(imagePath, tooltipText);
            case IconType.SMALL_BORDER -> node = smallBorderIcon(imagePath, tooltipText, iconColor);
            case IconType.MEDIUM -> node = mediumIcon(imagePath, tooltipText);
            case IconType.MEDIUM_SHADOW -> node = mediumShadowIcon(imagePath, tooltipText, iconColor);
            case IconType.MEDIUM_FADED -> node = mediumFadedIcon(imagePath, tooltipText);
            case IconType.MEDIUM_BORDER -> node = mediumBorderIcon(imagePath, tooltipText, iconColor);
            default -> node = smallIcon(imagePath, tooltipText);
        }
        pane.getChildren().add(node);
    }

    public void setButtonSelectedStyle(Button button) {
        button.setStyle("""
            -fx-background-color: #5a78a0;
            -fx-text-fill: #333;
        """);
    }

    public void setButtonUnselectedStyle(Button button) {
        button.setStyle("""
            -fx-background-color: #e0e0e0;
            -fx-text-fill: #333;
        """);
    }

    public void setPaneButtonsSelectionStyle(ActionEvent event, Pane buttonsPane) {
        Button clickedButton = (Button) event.getSource();

        buttonsPane.getChildren().stream()
                .filter(node -> node instanceof Button)
                .map(node -> (Button) node)
                .forEach(this::setButtonUnselectedStyle);

        setButtonSelectedStyle(clickedButton);
    }

    public Button addButtonToPane(Pane pane, String string, EventHandler<ActionEvent> eventHandler) {
        Button button = new Button(string);
        button.setOnAction(eventHandler);
        pane.getChildren().add(button);
        return button;
    }

    public Button addButtonToHorizontalPane(Pane pane, String string, EventHandler<ActionEvent> eventHandler) {
        Button button = addButtonToPane(pane, string, eventHandler);
        HBox.setHgrow(button, Priority.ALWAYS);
        button.setMaxWidth(Double.MAX_VALUE);
        return button;
    }

    public  <T extends EnumWithResource, S extends EnumWithResource> void addCount(Pane pane, Map<S, Long> statusCounts, Map<T, Map<S, Long>> typeStatusCounts) {
        Pane verticalDetailPane = createVBox(pane);

        PieChart pieChart = new PieChart();
        pieChart.setAnimated(false);
        /*+
        String color = switch (i) {
            case 0 -> "#66bb6a";
            case 1 -> "#ff7043";
            case 2 -> "#c62828";
            case 3 -> "#00796b";
            case 4 -> "#424242";
            default -> "black";
        };
        */

        pieChart.setMinHeight(400);
        pieChart.setPrefHeight(400);
        verticalDetailPane.getChildren().add(pieChart);
        addStatusPieChartSlices(pieChart, statusCounts, statusCounts.keySet().iterator().next());

        addSeparatorToPane(verticalDetailPane);
        Pane horizontalDetailsPane = createHBox(verticalDetailPane);
        addTypeCounts(horizontalDetailsPane, typeStatusCounts, typeStatusCounts.keySet().iterator().next(), statusCounts.keySet().iterator().next());

    }

    @SuppressWarnings("unchecked")
    public <S extends EnumWithResource> void addStatusPieChartSlices(PieChart pieChart, Map<S, Long> statusCounts, S sampleStatusClass) {
        for (EnumWithResource status : sampleStatusClass.getValues()) {
            addStatusPieChartSlice(pieChart, statusCounts, (S) status);
        }
    }

    public <S extends EnumWithResource> void addStatusPieChartSlice(PieChart pieChart, Map<S, Long> counts, S status) {
        int value = counts.getOrDefault(status, 0L).intValue();
        PieChart.Data slice = new PieChart.Data(status.toString(), value);
        pieChart.getData().add(slice);
    }

    @SuppressWarnings("unchecked")
    public <T extends EnumWithResource, S extends EnumWithResource> void addTypeCounts(Pane pane, Map<T, Map<S, Long>> counts, T sampleTypeClass, S sampleStatusClass)  {
        for (EnumWithResource type : sampleTypeClass.getValues()) {
            addTypeCount(pane, counts, (T) type, (S) sampleStatusClass.getPrimaryValue());
        }
    }

    public <T extends EnumWithResource, S extends EnumWithResource> void addTypeCount(Pane pane, Map<T, Map<S, Long>> counts, T type, S status) {
        Pane verticalDetailsPane = createVBox(pane, Pos.CENTER);
        Map<S, Long> statusCounts = counts.getOrDefault(type, Collections.emptyMap());
        addIconToPane(verticalDetailsPane, IconType.SMALL, IconColor.EMPTY, type.getResourcePath(), type.toString());
        long total = statusCounts.values().stream().mapToLong(Long::longValue).sum();
        String count = "";
        if (total > 0) {
            long selectedStatus = statusCounts.getOrDefault(status, 0L);
            count = selectedStatus + " / " + total;
        }
        addBodySmallLabel(verticalDetailsPane, count);
    }

}