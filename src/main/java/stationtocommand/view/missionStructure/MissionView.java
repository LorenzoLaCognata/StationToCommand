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
            case STRUCTURE_FIRE, VEHICLE_FIRE -> iconPath = "/images/fireMission.png";
            case WATER_RESCUE, COLLAPSE_RESCUE -> iconPath = "/images/rescueMission.png";
            case TRAFFIC_INCIDENT -> iconPath = "/images/rescueMission.png";
            case BURGLARY, ASSAULT, DISTURBANCE -> iconPath = "/images/policeMission.png";
            case HOMICIDE, DRUG_CRIME, VICE_CRIME -> iconPath = "/images/policeMission.png";
            case CROWD_CONTROL -> iconPath = "/images/policeMission.png";
            case MEDICAL_EMERGENCY, TRAUMA_RESPONSE -> iconPath = "/images/medicMission.png";
            case CARDIAC_ARREST, POISONING_OVERDOSE -> iconPath = "/images/medicMission.png";
            default -> iconPath = "/images/blank.png";
        }
        ImageView imageView = utilsView.mediumShadowIcon(iconPath, "red");
        utilsView.addImageToMap(pane, imageView, point);
    }

}
