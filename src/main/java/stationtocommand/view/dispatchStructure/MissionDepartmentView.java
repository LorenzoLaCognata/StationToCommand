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
import stationtocommand.model.stationStructure.StationType;
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
import stationtocommand.view.organizationStructure.StationView;
import stationtocommand.view.organizationStructure.UnitView;

import java.util.*;
import java.util.stream.Collectors;


public class MissionDepartmentView {

    private final MissionDepartmentLink missionDepartmentLink;
    private final UtilsView utilsView;
    private final SortedMap<MissionStationLink, MissionStationView> missionStationViews;
    private final SortedMap<MissionUnitLink, MissionUnitView> missionUnitViews;
    // TODO: restore MissionVehicleViews
    //private final SortedMap<MissionVehicleLink, MissionVehicleView> missionVehicleViews;
    // TODO: restore MissionResponderViews
    //private final SortedMap<MissionResponderLink, MissionResponderView> missionResponderViews;
    private final Group missionStationIcons;
    private final Group missionUnitIcons;
    private final Group missionVehicleIcons;
    private final Group missionResponderIcons;
    private final Map<MissionStationLink, Node> missionStationNodes;
    private final Map<MissionUnitLink, Node> missionUnitNodes;
    private final Map<MissionVehicleLink, Node> missionVehicleNodes;
    private final Map<MissionResponderLink, Node> missionResponderNodes;

