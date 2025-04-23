package stationtocommand.view.missionStructure;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MissionUnitView {

    private final UtilsView utilsView;
    private final MissionResponderListView missionResponderListView;
    private final MissionVehicleListView missionVehicleListView;

    public MissionUnitView(UtilsView utilsView) {
        this.utilsView = utilsView;
        this.missionResponderListView = new MissionResponderListView(utilsView);
        this.missionVehicleListView = new MissionVehicleListView(utilsView);
    }

    public void show(View view, MissionUnitLink missionUnitLink) {
        View.viewRunnable = () -> show(view, missionUnitLink);
        utilsView.addBreadCrumb(view.getBreadCrumbBar(), missionUnitLink);
        view.getNavigationPanel().clearAll();
        view.getWorldMap().setMapElementsNotVisible();
        showSidebar(view, missionUnitLink);
    }

    private void showSidebar(View view, MissionUnitLink missionUnitLink) {
        showMissionUnitDetails(view, missionUnitLink);

        Pane buttonsPane = view.getNavigationPanel().getButtonsPane();

        EventHandler<ActionEvent> missionVehiclesButtonHandler = event -> {
            utilsView.setPaneButtonsSelectionStyle(event, buttonsPane);
            showMissionUnitVehicles(view, missionUnitLink);
        };
        Button missionVehiclesButton = utilsView.addButtonToHorizontalPane(buttonsPane, "Vehicles", missionVehiclesButtonHandler);
        missionVehiclesButton.setGraphic(utilsView.smallIcon(PoliceVehicleType.PATROL_SEDAN.getResourcePath(), ""));

        EventHandler<ActionEvent> missionRespondersButtonHandler = event -> {
            utilsView.setPaneButtonsSelectionStyle(event, buttonsPane);
            showMissionUnitResponders(view, missionUnitLink);
        };
        Button missionRespondersButton = utilsView.addButtonToHorizontalPane(buttonsPane, "Responders", missionRespondersButtonHandler);
        missionRespondersButton.setGraphic(utilsView.smallIcon(AppearanceType.MALE_01.getResourcePath(), ""));

        utilsView.setButtonSelectedStyle(missionVehiclesButton);
        showMissionUnitVehicles(view, missionUnitLink);
    }

    private void showMissionUnitDetails(View view, MissionUnitLink missionUnitLink) {
        Pane titleAndSubtitlePane = utilsView.createVBox(view.getNavigationPanel().getTitlePane());
        Pane horizontalTitlePane = utilsView.createHBox(titleAndSubtitlePane);
        utilsView.addIconToPane(horizontalTitlePane, IconType.MEDIUM, IconColor.EMPTY, missionUnitLink.getMission().getMissionType());
        utilsView.addMainTitleLabel(horizontalTitlePane, missionUnitLink.getMission().toString());
        Pane horizontalSubtitlePane = utilsView.createHBox(titleAndSubtitlePane);
        utilsView.addIconToPane(horizontalSubtitlePane, IconType.SMALL, IconColor.EMPTY, missionUnitLink.getUnit().getUnitType());
        utilsView.addMainSubtitleLabel(horizontalSubtitlePane, missionUnitLink.getUnit().toString());
    }

    private void showMissionUnitVehicles(View view, MissionUnitLink missionUnitLink) {
        View.viewRunnable = () -> showMissionUnitVehicles(view, missionUnitLink);
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

    private void showMissionUnitResponders(View view, MissionUnitLink missionUnitLink) {
        View.viewRunnable = () -> showMissionUnitResponders(view, missionUnitLink);
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