package stationtocommand.view.dispatchStructure;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import stationtocommand.model.locationStructure.Location;
import stationtocommand.model.missionLinkStructure.*;
import stationtocommand.model.personStructure.AppearanceType;
import stationtocommand.model.rankTypeStructure.RankType;
import stationtocommand.model.responderStructure.Responder;
import stationtocommand.model.responderStructure.ResponderLink;
import stationtocommand.model.responderStructure.ResponderStatus;
import stationtocommand.model.stationStructure.StationType;
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


public class MissionDepartmentView {

    private final MissionDepartmentLink missionDepartmentLink;
    private final UtilsView utilsView;
    private final SortedMap<MissionStationLink, MissionStationView> missionStationViews;
    private final SortedMap<MissionUnitLink, MissionUnitView> missionUnitViews;
    private final SortedMap<MissionVehicleLink, MissionVehicleView> missionVehicleViews;
    private final SortedMap<MissionResponderLink, MissionResponderView> missionResponderViews;

    public MissionDepartmentView(MissionDepartmentLink missionDepartmentLink, View view, UtilsView utilsView) {
        System.out.println("Created MissionDepartmentView for " + missionDepartmentLink.getMission() + " - " + missionDepartmentLink.getDepartment());
        this.missionDepartmentLink = missionDepartmentLink;
        this.utilsView = utilsView;

        this.missionStationViews = new TreeMap<>();
        addMissionStationViews(view, utilsView);

        this.missionUnitViews = new TreeMap<>();
        this.missionVehicleViews = new TreeMap<>();
        this.missionResponderViews = new TreeMap<>();
        for (MissionStationView missionStationView : missionStationViews.values()) {
            for (MissionUnitView missionUnitView : missionStationView.getMissionUnitViews().values()) {
                missionUnitViews.put(missionUnitView.getMissionUnitLink(), missionUnitView);
                // TODO: restore after solving app freeze issue
                view.addToMapDISABLED(missionUnitView.getNode());
            }
            for (MissionVehicleView missionVehicleView : missionStationView.getMissionVehicleViews().values()) {
                missionVehicleViews.put(missionVehicleView.getMissionVehicleLink(), missionVehicleView);
                // TODO: restore after solving app freeze issue
                view.addToMapDISABLED(missionVehicleView.getNode());
            }
            for (MissionResponderView missionResponderView : missionStationView.getMissionResponderViews().values()) {
                missionResponderViews.put(missionResponderView.getMissionResponderLink(), missionResponderView);
                // TODO: restore after solving app freeze issue
                view.addToMapDISABLED(missionResponderView.getNode());
            }
        }
    }

    public SortedMap<MissionStationLink, MissionStationView> getMissionStationViews() {
        return missionStationViews;
    }

    public MissionStationView getMissionStationView(MissionStationLink missionStationLink) {
        return missionStationViews.get(missionStationLink);
    }

    public void addMissionStationViews(View view, UtilsView utilsView) {
        for (MissionStationLink missionStationLink : missionDepartmentLink.getStationLinks()) {
            addMissionStationView(view, utilsView, missionStationLink);
        }
    }

    private void addMissionStationView(View view, UtilsView utilsView, MissionStationLink missionStationLink) {
        if (!missionStationViews.containsKey(missionStationLink)) {
            MissionStationView missionStationView = new MissionStationView(missionStationLink, view, utilsView);
            missionStationViews.put(missionStationLink, missionStationView);
            // TODO: restore after solving app freeze issue
            view.addToMapDISABLED(missionStationView.getNode());
        }
    }

