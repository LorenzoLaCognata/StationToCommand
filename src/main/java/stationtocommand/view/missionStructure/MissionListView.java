package stationtocommand.view.missionStructure;

import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.missionLinkStructure.MissionDepartmentLink;
import stationtocommand.model.missionLinkStructure.MissionStationLink;
import stationtocommand.model.missionLinkStructure.MissionUnitLink;
import stationtocommand.model.missionStructure.Mission;
import stationtocommand.view.mainStructure.IconColor;
import stationtocommand.view.mainStructure.IconType;
import stationtocommand.view.mainStructure.UtilsView;

import java.util.List;

public class MissionListView {

    private final UtilsView utilsView;
    private final MissionView missionView;

    public MissionListView(UtilsView utilsView) {
        this.utilsView = utilsView;
        this.missionView = new MissionView(utilsView);
    }

    public MissionView getMissionView() {
        return missionView;
    }

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane navigationPanel, Pane worldMap, List<Mission> missions) {
        utilsView.addSectionTitleLabel(navigationPanel, "Missions");
        for (Mission mission : missions) {
            showSidebar(breadCrumbBar, navigationPanel, worldMap, mission);
            missionView.showMap(worldMap, mission);
        }
    }

    private void showSidebar(BreadCrumbBar<Object> breadCrumbBar, Pane navigationPanel, Pane worldMap, Mission mission) {
        Pane labelPane = utilsView.createHBox(navigationPanel);
        showMissionIcon(labelPane, mission);
        showMissionButton(breadCrumbBar, navigationPanel, worldMap, labelPane, mission);
        showMissionDepartments(labelPane, mission);
    }

    private void showMissionIcon(Pane labelPane, Mission mission) {
        utilsView.addIconToPane(labelPane, IconType.SMALL, IconColor.BLANK, utilsView.missionIconPath(mission.getMissionType()));
    }

    private void showMissionButton(BreadCrumbBar<Object> breadCrumbBar, Pane navigationPanel, Pane worldMap, Pane labelPane, Mission mission) {
        utilsView.addButtonToPane(labelPane, mission.toString(), (_ -> missionView.show(breadCrumbBar, navigationPanel, worldMap, mission)));
    }

    private void showMissionDepartments(Pane labelPane, Mission mission) {
        if (!mission.getDepartmentLinks().isEmpty()) {
            for (MissionDepartmentLink missionDepartmentLink : mission.getDepartmentLinks()) {
                utilsView.addIconToPane(labelPane, IconType.SMALL, IconColor.BLANK, utilsView.departmentIconPath(missionDepartmentLink.getDepartment().getDepartmentType()));

                for (MissionStationLink missionStationLink : missionDepartmentLink.getStationLinks()) {
                    if (!missionStationLink.getUnitLinks().isEmpty()) {
                        for (MissionUnitLink missionUnitLink : missionStationLink.getUnitLinks()) {
                            utilsView.addBodyLabel(labelPane, missionUnitLink.getUnit().toString());
                        }
                    }
                }
            }
        }
    }

}
