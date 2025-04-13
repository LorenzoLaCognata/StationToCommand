package stationtocommand.view.unitStructure;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
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
import stationtocommand.view.responderStructure.ResponderListView;
import stationtocommand.view.vehicleStructure.VehicleListView;

import java.util.Map;
import java.util.stream.Collectors;

public class UnitView {

    private final UtilsView utilsView;
    private final ResponderListView responderListView;
    private final VehicleListView vehicleListView;

    public UnitView(UtilsView utilsView) {
        this.utilsView = utilsView;
        this.responderListView = new ResponderListView(utilsView);
        this.vehicleListView = new VehicleListView(utilsView);
    }

    public ResponderListView getResponderListView() {
        return responderListView;
    }

    public VehicleListView getVehicleListView() {
        return vehicleListView;
    }

    public void show(View view, Unit unit) {
        View.viewRunnable = () -> show(view, unit);
        utilsView.addBreadCrumb(view.getBreadCrumbBar(), unit);
        view.getNavigationPanel().clearAll();
        showUnitDetails(view, unit);
        showUnitButtons(view, unit);
        showUnitVehicles(view, unit);
    }

    private void showUnitButtons(View view, Unit unit) {
        Pane buttonsPane = view.getNavigationPanel().getButtonsPane();

        EventHandler<ActionEvent> vehiclesButtonHandler = event -> {
            utilsView.setPaneButtonsSelectionStyle(event, buttonsPane);
            showUnitVehicles(view, unit);
        };
        Button vehiclesButton = utilsView.addButtonToHorizontalPane(buttonsPane, "Vehicles", vehiclesButtonHandler);
        vehiclesButton.setGraphic(utilsView.smallIcon(PoliceVehicleType.SUV));

        EventHandler<ActionEvent> respondersButtonHandler = event -> {
            utilsView.setPaneButtonsSelectionStyle(event, buttonsPane);
            showUnitResponders(view, unit);
        };
        Button respondersButton = utilsView.addButtonToHorizontalPane(buttonsPane, "Responders", respondersButtonHandler);
        respondersButton.setGraphic(utilsView.smallIcon(AppearanceType.MALE_01));

        utilsView.setButtonSelectedStyle(vehiclesButton);
    }

    private void showUnitDetails(View view, Unit unit) {
        Pane horizontalTitlePane = utilsView.createHBox(view.getNavigationPanel().getTitlePane());
        utilsView.addIconToPane(horizontalTitlePane, IconType.MEDIUM, IconColor.EMPTY, unit.getUnitType());
        utilsView.addMainTitleLabel(horizontalTitlePane, unit.toString());
    }

    public void showUnitVehicles(View view, Unit unit) {
        View.viewRunnable = () -> showUnitVehicles(view, unit);

        view.getNavigationPanel().clearDetails();
        showUnitVehiclesSidebar(view, unit);

        view.getWorldMap().clear();
        showUnitVehiclesMap(view, unit);
    }

    private void showUnitVehiclesSidebar(View view, Unit unit) {
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
        vehicleListView.show(view, unit.getVehicles());
    }

    public void showUnitVehiclesMap(View view, Unit unit) {
        for (Vehicle vehicle : unit.getVehicles()) {
            Point2D point = utilsView.locationToPoint(vehicle.getLocation(), IconType.SMALL);
            ImageView imageView = utilsView.smallIcon(vehicle.getVehicleType());
            utilsView.addNodeToPane(view.getWorldMap().getMapElementsLayer(), imageView, point);
        }
    }

    private void showUnitResponders(View view, Unit unit) {
        View.viewRunnable = () -> showUnitResponders(view, unit);

        view.getNavigationPanel().clearDetails();
        showUnitRespondersSidebar(view, unit);

        view.getWorldMap().clear();
        showUnitRespondersMap(view, unit);
    }

    private void showUnitRespondersSidebar(View view, Unit unit) {
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
        responderListView.show(view, unit.getResponders());
    }

    public void showUnitRespondersMap(View view, Unit unit) {
        for (Responder responder : unit.getResponders()) {
            Point2D point = utilsView.locationToPoint(responder.getLocation(), IconType.SMALL);
            ImageView imageView = utilsView.smallIcon(responder.getAppearanceType());
            utilsView.addNodeToPane(view.getWorldMap().getMapElementsLayer(), imageView, point);
        }
    }

}