    public void addMissionDetailsMissionDepartment(View view) {
        Pane horizontalDetailsPane = utilsView.createHBox(view.getDetailsPane());
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
        view.clearNavigationPanel();
        view.hideMap();

        addMissionDepartmentTitle(view);

        Pane buttonsPane = view.getButtonsPane();

        EventHandler<ActionEvent> missionStationsButtonHandler = event -> {
            utilsView.setPaneButtonsSelectionStyle(event, buttonsPane);
            showMissionDepartmentStations(view);
        };
        Button missionStationsButton = utilsView.addHorizontalButtonToPane(buttonsPane, "Stations", missionStationsButtonHandler);
        StationType stationType = switch(missionDepartmentLink.getDepartment().getDepartmentType()) {
            case FIRE_DEPARTMENT -> StationType.FIRE_STATION;
            case POLICE_DEPARTMENT -> StationType.POLICE_STATION;
            case MEDIC_DEPARTMENT -> StationType.MEDIC_STATION;
        };
        missionStationsButton.setGraphic(utilsView.smallIcon(stationType.getResourcePath(), ""));

        EventHandler<ActionEvent> missionUnitsButtonHandler = event -> {
            utilsView.setPaneButtonsSelectionStyle(event, buttonsPane);
            showMissionDepartmentUnits(view);
        };
        Button missionUnitsButton = utilsView.addHorizontalButtonToPane(buttonsPane, "Units", missionUnitsButtonHandler);
        UnitType unitType = switch(missionDepartmentLink.getDepartment().getDepartmentType()) {
            case FIRE_DEPARTMENT -> FireUnitType.values()[0];
            case POLICE_DEPARTMENT -> PoliceUnitType.values()[0];
            case MEDIC_DEPARTMENT -> MedicUnitType.values()[0];
        };
        missionUnitsButton.setGraphic(utilsView.smallIcon(unitType.getResourcePath(), ""));

        EventHandler<ActionEvent> missionVehiclesButtonHandler = event -> {
            utilsView.setPaneButtonsSelectionStyle(event, buttonsPane);
            showMissionDepartmentVehicles(view);
        };
        Button missionVehiclesButton = utilsView.addHorizontalButtonToPane(buttonsPane, "Vehicles", missionVehiclesButtonHandler);
        VehicleType vehicleType = switch(missionDepartmentLink.getDepartment().getDepartmentType()) {
            case FIRE_DEPARTMENT -> FireVehicleType.values()[0];
            case POLICE_DEPARTMENT -> PoliceVehicleType.values()[0];
            case MEDIC_DEPARTMENT -> MedicVehicleType.values()[0];
        };
        missionVehiclesButton.setGraphic(utilsView.smallIcon(vehicleType.getResourcePath(), ""));

        EventHandler<ActionEvent> missionRespondersButtonHandler = event -> {
            utilsView.setPaneButtonsSelectionStyle(event, buttonsPane);
            showMissionDepartmentResponders(view);
        };
        Button missionRespondersButton = utilsView.addHorizontalButtonToPane(buttonsPane, "Responders", missionRespondersButtonHandler);
        Responder chiefResponder = missionDepartmentLink.getDepartment().getStations().stream()
                .flatMap(station -> station.getUnits().stream())
                .flatMap(unit -> unit.getResponders().stream())
                .min(Comparator.naturalOrder())
                .orElse(null);
        missionRespondersButton.setGraphic(utilsView.smallIcon(chiefResponder != null ? chiefResponder.getAppearanceType().getResourcePath() : AppearanceType.MALE_01.getResourcePath(), ""));

        utilsView.setButtonSelectedStyle(missionStationsButton);
        showMissionDepartmentStations(view);

    }

    private void addMissionDepartmentTitle(View view) {
        Pane titleAndSubtitlePane = utilsView.createVBox(view.getTitlePane());
        Pane horizontalTitlePane = utilsView.createHBox(titleAndSubtitlePane);
        utilsView.addIconToPane(horizontalTitlePane, IconType.MEDIUM, IconColor.EMPTY, missionDepartmentLink.getMission().getMissionType());
        utilsView.addMainTitleLabel(horizontalTitlePane, missionDepartmentLink.getMission().toString());
        Pane horizontalSubtitlePane = utilsView.createHBox(titleAndSubtitlePane);
        utilsView.addIconToPane(horizontalSubtitlePane, IconType.SMALL, IconColor.EMPTY, missionDepartmentLink.getDepartment().getDepartmentType());
        utilsView.addMainSubtitleLabel(horizontalSubtitlePane, missionDepartmentLink.getDepartment().toString());
    }

