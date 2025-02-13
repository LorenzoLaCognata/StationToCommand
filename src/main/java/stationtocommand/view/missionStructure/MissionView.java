package stationtocommand.view.missionStructure;

import javafx.animation.FadeTransition;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.departmentStructure.Department;
import stationtocommand.model.departmentStructure.DepartmentLink;
import stationtocommand.model.missionStructure.Mission;
import stationtocommand.model.stationStructure.Station;
import stationtocommand.view.mainStructure.UtilsView;

import java.util.List;

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
        utilsView.addBreadCrumb(breadCrumbBar, mission);
        utilsView.clearPane(pane1);
        utilsView.clearPane(pane2);
        showMap(pane2, mission);
        missionDepartmentListView.show(breadCrumbBar, pane1, mission);
    }

    public void showMap(Pane pane2, Mission mission) {
        Point2D point = utilsView.locationToPoint(mission.getLocation());
        Circle circle = new Circle(point.getX(), point.getY(), 10, Color.RED);
        utilsView.addToMap(pane2, circle);
    }

}
