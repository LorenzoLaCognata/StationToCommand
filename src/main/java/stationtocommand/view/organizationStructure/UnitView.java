package stationtocommand.view.organizationStructure;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import stationtocommand.model.departmentStructure.AppearanceType;
import stationtocommand.model.rankTypeStructure.RankType;
import stationtocommand.model.responderStructure.Responder;
import stationtocommand.model.responderStructure.ResponderStatus;
import stationtocommand.model.unitStructure.Unit;
import stationtocommand.model.vehicleStructure.PoliceVehicleType;
import stationtocommand.model.vehicleStructure.Vehicle;
import stationtocommand.model.vehicleStructure.VehicleStatus;
import stationtocommand.model.vehicleStructure.VehicleType;
import stationtocommand.view.View;
import stationtocommand.view.mainStructure.IconColor;
import stationtocommand.view.mainStructure.IconType;
import stationtocommand.view.mainStructure.UtilsView;

import java.util.Map;
import java.util.stream.Collectors;

public class UnitView {

    private final Unit unit;
    private final UtilsView utilsView;
    private final Map<Vehicle, VehicleView> vehicleViews;
    private final Map<Responder, ResponderView> responderViews;

    public UnitView(Unit unit, UtilsView utilsView) {
        this.unit = unit;
        this.utilsView = utilsView;
        this.vehicleViews = unit.getStation().getVehicles().stream()
                                .collect(Collectors.toMap(
                                        vehicle -> vehicle, vehicle -> new VehicleView(vehicle, utilsView))
                                );
        this.responderViews = unit.getStation().getResponders().stream()
                                .collect(Collectors.toMap(
                                        responder -> responder, responder -> new ResponderView(responder, utilsView))
                                );
    }

    public VehicleView getVehicleView(Vehicle vehicle) {
        return vehicleViews.get(vehicle);
    }

    public ResponderView getResponderView(Responder responder) {
        return responderViews.get(responder);
    }

    public void addStationDetailsUnit(View view) {
        Pane horizontalDetailsPane = utilsView.createHBox(view.getNavigationPanel().getDetailsPane());
        addUnitIcon(horizontalDetailsPane);
        addUnitButton(view, horizontalDetailsPane);
        addUnitStatusIcon(horizontalDetailsPane);
    }

    private void addUnitIcon(Pane pane) {
        utilsView.addIconToPane(pane, IconType.SMALL, IconColor.EMPTY, unit.getUnitType());
    }

    private void addUnitButton(View view, Pane pane) {
        utilsView.addButtonToPane(pane, unit.toString(), (_ -> showUnit(view)));
    }

    private void addUnitStatusIcon(Pane pane) {
        utilsView.addIconToPane(pane, IconType.SMALL, IconColor.EMPTY, unit.getUnitStatus());
    }

    public void showUnit(View view) {
        View.viewRunnable = () -> showUnit(view);
        utilsView.addBreadCrumb(view.getBreadCrumbBar(), unit);
        view.getNavigationPanel().clearAll();
        showUnitDetails(view);
    }

    private void showUnitDetails(View view) {
        addUnitTitle(view);

        Pane buttonsPane = view.getNavigationPanel().getButtonsPane();

        EventHandler<ActionEvent> vehiclesButtonHandler = event -> {
            utilsView.setPaneButtonsSelectionStyle(event, buttonsPane);
            showUnitVehicles(view);
        };
        Button vehiclesButton = utilsView.addButtonToHorizontalPane(buttonsPane, "Vehicles", vehiclesButtonHandler);
        vehiclesButton.setGraphic(utilsView.smallIcon(PoliceVehicleType.SUV.getResourcePath(), ""));

        EventHandler<ActionEvent> respondersButtonHandler = event -> {
            utilsView.setPaneButtonsSelectionStyle(event, buttonsPane);
            showUnitResponders(view);
        };
        Button respondersButton = utilsView.addButtonToHorizontalPane(buttonsPane, "Responders", respondersButtonHandler);
        respondersButton.setGraphic(utilsView.smallIcon(AppearanceType.MALE_01.getResourcePath(), ""));

        utilsView.setButtonSelectedStyle(vehiclesButton);
        showUnitVehicles(view);
    }

    private void addUnitTitle(View view) {
        Pane horizontalTitlePane = utilsView.createHBox(view.getNavigationPanel().getTitlePane());
        utilsView.addIconToPane(horizontalTitlePane, IconType.MEDIUM, IconColor.EMPTY, unit.getUnitType());
        utilsView.addMainTitleLabel(horizontalTitlePane, unit.toString());
    }

    private void showUnitVehicles(View view) {
        View.viewRunnable = () -> showUnitVehicles(view);
        view.getNavigationPanel().clearDetails();

        Map<VehicleStatus, Long> vehicleStatusCounts = unit.getVehicles().stream()
                .collect(Collectors.groupingBy(
                        Vehicle::getVehicleStatus,
                        Collectors.counting())
                );

        Map<VehicleType, Map<VehicleStatus, Long>> vehicleTypeStatusCounts = unit.getVehicles().stream()
                .collect(Collectors.groupingBy(
                                Vehicle::getVehicleType,
                                Collectors.groupingBy(
                                        Vehicle::getVehicleStatus,
                                        Collectors.counting())
                        )
                );

        utilsView.addSeparatorToPane(view.getNavigationPanel().getDetailsPane());
        Pane horizontalDetailsPane = utilsView.createHBox(view.getNavigationPanel().getDetailsPane());
        utilsView.addAvailabilityCount(horizontalDetailsPane, vehicleStatusCounts, vehicleTypeStatusCounts);

        utilsView.addSeparatorToPane(view.getNavigationPanel().getDetailsPane());
        for (VehicleView vehicleView : vehicleViews.values()) {
            vehicleView.addStationDetailsVehicle(view);
        }
    }

    private void showUnitResponders(View view) {
        View.viewRunnable = () -> showUnitResponders(view);
        view.getNavigationPanel().clearDetails();

        Map<ResponderStatus, Long> responderStatusCounts = unit.getResponders().stream()
                .collect(Collectors.groupingBy(
                        Responder::getResponderStatus,
                        Collectors.counting())
                );

        Map<RankType, Map<ResponderStatus, Long>> responderRankStatusCounts = unit.getResponders().stream()
                .collect(Collectors.groupingBy(
                                responder -> responder.getRank().getRankType(),
                                Collectors.groupingBy(
                                        Responder::getResponderStatus,
                                        Collectors.counting())
                        )
                );

        utilsView.addSeparatorToPane(view.getNavigationPanel().getDetailsPane());
        Pane horizontalDetailsPane = utilsView.createHBox(view.getNavigationPanel().getDetailsPane());
        utilsView.addAvailabilityCount(horizontalDetailsPane, responderStatusCounts, responderRankStatusCounts);

        utilsView.addSeparatorToPane(view.getNavigationPanel().getDetailsPane());
        for (ResponderView responderView : responderViews.values()) {
            responderView.addStationDetailsResponder(view);
        }
    }

}