    private void showMissionDepartmentStations(View view) {
        View.viewRunnable = () -> showMissionDepartmentStations(view);
        showMissionDepartmentStationDetails(view);
        showMissionDepartmentStationsMap(view);
    }

    private void showMissionDepartmentStationDetails(View view) {
        view.clearDetailsPane();
        utilsView.addSeparatorToPane(view.getDetailsPane());
        for (MissionStationView missionStationView : missionStationViews.values()) {
            missionStationView.addMissionDepartmentDetailsMissionStation(view);
        }
    }

    private void showMissionDepartmentStationsMap(View view) {
        view.hideMap();

        Map<Location, List<MissionStationView>> missionStationViewsByLocation = missionStationViews.values().stream()
                .collect(Collectors.groupingBy(
                        v -> v.getMissionStationLink().getStation().getLocation()
                ));

        for (Map.Entry<Location, List<MissionStationView>> locationMissionStationViews : missionStationViewsByLocation.entrySet()) {
            List<Node> locationNodes = locationMissionStationViews.getValue().stream()
                    .map(MissionStationView::getNode)
                    .toList();
            utilsView.distributeResourceIconsByLocation(locationMissionStationViews.getKey(), locationNodes);
        }

        MissionView missionView = view.getDispatchView().getMissionView(missionDepartmentLink.getMission());
        missionView.setNodeVisible();
        for (MissionStationView missionStationView : missionStationViews.values()) {
            missionStationView.setNodeVisible();
        }
    }

    private void showMissionDepartmentUnits(View view) {
        View.viewRunnable = () -> showMissionDepartmentUnits(view);
        showMissionDepartmentUnitsDetails(view);
        showMissionDepartmentUnitsMap(view);
    }

    private void showMissionDepartmentUnitsDetails(View view) {
        view.clearDetailsPane();

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

        utilsView.addSeparatorToPane(view.getDetailsPane());
        Pane horizontalDetailsPane = utilsView.createHBox(view.getDetailsPane());
        utilsView.addTotalCount(horizontalDetailsPane, unitStatusCounts, unitTypeStatusCounts);

        utilsView.addSeparatorToPane(view.getDetailsPane());
        for (MissionUnitView missionUnitView : missionUnitViews.values()) {
            missionUnitView.addMissionDepartmentDetailsMissionUnit(view);
        }
    }

    public void showMissionDepartmentUnitsMap(View view) {
        view.hideMap();

        Map<Location, List<MissionUnitView>> missionUnitViewsByLocation = missionUnitViews.values().stream()
                .collect(Collectors.groupingBy(
                        v -> v.getMissionUnitLink().getUnit().getStation().getLocation()
                ));

        for (Map.Entry<Location, List<MissionUnitView>> locationMissionUnitViews : missionUnitViewsByLocation.entrySet()) {
            List<Node> locationNodes = locationMissionUnitViews.getValue().stream()
                    .map(MissionUnitView::getNode)
                    .toList();
            utilsView.distributeResourceIconsByLocation(locationMissionUnitViews.getKey(), locationNodes);
        }

        MissionView missionView = view.getDispatchView().getMissionView(missionDepartmentLink.getMission());
        missionView.setNodeVisible();
        for (MissionUnitView missionUnitView : missionUnitViews.values()) {
            missionUnitView.setNodeVisible();
        }
    }

    private void showMissionDepartmentVehicles(View view) {
        View.viewRunnable = () -> showMissionDepartmentVehicles(view);
        showMissionDepartmentVehiclesDetails(view);
        showMissionDepartmentVehiclesMap(view);
    }

