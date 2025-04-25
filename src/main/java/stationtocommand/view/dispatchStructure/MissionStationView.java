package stationtocommand.view.dispatchStructure;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import stationtocommand.model.locationStructure.Location;
import stationtocommand.model.missionLinkStructure.MissionResponderLink;
import stationtocommand.model.missionLinkStructure.MissionStationLink;
import stationtocommand.model.missionLinkStructure.MissionUnitLink;
import stationtocommand.model.missionLinkStructure.MissionVehicleLink;
import stationtocommand.model.personStructure.AppearanceType;
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
    private final SortedMap<MissionVehicleLink, MissionVehicleView> missionVehicleViews;
    private final SortedMap<MissionResponderLink, MissionResponderView> missionResponderViews;

    public MissionStationView(MissionStationLink missionStationLink, View view, UtilsView utilsView) {
        System.out.println("Created MissionStationView for " + missionStationLink.getMission() + " - " + missionStationLink.getStation());
        this.missionStationLink = missionStationLink;
        this.node = utilsView.createResourceIconWithLocation(IconType.SMALL, IconColor.EMPTY, missionStationLink.getStation().getStationType(), missionStationLink.getStation().getLocation());
        this.utilsView = utilsView;

        this.missionUnitViews = new TreeMap<>();
        addMissionUnitViews(view, utilsView);

        this.missionVehicleViews = new TreeMap<>();
        addMissionVehicleViews(view, utilsView);

        this.missionResponderViews = new TreeMap<>();
        addMissionResponderViews(view, utilsView);
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
        MissionUnitView missionUnitView = new MissionUnitView(missionUnitLink, view, utilsView);
        missionUnitViews.put(missionUnitLink, missionUnitView);
        // TODO: restore after solving app freeze issue
        view.addToMapDISABLED(missionUnitView.getNode());
    }

    public SortedMap<MissionVehicleLink, MissionVehicleView> getMissionVehicleViews() {
        return missionVehicleViews;
    }

    public MissionVehicleView getMissionVehicleView(MissionVehicleLink missionVehicleLink) {
        return missionVehicleViews.get(missionVehicleLink);
    }

    public void addMissionVehicleViews(View view, UtilsView utilsView) {
        for (MissionUnitLink missionUnitLink : missionStationLink.getUnitLinks()) {
            for (MissionVehicleLink missionVehicleLink : missionUnitLink.getVehicleLinks()) {
                   addMissionVehicleView(view, utilsView, missionVehicleLink);
            }
        }
    }

    private void addMissionVehicleView(View view, UtilsView utilsView, MissionVehicleLink missionVehicleLink) {
        MissionVehicleView missionVehicleView = new MissionVehicleView(missionVehicleLink, view, utilsView);
        missionVehicleViews.put(missionVehicleLink, missionVehicleView);
        // TODO: restore after solving app freeze issue
        view.addToMapDISABLED(missionVehicleView.getNode());
    }

    public SortedMap<MissionResponderLink, MissionResponderView> getMissionResponderViews() {
        return missionResponderViews;
    }

    public MissionResponderView getMissionResponderView(MissionResponderLink missionResponderLink) {
        return missionResponderViews.get(missionResponderLink);
    }

    public void addMissionResponderViews(View view, UtilsView utilsView) {
        for (MissionUnitLink missionUnitLink : missionStationLink.getUnitLinks()) {
            for (MissionResponderLink missionResponderLink : missionUnitLink.getResponderLinks()) {
                addMissionResponderView(view, utilsView, missionResponderLink);
            }
        }
    }

    private void addMissionResponderView(View view, UtilsView utilsView, MissionResponderLink missionResponderLink) {
        MissionResponderView missionResponderView = new MissionResponderView(missionResponderLink, view, utilsView);
        missionResponderViews.put(missionResponderLink, missionResponderView);
        // TODO: restore after solving app freeze issue
        view.addToMapDISABLED(missionResponderView.getNode());
    }
    public void addMissionDepartmentDetailsMissionStation(View view) {
        Pane horizontalDetailsPane = utilsView.createHBox(view.getDetailsPane());
        addMissionStationIcon(horizontalDetailsPane);
        addMissionStationButton(view, horizontalDetailsPane);
    }

    private void addMissionStationTitle(View view) {
        Pane titleAndSubtitlePane = utilsView.createVBox(view.getTitlePane());
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
        view.clearNavigationPanel();
        showMissionStationDetails(view);
    }

    private void showMissionStationDetails(View view) {
        addMissionStationTitle(view);

        Pane buttonsPane = view.getButtonsPane();

        List<MissionUnitLink> missionUnitLinks = missionStationLink.getUnitLinks().stream()
                  .toList();

        EventHandler<ActionEvent> missionUnitsButtonHandler = event -> {
            utilsView.setPaneButtonsSelectionStyle(event, buttonsPane);
            showMissionStationUnits(view);
        };
        Button missionUnitsButton = utilsView.addHorizontalButtonToPane(buttonsPane, "Units", missionUnitsButtonHandler);
        UnitType unitType = switch(missionStationLink.getStation().getDepartment().getDepartmentType()) {
            case FIRE_DEPARTMENT -> FireUnitType.values()[0];
            case POLICE_DEPARTMENT -> PoliceUnitType.values()[0];
            case MEDIC_DEPARTMENT -> MedicUnitType.values()[0];
        };
        missionUnitsButton.setGraphic(utilsView.smallIcon(unitType.getResourcePath(), ""));

        EventHandler<ActionEvent> missionVehiclesButtonHandler = event -> {
            utilsView.setPaneButtonsSelectionStyle(event, buttonsPane);
            showMissionStationVehicles(view);
        };
        Button missionVehiclesButton = utilsView.addHorizontalButtonToPane(buttonsPane, "Vehicles", missionVehiclesButtonHandler);
        VehicleType vehicleType = switch(missionStationLink.getStation().getDepartment().getDepartmentType()) {
            case FIRE_DEPARTMENT -> FireVehicleType.values()[0];
            case POLICE_DEPARTMENT -> PoliceVehicleType.values()[0];
            case MEDIC_DEPARTMENT -> MedicVehicleType.values()[0];
        };
        missionVehiclesButton.setGraphic(utilsView.smallIcon(vehicleType.getResourcePath(), ""));

        EventHandler<ActionEvent> missionRespondersButtonHandler = event -> {
            utilsView.setPaneButtonsSelectionStyle(event, buttonsPane);
            showMissionStationResponders(view);
        };
        Button missionRespondersButton = utilsView.addHorizontalButtonToPane(buttonsPane, "Responders", missionRespondersButtonHandler);
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
        view.clearDetailsPane();

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

        utilsView.addSeparatorToPane(view.getDetailsPane());
        Pane horizontalDetailsPane = utilsView.createHBox(view.getDetailsPane());
        utilsView.addTotalCount(horizontalDetailsPane, unitStatusCounts, unitTypeStatusCounts);

        utilsView.addSeparatorToPane(view.getDetailsPane());
        for (MissionUnitView missionUnitView : missionUnitViews.values()) {
            missionUnitView.addMissionStationDetailsMissionUnit(view);
        }
    }

    private void showMissionStationUnitsMap(View view) {
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

        MissionView missionView = view.getDispatchView().getMissionView(missionStationLink.getMission());
        missionView.setNodeVisible();
        for (MissionUnitView missionUnitView : missionUnitViews.values()) {
            missionUnitView.setNodeVisible();
        }
    }

    private void showMissionStationVehicles(View view) {
        View.viewRunnable = () -> showMissionStationVehicles(view);
        showMissionStationVehiclesDetails(view);
        showMissionStationVehiclesMap(view);
    }

    private void showMissionStationVehiclesDetails(View view) {
        view.clearDetailsPane();

        List<MissionVehicleLink> missionVehicleLinks = missionStationLink.getUnitLinks().stream()
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

        utilsView.addSeparatorToPane(view.getDetailsPane());
        Pane horizontalDetailsPane = utilsView.createHBox(view.getDetailsPane());
        utilsView.addTotalCount(horizontalDetailsPane, vehicleStatusCounts, vehicleTypeStatusCounts);

        utilsView.addSeparatorToPane(view.getDetailsPane());
        for (MissionVehicleView missionVehicleView : missionVehicleViews.values()) {
            missionVehicleView.addMissionStationDetailsVehicle(view);
        }
    }

    private void showMissionStationVehiclesMap(View view) {
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

        MissionView missionView = view.getDispatchView().getMissionView(missionStationLink.getMission());
        missionView.setNodeVisible();
        for (MissionVehicleView missionVehicleView : missionVehicleViews.values()) {
            missionVehicleView.setNodeVisible();
        }
    }

    private void showMissionStationResponders(View view) {
        View.viewRunnable = () -> showMissionStationResponders(view);
        showMissionStationRespondersDetails(view);
        showMissionStationRespondersMap(view);
    }

    private void showMissionStationRespondersDetails(View view) {
        view.clearDetailsPane();

        List<MissionResponderLink> missionResponderLinks = missionStationLink.getUnitLinks().stream()
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

        utilsView.addSeparatorToPane(view.getDetailsPane());
        Pane horizontalDetailsPane = utilsView.createHBox(view.getDetailsPane());
        utilsView.addTotalCount(horizontalDetailsPane, responderStatusCounts, responderRankStatusCounts);

        utilsView.addSeparatorToPane(view.getDetailsPane());
        for (MissionResponderView missionResponderView : missionResponderViews.values()) {
            missionResponderView.addMissionStationDetailsResponder(view);
        }
    }

    private void showMissionStationRespondersMap(View view) {
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

        MissionView missionView = view.getDispatchView().getMissionView(missionStationLink.getMission());
        missionView.setNodeVisible();
        for (MissionResponderView missionResponderView : missionResponderViews.values()) {
            missionResponderView.setNodeVisible();
        }
    }

}