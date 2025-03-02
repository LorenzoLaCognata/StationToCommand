package stationtocommand.view.missionStructure;

import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.missionLinkStructure.MissionDepartmentLink;
import stationtocommand.model.missionStructure.Mission;
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

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane navigationPanel, Pane worldMap, Mission mission) {
        utilsView.addSectionTitleLabel(navigationPanel, "Departments");
        for (MissionDepartmentLink missionDepartmentLink : mission.getDepartmentLinks()) {
            showSidebar(breadCrumbBar, navigationPanel, worldMap, missionDepartmentLink);
        }
    }

    private void showSidebar(BreadCrumbBar<Object> breadCrumbBar, Pane navigationPanel, Pane worldMap, MissionDepartmentLink missionDepartmentLink) {
        Pane labelPane = utilsView.createHBox(navigationPanel);
        showMissionDepartmentIcon(labelPane, missionDepartmentLink);
        showMissionDepartmentButton(breadCrumbBar, navigationPanel, worldMap, labelPane, missionDepartmentLink);
    }

    private void showMissionDepartmentIcon(Pane labelPane, MissionDepartmentLink missionDepartmentLink) {
        utilsView.addIconToPane(labelPane, IconType.SMALL, IconColor.BLANK, utilsView.departmentIconPath(missionDepartmentLink.getDepartment().getDepartmentType()));
    }

    private void showMissionDepartmentButton(BreadCrumbBar<Object> breadCrumbBar, Pane navigationPanel, Pane worldMap, Pane labelPane, MissionDepartmentLink missionDepartmentLink) {
        utilsView.addButtonToPane(labelPane, missionDepartmentLink.getDepartment().toString(), (_ -> missionDepartmentView.show(breadCrumbBar, navigationPanel, worldMap, missionDepartmentLink)));
    }

}
