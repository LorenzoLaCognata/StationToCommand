package stationtocommand.view.missionStructure;

import javafx.animation.FadeTransition;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.departmentStructure.Department;
import stationtocommand.model.missionStructure.Mission;
import stationtocommand.view.mainStructure.UtilsView;

import java.util.List;

public class MissionListView {

    private final UtilsView utilsView;
    private final MissionView missionView;

    public MissionListView(UtilsView utilsView) {
        this.utilsView = utilsView;
        this.missionView = new MissionView(utilsView);
    }

    public MissionView getMissionView() {
        return missionView;
    }

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane pane1, Pane pane2, List<Mission> missions) {
        utilsView.addLabel(pane1, "Missions");
        for (Mission mission : missions) {
            showSidebar(breadCrumbBar, pane1, pane2, mission);
            missionView.showMap(pane2, mission);
        }
    }

    private void showSidebar(BreadCrumbBar<Object> breadCrumbBar, Pane pane1, Pane pane2, Mission mission) {
        Button button = new Button(mission.toString());
        button.setOnAction(_ -> missionView.show(breadCrumbBar, pane1, pane2, mission));
        pane1.getChildren().add(button);
    }

}
