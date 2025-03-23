package stationtocommand.view.missionStructure;

import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
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

    public void show(View view, Mission mission) {
        View.viewRunnable = () -> show(view, mission);
        utilsView.addBreadCrumb(view.getBreadCrumbBar(), mission);
        view.getNavigationPanel().clear();
        view.getWorldMap().clear();
        showSidebar(view, mission);
        showMap(view, mission);
    }

    private void showSidebar(View view, Mission mission) {
        showMissionDetails(view, mission);
        missionDepartmentListView.show(view, mission);
    }

    public void showMap(View view, Mission mission) {
        Point2D point = utilsView.locationToPoint(mission.getLocation(), IconType.SMALL);
        ImageView imageView = utilsView.smallIcon(mission.getMissionType().getResourcePath(), mission.getMissionType().toString());
        Pane mapLayer = view.getWorldMap().getMapElementsLayer();
        utilsView.addNodeToPane(mapLayer, imageView, point);
    }

    private void showMissionDetails(View view, Mission mission) {
        Pane horizontalTitlePane = utilsView.createHBox(view.getNavigationPanel().getTitlePane());
        utilsView.addIconToPane(horizontalTitlePane, IconType.MEDIUM, IconColor.EMPTY, mission.getMissionType().getResourcePath(), mission.getMissionType().toString());
        utilsView.addMainTitleLabel(horizontalTitlePane, mission.toString());
    }

}
