package stationtocommand.view.organizationStructure;

import javafx.scene.Node;
import stationtocommand.model.departmentStructure.Department;
import stationtocommand.model.locationStructure.Location;
import stationtocommand.model.responderStructure.Responder;
import stationtocommand.model.stationStructure.Station;
import stationtocommand.model.unitStructure.Unit;
import stationtocommand.model.vehicleStructure.Vehicle;
import stationtocommand.view.View;
import stationtocommand.view.mainStructure.UtilsView;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DepartmentView {

    private final Department department;
    private final UtilsView utilsView;
    private final Map<Station, StationView> stationViews;

    public DepartmentView(Department department, View view, UtilsView utilsView) {
        this.department = department;
        this.utilsView = utilsView;

        this.stationViews = new LinkedHashMap<>();

        for (Station station : department.getStations()) {
            addStationView(station, view, utilsView);
        }
    }

    private void addStationView(Station station, View view, UtilsView utilsView) {
        if (!stationViews.containsKey(station)) {
            StationView stationView = new StationView(station, view, utilsView);
            stationViews.put(station, stationView);
            view.addToMap(stationView.getNode());
        }
    }

    public Department getDepartment() {
        return department;
    }

    public Map<Station, StationView> getStationViews() {
        return stationViews;
    }

    public StationView getStationView(Station station) {
        return stationViews.get(station);
    }

    public Map<Unit, UnitView> getUnitViews() {
        return stationViews.values().stream()
                .flatMap(stationView -> stationView.getUnitViews().entrySet().stream())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (existing, _) -> existing,
                        LinkedHashMap::new
                ));
    }

    public Map<Vehicle, VehicleView> getVehicleViews() {
        List<VehicleView> list = stationViews.values().stream()
                .flatMap(stationView -> stationView.getUnitViews().values().stream())
                .flatMap(unitView -> unitView.getVehicleViews().values().stream())
                .toList();

        return stationViews.values().stream()
                .flatMap(stationView -> stationView.getUnitViews().values().stream())
                .flatMap(unitView -> unitView.getVehicleViews().entrySet().stream())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (existing, _) -> existing,
                        LinkedHashMap::new
                ));

    }

    public Map<Responder, ResponderView> getResponderViews() {
        return stationViews.values().stream()
                .flatMap(stationView -> stationView.getUnitViews().values().stream())
                .flatMap(unitView -> unitView.getResponderViews().entrySet().stream())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (existing, _) -> existing,
                        LinkedHashMap::new
                ));
    }

    public Map<Location, List<Node>> stationNodesByLocation() {
        return stationViews.values().stream()
                .collect(Collectors.groupingBy(
                        v -> v.getStation().getLocation(),
                        Collectors.mapping(
                                StationView::getNode,
                                Collectors.toList()
                        )
                ));
    }

    public Map<Location, List<Node>> unitNodesByLocation() {
        return getUnitViews().values().stream()
                .collect(Collectors.groupingBy(
                        v -> v.getUnit().getStation().getLocation(),
                        Collectors.mapping(
                                UnitView::getNode,
                                Collectors.toList()
                        )
                ));
    }

    public Map<Location, List<Node>> vehicleNodesByLocation() {
        return getVehicleViews().values().stream()
                .collect(Collectors.groupingBy(
                        v -> v.getVehicle().getLocation(),
                        Collectors.mapping(
                                VehicleView::getNode,
                                Collectors.toList()
                        )
                ));
    }

    public Map<Location, List<Node>> responderNodesByLocation() {
        return getResponderViews().values().stream()
                .collect(Collectors.groupingBy(
                        v -> v.getResponder().getLocation(),
                        Collectors.mapping(
                                ResponderView::getNode,
                                Collectors.toList()
                        )
                ));
    }


    public void addListDetails(View view) {
        utilsView.addIconAndButton(view.getDetailsPane(), department.getDepartmentType(), department.toString(), (_ -> show(view)));
    }

    public void show(View view) {
        View.viewRunnable = () -> show(view);
        utilsView.addBreadCrumb(view.getBreadCrumbBar(), department);
        view.clearNavigationPanel();
        utilsView.addIconAndTitle(view.getTitlePane(), department.getDepartmentType(), department.toString());
        utilsView.addSelectedButtonWithGraphic(view.getButtonsPane(), department.defaultStationType(), "Stations", () -> showStations(view));
        utilsView.addButtonWithGraphic(view.getButtonsPane(), department.defaultUnitType(), "Units", () -> showUnits(view));
        utilsView.addButtonWithGraphic(view.getButtonsPane(), department.defaultVehicleType(), "Vehicles", () -> showVehicles(view));
        utilsView.addButtonWithGraphic(view.getButtonsPane(), department.defaultAppearanceType(), "Responders", () -> showResponders(view));


    }

    private void showStations(View view) {
        View.viewRunnable = () -> showStations(view);
        showNavigationPanelStations(view);
        showMapStations(view);
    }

    private void showNavigationPanelStations(View view) {
        view.clearDetailsPane();
        utilsView.addSeparatorToPane(view.getDetailsPane());
        for (StationView stationView : stationViews.values()) {
            stationView.addListDetails(view);
        }
    }

    public void showMapStations(View view) {
        view.hideMap();
        stationNodesByLocation().forEach(utilsView::distributeResourceIconsByLocation);
        for (StationView stationView : stationViews.values()) {
            stationView.showNode();
        }
    }

    private void showUnits(View view) {
        View.viewRunnable = () -> showUnits(view);
        showNavigationPanelUnits(view);
        showMapUnits(view);
    }

    private void showNavigationPanelUnits(View view) {
        view.clearDetailsPane();
        utilsView.addAvailableResources(view.getDetailsPane(), department.unitsByStatus(), department.unitsByTypeAndStatus());
        for (UnitView unitView : getUnitViews().values()) {
            unitView.addListDetails(view);
        }
    }

    public void showMapUnits(View view) {
        view.hideMap();
        unitNodesByLocation().forEach(utilsView::distributeResourceIconsByLocation);
        for (UnitView unitView : getUnitViews().values()) {
            unitView.showNode();
        }
    }

    private void showVehicles(View view) {
        View.viewRunnable = () -> showVehicles(view);
        showNavigationPanelVehicles(view);
        showMapVehicles(view);
    }

    private void showNavigationPanelVehicles(View view) {
        view.clearDetailsPane();
        utilsView.addAvailableResources(view.getDetailsPane(), department.vehiclesByStatus(), department.vehiclesByTypeAndStatus());
        for (VehicleView vehicleView : getVehicleViews().values()) {
            vehicleView.addListDetails(view);
        }
    }

    public void showMapVehicles(View view) {
        view.hideMap();
        vehicleNodesByLocation().forEach(utilsView::distributeResourceIconsByLocation);
        for (VehicleView vehicleView : getVehicleViews().values()) {
            vehicleView.showNode();
        }
    }

    private void showResponders(View view) {
        View.viewRunnable = () -> showResponders(view);
        showNavigationPanelResponders(view);
        showMapResponders(view);
    }

    private void showNavigationPanelResponders(View view) {
        view.clearDetailsPane();
        utilsView.addAvailableResources(view.getDetailsPane(), department.respondersByStatus(), department.respondersByRankAndStatus());
        for (ResponderView responderView : getResponderViews().values()) {
            responderView.addListDetails(view);
        }
    }

    public void showMapResponders(View view) {
        view.hideMap();
        responderNodesByLocation().forEach(utilsView::distributeResourceIconsByLocation);
        for (ResponderView responderView : getResponderViews().values()) {
            responderView.showNode();
        }
    }

}