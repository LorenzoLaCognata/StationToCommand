package stationtocommand.view.organizationStructure;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import stationtocommand.model.locationStructure.Location;
import stationtocommand.model.personStructure.AppearanceType;
import stationtocommand.model.rankTypeStructure.RankType;
import stationtocommand.model.responderStructure.Responder;
import stationtocommand.model.responderStructure.ResponderStatus;
import stationtocommand.model.unitStructure.Unit;
import stationtocommand.model.vehicleStructure.*;
import stationtocommand.view.View;
import stationtocommand.view.mainStructure.IconColor;
import stationtocommand.view.mainStructure.IconType;
import stationtocommand.view.mainStructure.UtilsView;

import java.util.*;
import java.util.stream.Collectors;

public class UnitView {

    private final Unit unit;
    private final Node node;
    private final UtilsView utilsView;
    private final SortedMap<Vehicle, VehicleView> vehicleViews;
    private final SortedMap<Responder, ResponderView> responderViews;

    public UnitView(Unit unit, View view, UtilsView utilsView) {
        this.unit = unit;
        this.node = utilsView.createResourceIconWithLocation(IconType.SMALL, IconColor.EMPTY, unit.getUnitType(), unit.getStation().getLocation());
        this.utilsView = utilsView;

        this.vehicleViews = new TreeMap<>();
        for (Vehicle vehicle : unit.getVehicles()) {
            VehicleView vehicleView = new VehicleView(vehicle, utilsView);
            vehicleViews.put(vehicle, vehicleView);
            view.getWorldMap().addMapElement(vehicleView.getNode());
        }

        this.responderViews = new TreeMap<>();
        for (Responder responder : unit.getResponders()) {
            ResponderView responderView = new ResponderView(responder, utilsView);
            responderViews.put(responder, responderView);
            view.getWorldMap().addMapElement(responderView.getNode());
        }
    }

    public Unit getUnit() {
        return unit;
    }

    public Node getNode() {
        return node;
    }

    public void setNodeVisible() {
        node.setVisible(true);
    }

    public SortedMap<Vehicle, VehicleView> getVehicleViews() {
        return vehicleViews;
    }

    public SortedMap<Responder, ResponderView> getResponderViews() {
        return responderViews;
    }

    public void addDepartmentDetailsUnit(View view) {
        addStationDetailsUnit(view);
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
        VehicleType vehicleType = switch(unit.getStation().getDepartment().getDepartmentType()) {
            case FIRE_DEPARTMENT -> FireVehicleType.values()[0];
            case POLICE_DEPARTMENT -> PoliceVehicleType.values()[0];
            case MEDIC_DEPARTMENT -> MedicVehicleType.values()[0];
        };
        vehiclesButton.setGraphic(utilsView.smallIcon(vehicleType.getResourcePath(), ""));

        EventHandler<ActionEvent> respondersButtonHandler = event -> {
            utilsView.setPaneButtonsSelectionStyle(event, buttonsPane);
            showUnitResponders(view);
        };
        Button respondersButton = utilsView.addButtonToHorizontalPane(buttonsPane, "Responders", respondersButtonHandler);
        Responder chiefResponder = unit.getStation().getDepartment().getStations().stream()
                .flatMap(station -> station.getUnits().stream())
                .flatMap(unit -> unit.getResponders().stream())
                .min(Comparator.naturalOrder())
                .orElse(null);
        respondersButton.setGraphic(utilsView.smallIcon(chiefResponder != null ? chiefResponder.getAppearanceType().getResourcePath() : AppearanceType.MALE_01.getResourcePath(), ""));

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
        showUnitVehiclesDetails(view);
        showUnitVehiclesMap(view);
    }

    private void showUnitVehiclesDetails(View view) {
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

    public void showUnitVehiclesMap(View view) {
        view.getWorldMap().setMapElementsNotVisible();

        Map<Location, List<VehicleView>> vehicleViewsByLocation = vehicleViews.values().stream()
                .collect(Collectors.groupingBy(
                    v -> v.getVehicle().getLocation()
                ));

        for (Map.Entry<Location, List<VehicleView>> locationVehicleViews : vehicleViewsByLocation.entrySet()) {
            Point2D nodesCenter = utilsView.locationToPoint(locationVehicleViews.getKey(), IconType.SMALL);
            List<Node> locationNodes = locationVehicleViews.getValue().stream()
                    .map(VehicleView::getNode)
                    .toList();
            utilsView.distributeResourceIconsByLocation(nodesCenter, locationNodes);
        }

        for (VehicleView vehicleView : vehicleViews.values()) {
            vehicleView.setNodeVisible();
        }
    }

    private void showUnitResponders(View view) {
        View.viewRunnable = () -> showUnitResponders(view);
        showUnitRespondersDetails(view);
        showUnitRespondersMap(view);
    }

    private void showUnitRespondersDetails(View view) {
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

    public void showUnitRespondersMap(View view) {
        view.getWorldMap().setMapElementsNotVisible();

        Map<Location, List<ResponderView>> responderViewsByLocation = responderViews.values().stream()
                .collect(Collectors.groupingBy(
                        v -> v.getResponder().getLocation()
                ));

        for (Map.Entry<Location, List<ResponderView>> locationResponderViews : responderViewsByLocation.entrySet()) {
            Point2D nodesCenter = utilsView.locationToPoint(locationResponderViews.getKey(), IconType.SMALL);
            List<Node> locationNodes = locationResponderViews.getValue().stream()
                    .map(ResponderView::getNode)
                    .toList();
            utilsView.distributeResourceIconsByLocation(nodesCenter, locationNodes);
        }

        for (ResponderView responderView : responderViews.values()) {
            responderView.setNodeVisible();
        }
    }

}