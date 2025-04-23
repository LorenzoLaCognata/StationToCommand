package stationtocommand.view.dispatchStructure;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import stationtocommand.model.personStructure.AppearanceType;
import stationtocommand.model.locationStructure.Location;
import stationtocommand.model.missionLinkStructure.*;
import stationtocommand.model.rankTypeStructure.RankType;
import stationtocommand.model.responderStructure.Responder;
import stationtocommand.model.responderStructure.ResponderLink;
import stationtocommand.model.responderStructure.ResponderStatus;
import stationtocommand.model.unitStructure.Unit;
import stationtocommand.model.unitStructure.UnitLink;
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

public class MissionStationView {

    private final MissionStationLink missionStationLink;
    private final Node node;
    private final UtilsView utilsView;
    private final SortedMap<MissionUnitLink, MissionUnitView> missionUnitViews;
    // TODO: restore MissionVehicleViews
    //private final SortedMap<MissionVehicleLink, MissionVehicleView> missionVehicleViews;
    // TODO: restore MissionResponderViews
    //private final SortedMap<MissionResponderLink, MissionResponderView> missionResponderViews;
    // TODO: replace/change below
    private final Group missionVehicleIcons;
    private final Group missionResponderIcons;
    private final Map<MissionVehicleLink, Node> missionVehicleNodes;
    private final Map<MissionResponderLink, Node> missionResponderNodes;
    private final MissionResponderListView missionResponderListView;
    private final MissionVehicleListView missionVehicleListView;

    public MissionStationView(MissionStationLink missionStationLink, View view, UtilsView utilsView) {
        System.out.println("Created MissionStationView for " + missionStationLink.getMission() + " - " + missionStationLink.getStation());
        this.missionStationLink = missionStationLink;
        this.node = utilsView.createResourceIconWithLocation(IconType.SMALL, IconColor.EMPTY, missionStationLink.getStation().getStationType(), missionStationLink.getStation().getLocation());
        this.utilsView = utilsView;

        this.missionUnitViews = new TreeMap<>();
        addMissionUnitViews(view, utilsView);

        Map<Location, List<Node>> missionVehicleNodesByLocation = new HashMap<>();
        this.missionVehicleIcons = new Group();
        this.missionVehicleNodes = new HashMap<>();
        /*
        this.vehicleViews =  this.unitViews.values().stream()
                .flatMap(unitView -> unitView.getVehicleViews().entrySet().stream())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (_, b) -> b,
                        TreeMap::new
                ));
        Map<Location, List<VehicleView>> vehicleViewsByLocation = vehicleViews.values().stream()
                .collect(Collectors.groupingBy(
                        v -> v.getVehicle().getLocation())
                );
        for (Map.Entry<Location, List<VehicleView>> vehicleViewLocationNodes : vehicleViewsByLocation.entrySet()) {
            for (VehicleView vehicleView : vehicleViewLocationNodes.getValue()) {
                Node resourceIcon = utilsView.createResourceIcon(IconType.SMALL, IconColor.EMPTY, vehicleView.getVehicle().getVehicleType());
                vehicleNodes.put(vehicleView.getVehicle(), resourceIcon);
                vehicleNodesByLocation.computeIfAbsent(vehicleViewLocationNodes.getKey(), _ -> new ArrayList<>()).add(resourceIcon);
            }
        }
        for (Map.Entry<Location, List<Node>> vehicleLocationNodes : vehicleNodesByLocation.entrySet()) {
            Point2D nodesCenter = utilsView.locationToPoint(vehicleLocationNodes.getKey(), IconType.SMALL);
            utilsView.distributeResourceIconsByLocation(nodesCenter, this.vehicleIcons, vehicleLocationNodes.getValue());
        }
        */
        Map<Location, List<Node>> missionResponderNodesByLocation = new HashMap<>();
        this.missionResponderIcons = new Group();
        this.missionResponderNodes = new HashMap<>();
        /*
        this.responderViews =  this.unitViews.values().stream()
                .flatMap(unitView -> unitView.getResponderViews().entrySet().stream())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (_, b) -> b,
                        TreeMap::new
                ));
        Map<Location, List<ResponderView>> responderViewsByLocation = responderViews.values().stream()
                .collect(Collectors.groupingBy(
                        r -> r.getResponder().getLocation())
                );
        for (Map.Entry<Location, List<ResponderView>> responderViewLocationNodes : responderViewsByLocation.entrySet()) {
            for (ResponderView responderView : responderViewLocationNodes.getValue()) {
                Node resourceIcon = utilsView.createResourceIcon(IconType.SMALL, IconColor.EMPTY, responderView.getResponder().getAppearanceType());
                responderNodes.put(responderView.getResponder(), resourceIcon);
                responderNodesByLocation.computeIfAbsent(responderViewLocationNodes.getKey(), _ -> new ArrayList<>()).add(resourceIcon);
            }
        }
        for (Map.Entry<Location, List<Node>> responderLocationNodes : responderNodesByLocation.entrySet()) {
            Point2D nodesCenter = utilsView.locationToPoint(responderLocationNodes.getKey(), IconType.SMALL);
            utilsView.distributeResourceIconsByLocation(nodesCenter, this.responderIcons, responderLocationNodes.getValue());
        }
        */
        // TODO: replace/change below
        this.missionResponderListView = new MissionResponderListView(utilsView);
        this.missionVehicleListView = new MissionVehicleListView(utilsView);
    }

