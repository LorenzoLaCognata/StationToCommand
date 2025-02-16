package stationtocommand.view.missionStructure;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.missionLinkStructure.MissionDepartmentLink;
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
        utilsView.addHeadingLabel(pane1, "Missions");
        for (Mission mission : missions) {
            showSidebar(breadCrumbBar, pane1, pane2, mission);
            missionView.showMap(pane2, mission);
        }
    }

    private void showSidebar(BreadCrumbBar<Object> breadCrumbBar, Pane pane1, Pane pane2, Mission mission) {

        Button button = new Button(mission.toString());
        button.setOnAction(_ -> missionView.show(breadCrumbBar, pane1, pane2, mission));

        if (mission.getDepartmentLinks().isEmpty()) {
            pane1.getChildren().add(button);
        }
        else {
            String string = "";
            for (int i=0; i<mission.getDepartmentLinks().size(); i++) {
                if (i > 0) {
                    string = string + ", ";
                }
                MissionDepartmentLink missionDepartmentLink = mission.getDepartmentLinks().get(i);
                string = string + missionDepartmentLink.getDepartment();
            }
            // TODO: replace label with department icons
            //ImageView icon = new ImageView("icon.png"); // Example icon
            HBox hBox = new HBox(10);
            hBox.getChildren().add(button);
            utilsView.addLabel(hBox, string);
            pane1.getChildren().add(hBox);
        }

    }

}
