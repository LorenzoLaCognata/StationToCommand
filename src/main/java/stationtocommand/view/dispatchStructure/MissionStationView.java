package stationtocommand.view.dispatchStructure;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import stationtocommand.model.departmentStructure.AppearanceType;
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
import stationtocommand.model.unitTypeStructure.UnitType;
import stationtocommand.model.vehicleStructure.*;
import stationtocommand.view.View;
import stationtocommand.view.mainStructure.IconColor;
import stationtocommand.view.mainStructure.IconType;
import stationtocommand.view.mainStructure.UtilsView;
import stationtocommand.view.organizationStructure.UnitView;

import java.util.*;
import java.util.stream.Collectors;

public class MissionStationView {

    private final MissionStationLink missionStationLink;
    private final UtilsView utilsView;
    private final SortedMap<MissionUnitLink, MissionUnitView> missionUnitViews;
    // TODO: restore MissionVehicleViews
    //private final SortedMap<MissionVehicleLink, MissionVehicleView> missionVehicleViews;
    // TODO: restore MissionResponderViews
    //private final SortedMap<MissionResponderLink, MissionResponderView> missionResponderViews;
    private final Group missionUnitIcons;
    private final Group missionVehicleIcons;
    private final Group missionResponderIcons;
    private final Map<MissionUnitLink, Node> missionUnitNodes;
    private final Map<MissionVehicleLink, Node> missionVehicleNodes;
    private final Map<MissionResponderLink, Node> missionResponderNodes;

    // TODO: replace/change below
    private final MissionResponderListView missionResponderListView;
    private final MissionVehicleListView missionVehicleListView;

