package stationtocommand.view.stationStructure;

import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.stationStructure.Station;
import stationtocommand.view.View;
import stationtocommand.view.mainStructure.IconColor;
import stationtocommand.view.mainStructure.IconType;
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

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane navigationPanel, Pane worldMap, Station station) {
        View.viewRunnable = () -> show(breadCrumbBar, navigationPanel, worldMap, station);
        utilsView.addBreadCrumb(breadCrumbBar, station);
        utilsView.clearPane(navigationPanel);
        utilsView.clearPane(worldMap);
        showSidebar(breadCrumbBar, navigationPanel, station);
        showMap(worldMap, station);
    }

    private void showSidebar(BreadCrumbBar<Object> breadCrumbBar, Pane navigationPanel, Station station) {
        showStationDetails(navigationPanel, station);
        unitListView.show(breadCrumbBar, navigationPanel, station.getUnits());
        responderListView.show(breadCrumbBar, navigationPanel, station.getResponders());
        vehicleListView.show(breadCrumbBar, navigationPanel, station.getVehicles());
    }

    private void showStationDetails(Pane navigationPanel, Station station) {
        Pane labelPane = utilsView.createHBox(navigationPanel);
        utilsView.addIconToPane(navigationPanel, IconType.MEDIUM, IconColor.BLANK, utilsView.stationIconPath(station.getDepartment().getDepartmentType()));
        utilsView.addMainTitleLabel(labelPane, station.toString());
    }

    public void showMap(Pane worldMap, Station station) {
        Point2D point = utilsView.locationToPoint(station.getLocation(), IconType.SMALL);
        ImageView imageView = utilsView.smallShadowIcon(utilsView.stationIconPath(station.getDepartment().getDepartmentType()), utilsView.departmentIconColor(station.getDepartment()));
        utilsView.addNodeToPane(worldMap, imageView, point);
    }

}
