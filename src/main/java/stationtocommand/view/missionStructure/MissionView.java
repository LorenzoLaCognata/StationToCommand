package stationtocommand.view.missionStructure;

import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
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
        missionDepartmentListView.show(breadCrumbBar, pane1, pane2, mission);
        showMap(pane2, mission);
    }

    public void showMap(Pane pane, Mission mission) {
        Point2D point = utilsView.locationToPoint(mission.getLocation());
        String iconPath;
        switch (mission.getMissionType()) {
            case STRUCTURE_FIRE -> iconPath = "/images/fireMission.png";
            case VEHICLE_FIRE -> iconPath = "/images/fireMission.png";
            case WATER_RESCUE -> iconPath = "/images/rescueMission.png";
            case COLLAPSE_RESCUE -> iconPath = "/images/rescueMission.png";
            case ANIMAL_RESCUE -> iconPath = "/images/rescueMission.png";
            case TRAFFIC_INCIDENT -> iconPath = "/images/rescueMission.png";
            case BURGLARY_IN_PROGRESS -> iconPath = "/images/policeMission.png";
            case DOMESTIC_DISTURBANCE -> iconPath = "/images/policeMission.png";
            case SUSPECT_APPREHENSION -> iconPath = "/images/policeMission.png";
            case CROWD_CONTROL -> iconPath = "/images/policeMission.png";
            case MEDICAL_EMERGENCY -> iconPath = "/images/medicMission.png";
            case TRAUMA_RESPONSE -> iconPath = "/images/medicMission.png";
            case CARDIAC_ARREST -> iconPath = "/images/medicMission.png";
            case POISONING_OVERDOSE -> iconPath = "/images/medicMission.png";
            case MATERNITY_EMERGENCY -> iconPath = "/images/medicMission.png";
            default -> iconPath = "/images/blank.png";
        }
        ImageView imageView = utilsView.stationIcon(iconPath);
        utilsView.addImageToMap(pane, imageView, point);
    }

}
