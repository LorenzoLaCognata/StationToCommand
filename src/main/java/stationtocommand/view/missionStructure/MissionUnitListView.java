package stationtocommand.view.missionStructure;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.missionLinkStructure.MissionStationLink;
import stationtocommand.model.missionLinkStructure.MissionUnitLink;
import stationtocommand.view.mainStructure.UtilsView;

public class MissionUnitListView {

    private final UtilsView utilsView;
    private final MissionUnitView missionUnitView;

    public MissionUnitListView(UtilsView utilsView) {
        this.utilsView = utilsView;
        this.missionUnitView = new MissionUnitView(utilsView);
    }

    public MissionUnitView getMissionUnitView() {
        return missionUnitView;
    }

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane pane1, Pane pane2, MissionStationLink missionStationLink) {
        utilsView.addSectionTitleLabel(pane1, "Units");
        for (MissionUnitLink missionUnitLink : missionStationLink.getUnitLinks()) {
            showSidebar(breadCrumbBar, pane1, pane2, missionUnitLink);
        }
    }

    private void showSidebar(BreadCrumbBar<Object> breadCrumbBar, Pane pane1, Pane pane2, MissionUnitLink missionUnitLink) {
        HBox hBox = new HBox(10);
        pane1.getChildren().add(hBox);

        showMissionUnitIcon(missionUnitLink, hBox);
        showMissionUnitButton(breadCrumbBar, pane1, pane2, missionUnitLink, hBox);
    }

    private void showMissionUnitIcon(MissionUnitLink missionUnitLink, HBox hBox) {
        ImageView imageView = utilsView.smallIcon(utilsView.unitIconPath(missionUnitLink.getUnit().getUnitType()));
        hBox.getChildren().add(imageView);
    }

    private void showMissionUnitButton(BreadCrumbBar<Object> breadCrumbBar, Pane pane1, Pane pane2, MissionUnitLink missionUnitLink, HBox hBox) {
        Button button = new Button(missionUnitLink.getUnit().toString());
        button.setOnAction(_ -> missionUnitView.show(breadCrumbBar, pane1, pane2, missionUnitLink));
        hBox.getChildren().add(button);
    }

}
