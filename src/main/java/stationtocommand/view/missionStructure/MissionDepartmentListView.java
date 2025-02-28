package stationtocommand.view.missionStructure;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.missionLinkStructure.MissionDepartmentLink;
import stationtocommand.model.missionStructure.Mission;
import stationtocommand.view.mainStructure.UtilsView;

public class MissionDepartmentListView {

    private final UtilsView utilsView;
    private final MissionDepartmentView missionDepartmentView;

    public MissionDepartmentListView(UtilsView utilsView) {
        this.utilsView = utilsView;
        this.missionDepartmentView = new MissionDepartmentView(utilsView);
    }

    public MissionDepartmentView getMissionDepartmentView() {
        return missionDepartmentView;
    }

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane pane1, Pane pane2, Mission mission) {
        utilsView.addSectionTitleLabel(pane1, "Departments");
        for (MissionDepartmentLink missionDepartmentLink : mission.getDepartmentLinks()) {
            showSidebar(breadCrumbBar, pane1, pane2, missionDepartmentLink);
        }
    }

    private void showSidebar(BreadCrumbBar<Object> breadCrumbBar, Pane pane1, Pane pane2, MissionDepartmentLink missionDepartmentLink) {
        HBox hBox = new HBox(10);
        pane1.getChildren().add(hBox);

        showMissionDepartmentIcon(missionDepartmentLink, hBox);
        showMissionDepartmentButton(breadCrumbBar, pane1, pane2, missionDepartmentLink, hBox);
    }

    private void showMissionDepartmentIcon(MissionDepartmentLink missionDepartmentLink, HBox hBox) {
        ImageView imageView = utilsView.smallIcon(utilsView.departmentIconPath(missionDepartmentLink.getDepartment().getDepartmentType()));
        hBox.getChildren().add(imageView);
    }

    private void showMissionDepartmentButton(BreadCrumbBar<Object> breadCrumbBar, Pane pane1, Pane pane2, MissionDepartmentLink missionDepartmentLink, HBox hBox) {
        Button button = new Button(missionDepartmentLink.getDepartment().toString());
        button.setOnAction(_ -> missionDepartmentView.show(breadCrumbBar, pane1, pane2, missionDepartmentLink));
        hBox.getChildren().add(button);
    }

}
