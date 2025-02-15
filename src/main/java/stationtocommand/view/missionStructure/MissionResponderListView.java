package stationtocommand.view.missionStructure;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.missionLinkStructure.MissionResponderLink;
import stationtocommand.model.missionLinkStructure.MissionStationLink;
import stationtocommand.model.missionLinkStructure.MissionUnitLink;
import stationtocommand.model.missionStructure.Mission;
import stationtocommand.model.responderStructure.Responder;
import stationtocommand.view.mainStructure.UtilsView;

import java.util.List;

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

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane pane, MissionStationLink missionStationLink) {
        utilsView.addLabel(pane, "Responders");
        for (MissionUnitLink missionUnitLink : missionStationLink.getUnitLinks()) {
            for (MissionResponderLink missionResponderLink : missionUnitLink.getResponderLinks()) {
                Button button = new Button(missionResponderLink.getResponder().toString());
                button.setOnAction(_ -> missionResponderView.show(breadCrumbBar, pane, missionResponderLink));
                pane.getChildren().add(button);
            }
        }
    }

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane pane, MissionUnitLink missionUnitLink) {
        utilsView.addLabel(pane, "Responders");
        for (MissionResponderLink missionResponderLink : missionUnitLink.getResponderLinks()) {
            Button button = new Button(missionResponderLink.getResponder().toString());
            button.setOnAction(_ -> missionResponderView.show(breadCrumbBar, pane, missionResponderLink));
            pane.getChildren().add(button);
        }
    }

}
