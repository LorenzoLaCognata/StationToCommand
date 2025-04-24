package stationtocommand.view.dispatchStructure;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import stationtocommand.model.missionLinkStructure.MissionResponderLink;
import stationtocommand.model.missionLinkStructure.MissionUnitLink;
import stationtocommand.model.missionLinkStructure.MissionVehicleLink;
import stationtocommand.model.personStructure.AppearanceType;
import stationtocommand.model.rankTypeStructure.RankType;
import stationtocommand.model.responderStructure.Responder;
import stationtocommand.model.responderStructure.ResponderLink;
import stationtocommand.model.responderStructure.ResponderStatus;
import stationtocommand.model.vehicleStructure.*;
import stationtocommand.view.View;
import stationtocommand.view.mainStructure.IconColor;
import stationtocommand.view.mainStructure.IconType;
import stationtocommand.view.mainStructure.UtilsView;

import java.util.*;
import java.util.stream.Collectors;

public class MissionUnitView {

    private final MissionUnitLink missionUnitLink;
    private final Node node;
    private final UtilsView utilsView;
    private final SortedMap<MissionVehicleLink, MissionVehicleView> missionVehicleViews;
    private final SortedMap<MissionResponderLink, MissionResponderView> missionResponderViews;

    public MissionUnitView(MissionUnitLink missionUnitLink, View view, UtilsView utilsView) {
        System.out.println("Created MissionUnitView for " + missionUnitLink.getMission() + " - " + missionUnitLink.getUnit());
        this.missionUnitLink = missionUnitLink;
        this.node = utilsView.createResourceIconWithLocation(IconType.SMALL, IconColor.EMPTY, missionUnitLink.getUnit().getUnitType(), missionUnitLink.getUnit().getStation().getLocation());
        this.utilsView = utilsView;

        this.missionVehicleViews = new TreeMap<>();
        addMissionVehicleViews(view, utilsView);

        this.missionResponderViews = new TreeMap<>();
        addMissionResponderViews(view, utilsView);
    }

    public MissionUnitLink getMissionUnitLink() {
        return missionUnitLink;
    }

    public Node getNode() {
        return node;
    }

    public void setNodeVisible() {
        node.setVisible(true);
    }

    public SortedMap<MissionVehicleLink, MissionVehicleView> getMissionVehicleViews() {
        return missionVehicleViews;
    }

    public MissionVehicleView getMissionVehicleView(MissionVehicleLink missionVehicleLink) {
        return missionVehicleViews.get(missionVehicleLink);
    }

    public void addMissionVehicleViews(View view, UtilsView utilsView) {
        for (MissionVehicleLink missionVehicleLink : missionUnitLink.getVehicleLinks()) {
            addMissionVehicleView(view, utilsView, missionVehicleLink);
        }
    }
    private void addMissionVehicleView(View view, UtilsView utilsView, MissionVehicleLink missionVehicleLink) {
        System.out.println("XXX" + missionVehicleLink.getVehicle());
        MissionVehicleView missionVehicleView = new MissionVehicleView(missionVehicleLink, view, utilsView);
        missionVehicleViews.put(missionVehicleLink, missionVehicleView);
        view.getWorldMap().addMapElement(missionVehicleView.getNode());
    }

    public SortedMap<MissionResponderLink, MissionResponderView> getMissionResponderViews() {
        return missionResponderViews;
    }

    public MissionResponderView getMissionResponderView(MissionResponderLink missionResponderLink) {
        return missionResponderViews.get(missionResponderLink);
    }

    public void addMissionResponderViews(View view, UtilsView utilsView) {
        for (MissionResponderLink missionResponderLink : missionUnitLink.getResponderLinks()) {
            addMissionResponderView(view, utilsView, missionResponderLink);
        }
    }

    private void addMissionResponderView(View view, UtilsView utilsView, MissionResponderLink missionResponderLink) {
        MissionResponderView missionResponderView = new MissionResponderView(missionResponderLink, view, utilsView);
        missionResponderViews.put(missionResponderLink, missionResponderView);
        view.getWorldMap().addMapElement(missionResponderView.getNode());
    }

    public void addMissionDepartmentDetailsMissionUnit(View view) {
        addMissionStationDetailsMissionUnit(view);
    }

    public void addMissionStationDetailsMissionUnit(View view) {
        Pane horizontalDetailsPane = utilsView.createHBox(view.getNavigationPanel().getDetailsPane());
        addMissionUnitIcon(horizontalDetailsPane);
        addMissionUnitButton(view, horizontalDetailsPane);
    }

    private void addMissionUnitIcon(Pane pane) {
        utilsView.addIconToPane(pane, IconType.SMALL, IconColor.EMPTY, missionUnitLink.getUnit().getUnitType());
    }

