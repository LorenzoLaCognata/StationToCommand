package stationtocommand.view.dispatchStructure;

import javafx.scene.layout.Pane;
import stationtocommand.model.missionLinkStructure.MissionDepartmentLink;
import stationtocommand.model.missionStructure.Mission;
import stationtocommand.view.View;
import stationtocommand.view.mainStructure.IconColor;
import stationtocommand.view.mainStructure.IconType;
import stationtocommand.view.mainStructure.UtilsView;

public class MissionDepartmentListView {

    private final UtilsView utilsView;
    private final MissionDepartmentView missionDepartmentView;

    public MissionDepartmentListView(UtilsView utilsView) {
        this.utilsView = utilsView;
        this.missionDepartmentView = new MissionDepartmentView(utilsView);
    }

    public MissionDepartmentView getMissionDepartmentView() {
        return missionDepartmentView;
    }

    public void show(View view, Mission mission) {
        for (MissionDepartmentLink missionDepartmentLink : mission.getDepartmentLinks()) {
            showSidebar(view, missionDepartmentLink);
        }
    }

    private void showSidebar(View view, MissionDepartmentLink missionDepartmentLink) {
        Pane horizontalDetailsPane = utilsView.createHBox(view.getNavigationPanel().getDetailsPane());
        showMissionDepartmentIcon(horizontalDetailsPane, missionDepartmentLink);
        showMissionDepartmentButton(view, horizontalDetailsPane, missionDepartmentLink);
    }

    private void showMissionDepartmentIcon(Pane pane, MissionDepartmentLink missionDepartmentLink) {
        utilsView.addIconToPane(pane, IconType.SMALL, IconColor.EMPTY, missionDepartmentLink.getDepartment().getDepartmentType());
    }

    private void showMissionDepartmentButton(View view, Pane pane, MissionDepartmentLink missionDepartmentLink) {
        utilsView.addButtonToPane(pane, missionDepartmentLink.getDepartment().toString(), (_ -> missionDepartmentView.show(view, missionDepartmentLink)));
    }

}