    public MissionDepartmentView(MissionDepartmentLink missionDepartmentLink, UtilsView utilsView) {
        System.out.println("Created MissionDepartmentView for " + missionDepartmentLink.getMission() + " - " + missionDepartmentLink.getDepartment());
        this.missionDepartmentLink = missionDepartmentLink;
        this.utilsView = utilsView;

        Map<Location, List<Node>> missionStationNodesByLocation = new HashMap<>();
        this.missionStationIcons = new Group();
        this.missionStationNodes = new HashMap<>();
        this.missionStationViews = missionDepartmentLink.getStationLinks().stream()
                .map(missionStationLink -> {
                    MissionStationView missionStationView = new MissionStationView(missionStationLink, utilsView);
                    Location location = missionStationLink.getStation().getLocation();
                    Node resourceIcon = utilsView.createResourceIconWithLocation(IconType.SMALL, IconColor.EMPTY, missionStationLink.getStation().getStationType(), location);
                    missionStationNodes.put(missionStationLink, resourceIcon);
                    missionStationNodesByLocation.computeIfAbsent(location, _ -> new ArrayList<>()).add(resourceIcon);
                    return Map.entry(missionStationLink, missionStationView);
                })
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (_, b) -> b,
                        TreeMap::new
                ));
        for (Map.Entry<Location, List<Node>> missionStationLocationNodes : missionStationNodesByLocation.entrySet()) {
            Point2D nodesCenter = utilsView.locationToPoint(missionStationLocationNodes.getKey(), IconType.SMALL);
            utilsView.distributeResourceIconsByLocation(nodesCenter, this.missionStationIcons, missionStationLocationNodes.getValue());
        }

        Map<Location, List<Node>> missionUnitNodesByLocation = new HashMap<>();
        this.missionUnitIcons = new Group();
        this.missionUnitNodes = new HashMap<>();
        this.missionUnitViews =  this.missionStationViews.values().stream()
                .flatMap(missionStationView -> missionStationView.getMissionUnitViews().entrySet().stream())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (_, b) -> b,
                        TreeMap::new
                ));
        Map<Location, List<MissionUnitView>> missionUnitViewsByLocation = missionUnitViews.values().stream()
                .collect(Collectors.groupingBy(
                        u -> u.getMissionUnitLink().getUnit().getStation().getLocation())
                );
        for (Map.Entry<Location, List<MissionUnitView>> missionUnitViewLocationNodes : missionUnitViewsByLocation.entrySet()) {
            for (MissionUnitView missionUnitView : missionUnitViewLocationNodes.getValue()) {
                Node resourceIcon = utilsView.createResourceIcon(IconType.SMALL, IconColor.EMPTY, missionUnitView.getMissionUnitLink().getUnit().getUnitType());
                missionUnitNodes.put(missionUnitView.getMissionUnitLink(), resourceIcon);
                missionUnitNodesByLocation.computeIfAbsent(missionUnitViewLocationNodes.getKey(), _ -> new ArrayList<>()).add(resourceIcon);
            }
        }
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
    }

    public MissionStationView getMissionStationView(MissionStationLink missionStationLink) {
        return missionStationViews.get(missionStationLink);
    }

    public void addMissionStationView(MissionStationLink missionStationLink) {
        missionStationViews.put(missionStationLink, new MissionStationView(missionStationLink, utilsView));
    }

    public void addMissionDetailsMissionDepartment(View view) {
        Pane horizontalDetailsPane = utilsView.createHBox(view.getNavigationPanel().getDetailsPane());
        addMissionDepartmentIcon(horizontalDetailsPane);
        addMissionDepartmentButton(view, horizontalDetailsPane);
    }

    private void addMissionDepartmentIcon(Pane pane) {
        utilsView.addIconToPane(pane, IconType.SMALL, IconColor.EMPTY, missionDepartmentLink.getDepartment().getDepartmentType());
    }

    private void addMissionDepartmentButton(View view, Pane pane) {
        utilsView.addButtonToPane(pane, missionDepartmentLink.getDepartment().toString(), (_ -> showMissionDepartment(view)));
    }

    public void showMissionDepartment(View view) {
        View.viewRunnable = () -> showMissionDepartment(view);
        utilsView.addBreadCrumb(view.getBreadCrumbBar(), missionDepartmentLink);
        view.getNavigationPanel().clearAll();
        view.getWorldMap().setMapElementsNotVisible();

        showMissionDepartmentDetails(view);

        Pane buttonsPane = view.getNavigationPanel().getButtonsPane();

        EventHandler<ActionEvent> missionStationsButtonHandler = event -> {
            utilsView.setPaneButtonsSelectionStyle(event, buttonsPane);
            showMissionDepartmentStations(view);
        };
        Button missionStationsButton = utilsView.addButtonToHorizontalPane(buttonsPane, "Stations", missionStationsButtonHandler);
        missionStationsButton.setGraphic(utilsView.smallIcon(StationType.FIRE_STATION.getResourcePath(), ""));

        EventHandler<ActionEvent> missionUnitsButtonHandler = event -> {
            utilsView.setPaneButtonsSelectionStyle(event, buttonsPane);
            showMissionDepartmentUnits(view);
        };
        Button missionUnitsButton = utilsView.addButtonToHorizontalPane(buttonsPane, "Units", missionUnitsButtonHandler);
        missionUnitsButton.setGraphic(utilsView.smallIcon(FireUnitType.FIRE_ENGINE.getResourcePath(), ""));

        EventHandler<ActionEvent> missionVehiclesButtonHandler = event -> {
            utilsView.setPaneButtonsSelectionStyle(event, buttonsPane);
            showMissionDepartmentVehicles(view);
        };
        Button missionVehiclesButton = utilsView.addButtonToHorizontalPane(buttonsPane, "Vehicles", missionVehiclesButtonHandler);
        missionVehiclesButton.setGraphic(utilsView.smallIcon(PoliceVehicleType.SUV.getResourcePath(), ""));

        EventHandler<ActionEvent> missionRespondersButtonHandler = event -> {
            utilsView.setPaneButtonsSelectionStyle(event, buttonsPane);
            showMissionDepartmentResponders(view);
        };
        Button missionRespondersButton = utilsView.addButtonToHorizontalPane(buttonsPane, "Responders", missionRespondersButtonHandler);
        missionRespondersButton.setGraphic(utilsView.smallIcon(AppearanceType.MALE_01.getResourcePath(), ""));

        utilsView.setButtonSelectedStyle(missionStationsButton);
        showMissionDepartmentStations(view);

    }

    private void showMissionDepartmentDetails(View view) {
        Pane titleAndSubtitlePane = utilsView.createVBox(view.getNavigationPanel().getTitlePane());
        Pane horizontalTitlePane = utilsView.createHBox(titleAndSubtitlePane);
        utilsView.addIconToPane(horizontalTitlePane, IconType.MEDIUM, IconColor.EMPTY, missionDepartmentLink.getMission().getMissionType());
        utilsView.addMainTitleLabel(horizontalTitlePane, missionDepartmentLink.getMission().toString());
        Pane horizontalSubtitlePane = utilsView.createHBox(titleAndSubtitlePane);
        utilsView.addIconToPane(horizontalSubtitlePane, IconType.SMALL, IconColor.EMPTY, missionDepartmentLink.getDepartment().getDepartmentType());
        utilsView.addMainSubtitleLabel(horizontalSubtitlePane, missionDepartmentLink.getDepartment().toString());
    }

    private void showMissionDepartmentStations(View view) {
        View.viewRunnable = () -> showMissionDepartmentStations(view);
        view.getNavigationPanel().clearDetails();
        view.getWorldMap().setMapElementsNotVisible();
        utilsView.addSeparatorToPane(view.getNavigationPanel().getDetailsPane());
        for (MissionStationView missionStationView : missionStationViews.values()) {
            missionStationView.addMissionDepartmentDetailsMissionStation(view);
        }
    }

    private void showMissionDepartmentUnits(View view) {
        View.viewRunnable = () -> showMissionDepartmentUnits(view);
        view.getNavigationPanel().clearDetails();

        List<MissionStationLink> missionStationLinks = missionDepartmentLink.getStationLinks().stream()
                .toList();
        List<MissionUnitLink> missionUnitLinks = missionStationLinks.stream()
                .flatMap(stationLink -> stationLink.getUnitLinks().stream())
                .toList();

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
            missionUnitView.addMissionDepartmentDetailsMissionUnit(view);
        }

    }

    private void showMissionDepartmentVehicles(View view) {
        View.viewRunnable = () -> showMissionDepartmentVehicles(view);
        view.getNavigationPanel().clearDetails();

        List<MissionStationLink> missionStationLinks = missionDepartmentLink.getStationLinks().stream()
                .toList();
        List<MissionVehicleLink> missionVehicleLinks = missionStationLinks.stream()
                .flatMap(stationLink -> stationLink.getUnitLinks().stream())
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
        // TODO: replace
        //missionStationListView.getMissionStationView().getMissionVehicleListView().show(view, missionVehicleLinks);

    }

    private void showMissionDepartmentResponders(View view) {
        View.viewRunnable = () -> showMissionDepartmentResponders(view);
        view.getNavigationPanel().clearDetails();

        List<MissionStationLink> missionStationLinks = missionDepartmentLink.getStationLinks().stream()
                .toList();
        List<MissionResponderLink> missionResponderLinks = missionStationLinks.stream()
                .flatMap(stationLink -> stationLink.getUnitLinks().stream())
                .flatMap(unitLink -> unitLink.getResponderLinks().stream())
                .toList();

        Map<ResponderStatus, Long> responderStatusCounts = missionResponderLinks.stream()
                .map(ResponderLink::getResponder)
                .collect(Collectors.groupingBy(
                        Responder::getResponderStatus,
                        Collectors.counting())
                );

        Map<RankType, Map<ResponderStatus, Long>> responderTypeStatusCounts = missionResponderLinks.stream()
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
        utilsView.addTotalCount(horizontalDetailsPane, responderStatusCounts, responderTypeStatusCounts);

        utilsView.addSeparatorToPane(view.getNavigationPanel().getDetailsPane());
        // TODO: replace
        //missionStationListView.getMissionStationView().getMissionResponderListView().show(view, missionResponderLinks);

    }

}