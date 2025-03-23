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

    public void show(BreadCrumbBar<Object> breadCrumbBar, View view, Station station) {
        View.viewRunnable = () -> show(breadCrumbBar, view, station);
        utilsView.addBreadCrumb(breadCrumbBar, station);
        view.getNavigationPanel().clear();
        view.getWorldMap().clear();
        showSidebar(breadCrumbBar, view, station);
        showMap(view, station);
    }

    private void showSidebar(BreadCrumbBar<Object> breadCrumbBar, View view, Station station) {
        showStationDetails(view, station);
        unitListView.show(breadCrumbBar, view, station.getUnits());
        responderListView.show(breadCrumbBar, view, station.getResponders());
        vehicleListView.show(breadCrumbBar, view, station.getVehicles());
    }

    private void showStationDetails(View view, Station station) {
        Pane horizontalTitlePane = utilsView.createHBox(view.getNavigationPanel().getTitlePane());
        utilsView.addIconToPane(horizontalTitlePane, IconType.MEDIUM, IconColor.EMPTY, station.getStationType().getResourcePath(), "");
        utilsView.addMainTitleLabel(horizontalTitlePane, station.toString());
    }

    public void showMap(View view, Station station) {
        Point2D point = utilsView.locationToPoint(station.getLocation(), IconType.SMALL);
        ImageView imageView = utilsView.smallShadowIcon(station.getStationType().getResourcePath(), station.getStationType().toString(), utilsView.departmentIconColor(station.getDepartment()));
        Pane mapLayer = view.getWorldMap().getMapElementsLayer();
        utilsView.addNodeToPane(mapLayer, imageView, point);
    }

}
