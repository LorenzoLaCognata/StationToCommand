package stationtocommand.view.stationStructure;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.stationStructure.Station;
import stationtocommand.view.mainStructure.TreeItemType;
import stationtocommand.view.mainStructure.UtilsView;

import java.util.ArrayList;
import java.util.List;

public class StationListView {

    private boolean isSelected = false;

    private final UtilsView utilsView;
    private final StationView stationView;

    public StationListView(UtilsView utilsView) {
        this.utilsView = utilsView;
        this.stationView = new StationView(utilsView);
    }

    public StationView getStationView() {
        return stationView;
    }

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane pane1, Pane pane2, List<Node> nodes1, List<Node> nodes2, List<Station> stations) {

        for (Station station : stations) {
            String text1 = station.toString();
            String text2 = station.getUnits().size() + " units";
            EventHandler<MouseEvent> eventHandler = event -> {
                //List<Node> nextNodes1 = utilsView.resetAndAddToPane(pane1, nodes1, button);
                List<Node> nextNodes1 = new ArrayList<>();
                stationView.show(breadCrumbBar, pane1, nextNodes1, station);
            };

            utilsView.addTreeItem(pane1, text1, TreeItemType.STATION_ITEM, station, TreeItemType.STATION_HEADER, station.getDepartment());

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