    public MissionStationLink getMissionStationLink() {
        return missionStationLink;
    }

    public Node getNode() {
        return node;
    }

    public void setNodeVisible() {
        node.setVisible(true);
    }

    public SortedMap<MissionUnitLink, MissionUnitView> getMissionUnitViews() {
        return missionUnitViews;
    }

    public MissionUnitView getMissionUnitView(MissionUnitLink missionUnitLink) {
        return missionUnitViews.get(missionUnitLink);
    }

    public void addMissionUnitViews(View view, UtilsView utilsView) {
        for (MissionUnitLink missionUnitLink : missionStationLink.getUnitLinks()) {
            addMissionUnitView(view, utilsView, missionUnitLink);
        }
    }

    private void addMissionUnitView(View view, UtilsView utilsView, MissionUnitLink missionUnitLink) {
        MissionUnitView missionUnitView = new MissionUnitView(missionUnitLink, utilsView);
        missionUnitViews.put(missionUnitLink, missionUnitView);
        view.getWorldMap().addMapElement(missionUnitView.getNode());
    }

    public MissionResponderListView getMissionResponderListView() {
        return missionResponderListView;
    }

    public MissionVehicleListView getMissionVehicleListView() {
        return missionVehicleListView;
    }

    public void addMissionDepartmentDetailsMissionStation(View view) {
        Pane horizontalDetailsPane = utilsView.createHBox(view.getNavigationPanel().getDetailsPane());
        addMissionStationIcon(horizontalDetailsPane);
        addMissionStationButton(view, horizontalDetailsPane);
    }

    private void addMissionStationTitle(View view, MissionStationLink missionStationLink) {
        Pane titleAndSubtitlePane = utilsView.createVBox(view.getNavigationPanel().getTitlePane());
        Pane horizontalTitlePane = utilsView.createHBox(titleAndSubtitlePane);
        utilsView.addIconToPane(horizontalTitlePane, IconType.MEDIUM, IconColor.EMPTY, missionStationLink.getMission().getMissionType());
        utilsView.addMainTitleLabel(horizontalTitlePane, missionStationLink.getMission().toString());
        Pane horizontalSubtitlePane = utilsView.createHBox(titleAndSubtitlePane);
        utilsView.addIconToPane(horizontalSubtitlePane, IconType.SMALL, IconColor.EMPTY, missionStationLink.getStation().getStationType());
        utilsView.addMainSubtitleLabel(horizontalSubtitlePane, missionStationLink.getStation().toString());
    }

    private void addMissionStationIcon(Pane pane) {
        utilsView.addIconToPane(pane, IconType.SMALL, IconColor.EMPTY, missionStationLink.getStation().getStationType());
    }

    private void addMissionStationButton(View view, Pane pane) {
        utilsView.addButtonToPane(pane, missionStationLink.getStation().toString(), (_ -> showMissionStation(view)));
    }

    public void showMissionStation(View view) {
        View.viewRunnable = () -> showMissionStation(view);
        utilsView.addBreadCrumb(view.getBreadCrumbBar(), missionStationLink);
        view.getNavigationPanel().clearAll();
        showMissionStationDetails(view);
    }

