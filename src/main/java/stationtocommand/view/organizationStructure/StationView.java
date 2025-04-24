package stationtocommand.view.organizationStructure;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import stationtocommand.model.locationStructure.Location;
import stationtocommand.model.responderStructure.Responder;
import stationtocommand.model.stationStructure.Station;
import stationtocommand.model.unitStructure.Unit;
import stationtocommand.model.unitTypeStructure.UnitType;
import stationtocommand.model.vehicleStructure.*;
import stationtocommand.view.View;
import stationtocommand.view.mainStructure.IconColor;
import stationtocommand.view.mainStructure.IconType;
import stationtocommand.view.mainStructure.UtilsView;

import java.util.*;
import java.util.stream.Collectors;

public class StationView {

    private final Station station;
    private final Node node;
    private final UtilsView utilsView;
    private final SortedMap<Unit, UnitView> unitViews;
    private final SortedMap<Vehicle, VehicleView> vehicleViews;
    private final SortedMap<Responder, ResponderView> responderViews;

    public StationView(Station station, View view, UtilsView utilsView) {
        this.station = station;
        this.node = utilsView.createResourceIconWithLocation(IconType.SMALL, IconColor.EMPTY, station.getStationType(), station.getLocation());
        this.utilsView = utilsView;

        this.unitViews = new TreeMap<>();
        for (Unit unit : station.getUnits()) {
            UnitView unitView = new UnitView(unit, view, utilsView);
            unitViews.put(unit, unitView);
            view.addToMap(unitView.getNode());
        }

        this.vehicleViews = new TreeMap<>();
        this.responderViews = new TreeMap<>();
        for (UnitView unitView : unitViews.values()) {
            for (VehicleView vehicleView : unitView.getVehicleViews().values()) {
                vehicleViews.put(vehicleView.getVehicle(), vehicleView);
                view.addToMap(vehicleView.getNode());
            }
            for (ResponderView responderView : unitView.getResponderViews().values()) {
                responderViews.put(responderView.getResponder(), responderView);
                view.addToMap(responderView.getNode());
            }
        }
    }

    public Station getStation() {
        return station;
    }

    public Node getNode() {
        return node;
    }

    public void showNode() {
        node.setVisible(true);
    }

    public void showOnlyNode(View view) {
        view.hideMap();
        showNode();
    }

    public SortedMap<Unit, UnitView> getUnitViews() {
        return unitViews;
    }

    public UnitView getUnitView(Unit unit) {
        return unitViews.get(unit);
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

    public SortedMap<Vehicle, VehicleView> getVehicleViews() {
        return vehicleViews;
    }

    public VehicleView getVehicleView(Vehicle vehicle) {
        return vehicleViews.get(vehicle);
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

    public SortedMap<Responder, ResponderView> getResponderViews() {
        return responderViews;
    }

    public ResponderView getResponderView(Responder responder) {
        return responderViews.get(responder);
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

    private void addUnitTypesIcons(Pane pane) {
        for (UnitType unitType : station.getDepartment().unitTypesList()) {
            if (station.getUnitManager().getUnits(unitType).isEmpty()) {
                utilsView.addIconToPane(pane, IconType.SMALL_FADED, IconColor.EMPTY, unitType);
            }
            else {
                utilsView.addIconToPane(pane, IconType.SMALL, IconColor.EMPTY, unitType);
            }
        }
    }

    public void addListDetails(View view) {
        Pane horizontalPane = utilsView.addIconAndButton(view.getDetailsPane(), station.getStationType(), station.toString(), (_ -> show(view)));
        addUnitTypesIcons(horizontalPane);
    }

    public void show(View view) {
        View.viewRunnable = () -> show(view);
        utilsView.addBreadCrumb(view.getBreadCrumbBar(), station);
        view.clearNavigationPanel();
        utilsView.addIconAndTitle(view.getTitlePane(), station.getStationType(), station.toString());
        utilsView.addSelectedButtonWithGraphic(view.getButtonsPane(), station.getDepartment().defaultUnitType(), "Units", () -> showUnits(view));
        utilsView.addButtonWithGraphic(view.getButtonsPane(), station.getDepartment().defaultVehicleType(), "Vehicles", () -> showVehicles(view));
        utilsView.addButtonWithGraphic(view.getButtonsPane(), station.getDepartment().defaultAppearanceType(), "Responders", () -> showResponders(view));
    }

    private void showUnits(View view) {
        View.viewRunnable = () -> showUnits(view);
        showNavigationPanelUnits(view);
        showMapUnits(view);
    }

    private void showNavigationPanelUnits(View view) {
        view.clearDetailsPane();
        utilsView.addAvailableResourcesNEW(view.getDetailsPane(), station.unitsByStatus(), station.unitsByTypeAndStatus());
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
        utilsView.addAvailableResourcesNEW(view.getDetailsPane(), station.vehiclesByStatus(), station.vehiclesByTypeAndStatus());
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
        utilsView.addAvailableResourcesNEW(view.getDetailsPane(), station.respondersByStatus(), station.respondersByRankAndStatus());
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