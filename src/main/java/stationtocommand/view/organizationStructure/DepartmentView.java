package stationtocommand.view.organizationStructure;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import stationtocommand.model.departmentStructure.Department;
import stationtocommand.model.locationStructure.Location;
import stationtocommand.model.personStructure.AppearanceType;
import stationtocommand.model.rankTypeStructure.RankType;
import stationtocommand.model.responderStructure.Responder;
import stationtocommand.model.responderStructure.ResponderStatus;
import stationtocommand.model.stationStructure.Station;
import stationtocommand.model.stationStructure.StationType;
import stationtocommand.model.unitStructure.Unit;
import stationtocommand.model.unitStructure.UnitStatus;
import stationtocommand.model.unitTypeStructure.FireUnitType;
import stationtocommand.model.unitTypeStructure.MedicUnitType;
import stationtocommand.model.unitTypeStructure.PoliceUnitType;
import stationtocommand.model.unitTypeStructure.UnitType;
import stationtocommand.model.vehicleStructure.*;
import stationtocommand.view.View;
import stationtocommand.view.mainStructure.IconColor;
import stationtocommand.view.mainStructure.IconType;
import stationtocommand.view.mainStructure.UtilsView;

import java.util.*;
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

    public void addOrganizationDetailsDepartment(View view) {
        Pane horizontalDetailsPane = utilsView.createHBox(view.getDetailsPane());
        addDepartmentIcon(horizontalDetailsPane);
        addDepartmentButton(view, horizontalDetailsPane);
    }

    private void addDepartmentTitle(View view) {
        Pane horizontalTitlePane = utilsView.createHBox(view.getTitlePane());
        utilsView.addIconToPane(horizontalTitlePane, IconType.MEDIUM, IconColor.EMPTY, department.getDepartmentType());
        utilsView.addMainTitleLabel(horizontalTitlePane, department.toString());
    }

    private void addDepartmentIcon(Pane pane) {
        utilsView.addIconToPane(pane, IconType.SMALL, IconColor.EMPTY, department.getDepartmentType());
    }

    private void addDepartmentButton(View view, Pane pane) {
        utilsView.addButtonToPane(pane, department.toString(), (_ -> showDepartment(view)));
    }

    public void showDepartment(View view) {
        View.viewRunnable = () -> showDepartment(view);
        utilsView.addBreadCrumb(view.getBreadCrumbBar(), department);
        view.clearNavigationPanel();
        view.hideMap();
        addDepartmentTitle(view);

        Pane buttonsPane = view.getButtonsPane();

        EventHandler<ActionEvent> stationsButtonHandler = event -> {
            utilsView.setPaneButtonsSelectionStyle(event, buttonsPane);
            showDepartmentStations(view);
        };
        Button stationsButton = utilsView.addHorizontalButtonToPane(buttonsPane, "Stations", stationsButtonHandler);
        StationType stationType = switch(department.getDepartmentType()) {
            case FIRE_DEPARTMENT -> StationType.FIRE_STATION;
            case POLICE_DEPARTMENT -> StationType.POLICE_STATION;
            case MEDIC_DEPARTMENT -> StationType.MEDIC_STATION;
        };
        stationsButton.setGraphic(utilsView.smallIcon(stationType.getResourcePath(), ""));

        EventHandler<ActionEvent> unitsButtonHandler = event -> {
            utilsView.setPaneButtonsSelectionStyle(event, buttonsPane);
            showDepartmentUnits(view);
        };
        Button unitsButton = utilsView.addHorizontalButtonToPane(buttonsPane, "Units", unitsButtonHandler);
        UnitType unitType = switch(department.getDepartmentType()) {
            case FIRE_DEPARTMENT -> FireUnitType.values()[0];
            case POLICE_DEPARTMENT -> PoliceUnitType.values()[0];
            case MEDIC_DEPARTMENT -> MedicUnitType.values()[0];
        };
        unitsButton.setGraphic(utilsView.smallIcon(unitType.getResourcePath(), ""));

        EventHandler<ActionEvent> vehiclesButtonHandler = event -> {
            utilsView.setPaneButtonsSelectionStyle(event, buttonsPane);
            showDepartmentVehicles(view);
        };
        Button vehiclesButton = utilsView.addHorizontalButtonToPane(buttonsPane, "Vehicles", vehiclesButtonHandler);
        VehicleType vehicleType = switch(department.getDepartmentType()) {
            case FIRE_DEPARTMENT -> FireVehicleType.values()[0];
            case POLICE_DEPARTMENT -> PoliceVehicleType.values()[0];
            case MEDIC_DEPARTMENT -> MedicVehicleType.values()[0];
        };
        vehiclesButton.setGraphic(utilsView.smallIcon(vehicleType.getResourcePath(), ""));

        EventHandler<ActionEvent> respondersButtonHandler = event -> {
            utilsView.setPaneButtonsSelectionStyle(event, buttonsPane);
            showDepartmentResponders(view);
        };
        Button respondersButton = utilsView.addHorizontalButtonToPane(buttonsPane, "Responders", respondersButtonHandler);
        Responder chiefResponder = department.getStations().stream()
                .flatMap(station -> station.getUnits().stream())
                .flatMap(unit -> unit.getResponders().stream())
                .min(Comparator.naturalOrder())
                .orElse(null);
        respondersButton.setGraphic(utilsView.smallIcon(chiefResponder != null ? chiefResponder.getAppearanceType().getResourcePath() : AppearanceType.MALE_01.getResourcePath(), ""));

        utilsView.setButtonSelectedStyle(stationsButton);
        showDepartmentStations(view);
    }

    private void showDepartmentStations(View view) {
        View.viewRunnable = () -> showDepartmentStations(view);
        showDepartmentStationsDetails(view);
        showDepartmentStationsMap(view);
    }

    private void showDepartmentStationsDetails(View view) {
        view.clearDetailsPane();
        utilsView.addSeparatorToPane(view.getDetailsPane());
        for (StationView stationView : stationViews.values()) {
            stationView.addListDetails(view);
        }
    }

    public void showDepartmentStationsMap(View view) {
        view.hideMap();

        Map<Location, List<StationView>> stationViewsByLocation = stationViews.values().stream()
                .collect(Collectors.groupingBy(
                        v -> v.getStation().getLocation()
                ));

        for (Map.Entry<Location, List<StationView>> locationStationViews : stationViewsByLocation.entrySet()) {
            List<Node> locationNodes = locationStationViews.getValue().stream()
                    .map(StationView::getNode)
                    .toList();
            utilsView.distributeResourceIconsByLocation(locationStationViews.getKey(), locationNodes);
        }

        for (StationView stationView : stationViews.values()) {
            stationView.showNode();
        }
    }

    private void showDepartmentUnits(View view) {
        View.viewRunnable = () -> showDepartmentUnits(view);
        showDepartmentUnitsDetails(view);
        showDepartmentUnitsMap(view);
    }

    private void showDepartmentUnitsDetails(View view) {
        view.clearDetailsPane();

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

        utilsView.addSeparatorToPane(view.getDetailsPane());
        Pane horizontalDetailsPane = utilsView.createHBox(view.getDetailsPane());
        utilsView.addAvailabilityCount(horizontalDetailsPane, unitStatusCounts, unitTypeStatusCounts);

        utilsView.addSeparatorToPane(view.getDetailsPane());
        for (UnitView unitView : unitViews.values()) {
            unitView.addListDetails(view);
        }
    }

    public void showDepartmentUnitsMap(View view) {
        view.hideMap();

        Map<Location, List<UnitView>> unitViewsByLocation = unitViews.values().stream()
                .collect(Collectors.groupingBy(
                        v -> v.getUnit().getStation().getLocation()
                ));

        for (Map.Entry<Location, List<UnitView>> locationUnitViews : unitViewsByLocation.entrySet()) {
            List<Node> locationNodes = locationUnitViews.getValue().stream()
                    .map(UnitView::getNode)
                    .toList();
            utilsView.distributeResourceIconsByLocation(locationUnitViews.getKey(), locationNodes);
        }

        for (UnitView unitView : unitViews.values()) {
            unitView.showNode();
        }
    }

    private void showDepartmentVehicles(View view) {
        View.viewRunnable = () -> showDepartmentVehicles(view);
        showDepartmentVehiclesDetails(view);
        showDepartmentVehiclesMap(view);
    }

    private void showDepartmentVehiclesDetails(View view) {
        view.clearDetailsPane();

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

        utilsView.addSeparatorToPane(view.getDetailsPane());
        Pane horizontalDetailsPane = utilsView.createHBox(view.getDetailsPane());
        utilsView.addAvailabilityCount(horizontalDetailsPane, vehicleStatusCounts, vehicleTypeStatusCounts);

        utilsView.addSeparatorToPane(view.getDetailsPane());
        for (VehicleView vehicleView : vehicleViews.values()) {
            vehicleView.addListDetails(view);
        }
    }

    public void showDepartmentVehiclesMap(View view) {
        view.hideMap();

        Map<Location, List<VehicleView>> vehicleViewsByLocation = vehicleViews.values().stream()
                .collect(Collectors.groupingBy(
                        v -> v.getVehicle().getLocation()
                ));

        for (Map.Entry<Location, List<VehicleView>> locationVehicleViews : vehicleViewsByLocation.entrySet()) {
            List<Node> locationNodes = locationVehicleViews.getValue().stream()
                    .map(VehicleView::getNode)
                    .toList();
            utilsView.distributeResourceIconsByLocation(locationVehicleViews.getKey(), locationNodes);
        }

        for (VehicleView vehicleView : vehicleViews.values()) {
            vehicleView.showNode();
        }
    }

    private void showDepartmentResponders(View view) {
        View.viewRunnable = () -> showDepartmentResponders(view);
        showDepartmentRespondersDetails(view);
        showDepartmentRespondersMap(view);
    }

    private void showDepartmentRespondersDetails(View view) {
        view.clearDetailsPane();

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

        utilsView.addSeparatorToPane(view.getDetailsPane());
        Pane horizontalDetailsPane = utilsView.createHBox(view.getDetailsPane());
        utilsView.addAvailabilityCount(horizontalDetailsPane, responderStatusCounts, responderRankStatusCounts);

        utilsView.addSeparatorToPane(view.getDetailsPane());
        for (ResponderView responderView : responderViews.values()) {
            responderView.addListDetails(view);
        }
    }

    public void showDepartmentRespondersMap(View view) {
        view.hideMap();

        Map<Location, List<ResponderView>> responderViewsByLocation = responderViews.values().stream()
                .collect(Collectors.groupingBy(
                        v -> v.getResponder().getLocation()
                ));

        for (Map.Entry<Location, List<ResponderView>> locationResponderViews : responderViewsByLocation.entrySet()) {
            List<Node> locationNodes = locationResponderViews.getValue().stream()
                    .map(ResponderView::getNode)
                    .toList();
            utilsView.distributeResourceIconsByLocation(locationResponderViews.getKey(), locationNodes);
        }

        for (ResponderView responderView : responderViews.values()) {
            responderView.showNode();
        }
    }

}