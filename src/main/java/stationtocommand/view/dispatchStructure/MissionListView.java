package stationtocommand.view.dispatchStructure;

import javafx.scene.layout.Pane;
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

    public void show(View view, List<Mission> missions) {
        for (Mission mission : missions) {
            showSidebar(view, mission);
            missionView.showMap(view, mission);
        }
    }

    private void showSidebar(View view, Mission mission) {
        Pane horizontalDetailsPane = utilsView.createHBox(view.getNavigationPanel().getDetailsPane());
        showMissionIcon(horizontalDetailsPane, mission);
        showMissionButton(view, horizontalDetailsPane, mission);
        showMissionDepartments(horizontalDetailsPane, mission);
    }

    private void showMissionIcon(Pane pane, Mission mission) {
        utilsView.addIconToPane(pane, IconType.SMALL, IconColor.EMPTY, mission.getMissionType());
    }

    private void showMissionButton(View view, Pane pane, Mission mission) {
        utilsView.addButtonToPane(pane, mission.toString(), (_ -> missionView.show(view, mission)));
    }

    private void showMissionDepartments(Pane pane, Mission mission) {
        if (!mission.getDepartmentLinks().isEmpty()) {
            for (MissionDepartmentLink missionDepartmentLink : mission.getDepartmentLinks()) {
                utilsView.addIconToPane(pane, IconType.SMALL, IconColor.EMPTY, missionDepartmentLink.getDepartment().getDepartmentType());

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