    private void showMissionStationDetails(View view) {
        addMissionStationTitle(view, missionStationLink);

        Pane buttonsPane = view.getNavigationPanel().getButtonsPane();

        List<MissionUnitLink> missionUnitLinks = missionStationLink.getUnitLinks().stream()
                  .toList();

        EventHandler<ActionEvent> missionUnitsButtonHandler = event -> {
            utilsView.setPaneButtonsSelectionStyle(event, buttonsPane);
            showMissionStationUnits(view);
        };
        Button missionUnitsButton = utilsView.addButtonToHorizontalPane(buttonsPane, "Units", missionUnitsButtonHandler);
        UnitType unitType = switch(missionStationLink.getStation().getDepartment().getDepartmentType()) {
            case FIRE_DEPARTMENT -> FireUnitType.values()[0];
            case POLICE_DEPARTMENT -> PoliceUnitType.values()[0];
            case MEDIC_DEPARTMENT -> MedicUnitType.values()[0];
        };
        missionUnitsButton.setGraphic(utilsView.smallIcon(unitType.getResourcePath(), ""));

        EventHandler<ActionEvent> missionVehiclesButtonHandler = event -> {
            utilsView.setPaneButtonsSelectionStyle(event, buttonsPane);
            showMissionStationVehicles(view, missionUnitLinks);
        };
        Button missionVehiclesButton = utilsView.addButtonToHorizontalPane(buttonsPane, "Vehicles", missionVehiclesButtonHandler);
        VehicleType vehicleType = switch(missionStationLink.getStation().getDepartment().getDepartmentType()) {
            case FIRE_DEPARTMENT -> FireVehicleType.values()[0];
            case POLICE_DEPARTMENT -> PoliceVehicleType.values()[0];
            case MEDIC_DEPARTMENT -> MedicVehicleType.values()[0];
        };
        missionVehiclesButton.setGraphic(utilsView.smallIcon(vehicleType.getResourcePath(), ""));

        EventHandler<ActionEvent> missionRespondersButtonHandler = event -> {
            utilsView.setPaneButtonsSelectionStyle(event, buttonsPane);
            showMissionStationResponders(view, missionUnitLinks);
        };
        Button missionRespondersButton = utilsView.addButtonToHorizontalPane(buttonsPane, "Responders", missionRespondersButtonHandler);
        Responder chiefResponder = missionStationLink.getStation().getDepartment().getStations().stream()
                .flatMap(station -> station.getUnits().stream())
                .flatMap(unit -> unit.getResponders().stream())
                .min(Comparator.naturalOrder())
                .orElse(null);
        missionRespondersButton.setGraphic(utilsView.smallIcon(chiefResponder != null ? chiefResponder.getAppearanceType().getResourcePath() : AppearanceType.MALE_01.getResourcePath(), ""));

        utilsView.setButtonSelectedStyle(missionUnitsButton);
        showMissionStationUnits(view);
    }

    private void showMissionStationUnits(View view) {
        View.viewRunnable = () -> showMissionStationUnits(view);
        showMissionStationUnitsDetails(view);
        showMissionStationUnitsMap(view);
    }

    private void showMissionStationUnitsDetails(View view) {
        view.getNavigationPanel().clearDetails();

        Map<UnitStatus, Long> unitStatusCounts = missionStationLink.getUnitLinks().stream()
                .map(UnitLink::getUnit)
                .collect(Collectors.groupingBy(
                        Unit::getUnitStatus,
                        Collectors.counting())
                );

        Map<UnitType, Map<UnitStatus, Long>> unitTypeStatusCounts = missionStationLink.getUnitLinks().stream()
                .map(UnitLink::getUnit)
                .collect(Collectors.groupingBy(
                                Unit::getUnitType,
                                Collectors.groupingBy(
                                        Unit::getUnitStatus,
                                        Collectors.counting())
                        )
                );

        utilsView.addSeparatorToPane(view.getNavigationPanel().getDetailsPane());
        Pane horizontalDetailsPane = utilsView.createHBox(view.getNavigationPanel().getDetailsPane());
        utilsView.addTotalCount(horizontalDetailsPane, unitStatusCounts, unitTypeStatusCounts);

        utilsView.addSeparatorToPane(view.getNavigationPanel().getDetailsPane());
        for (MissionUnitView missionUnitView : missionUnitViews.values()) {
            missionUnitView.addMissionStationDetailsMissionUnit(view);
        }
    }

