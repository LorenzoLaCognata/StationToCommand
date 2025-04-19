package stationtocommand.view.missionStructure;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import stationtocommand.model.departmentStructure.AppearanceType;
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

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class MissionDepartmentView {

    private final UtilsView utilsView;
    private final MissionStationListView missionStationListView;

    public MissionDepartmentView(UtilsView utilsView) {
        this.utilsView = utilsView;
        this.missionStationListView = new MissionStationListView(utilsView);
    }

    public MissionStationListView getMissionStationListView() {
        return missionStationListView;
    }

    public void show(View view, MissionDepartmentLink missionDepartmentLink) {
        View.viewRunnable = () -> show(view, missionDepartmentLink);
        utilsView.addBreadCrumb(view.getBreadCrumbBar(), missionDepartmentLink);
        view.getNavigationPanel().clearAll();
        view.getWorldMap().setMapElementsNotVisible();

        showMissionDepartmentDetails(view, missionDepartmentLink);

        Pane buttonsPane = view.getNavigationPanel().getButtonsPane();

        EventHandler<ActionEvent> missionStationsButtonHandler = event -> {
            utilsView.setPaneButtonsSelectionStyle(event, buttonsPane);
            showMissionDepartmentStations(view, missionDepartmentLink);
        };
        Button missionStationsButton = utilsView.addButtonToHorizontalPane(buttonsPane, "Stations", missionStationsButtonHandler);
        missionStationsButton.setGraphic(utilsView.smallIcon(StationType.FIRE_STATION.getResourcePath(), ""));

        EventHandler<ActionEvent> missionUnitsButtonHandler = event -> {
            utilsView.setPaneButtonsSelectionStyle(event, buttonsPane);
            showMissionDepartmentUnits(view, missionDepartmentLink);
        };
        Button missionUnitsButton = utilsView.addButtonToHorizontalPane(buttonsPane, "Units", missionUnitsButtonHandler);
        missionUnitsButton.setGraphic(utilsView.smallIcon(FireUnitType.FIRE_ENGINE.getResourcePath(), ""));

        EventHandler<ActionEvent> missionVehiclesButtonHandler = event -> {
            utilsView.setPaneButtonsSelectionStyle(event, buttonsPane);
            showMissionDepartmentVehicles(view, missionDepartmentLink);
        };
        Button missionVehiclesButton = utilsView.addButtonToHorizontalPane(buttonsPane, "Vehicles", missionVehiclesButtonHandler);
        missionVehiclesButton.setGraphic(utilsView.smallIcon(PoliceVehicleType.SUV.getResourcePath(), ""));

        EventHandler<ActionEvent> missionRespondersButtonHandler = event -> {
            utilsView.setPaneButtonsSelectionStyle(event, buttonsPane);
            showMissionDepartmentResponders(view, missionDepartmentLink);
        };
        Button missionRespondersButton = utilsView.addButtonToHorizontalPane(buttonsPane, "Responders", missionRespondersButtonHandler);
        missionRespondersButton.setGraphic(utilsView.smallIcon(AppearanceType.MALE_01.getResourcePath(), ""));

        utilsView.setButtonSelectedStyle(missionStationsButton);
        showMissionDepartmentStations(view, missionDepartmentLink);

    }

    private void showMissionDepartmentDetails(View view, MissionDepartmentLink missionDepartmentLink) {
        Pane titleAndSubtitlePane = utilsView.createVBox(view.getNavigationPanel().getTitlePane());
        Pane horizontalTitlePane = utilsView.createHBox(titleAndSubtitlePane);
        utilsView.addIconToPane(horizontalTitlePane, IconType.MEDIUM, IconColor.EMPTY, missionDepartmentLink.getMission().getMissionType());
        utilsView.addMainTitleLabel(horizontalTitlePane, missionDepartmentLink.getMission().toString());
        Pane horizontalSubtitlePane = utilsView.createHBox(titleAndSubtitlePane);
        utilsView.addIconToPane(horizontalSubtitlePane, IconType.SMALL, IconColor.EMPTY, missionDepartmentLink.getDepartment().getDepartmentType());
        utilsView.addMainSubtitleLabel(horizontalSubtitlePane, missionDepartmentLink.getDepartment().toString());
    }

    private void showMissionDepartmentStations(View view, MissionDepartmentLink missionDepartmentLink) {
        View.viewRunnable = () -> showMissionDepartmentStations(view, missionDepartmentLink);
        view.getNavigationPanel().clearDetails();
        view.getWorldMap().setMapElementsNotVisible();
        missionStationListView.show(view, missionDepartmentLink);
    }

    private void showMissionDepartmentUnits(View view, MissionDepartmentLink missionDepartmentLink) {
        View.viewRunnable = () -> showMissionDepartmentUnits(view, missionDepartmentLink);
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
        missionStationListView.getMissionStationView().getMissionUnitListView().show(view, missionUnitLinks);

    }

    private void showMissionDepartmentVehicles(View view, MissionDepartmentLink missionDepartmentLink) {
        View.viewRunnable = () -> showMissionDepartmentVehicles(view, missionDepartmentLink);
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
        missionStationListView.getMissionStationView().getMissionVehicleListView().show(view, missionVehicleLinks);

    }

    private void showMissionDepartmentResponders(View view, MissionDepartmentLink missionDepartmentLink) {
        View.viewRunnable = () -> showMissionDepartmentResponders(view, missionDepartmentLink);
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
        missionStationListView.getMissionStationView().getMissionResponderListView().show(view, missionResponderLinks);

    }

}