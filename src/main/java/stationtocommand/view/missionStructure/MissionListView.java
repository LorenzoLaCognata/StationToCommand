package stationtocommand.view.missionStructure;

import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.missionLinkStructure.MissionDepartmentLink;
import stationtocommand.model.missionLinkStructure.MissionStationLink;
import stationtocommand.model.missionLinkStructure.MissionUnitLink;
import stationtocommand.model.missionStructure.Mission;
import stationtocommand.view.View;
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

    public void show(BreadCrumbBar<Object> breadCrumbBar, View view, List<Mission> missions) {
        utilsView.addSectionTitleLabel(view.getNavigationPanel().getTitlePane(), "Missions");
        for (Mission mission : missions) {
            showSidebar(breadCrumbBar, view, mission);
            missionView.showMap(view, mission);
        }
    }

    private void showSidebar(BreadCrumbBar<Object> breadCrumbBar, View view, Mission mission) {
        Pane horizontalDetailsPane = utilsView.createHBox(view.getNavigationPanel().getDetailsPane());
        showMissionIcon(horizontalDetailsPane, mission);
        showMissionButton(breadCrumbBar, view, horizontalDetailsPane, mission);
        showMissionDepartments(horizontalDetailsPane, mission);
    }

    private void showMissionIcon(Pane pane, Mission mission) {
        utilsView.addIconToPane(pane, IconType.SMALL, IconColor.EMPTY, mission.getMissionType().getResourcePath(), mission.getMissionType().toString());
    }

    private void showMissionButton(BreadCrumbBar<Object> breadCrumbBar, View view, Pane pane, Mission mission) {
        utilsView.addButtonToPane(pane, mission.toString(), (_ -> missionView.show(breadCrumbBar, view, mission)));
    }

    private void showMissionDepartments(Pane pane, Mission mission) {
        if (!mission.getDepartmentLinks().isEmpty()) {
            for (MissionDepartmentLink missionDepartmentLink : mission.getDepartmentLinks()) {
                utilsView.addIconToPane(pane, IconType.SMALL, IconColor.EMPTY, missionDepartmentLink.getDepartment().getDepartmentType().getResourcePath(), missionDepartmentLink.getDepartment().getDepartmentType().toString());

                for (MissionStationLink missionStationLink : missionDepartmentLink.getStationLinks()) {
                    if (!missionStationLink.getUnitLinks().isEmpty()) {
                        for (MissionUnitLink missionUnitLink : missionStationLink.getUnitLinks()) {
                            utilsView.addBodyLabel(pane, missionUnitLink.getUnit().toString());
                        }
                    }
                }
            }
        }
    }

}
