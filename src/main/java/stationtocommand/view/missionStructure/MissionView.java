package stationtocommand.view.missionStructure;

import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.missionStructure.Mission;
import stationtocommand.view.View;
import stationtocommand.view.mainStructure.IconColor;
import stationtocommand.view.mainStructure.IconType;
import stationtocommand.view.mainStructure.UtilsView;

public class MissionView {

    private final UtilsView utilsView;
    private final MissionDepartmentListView missionDepartmentListView;

    public MissionView(UtilsView utilsView) {
        this.utilsView = utilsView;
        this.missionDepartmentListView = new MissionDepartmentListView(utilsView);
    }

    public MissionDepartmentListView getMissionDepartmentListView() {
        return missionDepartmentListView;
    }

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane navigationPanel, Pane worldMap, Mission mission) {
        View.viewRunnable = () -> show(breadCrumbBar, navigationPanel, worldMap, mission);
        utilsView.addBreadCrumb(breadCrumbBar, mission);
        utilsView.clearPane(navigationPanel);
        utilsView.clearPane(worldMap);
        showSidebar(breadCrumbBar, navigationPanel, worldMap, mission);
        showMap(worldMap, mission);
    }

    private void showSidebar(BreadCrumbBar<Object> breadCrumbBar, Pane navigationPanel, Pane worldMap, Mission mission) {
        showMissionDetails(navigationPanel, mission);
        missionDepartmentListView.show(breadCrumbBar, navigationPanel, worldMap, mission);
    }

    public void showMap(Pane worldMap, Mission mission) {
        Point2D point = utilsView.locationToPoint(mission.getLocation(), IconType.SMALL);
        ImageView imageView = utilsView.smallIcon(mission.getMissionType().getResourcePath(), mission.getMissionType().toString());
        utilsView.addNodeToPane(worldMap, imageView, point);
    }

    private void showMissionDetails(Pane navigationPanel, Mission mission) {
        Pane labelPane = utilsView.createHBox(navigationPanel);
        utilsView.addIconToPane(navigationPanel, IconType.MEDIUM, IconColor.EMPTY, mission.getMissionType().getResourcePath(), mission.getMissionType().toString());
        utilsView.addMainTitleLabel(labelPane, mission.toString());
    }

}
