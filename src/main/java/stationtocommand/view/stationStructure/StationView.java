package stationtocommand.view.stationStructure;

import javafx.animation.FadeTransition;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.stationStructure.Station;
import stationtocommand.view.View;
import stationtocommand.view.mainStructure.UtilsView;
import stationtocommand.view.responderStructure.ResponderListView;
import stationtocommand.view.unitStructure.UnitListView;
import stationtocommand.view.vehicleStructure.VehicleListView;

public class StationView {

    private boolean isSelected = false;

    private final UtilsView utilsView;
    private final UnitListView unitListView;
    private final ResponderListView responderListView;
    private final VehicleListView vehicleListView;

    public StationView(UtilsView utilsView) {
        this.utilsView = utilsView;
        this.unitListView = new UnitListView(utilsView);
        this.responderListView = new ResponderListView(utilsView);
        this.vehicleListView = new VehicleListView(utilsView);
    }

    public UnitListView getUnitListView() {
        return unitListView;
    }

    public ResponderListView getResponderListView() {
        return responderListView;
    }

    public VehicleListView getVehicleListView() {
        return vehicleListView;
    }

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane pane1, Pane pane2, Station station) {
        View.viewRunnable = () -> show(breadCrumbBar, pane1, pane2, station);
        utilsView.addBreadCrumb(breadCrumbBar, station);
        utilsView.clearPane(pane1);
        utilsView.clearPane(pane2);
        showMap(pane2, station);
        unitListView.show(breadCrumbBar, pane1, station.getUnits());
        responderListView.show(breadCrumbBar, pane1, station.getResponders());
        vehicleListView.show(breadCrumbBar, pane1, station.getVehicles());
    }

    public void showMap(Pane pane, Station station) {
        Point2D point = utilsView.locationToPoint(station.getLocation());

        String iconPath;
        switch (station.getDepartment().getDepartmentType()) {
            case FIRE_DEPARTMENT -> iconPath = "/images/fireStation.png";
            case POLICE_DEPARTMENT -> iconPath = "/images/policeStation.png";
            case MEDIC_DEPARTMENT -> iconPath = "/images/medicStation.png";
            default -> iconPath = "/images/blank.png";
        }
        ImageView imageView = utilsView.stationIcon(iconPath);
        FadeTransition fadeTransition = utilsView.stationIconTransition(imageView);
        Group group = mapElementsGroup(imageView, point, fadeTransition);
        utilsView.addToMap(pane, group);
    }

    private Group mapElementsGroup(ImageView stationIcon, Point2D point, FadeTransition flash) {
        // TODO: to improve station icon management
        Group group = new Group();
        group.getChildren().add(stationIcon);
        group.setLayoutX(point.getX());
        group.setLayoutY(point.getY());

        group.setOnMouseClicked(_ -> {
            isSelected = !isSelected;
            if (!isSelected) {
                flash.play();
            }
            else {
                flash.stop();
            }
        });
        return group;
    }

}
