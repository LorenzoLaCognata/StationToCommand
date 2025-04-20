package stationtocommand.view.dispatchStructure;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import stationtocommand.model.missionLinkStructure.MissionResponderLink;
import stationtocommand.model.missionLinkStructure.MissionStationLink;
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

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MissionUnitView {

    private final MissionUnitLink missionUnitLink;
    private final UtilsView utilsView;
    private final MissionResponderListView missionResponderListView;
    private final MissionVehicleListView missionVehicleListView;

    public MissionUnitView(MissionUnitLink missionUnitLink, UtilsView utilsView) {
        System.out.println("Created MissionUnitView for " + missionUnitLink.getMission() + " - " + missionUnitLink.getUnit());
        this.missionUnitLink = missionUnitLink;
        this.utilsView = utilsView;
        this.missionResponderListView = new MissionResponderListView(utilsView);
        this.missionVehicleListView = new MissionVehicleListView(utilsView);
    }

    public MissionUnitLink getMissionUnitLink() {
        return missionUnitLink;
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
        view.getWorldMap().setMapElementsNotVisible();
        showSidebar(view);
    }

    private void showSidebar(View view) {
        showMissionUnitDetails(view);

        Pane buttonsPane = view.getNavigationPanel().getButtonsPane();

        EventHandler<ActionEvent> missionVehiclesButtonHandler = event -> {
            utilsView.setPaneButtonsSelectionStyle(event, buttonsPane);
            showMissionUnitVehicles(view);
        };
        Button missionVehiclesButton = utilsView.addButtonToHorizontalPane(buttonsPane, "Vehicles", missionVehiclesButtonHandler);
        missionVehiclesButton.setGraphic(utilsView.smallIcon(PoliceVehicleType.PATROL_SEDAN.getResourcePath(), ""));

        EventHandler<ActionEvent> missionRespondersButtonHandler = event -> {
            utilsView.setPaneButtonsSelectionStyle(event, buttonsPane);
            showMissionUnitResponders(view);
        };
        Button missionRespondersButton = utilsView.addButtonToHorizontalPane(buttonsPane, "Responders", missionRespondersButtonHandler);
        missionRespondersButton.setGraphic(utilsView.smallIcon(AppearanceType.MALE_01.getResourcePath(), ""));

        utilsView.setButtonSelectedStyle(missionVehiclesButton);
        showMissionUnitVehicles(view);
    }

    private void showMissionUnitDetails(View view) {
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
        missionVehicleListView.show(view, missionVehicleLinks);

    }

    private void showMissionUnitResponders(View view) {
        View.viewRunnable = () -> showMissionUnitResponders(view);
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
        missionResponderListView.show(view, missionResponderLinks);
    }

}