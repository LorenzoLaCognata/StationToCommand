package stationtocommand.view.organizationStructure;

import javafx.scene.Node;
import stationtocommand.model.locationStructure.Location;
import stationtocommand.model.responderStructure.Responder;
import stationtocommand.model.unitStructure.Unit;
import stationtocommand.model.vehicleStructure.Vehicle;
import stationtocommand.view.View;
import stationtocommand.view.mainStructure.IconColor;
import stationtocommand.view.mainStructure.IconType;
import stationtocommand.view.mainStructure.UtilsView;
import stationtocommand.view.mainStructure.ViewWithNode;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class UnitView extends ViewWithNode {

    private final Unit unit;
    private final UtilsView utilsView;
    private final Map<Vehicle, VehicleView> vehicleViews;
    private final Map<Responder, ResponderView> responderViews;

    // Constructor

    public UnitView(Unit unit, View view, UtilsView utilsView) {
        super(utilsView.createResourceIconWithLocation(IconType.SMALL, IconColor.EMPTY, unit.getUnitType(), unit.getStation().getLocation()));
        this.unit = unit;
        this.utilsView = utilsView;

        this.vehicleViews = new LinkedHashMap<>();
        this.responderViews = new LinkedHashMap<>();

        for (Vehicle vehicle : unit.getVehicles()) {
            addVehicleView(vehicle, view);
        }
        for (Responder responder : unit.getResponders()) {
            addResponderView(responder, view);
        }
    }

    private void addVehicleView(Vehicle vehicle, View view) {
        if (!vehicleViews.containsKey(vehicle)) {
            VehicleView vehicleView = new VehicleView(vehicle, utilsView);
            vehicleViews.put(vehicle, vehicleView);
            view.addToMap(vehicleView.getNode());
        }
    }

    private void addResponderView(Responder responder, View view) {
        if (!responderViews.containsKey(responder)) {
            ResponderView responderView = new ResponderView(responder, utilsView);
            responderViews.put(responder, responderView);
            view.addToMap(responderView.getNode());
        }
    }


    // Getter

    public Unit getUnit() {
        return unit;
    }

    public Map<Vehicle, VehicleView> getVehicleViews() {
        return vehicleViews;
    }

    public Map<Responder, ResponderView> getResponderViews() {
        return responderViews;
    }


    // Methods

    public void addListDetails(View view) {
        utilsView.addIconAndButtonAndIcon(view.getDetailsPane(), unit.getUnitType(), unit.toString(), (_ -> show(view)), unit.getUnitStatus());
    }

    public void show(View view) {
        View.viewRunnable = () -> show(view);
        utilsView.addBreadCrumb(view.getBreadCrumbBar(), unit);
        view.clearNavigationPanel();
        utilsView.addIconAndTitle(view.getTitlePane(), unit.getUnitType(), unit.toString());
        utilsView.addSelectedButtonWithGraphic(view.getButtonsPane(), unit.getStation().getDepartment().defaultVehicleType(), "Vehicles", () -> showVehicles(view));
        utilsView.addButtonWithGraphic(view.getButtonsPane(), unit.getStation().getDepartment().defaultAppearanceType(), "Responders", () -> showResponders(view));
    }


    // Vehicles

    public Map<Location, List<Node>> vehicleNodesByLocation() {
        return utilsView.nodesByLocation(vehicleViews, v -> v.getVehicle().getLocation());
    }

    private void showVehicles(View view) {
        View.viewRunnable = () -> showVehicles(view);
        showNavigationPanelVehicles(view);
        showMapVehicles(view);
    }

    private void showNavigationPanelVehicles(View view) {
        view.clearDetailsPane();
        utilsView.addAvailableResources(view.getDetailsPane(), unit.vehiclesByStatus(), unit.vehiclesByTypeAndStatus());
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


    // Responders

    public Map<Location, List<Node>> responderNodesByLocation() {
        return utilsView.nodesByLocation(responderViews, v -> v.getResponder().getLocation());
    }

    private void showResponders(View view) {
        View.viewRunnable = () -> showResponders(view);
        showNavigationPanelResponders(view);
        showMapResponders(view);
    }

    private void showNavigationPanelResponders(View view) {
        view.clearDetailsPane();
        utilsView.addAvailableResources(view.getDetailsPane(), unit.respondersByStatus(), unit.respondersByRankAndStatus());
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