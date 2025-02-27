package stationtocommand.view.missionStructure;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.missionLinkStructure.MissionDepartmentLink;
import stationtocommand.model.missionLinkStructure.MissionStationLink;
import stationtocommand.model.missionLinkStructure.MissionUnitLink;
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
        utilsView.addSectionTitleLabel(pane1, "Missions");
        for (Mission mission : missions) {
            showSidebar(breadCrumbBar, pane1, pane2, mission);
            missionView.showMap(pane2, mission);
        }
    }

    private void showSidebar(BreadCrumbBar<Object> breadCrumbBar, Pane pane1, Pane pane2, Mission mission) {
        HBox hBox = new HBox(10);
        pane1.getChildren().add(hBox);

        showMissionIcon(mission, hBox);
        showMissionButton(breadCrumbBar, pane1, pane2, mission, hBox);
        showMissionDepartments(mission, hBox);
    }

    private void showMissionIcon(Mission mission, HBox hBox) {
        ImageView imageView = utilsView.smallIcon(utilsView.missionIconPath(mission.getMissionType()));
        hBox.getChildren().add(imageView);
    }

    private void showMissionButton(BreadCrumbBar<Object> breadCrumbBar, Pane pane1, Pane pane2, Mission mission, HBox hBox) {
        Button button = new Button(mission.toString());
        button.setOnAction(_ -> missionView.show(breadCrumbBar, pane1, pane2, mission));
        hBox.getChildren().add(button);
    }

    private void showMissionDepartments(Mission mission, HBox hBox) {
        if (!mission.getDepartmentLinks().isEmpty()) {
            for (MissionDepartmentLink missionDepartmentLink : mission.getDepartmentLinks()) {
                ImageView imageView = utilsView.smallIcon(utilsView.departmentIconPath(missionDepartmentLink.getDepartment().getDepartmentType()));
                hBox.getChildren().add(imageView);

                for (MissionStationLink missionStationLink : missionDepartmentLink.getStationLinks()) {
                    if (!missionStationLink.getUnitLinks().isEmpty()) {
                        for (MissionUnitLink missionUnitLink : missionStationLink.getUnitLinks()) {
                            utilsView.addBodyLabel(hBox, missionUnitLink.getUnit().toString());
                        }
                    }
                }
            }
        }
    }

}
