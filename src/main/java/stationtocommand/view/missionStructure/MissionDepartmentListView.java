package stationtocommand.view.missionStructure;

import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
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

    public void show(BreadCrumbBar<Object> breadCrumbBar, View view, Mission mission) {
        utilsView.addSectionTitleLabel(view.getNavigationPanel().getDetailsPane(), "Departments");
        for (MissionDepartmentLink missionDepartmentLink : mission.getDepartmentLinks()) {
            showSidebar(breadCrumbBar, view, missionDepartmentLink);
        }
    }

    private void showSidebar(BreadCrumbBar<Object> breadCrumbBar, View view, MissionDepartmentLink missionDepartmentLink) {
        Pane horizontalDetailsPane = utilsView.createHBox(view.getNavigationPanel().getDetailsPane());
        showMissionDepartmentIcon(horizontalDetailsPane, missionDepartmentLink);
        showMissionDepartmentButton(breadCrumbBar, view, horizontalDetailsPane, missionDepartmentLink);
    }

    private void showMissionDepartmentIcon(Pane pane, MissionDepartmentLink missionDepartmentLink) {
        utilsView.addIconToPane(pane, IconType.SMALL, IconColor.EMPTY, missionDepartmentLink.getDepartment().getDepartmentType().getResourcePath(), missionDepartmentLink.getDepartment().getDepartmentType().toString());
    }

    private void showMissionDepartmentButton(BreadCrumbBar<Object> breadCrumbBar, View view, Pane pane, MissionDepartmentLink missionDepartmentLink) {
        utilsView.addButtonToPane(pane, missionDepartmentLink.getDepartment().toString(), (_ -> missionDepartmentView.show(breadCrumbBar, view, missionDepartmentLink)));
    }

}
