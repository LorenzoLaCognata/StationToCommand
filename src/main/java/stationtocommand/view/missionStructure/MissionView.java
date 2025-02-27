package stationtocommand.view.missionStructure;

import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.missionStructure.Mission;
import stationtocommand.view.View;
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

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane pane1, Pane pane2, Mission mission) {
        View.viewRunnable = () -> show(breadCrumbBar, pane1, pane2, mission);
        utilsView.addBreadCrumb(breadCrumbBar, mission);
        utilsView.clearPane(pane1);
        utilsView.clearPane(pane2);
        showSidebar(breadCrumbBar, pane1, pane2, mission);
        showMap(pane2, mission);
    }

    private void showSidebar(BreadCrumbBar<Object> breadCrumbBar, Pane pane1, Pane pane2, Mission mission) {
        showMissionDetails(pane1, mission);
        missionDepartmentListView.show(breadCrumbBar, pane1, pane2, mission);
    }

    public void showMap(Pane pane, Mission mission) {
        Point2D point = utilsView.locationToPoint(mission.getLocation());
        ImageView imageView = utilsView.mediumShadowIcon(utilsView.missionIconPath(mission.getMissionType()), "red");
        utilsView.addNodeToPane(pane, imageView, point);
    }

    private void showMissionDetails(Pane pane, Mission mission) {
        HBox hBox = new HBox(10);
        ImageView imageView = utilsView.mediumIcon(utilsView.missionIconPath(mission.getMissionType()));
        utilsView.addNodeToPane(hBox, imageView);
        utilsView.addMainTitleLabel(hBox, mission.toString());
        utilsView.addNodeToPane(pane, hBox);
    }

}
