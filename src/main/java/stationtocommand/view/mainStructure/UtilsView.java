package stationtocommand.view.mainStructure;

import javafx.application.Platform;
import javafx.collections.ObservableList;
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
import javafx.scene.text.Text;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.locationStructure.Location;
import stationtocommand.model.locationStructure.LocationManager;
import stationtocommand.model.utilsStructure.EnumWithResource;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class UtilsView {

    public static final float SMALL_ICON_SIZE = 32;
    public static final float MEDIUM_ICON_SIZE = 48;

    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("E dd-MM-yyyy HH:mm");

    public UtilsView() {
    }

    public DateTimeFormatter getDateFormat() {
        return dateFormat;
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

    public void addMainSubtitleLabel(Pane pane, String text) {
        Label label = new Label(text);
        label.setStyle("""
            -fx-text-fill: white;
            -fx-font-size: 14px;
            -fx-font-weight: bold;
            -fx-padding: 10px 15px 10px 30px;
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
            -fx-padding: 10px 15px;
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

    public void addTitle(Pane pane, String title) {
        Pane horizontalPane = createHBox(pane);
        addMainTitleLabel(horizontalPane, title);
    }

    public <T extends EnumWithResource> void addIconAndTitle(Pane pane, T resource, String title) {
        Pane horizontalPane = createHBox(pane);
        addIconToPane(horizontalPane, IconType.MEDIUM, IconColor.EMPTY, resource);
        addMainTitleLabel(horizontalPane, title);
    }

    public <T extends EnumWithResource> void addIconAndSubtitle(Pane pane, T resource, String subtitle) {
        Pane horizontalPane = createHBox(pane);
        addIconToPane(horizontalPane, IconType.MEDIUM, IconColor.EMPTY, resource);
        addMainSubtitleLabel(horizontalPane, subtitle);
    }

    public <T extends EnumWithResource> void addIconAndTitleWithSubtitle(Pane pane, T resourceTitle, String title, T resourceSubtitle, String subtitle) {
        Pane verticalPane = createVBox(pane);
        addIconAndTitle(verticalPane, resourceTitle, title);
        addIconAndSubtitle(verticalPane, resourceSubtitle, subtitle);
    }

    public <T extends EnumWithResource> void addIconAndButtonAndIcon(Pane pane, T resource1, String label, EventHandler<ActionEvent> eventHandler, T resource2) {
        Pane horizontalPane = addIconAndButton(pane, resource1, label, eventHandler);
        addIconToPane(horizontalPane, IconType.SMALL, IconColor.EMPTY, resource2);
    }

    public <T extends EnumWithResource> Pane addIconAndButton(Pane pane, T resource, String label, EventHandler<ActionEvent> eventHandler) {
        Pane horizontalPane = createHBox(pane);
        addIconToPane(horizontalPane, IconType.SMALL, IconColor.EMPTY, resource);
        addButtonToPane(horizontalPane, label, eventHandler);
        return horizontalPane;
    }

    public <T extends EnumWithResource> void addLabelAndIcon(Pane pane, String label, T resource) {
        Pane horizontalPane = createHBox(pane);
        addBodyLabel(horizontalPane, label);
        addIconToPane(horizontalPane, IconType.SMALL, IconColor.EMPTY, resource);
    }

    public void addLabelAndText(Pane pane, String label, String text) {
        Pane horizontalPane = createHBox(pane);
        addBodyLabel(horizontalPane, label);
        addSectionTitleLabel(horizontalPane, text);
    }

    public <T extends EnumWithResource> Button addButtonWithGraphic(Pane pane, T resource, String label, Runnable action) {
        EventHandler<ActionEvent> buttonHandler = event -> {
            setPaneButtonsSelectionStyle(event, pane);
            action.run();
        };
        Button button = addHorizontalButtonToPane(pane, label, buttonHandler);
        button.setGraphic(smallIcon(resource.getImage(), ""));
        return button;
    }

    public <T extends EnumWithResource> void addSelectedButtonWithGraphic(Pane pane, T resource, String label, Runnable action) {
        Button button = addButtonWithGraphic(pane, resource, label, action);
        setButtonSelectedStyle(button);
        action.run();
    }

    double normalize(double value, double min, double max) {
        return (value - min) / (max - min);
    }

    public Point2D locationToPoint(Location location, IconType iconType) {
        double iconSize = 0;
        switch (iconType) {
            case IconType.SMALL, IconType.SMALL_FADED, IconType.SMALL_BORDER -> iconSize = SMALL_ICON_SIZE;
            case IconType.MEDIUM, IconType.MEDIUM_FADED, IconType.MEDIUM_BORDER -> iconSize = MEDIUM_ICON_SIZE;
        }
        double x = normalize(location.longitude(), LocationManager.MIN_LONGITUDE, LocationManager.MAX_LONGITUDE) * WorldMap.MAP_WIDTH;
        double y = (1 - normalize(location.latitude(), LocationManager.MIN_LATITUDE, LocationManager.MAX_LATITUDE)) * WorldMap.MAP_HEIGHT;
        return new Point2D(x - (iconSize/2.0), y  - (iconSize/2.0));
    }

    public void distributeResourceIconsByLocation(Location location, List<Node> resourceIcons) {
        Point2D nodesCenter = locationToPoint(location, IconType.SMALL);
        int count = resourceIcons.size();
        int cols = (int) Math.ceil(Math.sqrt(count));
        double spacing = SMALL_ICON_SIZE;

        if (count == 1) {
            Node node = resourceIcons.getFirst();
            node.setLayoutX(nodesCenter.getX());
            node.setLayoutY(nodesCenter.getY());
        }
        else {
            for (int i = 0; i < count; i++) {
                int row = i / cols;
                int col = i % cols;

                double offsetX = (col - cols / 2.0) * spacing;
                double offsetY = (row - (double) count / cols / 2.0) * spacing;

                Node node = resourceIcons.get(i);
                node.setLayoutX(nodesCenter.getX() + offsetX);
                node.setLayoutY(nodesCenter.getY() + offsetY);
            }
        }
    }

    public ImageView basicIcon(Image image, String tooltipText) {
        ImageView imageView = new ImageView(image);
        if (!tooltipText.isEmpty()) {
            Tooltip tooltip = new Tooltip(tooltipText);
            tooltip.setShowDelay(javafx.util.Duration.millis(100));
            tooltip.setShowDuration(javafx.util.Duration.seconds(10));
            Tooltip.install(imageView, tooltip);
        }
        return imageView;
    }

    public ImageView mediumIcon(Image image, String tooltipText) {
        ImageView imageView = basicIcon(image, tooltipText);
        imageView.setFitWidth(MEDIUM_ICON_SIZE);
        imageView.setFitHeight(MEDIUM_ICON_SIZE);
        return imageView;
    }

    public ImageView mediumFadedIcon(Image image, String tooltipText) {
        ImageView imageView = mediumIcon(image, tooltipText);
        imageView.setOpacity(0.2);
        return imageView;
    }

    public StackPane mediumBorderIcon(Image image, String tooltipText, IconColor iconColor) {
        ImageView imageView = mediumIcon(image, tooltipText);
        StackPane imageContainer = new StackPane(imageView);
        imageContainer.setStyle("-fx-border-color: " + iconColor + "; -fx-border-width: 1px; -fx-border-radius: 2px;");
        return imageContainer;
    }

    public ImageView smallIcon(Image image, String tooltipText) {
        ImageView imageView = basicIcon(image, tooltipText);
        imageView.setFitWidth(SMALL_ICON_SIZE);
        imageView.setFitHeight(SMALL_ICON_SIZE);
        return imageView;
    }

    public ImageView smallFadedIcon(Image image, String tooltipText) {
        ImageView imageView = smallIcon(image, tooltipText);
        imageView.setOpacity(0.2);
        return imageView;
    }

    public StackPane smallBorderIcon(Image image, String tooltipText, IconColor iconColor) {
        ImageView imageView = smallIcon(image, tooltipText);
        StackPane imageContainer = new StackPane(imageView);
        imageContainer.setStyle("-fx-border-color: " + iconColor + "; -fx-border-width: 1px; -fx-border-radius: 2px;");
        return imageContainer;
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

    public <T extends EnumWithResource> Node createResourceIconWithLocation(IconType iconType, IconColor iconColor, T resource, Location location) {
        Node node = createResourceIcon(iconType, iconColor, resource);
        Point2D point = locationToPoint(location, iconType);
        node.setLayoutX(point.getX());
        node.setLayoutY(point.getY());
        return node;
    }

    public <T extends EnumWithResource> Node createResourceIcon(IconType iconType, IconColor iconColor, T resource) {
        Node node;
        Image image = resource.getImage();
        String tooltipText = resource.toString();

        switch (iconType) {
            case IconType.SMALL -> node = smallIcon(image, tooltipText);
            case IconType.SMALL_FADED -> node = smallFadedIcon(image, tooltipText);
            case IconType.SMALL_BORDER -> node = smallBorderIcon(image, tooltipText, iconColor);
            case IconType.MEDIUM -> node = mediumIcon(image, tooltipText);
            case IconType.MEDIUM_FADED -> node = mediumFadedIcon(image, tooltipText);
            case IconType.MEDIUM_BORDER -> node = mediumBorderIcon(image, tooltipText, iconColor);
            default -> node = smallIcon(image, tooltipText);
        }

        return node;
    }

    public <T extends EnumWithResource> void addIconToPane(Pane pane, IconType iconType, IconColor iconColor, T resource) {
        Node node = createResourceIcon(iconType, iconColor, resource);
        pane.getChildren().add(node);
    }

    public <T extends EnumWithResource> void addIconWithLocationToPane(Pane pane, IconType iconType, IconColor iconColor, T resource, Location location) {
        Node node = createResourceIconWithLocation(iconType, iconColor, resource, location);
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

    public Button addHorizontalButtonToPane(Pane pane, String string, EventHandler<ActionEvent> eventHandler) {
        Button button = addButtonToPane(pane, string, eventHandler);
        HBox.setHgrow(button, Priority.ALWAYS);
        button.setMaxWidth(Double.MAX_VALUE);
        return button;
    }

    public  <T extends EnumWithResource, S extends EnumWithResource> void addAvailableResources(Pane pane, Map<S, Long> statusCounts, Map<T, Map<S, Long>> typeStatusCounts) {
        addSeparatorToPane(pane);
        Pane horizontalPane = createHBox(pane);
        addAvailabilityTypeCounts(horizontalPane, typeStatusCounts, typeStatusCounts.keySet().iterator().next(), statusCounts.keySet().iterator().next());
        addSeparatorToPane(pane);
    }

    // TODO: replace
    public  <T extends EnumWithResource, S extends EnumWithResource> void addAvailableResourcesOLD(Pane pane, Map<S, Long> statusCounts, Map<T, Map<S, Long>> typeStatusCounts) {
        addSeparatorToPane(pane);
        Pane horizontalPane = createHBox(pane);
        Pane verticalPane = createVBox(horizontalPane);
        addPieChart(statusCounts, verticalPane);
        addSeparatorToPane(verticalPane);
        Pane horizontalDetailsPane = createHBox(verticalPane);
        addAvailabilityTypeCounts(horizontalDetailsPane, typeStatusCounts, typeStatusCounts.keySet().iterator().next(), statusCounts.keySet().iterator().next());
        addSeparatorToPane(pane);
    }

    public  <T extends EnumWithResource, S extends EnumWithResource> void addTotalResources(Pane pane, Map<S, Long> statusCounts, Map<T, Map<S, Long>> typeStatusCounts) {
        addSeparatorToPane(pane);
        Pane horizontalPane = createHBox(pane);
        Pane verticalPane = createVBox(horizontalPane);
        addPieChart(statusCounts, verticalPane);
        addSeparatorToPane(verticalPane);
        Pane horizontalDetailsPane = createHBox(verticalPane);
        addTotalTypeCounts(horizontalDetailsPane, typeStatusCounts, typeStatusCounts.keySet().iterator().next());
        addSeparatorToPane(pane);
    }

    public  <T extends EnumWithResource, S extends EnumWithResource> void addTotalCount(Pane pane, Map<S, Long> statusCounts, Map<T, Map<S, Long>> typeStatusCounts) {
        Pane verticalDetailPane = createVBox(pane);
        addPieChart(statusCounts, verticalDetailPane);
        addSeparatorToPane(verticalDetailPane);
        Pane horizontalDetailsPane = createHBox(verticalDetailPane);
        addTotalTypeCounts(horizontalDetailsPane, typeStatusCounts, typeStatusCounts.keySet().iterator().next());

    }

    private <S extends EnumWithResource> void addPieChart(Map<S, Long> statusCounts, Pane verticalDetailPane) {
        PieChart pieChart = new PieChart();
        pieChart.setAnimated(false);
        pieChart.setMinHeight(400);
        pieChart.setPrefHeight(400);
        verticalDetailPane.getChildren().add(pieChart);
        addStatusPieChartSlices(pieChart, statusCounts, statusCounts.keySet().iterator().next());
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
    public <S extends EnumWithResource> void updateStatusPieChartSlices(PieChart pieChart, Map<S, Long> statusCounts, S sampleStatusClass) {
        for (EnumWithResource status : sampleStatusClass.getValues()) {
            pieChart.getData().stream()
                .filter(data -> data.getName().equals(status.toString()))
                .findFirst().ifPresent(data -> updateStatusPieChartSlice(data, statusCounts, (S) status));
        }
        updateLaterPieChartLabels(pieChart);
    }

    public <S extends EnumWithResource> void updateStatusPieChartSlice(PieChart.Data data, Map<S, Long> counts, S status) {
        int value = counts.getOrDefault(status, 0L).intValue();
        data.setPieValue(value);
    }

    @SuppressWarnings("unchecked")
    public <T extends EnumWithResource, S extends EnumWithResource> void addAvailabilityTypeCounts(Pane pane, Map<T, Map<S, Long>> counts, T sampleTypeClass, S sampleStatusClass)  {
        for (EnumWithResource type : sampleTypeClass.getValues()) {
            addAvailabilityTypeCount(pane, counts, (T) type, (S) sampleStatusClass.getPrimaryValue());
        }
    }

    public <T extends EnumWithResource, S extends EnumWithResource> void addAvailabilityTypeCount(Pane pane, Map<T, Map<S, Long>> counts, T type, S status) {
        Pane verticalDetailsPane = createVBox(pane, Pos.CENTER);
        Map<S, Long> statusCounts = counts.getOrDefault(type, Collections.emptyMap());
        addIconToPane(verticalDetailsPane, IconType.SMALL, IconColor.EMPTY, type);
        long total = statusCounts.values().stream().mapToLong(Long::longValue).sum();
        String count = "";
        if (total > 0) {
            long selectedStatus = statusCounts.getOrDefault(status, 0L);
            count = selectedStatus + " / " + total;
        }
        addBodySmallLabel(verticalDetailsPane, count);
    }

    @SuppressWarnings("unchecked")
    public <T extends EnumWithResource, S extends EnumWithResource> void addTotalTypeCounts(Pane pane, Map<T, Map<S, Long>> counts, T sampleTypeClass)  {
        for (EnumWithResource type : sampleTypeClass.getValues()) {
            addTotalTypeCount(pane, counts, (T) type);
        }
    }

    public <T extends EnumWithResource, S extends EnumWithResource> void addTotalTypeCount(Pane pane, Map<T, Map<S, Long>> counts, T type) {
        Pane verticalDetailsPane = createVBox(pane, Pos.CENTER);
        Map<S, Long> statusCounts = counts.getOrDefault(type, Collections.emptyMap());
        addIconToPane(verticalDetailsPane, IconType.SMALL, IconColor.EMPTY, type);
        long total = statusCounts.values().stream().mapToLong(Long::longValue).sum();
        addBodySmallLabel(verticalDetailsPane, String.valueOf(total));
    }

    public String availabilityPieChartColors(String status) {
        String color;
        switch (status) {
            case "Available" -> color = "#66bb6a";
            case "Dispatched" -> color = "#ff7043";
            case "On Scene" -> color = "#c62828";
            case "Returning" -> color = "#00796b";
            case "Unavailable" -> color = "#424242";
            default -> color = "black";
        }
        return color;
    }


    public void setPieChartAvailabilityColors(PieChart pieChart) {

        ObservableList<PieChart.Data> dataList = pieChart.getData();
        Set<Node> legendItems = pieChart.lookupAll(".chart-legend-item");

        List<Node> legendItemList = new ArrayList<>(legendItems);
        legendItemList.sort(Comparator.comparingDouble(Node::getLayoutX));

        for (int i = 0; i < dataList.size() && i < legendItemList.size(); i++) {
            PieChart.Data data = dataList.get(i);
            Node legendItem = legendItemList.get(i);

            Node labelNode = legendItem.lookup(".label");
            Node symbolNode = legendItem.lookup(".chart-legend-item-symbol");

            String legendText = "";
            if (labelNode instanceof Labeled labeled) {
                legendText = labeled.getText();
            } else if (labelNode instanceof Text text) {
                legendText = text.getText();
            }

            String color = availabilityPieChartColors(legendText);

            if (symbolNode != null) {
                symbolNode.setStyle("-fx-background-color: " + color + ";");
            }

            Node sliceNode = data.getNode();
            if (sliceNode != null) {
                sliceNode.setStyle("-fx-pie-color: " + color + ";");
            }

        }

    }

    public void updateLaterPieChartLabels(PieChart pieChart) {
        Platform.runLater(() -> {
            pieChart.applyCss();
            pieChart.layout();
            updatePieChartLabels(pieChart);
        });

    }

    public void updatePieChartLabels(PieChart pieChart) {
        Map<String, PieChart.Data> dataByName = pieChart.getData().stream()
                .collect(Collectors.toMap(PieChart.Data::getName, d -> d));

        for (Node label : pieChart.lookupAll(".chart-pie-label")) {
            if (label instanceof Text text) {
                PieChart.Data match = dataByName.get((String) text.getUserData());
                if (match != null) {
                    text.setStyle("-fx-fill: white;");
                    if (match.getPieValue() > 0) {
                        text.setText(String.valueOf((int) match.getPieValue()));
                    }
                    else {
                        text.setText("");
                    }
                }
                else {
                    for (String name : dataByName.keySet()) {
                        if (text.getText().equals(name)) {
                            text.setUserData(name);
                            PieChart.Data data = dataByName.get(name);
                            text.setStyle("-fx-fill: white;");
                            if (data.getPieValue() > 0) {
                                text.setText(String.valueOf((int) data.getPieValue()));
                            }
                            else {
                                text.setText("");
                            }
                            break;
                        }
                    }
                }
            }
        }
    }

    public void updateSliceValue(PieChart pieChart, PieChart.Data data, double value) {
        data.setPieValue(value);
        updateLaterPieChartLabels(pieChart);
    }

    public <X, Y extends ViewWithNode> Map<Location, List<Node>> nodesByLocation(Map<X, Y> viewWithNode, Function<Y, Location> viewLocationFunction) {
        return viewWithNode.values().stream()
                .collect(Collectors.groupingBy(
                        viewLocationFunction,
                        Collectors.mapping(
                                Y::getNode,
                                Collectors.toList()
                        )
                ));
    }

}