package stationtocommand.view.missionStructure;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.missionLinkStructure.MissionDepartmentLink;
import stationtocommand.model.missionLinkStructure.MissionStationLink;
import stationtocommand.view.mainStructure.UtilsView;

public class MissionStationListView {

    private final UtilsView utilsView;
    private final MissionStationView missionStationView;

    public MissionStationListView(UtilsView utilsView) {
        this.utilsView = utilsView;
        this.missionStationView = new MissionStationView(utilsView);
    }

    public MissionStationView getMissionStationView() {
        return missionStationView;
    }

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane pane1, Pane pane2, MissionDepartmentLink missionDepartmentLink) {
        utilsView.addSectionTitleLabel(pane1, "Stations");
        for (MissionStationLink missionStationLink : missionDepartmentLink.getStationLinks()) {
            showSidebar(breadCrumbBar, pane1, pane2, missionStationLink);
        }
    }

    private void showSidebar(BreadCrumbBar<Object> breadCrumbBar, Pane pane1, Pane pane2, MissionStationLink missionStationLink) {
        HBox hBox = new HBox(10);
        pane1.getChildren().add(hBox);

        showMissionStationIcon(missionStationLink, hBox);
        showMissionStationButton(breadCrumbBar, pane1, pane2, missionStationLink, hBox);
    }

    private void showMissionStationIcon(MissionStationLink missionStationLink, HBox hBox) {
        ImageView imageView = utilsView.smallIcon(utilsView.stationIconPath(missionStationLink.getStation().getDepartment().getDepartmentType()));
        hBox.getChildren().add(imageView);
    }

    private void showMissionStationButton(BreadCrumbBar<Object> breadCrumbBar, Pane pane1, Pane pane2, MissionStationLink missionStationLink, HBox hBox) {
        Button button = new Button(missionStationLink.getStation().toString());
        button.setOnAction(_ -> missionStationView.show(breadCrumbBar, pane1, pane2, missionStationLink));
        hBox.getChildren().add(button);
    }

}
