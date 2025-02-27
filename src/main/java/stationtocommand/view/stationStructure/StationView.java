package stationtocommand.view.stationStructure;

import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
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
        showSidebar(breadCrumbBar, pane1, station);
        showMap(pane2, station);
    }

    private void showSidebar(BreadCrumbBar<Object> breadCrumbBar, Pane pane, Station station) {
        showStationDetails(pane, station);
        unitListView.show(breadCrumbBar, pane, station.getUnits());
        responderListView.show(breadCrumbBar, pane, station.getResponders());
        vehicleListView.show(breadCrumbBar, pane, station.getVehicles());
    }

    private void showStationDetails(Pane pane1, Station station) {
        HBox hBox = new HBox(10);
        ImageView imageView = utilsView.mediumIcon(utilsView.stationIconPath(station.getDepartment().getDepartmentType()));
        utilsView.addNodeToPane(hBox, imageView);
        utilsView.addMainTitleLabel(hBox, station.toString());
        utilsView.addNodeToPane(pane1, hBox);
    }

    public void showMap(Pane pane, Station station) {
        Point2D point = utilsView.locationToPoint(station.getLocation());
        ImageView imageView = utilsView.smallShadowIcon(utilsView.stationIconPath(station.getDepartment().getDepartmentType()), utilsView.departmentIconColor(station.getDepartment()));
        utilsView.addNodeToPane(pane, imageView, point);
    }

}
