package stationtocommand.view.stationStructure;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import stationtocommand.model.departmentStructure.AppearanceType;
import stationtocommand.model.rankTypeStructure.RankType;
import stationtocommand.model.responderStructure.Responder;
import stationtocommand.model.responderStructure.ResponderStatus;
import stationtocommand.model.stationStructure.Station;
import stationtocommand.model.unitStructure.Unit;
import stationtocommand.model.unitStructure.UnitStatus;
import stationtocommand.model.unitTypeStructure.FireUnitType;
import stationtocommand.model.unitTypeStructure.UnitType;
import stationtocommand.model.utilsStructure.EnumWithResource;
import stationtocommand.model.vehicleStructure.PoliceVehicleType;
import stationtocommand.model.vehicleStructure.Vehicle;
import stationtocommand.model.vehicleStructure.VehicleStatus;
import stationtocommand.model.vehicleStructure.VehicleType;
import stationtocommand.view.View;
import stationtocommand.view.mainStructure.IconColor;
import stationtocommand.view.mainStructure.IconType;
import stationtocommand.view.mainStructure.UtilsView;
import stationtocommand.view.responderStructure.ResponderListView;
import stationtocommand.view.unitStructure.UnitListView;
import stationtocommand.view.vehicleStructure.VehicleListView;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

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

    public void show(View view, Station station) {
        View.viewRunnable = () -> show(view, station);
        utilsView.addBreadCrumb(view.getBreadCrumbBar(), station);
        view.getNavigationPanel().clearAll();
        view.getWorldMap().clear();
        showSidebar(view, station);
        showMap(view, station);
    }

    private void showSidebar(View view, Station station) {
        showStationDetails(view, station);

        Pane buttonsPane = view.getNavigationPanel().getButtonsPane();

        EventHandler<ActionEvent> unitsButtonHandler = event -> {
            utilsView.setPaneButtonsSelectionStyle(event, buttonsPane);
            showStationUnits(view, station);
        };
        Button unitsButton = utilsView.addButtonToHorizontalPane(buttonsPane, "Units", unitsButtonHandler);
        unitsButton.setGraphic(utilsView.smallIcon(FireUnitType.FIRE_ENGINE.getResourcePath(), ""));

        EventHandler<ActionEvent> vehiclesButtonHandler = event -> {
            utilsView.setPaneButtonsSelectionStyle(event, buttonsPane);
            showStationVehicles(view, station);
        };
        Button vehiclesButton = utilsView.addButtonToHorizontalPane(buttonsPane, "Vehicles", vehiclesButtonHandler);
        vehiclesButton.setGraphic(utilsView.smallIcon(PoliceVehicleType.SUV.getResourcePath(), ""));

        EventHandler<ActionEvent> respondersButtonHandler = event -> {
            utilsView.setPaneButtonsSelectionStyle(event, buttonsPane);
            showStationResponders(view, station);
        };
        Button respondersButton = utilsView.addButtonToHorizontalPane(buttonsPane, "Responders", respondersButtonHandler);
        respondersButton.setGraphic(utilsView.smallIcon(AppearanceType.MALE_01.getResourcePath(), ""));

        utilsView.setButtonSelectedStyle(unitsButton);
        showStationUnits(view, station);
    }

    private void showStationDetails(View view, Station station) {
        Pane horizontalTitlePane = utilsView.createHBox(view.getNavigationPanel().getTitlePane());
        utilsView.addIconToPane(horizontalTitlePane, IconType.MEDIUM, IconColor.EMPTY, station.getStationType().getResourcePath(), "");
        utilsView.addMainTitleLabel(horizontalTitlePane, station.toString());
    }

    private void showStationUnits(View view, Station station) {
        View.viewRunnable = () -> showStationUnits(view, station);
        view.getNavigationPanel().clearDetails();

        Map<UnitStatus, Long> unitStatusCounts = station.getUnits().stream()
            .collect(Collectors.groupingBy(
                Unit::getUnitStatus,
                Collectors.counting())
            );

        Map<UnitType, Map<UnitStatus, Long>> unitTypeStatusCounts = station.getUnits().stream()
                .collect(Collectors.groupingBy(
                    Unit::getUnitType,
                    Collectors.groupingBy(
                            Unit::getUnitStatus,
                            Collectors.counting())
                    )
                );

        utilsView.addSeparatorToPane(view.getNavigationPanel().getDetailsPane());
        Pane horizontalDetailsPane = utilsView.createHBox(view.getNavigationPanel().getDetailsPane());
        addCount(horizontalDetailsPane, unitStatusCounts, unitTypeStatusCounts);

        utilsView.addSeparatorToPane(view.getNavigationPanel().getDetailsPane());
        unitListView.show(view, station.getUnits());
    }

    private void showStationVehicles(View view, Station station) {
        View.viewRunnable = () -> showStationVehicles(view, station);
        view.getNavigationPanel().clearDetails();

        Map<VehicleStatus, Long> vehicleStatusCounts = station.getUnits().stream()
            .flatMap(unit -> unit.getVehicles().stream())
            .collect(Collectors.groupingBy(
                Vehicle::getVehicleStatus,
                Collectors.counting())
            );

        Map<VehicleType, Map<VehicleStatus, Long>> vehicleTypeStatusCounts = station.getUnits().stream()
            .flatMap(unit -> unit.getVehicles().stream())
            .collect(Collectors.groupingBy(
                Vehicle::getVehicleType,
                Collectors.groupingBy(
                    Vehicle::getVehicleStatus,
                    Collectors.counting())
                )
            );

        utilsView.addSeparatorToPane(view.getNavigationPanel().getDetailsPane());
        Pane horizontalDetailsPane = utilsView.createHBox(view.getNavigationPanel().getDetailsPane());
        addCount(horizontalDetailsPane, vehicleStatusCounts, vehicleTypeStatusCounts);

        utilsView.addSeparatorToPane(view.getNavigationPanel().getDetailsPane());
        vehicleListView.show(view, station.getVehicles());

    }

    private void showStationResponders(View view, Station station) {
        View.viewRunnable = () -> showStationResponders(view, station);
        view.getNavigationPanel().clearDetails();

        Map<ResponderStatus, Long> responderStatusCounts = station.getUnits().stream()
            .flatMap(unit -> unit.getResponders().stream())
            .collect(Collectors.groupingBy(
                Responder::getResponderStatus,
                Collectors.counting())
            );

        Map<RankType, Map<ResponderStatus, Long>> responderRankStatusCounts = station.getUnits().stream()
            .flatMap(unit -> unit.getResponders().stream())
            .collect(Collectors.groupingBy(
                responder -> responder.getRank().getRankType(),
                Collectors.groupingBy(
                    Responder::getResponderStatus,
                    Collectors.counting())
                )
            );

        utilsView.addSeparatorToPane(view.getNavigationPanel().getDetailsPane());
        Pane horizontalDetailsPane = utilsView.createHBox(view.getNavigationPanel().getDetailsPane());
        addCount(horizontalDetailsPane, responderStatusCounts, responderRankStatusCounts);

        utilsView.addSeparatorToPane(view.getNavigationPanel().getDetailsPane());
        responderListView.show(view, station.getResponders());
    }

    public void showMap(View view, Station station) {
        Point2D point = utilsView.locationToPoint(station.getLocation(), IconType.SMALL);
        ImageView imageView = utilsView.smallShadowIcon(station.getStationType().getResourcePath(), station.getStationType().toString(), utilsView.departmentIconColor(station.getDepartment()));
        Pane mapLayer = view.getWorldMap().getMapElementsLayer();
        utilsView.addNodeToPane(mapLayer, imageView, point);
    }

    public  <T extends EnumWithResource, S extends EnumWithResource> void addCount(Pane pane, Map<S, Long> statusCounts, Map<T, Map<S, Long>> typeStatusCounts) {
        Pane verticalDetailPane = utilsView.createVBox(pane);

        PieChart pieChart = new PieChart();
        pieChart.setAnimated(false);
        /*+
        String color = switch (i) {
            case 0 -> "#66bb6a";
            case 1 -> "#ff7043";
            case 2 -> "#c62828";
            case 3 -> "#00796b";
            case 4 -> "#424242";
            default -> "black";
        };
        */

        pieChart.setMinHeight(400);
        pieChart.setPrefHeight(400);
        verticalDetailPane.getChildren().add(pieChart);
        addStatusPieChartSlices(pieChart, statusCounts, statusCounts.keySet().iterator().next());

        utilsView.addSeparatorToPane(verticalDetailPane);
        Pane horizontalDetailsPane = utilsView.createHBox(verticalDetailPane);
        addTypeCounts(horizontalDetailsPane, typeStatusCounts, typeStatusCounts.keySet().iterator().next(), statusCounts.keySet().iterator().next());

    }

    @SuppressWarnings("unchecked")
    private <S extends EnumWithResource> void addStatusPieChartSlices(PieChart pieChart, Map<S, Long> statusCounts, S sampleStatusClass) {
        for (EnumWithResource status : sampleStatusClass.getValues()) {
            addStatusPieChartSlice(pieChart, statusCounts, (S) status);
        }
    }

    private <S extends EnumWithResource> void addStatusPieChartSlice(PieChart pieChart, Map<S, Long> counts, S status) {
        int value = counts.getOrDefault(status, 0L).intValue();
        PieChart.Data slice = new PieChart.Data(status.toString(), value);
        pieChart.getData().add(slice);
    }

    @SuppressWarnings("unchecked")
    private <T extends EnumWithResource, S extends EnumWithResource> void addTypeCounts(Pane pane, Map<T, Map<S, Long>> counts, T sampleTypeClass, S sampleStatusClass)  {
        for (EnumWithResource type : sampleTypeClass.getValues()) {
            addTypeCount(pane, counts, (T) type, (S) sampleStatusClass.getPrimaryValue());
        }
    }

    private <T extends EnumWithResource, S extends EnumWithResource> void addTypeCount(Pane pane, Map<T, Map<S, Long>> counts, T type, S status) {
        Pane verticalDetailsPane = utilsView.createVBox(pane, Pos.CENTER);
        Map<S, Long> statusCounts = counts.getOrDefault(type, Collections.emptyMap());
        utilsView.addIconToPane(verticalDetailsPane, IconType.SMALL, IconColor.EMPTY, type.getResourcePath(), type.toString());
        long total = statusCounts.values().stream().mapToLong(Long::longValue).sum();
        String count = "";
        if (total > 0) {
            long selectedStatus = statusCounts.getOrDefault(status, 0L);
            count = selectedStatus + " / " + total;
        }
        utilsView.addBodySmallLabel(verticalDetailsPane, count);
    }

}