    private void addMissionUnitButton(View view, Pane pane) {
        utilsView.addButtonToPane(pane, missionUnitLink.getUnit().toString(), (_ -> showMissionUnit(view)));
    }

    public void showMissionUnit(View view) {
        View.viewRunnable = () -> showMissionUnit(view);
        utilsView.addBreadCrumb(view.getBreadCrumbBar(), missionUnitLink);
        view.getNavigationPanel().clearAll();
        showMissionUnitDetails(view);
    }

    private void showMissionUnitDetails(View view) {
        addMissionUnitTitle(view);

        Pane buttonsPane = view.getNavigationPanel().getButtonsPane();

        EventHandler<ActionEvent> missionVehiclesButtonHandler = event -> {
            utilsView.setPaneButtonsSelectionStyle(event, buttonsPane);
            showMissionUnitVehicles(view);
        };
        Button missionVehiclesButton = utilsView.addButtonToHorizontalPane(buttonsPane, "Vehicles", missionVehiclesButtonHandler);
        VehicleType vehicleType = switch(missionUnitLink.getUnit().getStation().getDepartment().getDepartmentType()) {
            case FIRE_DEPARTMENT -> FireVehicleType.values()[0];
            case POLICE_DEPARTMENT -> PoliceVehicleType.values()[0];
            case MEDIC_DEPARTMENT -> MedicVehicleType.values()[0];
        };
        missionVehiclesButton.setGraphic(utilsView.smallIcon(vehicleType.getResourcePath(), ""));

        EventHandler<ActionEvent> missionRespondersButtonHandler = event -> {
            utilsView.setPaneButtonsSelectionStyle(event, buttonsPane);
            showMissionUnitResponders(view);
        };
        Button missionRespondersButton = utilsView.addButtonToHorizontalPane(buttonsPane, "Responders", missionRespondersButtonHandler);
        Responder chiefResponder = missionUnitLink.getUnit().getStation().getDepartment().getStations().stream()
                .flatMap(station -> station.getUnits().stream())
                .flatMap(unit -> unit.getResponders().stream())
                .min(Comparator.naturalOrder())
                .orElse(null);
        missionRespondersButton.setGraphic(utilsView.smallIcon(chiefResponder != null ? chiefResponder.getAppearanceType().getResourcePath() : AppearanceType.MALE_01.getResourcePath(), ""));

        utilsView.setButtonSelectedStyle(missionVehiclesButton);
        showMissionUnitVehicles(view);
    }

    private void addMissionUnitTitle(View view) {
        Pane titleAndSubtitlePane = utilsView.createVBox(view.getNavigationPanel().getTitlePane());
        Pane horizontalTitlePane = utilsView.createHBox(titleAndSubtitlePane);
        utilsView.addIconToPane(horizontalTitlePane, IconType.MEDIUM, IconColor.EMPTY, missionUnitLink.getMission().getMissionType());
        utilsView.addMainTitleLabel(horizontalTitlePane, missionUnitLink.getMission().toString());
        Pane horizontalSubtitlePane = utilsView.createHBox(titleAndSubtitlePane);
        utilsView.addIconToPane(horizontalSubtitlePane, IconType.SMALL, IconColor.EMPTY, missionUnitLink.getUnit().getUnitType());
        utilsView.addMainSubtitleLabel(horizontalSubtitlePane, missionUnitLink.getUnit().toString());
    }

    private void showMissionUnitVehicles(View view) {
        View.viewRunnable = () -> showMissionUnitVehicles(view);
        showMissionUnitVehiclesDetails(view);
        showMissionUnitVehiclesMap(view);
    }

    private void showMissionUnitVehiclesDetails(View view) {
        view.getNavigationPanel().clearDetails();

        List<MissionVehicleLink> missionVehicleLinks = missionUnitLink.getVehicleLinks().stream()
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
        for (MissionVehicleView missionVehicleView : missionVehicleViews.values()) {
            missionVehicleView.addMissionUnitDetailsVehicle(view);
        }
    }

    private void showMissionUnitVehiclesMap(View view) {
        view.getWorldMap().setMapElementsNotVisible();
        // TODO
    }

    private void showMissionUnitResponders(View view) {
        View.viewRunnable = () -> showMissionUnitResponders(view);
        showMissionUnitRespondersDetails(view);
        showMissionUnitRespondersMap(view);
    }

    private void showMissionUnitRespondersDetails(View view) {
        view.getNavigationPanel().clearDetails();

        List<MissionResponderLink> missionResponderLinks = missionUnitLink.getResponderLinks().stream()
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
        for (MissionResponderView missionResponderView : missionResponderViews.values()) {
            missionResponderView.addMissionUnitDetailsResponder(view);
        }
    }

    private void showMissionUnitRespondersMap(View view) {
        view.getWorldMap().setMapElementsNotVisible();
        // TODO
    }

}