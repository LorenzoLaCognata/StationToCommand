package stationtocommand.view.stationStructure;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.stationStructure.Station;
import stationtocommand.view.mainStructure.UtilsView;

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

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane pane1, Pane pane2, List<Station> stations) {
        utilsView.addLabel(pane1, "Stations");
        for (Station station : stations) {
            showSidebar(breadCrumbBar, pane1, station);
            showMap(pane2, station);
        }
    }

    private void showSidebar(BreadCrumbBar<Object> breadCrumbBar, Pane pane, Station station) {
        Button button = new Button(station.toString());
        button.setOnAction(_ -> stationView.show(breadCrumbBar, pane, station));
        pane.getChildren().add(button);
    }

    private void showMap(Pane pane, Station station) {

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

        utilsView.addToMap(pane, group);
    }


}