    public MissionStationView(MissionStationLink missionStationLink, UtilsView utilsView) {
        System.out.println("Created MissionStationView for " + missionStationLink.getMission() + " - " + missionStationLink.getStation());
        this.missionStationLink = missionStationLink;
        this.utilsView = utilsView;
        Map<Location, List<Node>> missionUnitNodesByLocation = new HashMap<>();
        this.missionUnitIcons = new Group();
        this.missionUnitNodes = new HashMap<>();
        this.missionUnitViews =  this.missionStationLink.getUnitLinks().stream()
                .map(missionUnitLink -> {
                    MissionUnitView missionUnitView = new MissionUnitView(missionUnitLink, utilsView);
                    Location location = missionUnitLink.getUnit().getStation().getLocation();
                    Node resourceIcon = utilsView.createResourceIconWithLocation(IconType.SMALL, IconColor.EMPTY, missionUnitLink.getUnit().getUnitType(), location);
                    missionUnitNodes.put(missionUnitLink, resourceIcon);
                    missionUnitNodesByLocation.computeIfAbsent(location, _ -> new ArrayList<>()).add(resourceIcon);
                    return Map.entry(missionUnitLink, missionUnitView);
                })
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (_, b) -> b,
                        TreeMap::new
                ));
        for (Map.Entry<Location, List<Node>> missionUnitLocationNodes : missionUnitNodesByLocation.entrySet()) {
            Point2D nodesCenter = utilsView.locationToPoint(missionUnitLocationNodes.getKey(), IconType.SMALL);
            utilsView.distributeResourceIconsByLocation(nodesCenter, this.missionUnitIcons, missionUnitLocationNodes.getValue());
        }

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

    public SortedMap<MissionUnitLink, MissionUnitView> getMissionUnitViews() {
        return missionUnitViews;
    }

    public MissionUnitView getMissionUnitView(MissionUnitLink missionUnitLink) {
        return missionUnitViews.get(missionUnitLink);
    }

    public void addMissionUnitView(MissionUnitLink missionUnitLink) {
        missionUnitViews.put(missionUnitLink, new MissionUnitView(missionUnitLink, utilsView));
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

    private void addMissionStationIcon(Pane pane) {
        utilsView.addIconToPane(pane, IconType.SMALL, IconColor.EMPTY, missionStationLink.getStation().getStationType());
    }

    private void addMissionStationButton(View view, Pane pane) {
        utilsView.addButtonToPane(pane, missionStationLink.getStation().toString(), (_ -> showMissionStation(view, missionStationLink)));
    }










    public void showMissionStation(View view, MissionStationLink missionStationLink) {
        View.viewRunnable = () -> showMissionStation(view, missionStationLink);
        utilsView.addBreadCrumb(view.getBreadCrumbBar(), missionStationLink);
        view.getNavigationPanel().clearAll();
        view.getWorldMap().setMapElementsNotVisible();
        showSidebar(view, missionStationLink);
    }

    private void showSidebar(View view, MissionStationLink missionStationLink) {
        showMissionStationDetails(view, missionStationLink);

        Pane buttonsPane = view.getNavigationPanel().getButtonsPane();

        List<MissionUnitLink> missionUnitLinks = missionStationLink.getUnitLinks().stream()
                  .toList();

        EventHandler<ActionEvent> missionUnitsButtonHandler = event -> {
            utilsView.setPaneButtonsSelectionStyle(event, buttonsPane);
            showMissionStationUnits(view, missionUnitLinks);
        };
        Button missionUnitsButton = utilsView.addButtonToHorizontalPane(buttonsPane, "Units", missionUnitsButtonHandler);
        missionUnitsButton.setGraphic(utilsView.smallIcon(FireUnitType.FIRE_ENGINE.getResourcePath(), ""));

        EventHandler<ActionEvent> missionVehiclesButtonHandler = event -> {
            utilsView.setPaneButtonsSelectionStyle(event, buttonsPane);
            showMissionStationVehicles(view, missionUnitLinks);
        };
        Button missionVehiclesButton = utilsView.addButtonToHorizontalPane(buttonsPane, "Vehicles", missionVehiclesButtonHandler);
        missionVehiclesButton.setGraphic(utilsView.smallIcon(PoliceVehicleType.PATROL_SEDAN.getResourcePath(), ""));

        EventHandler<ActionEvent> missionRespondersButtonHandler = event -> {
            utilsView.setPaneButtonsSelectionStyle(event, buttonsPane);
            showMissionStationResponders(view, missionUnitLinks);
        };
        Button missionRespondersButton = utilsView.addButtonToHorizontalPane(buttonsPane, "Responders", missionRespondersButtonHandler);
        missionRespondersButton.setGraphic(utilsView.smallIcon(AppearanceType.MALE_01.getResourcePath(), ""));

        utilsView.setButtonSelectedStyle(missionUnitsButton);
        showMissionStationUnits(view, missionUnitLinks);
    }

    private void showMissionStationDetails(View view, MissionStationLink missionStationLink) {
        Pane titleAndSubtitlePane = utilsView.createVBox(view.getNavigationPanel().getTitlePane());
        Pane horizontalTitlePane = utilsView.createHBox(titleAndSubtitlePane);
        utilsView.addIconToPane(horizontalTitlePane, IconType.MEDIUM, IconColor.EMPTY, missionStationLink.getMission().getMissionType());
        utilsView.addMainTitleLabel(horizontalTitlePane, missionStationLink.getMission().toString());
        Pane horizontalSubtitlePane = utilsView.createHBox(titleAndSubtitlePane);
        utilsView.addIconToPane(horizontalSubtitlePane, IconType.SMALL, IconColor.EMPTY, missionStationLink.getStation().getStationType());
        utilsView.addMainSubtitleLabel(horizontalSubtitlePane, missionStationLink.getStation().toString());
    }

    private void showMissionStationUnits(View view, List<MissionUnitLink> missionUnitLinks) {
        View.viewRunnable = () -> showMissionStationUnits(view, missionUnitLinks);
        view.getNavigationPanel().clearDetails();

        Map<UnitStatus, Long> unitStatusCounts = missionUnitLinks.stream()
                .map(UnitLink::getUnit)
                .collect(Collectors.groupingBy(
                        Unit::getUnitStatus,
                        Collectors.counting())
                );

        Map<UnitType, Map<UnitStatus, Long>> unitTypeStatusCounts = missionUnitLinks.stream()
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