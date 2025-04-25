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

import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class DepartmentView {

    private final Department department;
    private final UtilsView utilsView;
    private final SortedMap<Station, StationView> stationViews;
    private final SortedMap<Unit, UnitView> unitViews;
    private final SortedMap<Vehicle, VehicleView> vehicleViews;
    private final SortedMap<Responder, ResponderView> responderViews;

    public DepartmentView(Department department, View view, UtilsView utilsView) {
        this.department = department;
        this.utilsView = utilsView;

        this.stationViews = new TreeMap<>();
        for (Station station : department.getStations()) {
            StationView stationView = new StationView(station, view, utilsView);
            stationViews.put(station, stationView);
            view.addToMap(stationView.getNode());
        }

        this.unitViews = new TreeMap<>();
        this.vehicleViews = new TreeMap<>();
        this.responderViews = new TreeMap<>();
        for (StationView stationView : stationViews.values()) {
            for (UnitView unitView : stationView.getUnitViews().values()) {
                unitViews.put(unitView.getUnit(), unitView);
                view.addToMap(unitView.getNode());
            }
            for (VehicleView vehicleView : stationView.getVehicleViews().values()) {
                vehicleViews.put(vehicleView.getVehicle(), vehicleView);
                view.addToMap(vehicleView.getNode());
            }
            for (ResponderView responderView : stationView.getResponderViews().values()) {
                responderViews.put(responderView.getResponder(), responderView);
                view.addToMap(responderView.getNode());
            }
        }
    }

    public Department getDepartment() {
        return department;
    }

    public SortedMap<Station, StationView> getStationViews() {
        return stationViews;
    }

    public StationView getStationView(Station station) {
        return stationViews.get(station);
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
        return unitViews.values().stream()
                .collect(Collectors.groupingBy(
                        v -> v.getUnit().getStation().getLocation(),
                        Collectors.mapping(
                                UnitView::getNode,
                                Collectors.toList()
                        )
                ));
    }

    public Map<Location, List<Node>> vehicleNodesByLocation() {
        return vehicleViews.values().stream()
                .collect(Collectors.groupingBy(
                        v -> v.getVehicle().getLocation(),
                        Collectors.mapping(
                                VehicleView::getNode,
                                Collectors.toList()
                        )
                ));
    }

    public Map<Location, List<Node>> responderNodesByLocation() {
        return responderViews.values().stream()
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
        for (UnitView unitView : unitViews.values()) {
            unitView.addListDetails(view);
        }
    }

    public void showMapUnits(View view) {
        view.hideMap();
        unitNodesByLocation().forEach(utilsView::distributeResourceIconsByLocation);
        for (UnitView unitView : unitViews.values()) {
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
        for (VehicleView vehicleView : vehicleViews.values()) {
            vehicleView.addListDetails(view);
        }
    }

    public void showMapVehicles(View view) {
        view.hideMap();
        vehicleNodesByLocation().forEach(utilsView::distributeResourceIconsByLocation);
        for (VehicleView vehicleView : vehicleViews.values()) {
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
        for (ResponderView responderView : responderViews.values()) {
            responderView.addListDetails(view);
        }
    }

    public void showMapResponders(View view) {
        view.hideMap();
        responderNodesByLocation().forEach(utilsView::distributeResourceIconsByLocation);
        for (ResponderView responderView : responderViews.values()) {
            responderView.showNode();
        }
    }

}