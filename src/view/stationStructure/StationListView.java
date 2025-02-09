package view.stationStructure;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import model.stationStructure.Station;
import view.mainStructure.UtilsView;

import java.util.List;

public class StationListView {

    private boolean isSelected = false;

    private final UtilsView utilsView;
    private final StationView stationView;

    public StationListView(UtilsView utilsView) {
        this.utilsView = utilsView;
        this.stationView = new StationView(utilsView);
    }

    public void show(Pane pane1, Pane pane2, List<Node> nodes1, List<Node> nodes2, List<Station> stations) {
        Label header = new Label("Stations");
        header.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 0;");
        pane1.getChildren().addAll(header);

        for (Station station : stations) {
            Button button = new Button();
            EventHandler<MouseEvent> eventHandler = event -> {
                List<Node> nextNodes1 = utilsView.resetAndAddToPane(pane1, nodes1, button);
                stationView.show(pane1, nextNodes1, station);
            };
            // TODO: restore or delete
            //utilsView.addToSidebar(pane1, button, station.toString(), station.getUnits().size() + " units");
            utilsView.addToSidebarNew(pane1, station.toString(), station.getUnits().size() + " units", eventHandler);

            Point2D point = utilsView.locationToPoint(station.getLocation());

            // TODO: to improve station icon management
            Group group = new Group();

            ImageView stationIcon = new ImageView(new Image("file:C:\\Users\\vodev\\OneDrive\\Desktop\\station.png"));
            stationIcon.setFitWidth(40);
            stationIcon.setFitHeight(40);
            stationIcon.setOpacity(0.85);
            stationIcon.setStyle("-fx-effect: dropshadow(gaussian, yellow, 15, 0.7, 0, 0);");

            Tooltip tooltip = new Tooltip(station.getUnits().size() + "units");
            Tooltip.install(stationIcon, tooltip);

            group.getChildren().add(stationIcon);
            group.setLayoutX(point.getX());
            group.setLayoutY(point.getY());

            FadeTransition flash = new FadeTransition(Duration.seconds(0.5), stationIcon);
            flash.setFromValue(1.0);
            flash.setToValue(0.5);
            flash.setCycleCount(Animation.INDEFINITE);
            flash.setAutoReverse(true);

            group.setOnMouseClicked(_ -> {
                isSelected = !isSelected;
                if (!isSelected) {
                    flash.play();
                }
                else {
                    flash.stop();
                }
            });

            utilsView.addToMap(pane2, group);

        }
    }


}
