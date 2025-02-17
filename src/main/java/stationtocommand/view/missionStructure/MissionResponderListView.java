package stationtocommand.view.missionStructure;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.missionLinkStructure.MissionResponderLink;
import stationtocommand.model.missionLinkStructure.MissionStationLink;
import stationtocommand.model.missionLinkStructure.MissionUnitLink;
import stationtocommand.view.View;
import stationtocommand.view.mainStructure.UtilsView;

public class MissionResponderListView {

    private final UtilsView utilsView;
    private final MissionResponderView missionResponderView;

    public MissionResponderListView(UtilsView utilsView) {
        this.utilsView = utilsView;
        this.missionResponderView = new MissionResponderView(utilsView);
    }

    public MissionResponderView getMissionResponderView() {
        return missionResponderView;
    }

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane pane1, Pane pane2, MissionStationLink missionStationLink) {
        utilsView.addHeadingLabel(pane1, "Responders");
        for (MissionUnitLink missionUnitLink : missionStationLink.getUnitLinks()) {
            for (MissionResponderLink missionResponderLink : missionUnitLink.getResponderLinks()) {
                Button button = new Button(missionResponderLink.getResponder().toString());
                button.setOnAction(_ -> missionResponderView.show(breadCrumbBar, pane1, pane2, missionResponderLink));
                pane1.getChildren().add(button);
            }
        }
    }

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane pane1, Pane pane2, MissionUnitLink missionUnitLink) {
        utilsView.addHeadingLabel(pane1, "Responders");
        for (MissionResponderLink missionResponderLink : missionUnitLink.getResponderLinks()) {
            showSidebar(breadCrumbBar, pane1, pane2, missionResponderLink);
            showMap(pane2, missionResponderLink);
        }
    }

    public void showSidebar(BreadCrumbBar<Object> breadCrumbBar, Pane pane1, Pane pane2, MissionResponderLink missionResponderLink) {
        Button button = new Button(missionResponderLink.getResponder().toString());
        button.setOnAction(_ -> missionResponderView.show(breadCrumbBar, pane1, pane2, missionResponderLink));
        pane1.getChildren().add(button);
    }

    public void showMap(Pane pane, MissionResponderLink missionResponderLink) {
        missionResponderView.showMap(pane, missionResponderLink);
    }

}
