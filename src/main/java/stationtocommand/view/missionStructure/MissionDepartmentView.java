package stationtocommand.view.missionStructure;

import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.missionLinkStructure.MissionDepartmentLink;
import stationtocommand.view.View;
import stationtocommand.view.mainStructure.IconColor;
import stationtocommand.view.mainStructure.IconType;
import stationtocommand.view.mainStructure.UtilsView;


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

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane navigationPanel, Pane worldMap, MissionDepartmentLink missionDepartmentLink) {
        View.viewRunnable = () -> show(breadCrumbBar, navigationPanel, worldMap, missionDepartmentLink);
        utilsView.addBreadCrumb(breadCrumbBar, missionDepartmentLink);
        utilsView.clearPane(navigationPanel);
        utilsView.clearPane(worldMap);
        showSidebar(breadCrumbBar, navigationPanel, worldMap, missionDepartmentLink);
    }

    private void showSidebar(BreadCrumbBar<Object> breadCrumbBar, Pane navigationPanel, Pane worldMap, MissionDepartmentLink missionDepartmentLink) {
        showMissionDepartmentDetails(navigationPanel, missionDepartmentLink);
        missionStationListView.show(breadCrumbBar, navigationPanel, worldMap, missionDepartmentLink);
    }

    private void showMissionDepartmentDetails(Pane navigationPanel, MissionDepartmentLink missionDepartmentLink) {
        Pane labelPane = utilsView.createHBox(navigationPanel);
        utilsView.addIconToPane(navigationPanel, IconType.MEDIUM, IconColor.BLANK, utilsView.missionIconPath(missionDepartmentLink.getMission().getMissionType()));
        utilsView.addMainTitleLabel(labelPane, missionDepartmentLink.getMission().toString());
    }

}
