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

import java.awt.image.ImagingOpException;
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
            showSidebar(breadCrumbBar, pane1, pane2, station);
            stationView.showMap(pane2, station);
        }
    }

    private void showSidebar(BreadCrumbBar<Object> breadCrumbBar, Pane pane1, Pane pane2, Station station) {
        Button button = new Button(station.toString());
        button.setOnAction(_ -> stationView.show(breadCrumbBar, pane1, pane2, station));
        pane1.getChildren().add(button);
    }

}
