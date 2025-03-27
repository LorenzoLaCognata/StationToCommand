package stationtocommand.view.departmentStructure;

import javafx.geometry.Pos;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import stationtocommand.model.departmentStructure.AppearanceType;
import stationtocommand.model.departmentStructure.Department;
import stationtocommand.model.rankTypeStructure.RankType;
import stationtocommand.model.responderStructure.Responder;
import stationtocommand.model.responderStructure.ResponderStatus;
import stationtocommand.model.stationStructure.StationType;
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
import stationtocommand.view.stationStructure.StationListView;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public class DepartmentView {

    private final UtilsView utilsView;
    private final StationListView stationListView;

    public DepartmentView(UtilsView utilsView) {
        this.utilsView = utilsView;
        this.stationListView = new StationListView(utilsView);
    }

    public StationListView getStationListView() {
        return stationListView;
    }

    public void show(View view, Department department) {
        View.viewRunnable = () -> show(view, department);
        utilsView.addBreadCrumb(view.getBreadCrumbBar(), department);
        view.getNavigationPanel().clearAll();
        view.getWorldMap().clear();
        showDepartmentDetails(view, department);

        Button stationsButton = utilsView.addButtonToHorizontalPane(view.getNavigationPanel().getButtonsPane(), "Stations", (_ -> showDepartmentStations(view, department)));
        stationsButton.setGraphic(utilsView.smallIcon(StationType.FIRE_STATION.getResourcePath(), ""));
        Button unitsButton = utilsView.addButtonToHorizontalPane(view.getNavigationPanel().getButtonsPane(), "Units", (_ -> showDepartmentUnitCounts(view, department)));
        unitsButton.setGraphic(utilsView.smallIcon(FireUnitType.FIRE_ENGINE.getResourcePath(), ""));
        Button vehiclesButton = utilsView.addButtonToHorizontalPane(view.getNavigationPanel().getButtonsPane(), "Vehicles", (_ -> showDepartmentVehicleCounts(view, department)));
        vehiclesButton.setGraphic(utilsView.smallIcon(PoliceVehicleType.SUV.getResourcePath(), ""));
        Button respondersButton = utilsView.addButtonToHorizontalPane(view.getNavigationPanel().getButtonsPane(), "Responders", (_ -> showDepartmentResponderCounts(view, department)));
        respondersButton.setGraphic(utilsView.smallIcon(AppearanceType.MALE_01.getResourcePath(), ""));

        showDepartmentStations(view, department);
    }

    private void showDepartmentStations(View view, Department department) {
        View.viewRunnable = () -> showDepartmentStations(view, department);
        view.getNavigationPanel().clearDetails();
        view.getWorldMap().clear();
        stationListView.show(view, department.getStations());
    }

    private void showDepartmentDetails(View view, Department department) {
        Pane horizontalTitlePane = utilsView.createHBox(view.getNavigationPanel().getTitlePane());
        utilsView.addIconToPane(horizontalTitlePane, IconType.MEDIUM, IconColor.EMPTY, department.getDepartmentType().getResourcePath(), "");
        utilsView.addMainTitleLabel(horizontalTitlePane, department.toString());
    }

    private void showDepartmentUnitCounts(View view, Department department) {
        View.viewRunnable = () -> showDepartmentUnitCounts(view, department);
        view.getNavigationPanel().clearDetails();

        Map<UnitStatus, Long> unitStatusCounts = department.getStations().stream()
                .flatMap(station -> station.getUnits().stream())
                .collect(Collectors.groupingBy(
                        Unit::getUnitStatus,
                        Collectors.counting())
                );

        Map<UnitType, Map<UnitStatus, Long>> unitTypeStatusCounts = department.getStations().stream()
                .flatMap(station -> station.getUnits().stream())
                .collect(Collectors.groupingBy(
                                Unit::getUnitType,
                                Collectors.groupingBy(
                                        Unit::getUnitStatus,
                                        Collectors.counting())
                        )
                );

        utilsView.addSectionTitleLabel(view.getNavigationPanel().getDetailsPane(), "Unit Status");
        Pane horizontalDetailsPane = utilsView.createHBox(view.getNavigationPanel().getDetailsPane());
        addCount(horizontalDetailsPane, department, unitStatusCounts, unitTypeStatusCounts);

    }

    private void showDepartmentVehicleCounts(View view, Department department) {
        View.viewRunnable = () -> showDepartmentVehicleCounts(view, department);
        view.getNavigationPanel().clearDetails();

        Map<VehicleStatus, Long> vehicleStatusCounts = department.getStations().stream()
                .flatMap(station -> station.getUnits().stream())
                .flatMap(unit -> unit.getVehicles().stream())
                .collect(Collectors.groupingBy(
                        Vehicle::getVehicleStatus,
                        Collectors.counting())
                );

        Map<VehicleType, Map<VehicleStatus, Long>> vehicleTypeStatusCounts = department.getStations().stream()
                .flatMap(station -> station.getUnits().stream())
                .flatMap(unit -> unit.getVehicles().stream())
                .collect(Collectors.groupingBy(
                                Vehicle::getVehicleType,
                                Collectors.groupingBy(
                                        Vehicle::getVehicleStatus,
                                        Collectors.counting())
                        )
                );

        utilsView.addSectionTitleLabel(view.getNavigationPanel().getDetailsPane(), "Vehicle Status");
        Pane horizontalDetailsPane = utilsView.createHBox(view.getNavigationPanel().getDetailsPane());
        addCount(horizontalDetailsPane, department, vehicleStatusCounts, vehicleTypeStatusCounts);

    }

    private void showDepartmentResponderCounts(View view, Department department) {
        View.viewRunnable = () -> showDepartmentResponderCounts(view, department);
        view.getNavigationPanel().clearDetails();

        Map<ResponderStatus, Long> responderStatusCounts = department.getStations().stream()
                .flatMap(station -> station.getUnits().stream())
                .flatMap(unit -> unit.getResponders().stream())
                .collect(Collectors.groupingBy(
                    Responder::getResponderStatus,
                    Collectors.counting())
                );

        Map<RankType, Map<ResponderStatus, Long>> responderRankStatusCounts = department.getStations().stream()
                .flatMap(station -> station.getUnits().stream())
                .flatMap(unit -> unit.getResponders().stream())
                .collect(Collectors.groupingBy(
                    responder -> responder.getRank().getRankType(),
                    Collectors.groupingBy(
                        Responder::getResponderStatus,
                        Collectors.counting())
                    )
                );

        utilsView.addSectionTitleLabel(view.getNavigationPanel().getDetailsPane(), "Responder Status");
        Pane horizontalDetailsPane = utilsView.createHBox(view.getNavigationPanel().getDetailsPane());
        addCount(horizontalDetailsPane, department, responderStatusCounts, responderRankStatusCounts);

    }

    private <T extends EnumWithResource, S extends EnumWithResource> void addCount(Pane pane, Department department, Map<S, Long> statusCounts, Map<T, Map<S, Long>> typeStatusCounts) {
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

        verticalDetailPane.getChildren().add(pieChart);
        addStatusPieChartSlices(pieChart, statusCounts, statusCounts.keySet().iterator().next());

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