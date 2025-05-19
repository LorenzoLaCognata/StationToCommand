package stationtocommand.view.organizationStructure;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
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

    private final PieChart pieChart = new PieChart();

    // Constructor

    public DepartmentView(Department department, View view, UtilsView utilsView) {
        this.department = department;
        this.utilsView = utilsView;

        this.stationViews = new LinkedHashMap<>();

        for (Station station : department.getStations()) {
            addStationView(station, view, utilsView);
        }

        createPieChart();
    }

    private void createPieChart() {
        pieChart.setAnimated(false);
        pieChart.setMinHeight(400);
        pieChart.setPrefHeight(400);

        utilsView.addStatusPieChartSlices(pieChart, department.unitsByStatus(), department.unitsByStatus().keySet().iterator().next());
/*
        PieChart.Data slice1 = new PieChart.Data("A", 0);
        PieChart.Data slice2 = new PieChart.Data("B", 0);
        PieChart.Data slice3 = new PieChart.Data("C", 0);
        PieChart.Data slice4 = new PieChart.Data("D", 0);
        PieChart.Data slice5 = new PieChart.Data("E", 0);
        pieChart.getData().clear();
        pieChart.getData().addAll(slice1, slice2, slice3, slice4, slice5);
*/

        Platform.runLater(() -> Platform.runLater(() -> {
            utilsView.setPieChartAvailabilityColors(pieChart);
            utilsView.updatePieChartLabels(pieChart);
        }));
    }

    private void addStationView(Station station, View view, UtilsView utilsView) {
        if (!stationViews.containsKey(station)) {
            StationView stationView = new StationView(station, view, utilsView);
            stationViews.put(station, stationView);
            view.addToMap(stationView.getNode());
        }
    }


    // Getter

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


    // Methods

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


    // Stations

    public Map<Location, List<Node>> stationNodesByLocation() {
        return utilsView.nodesByLocation(stationViews, v -> v.getStation().getLocation());
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


    // Units

    public Map<Location, List<Node>> unitNodesByLocation() {
        return utilsView.nodesByLocation(getUnitViews(), v -> v.getUnit().getStation().getLocation());
    }

    private void showUnits(View view) {
        View.viewRunnable = () -> showUnits(view);
        showNavigationPanelUnits(view);
        showMapUnits(view);
    }

    private void showNavigationPanelUnits(View view) {
        view.clearDetailsPane();

        view.getDetailsPane().getChildren().add(pieChart);
        Platform.runLater(() -> Platform.runLater(() -> {
            utilsView.setPieChartAvailabilityColors(pieChart);
            utilsView.updatePieChartLabels(pieChart);
        }));

        utilsView.updateStatusPieChartSlices(pieChart, department.unitsByStatus(), department.unitsByStatus().keySet().iterator().next());
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


    // Vehicles

    public Map<Location, List<Node>> vehicleNodesByLocation() {
        return utilsView.nodesByLocation(getVehicleViews(), v -> v.getVehicle().getLocation());
    }

    private void showVehicles(View view) {
        View.viewRunnable = () -> showVehicles(view);
        showNavigationPanelVehicles(view);
        showMapVehicles(view);
    }

    private void showNavigationPanelVehicles(View view) {
        view.clearDetailsPane();
        utilsView.addAvailableResourcesOLD(view.getDetailsPane(), department.vehiclesByStatus(), department.vehiclesByTypeAndStatus());
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


    // Responders

    public Map<Location, List<Node>> responderNodesByLocation() {
        return utilsView.nodesByLocation(getResponderViews(), v -> v.getResponder().getLocation());
    }

    private void showResponders(View view) {
        View.viewRunnable = () -> showResponders(view);
        showNavigationPanelResponders(view);
        showMapResponders(view);
    }

    private void showNavigationPanelResponders(View view) {
        view.clearDetailsPane();
        utilsView.addAvailableResourcesOLD(view.getDetailsPane(), department.respondersByStatus(), department.respondersByRankAndStatus());
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