    private void showMissionStationUnitsMap(View view) {
        view.getWorldMap().setMapElementsNotVisible();

        Map<Location, List<MissionUnitView>> missionUnitViewsByLocation = missionUnitViews.values().stream()
                .collect(Collectors.groupingBy(
                        v -> v.getMissionUnitLink().getUnit().getStation().getLocation()
                ));

        for (Map.Entry<Location, List<MissionUnitView>> locationMissionUnitViews : missionUnitViewsByLocation.entrySet()) {
            Point2D nodesCenter = utilsView.locationToPoint(locationMissionUnitViews.getKey(), IconType.SMALL);
            List<Node> locationNodes = locationMissionUnitViews.getValue().stream()
                    .map(MissionUnitView::getNode)
                    .toList();
            utilsView.distributeResourceIconsByLocation(nodesCenter, locationNodes);
        }

        MissionView missionView = view.getDispatchView().getMissionView(missionStationLink.getMission());
        missionView.setNodeVisible();
        for (MissionUnitView missionUnitView : missionUnitViews.values()) {
            missionUnitView.setNodeVisible();
        }
    }

    private void showMissionStationVehicles(View view, List<MissionUnitLink> missionUnitLinks) {
        View.viewRunnable = () -> showMissionStationVehicles(view, missionUnitLinks);
        view.getNavigationPanel().clearDetails();

        List<MissionVehicleLink> missionVehicleLinks = missionUnitLinks.stream()
                .flatMap(unitLink -> unitLink.getVehicleLinks().stream())
                .toList();

        Map<VehicleStatus, Long> vehicleStatusCounts = missionVehicleLinks.stream()
                .map(VehicleLink::getVehicle)
                .collect(Collectors.groupingBy(
                        Vehicle::getVehicleStatus,
                        Collectors.counting())
                );

        Map<VehicleType, Map<VehicleStatus, Long>> vehicleTypeStatusCounts = missionVehicleLinks.stream()
                .map(VehicleLink::getVehicle)
                .collect(Collectors.groupingBy(
                                Vehicle::getVehicleType,
                                Collectors.groupingBy(
                                        Vehicle::getVehicleStatus,
                                        Collectors.counting())
                        )
                );

        utilsView.addSeparatorToPane(view.getNavigationPanel().getDetailsPane());
        Pane horizontalDetailsPane = utilsView.createHBox(view.getNavigationPanel().getDetailsPane());
        utilsView.addTotalCount(horizontalDetailsPane, vehicleStatusCounts, vehicleTypeStatusCounts);

        utilsView.addSeparatorToPane(view.getNavigationPanel().getDetailsPane());
        missionVehicleListView.show(view, missionVehicleLinks);

    }

    private void showMissionStationResponders(View view, List<MissionUnitLink> missionUnitLinks) {
        View.viewRunnable = () -> showMissionStationResponders(view, missionUnitLinks);
        view.getNavigationPanel().clearDetails();

        List<MissionResponderLink> missionResponderLinks = missionUnitLinks.stream()
                .flatMap(unitLink -> unitLink.getResponderLinks().stream())
                .toList();

        Map<ResponderStatus, Long> responderStatusCounts = missionResponderLinks.stream()
                .map(ResponderLink::getResponder)
                .collect(Collectors.groupingBy(
                        Responder::getResponderStatus,
                        Collectors.counting())
                );

        Map<RankType, Map<ResponderStatus, Long>> responderRankStatusCounts = missionResponderLinks.stream()
                .map(ResponderLink::getResponder)
                .collect(Collectors.groupingBy(
                                responder -> responder.getRank().getRankType(),
                                Collectors.groupingBy(
                                        Responder::getResponderStatus,
                                        Collectors.counting())
                        )
                );

        utilsView.addSeparatorToPane(view.getNavigationPanel().getDetailsPane());
        Pane horizontalDetailsPane = utilsView.createHBox(view.getNavigationPanel().getDetailsPane());
        utilsView.addTotalCount(horizontalDetailsPane, responderStatusCounts, responderRankStatusCounts);

        utilsView.addSeparatorToPane(view.getNavigationPanel().getDetailsPane());
        missionResponderListView.show(view, missionResponderLinks);
    }

}