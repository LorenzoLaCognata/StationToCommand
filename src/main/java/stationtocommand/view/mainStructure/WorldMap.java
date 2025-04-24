package stationtocommand.view.mainStructure;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.util.Objects;

public class WorldMap {

    public static final float MAP_WIDTH = 1400.0f;
    public static final float MAP_HEIGHT = 900.0f;

    private final StackPane container = new StackPane();
    private final Pane mapBackgroundLayer = new Pane();
    private final Pane mapElementsLayer = new Pane();

    public WorldMap() {
        container.setStyle("-fx-background-color: #ffffff;");
        container.getChildren().addAll(mapBackgroundLayer, mapElementsLayer);

        ImageView mapView = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/map-light.png")).toExternalForm()));
        mapView.setFitWidth(MAP_WIDTH);
        mapView.setFitHeight(MAP_HEIGHT);
        mapView.setOpacity(0.60);
        mapView.setPreserveRatio(true);
        mapBackgroundLayer.getChildren().add(mapView);

    }

    public StackPane getContainer() {
        return container;
    }

    public Pane getMapBackgroundLayer() {
        return mapBackgroundLayer;
    }

    public Pane getMapElementsLayer() {
        return mapElementsLayer;
    }

}