    private void showMissionDepartmentVehiclesDetails(View view) {
        view.clearDetailsPane();

        List<MissionStationLink> missionStationLinks = missionDepartmentLink.getStationLinks().stream()
                .toList();
        List<MissionVehicleLink> missionVehicleLinks = missionStationLinks.stream()
                .flatMap(missionStationLink -> missionStationLink.getUnitLinks().stream())
                .flatMap(missionUnitLink -> missionUnitLink.getVehicleLinks().stream())
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

        utilsView.addSeparatorToPane(view.getDetailsPane());
        Pane horizontalDetailsPane = utilsView.createHBox(view.getDetailsPane());
        utilsView.addTotalCount(horizontalDetailsPane, vehicleStatusCounts, vehicleTypeStatusCounts);

        utilsView.addSeparatorToPane(view.getDetailsPane());
        for (MissionVehicleView missionVehicleView : missionVehicleViews.values()) {
            missionVehicleView.addMissionDepartmentDetailsVehicle(view);
        }
    }

    private void showMissionDepartmentVehiclesMap(View view) {
        view.hideMap();

        Map<Location, List<MissionVehicleView>> missionVehicleViewsByLocation = missionVehicleViews.values().stream()
                .collect(Collectors.groupingBy(
                        v -> v.getMissionVehicleLink().getVehicle().getLocation()
                ));

        for (Map.Entry<Location, List<MissionVehicleView>> locationMissionVehicleViews : missionVehicleViewsByLocation.entrySet()) {
            List<Node> locationNodes = locationMissionVehicleViews.getValue().stream()
                    .map(MissionVehicleView::getNode)
                    .toList();
            utilsView.distributeResourceIconsByLocation(locationMissionVehicleViews.getKey(), locationNodes);
        }

        MissionView missionView = view.getDispatchView().getMissionView(missionDepartmentLink.getMission());
        missionView.setNodeVisible();
        for (MissionVehicleView missionVehicleView : missionVehicleViews.values()) {
            missionVehicleView.setNodeVisible();
        }
    }

    private void showMissionDepartmentResponders(View view) {
        View.viewRunnable = () -> showMissionDepartmentResponders(view);
        showMissionDepartmentRespondersDetails(view);
        showMissionDepartmentRespondersMap(view);
    }

    private void showMissionDepartmentRespondersDetails(View view) {
        System.out.println("YYY");
        view.clearDetailsPane();

        List<MissionStationLink> missionStationLinks = missionDepartmentLink.getStationLinks().stream()
                .toList();
        System.out.println(missionStationLinks.size());
        List<MissionResponderLink> missionResponderLinks = missionStationLinks.stream()
                .flatMap(stationLink -> stationLink.getUnitLinks().stream())
                .flatMap(unitLink -> unitLink.getResponderLinks().stream())
                .toList();
        System.out.println(missionResponderLinks.size());

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

        utilsView.addSeparatorToPane(view.getDetailsPane());
        Pane horizontalDetailsPane = utilsView.createHBox(view.getDetailsPane());
        utilsView.addTotalCount(horizontalDetailsPane, responderStatusCounts, responderTypeStatusCounts);

        utilsView.addSeparatorToPane(view.getDetailsPane());
        System.out.println(missionResponderViews.size());
        for (MissionResponderView missionResponderView : missionResponderViews.values()) {
            System.out.println(missionResponderView.getMissionResponderLink().getResponder());
            missionResponderView.addMissionDepartmentDetailsResponder(view);
        }
    }

    private void showMissionDepartmentRespondersMap(View view) {
        view.hideMap();

        Map<Location, List<MissionResponderView>> missionResponderViewsByLocation = missionResponderViews.values().stream()
                .collect(Collectors.groupingBy(
                        v -> v.getMissionResponderLink().getResponder().getLocation()
                ));

        for (Map.Entry<Location, List<MissionResponderView>> locationMissionResponderViews : missionResponderViewsByLocation.entrySet()) {
            List<Node> locationNodes = locationMissionResponderViews.getValue().stream()
                    .map(MissionResponderView::getNode)
                    .toList();
            utilsView.distributeResourceIconsByLocation(locationMissionResponderViews.getKey(), locationNodes);
        }

        MissionView missionView = view.getDispatchView().getMissionView(missionDepartmentLink.getMission());
        missionView.setNodeVisible();
        for (MissionResponderView missionResponderView : missionResponderViews.values()) {
            missionResponderView.setNodeVisible();
        }
    }

}