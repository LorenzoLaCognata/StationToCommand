package stationtocommand.view.missionStructure;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.missionLinkStructure.MissionStationLink;
import stationtocommand.model.missionLinkStructure.MissionUnitLink;
import stationtocommand.model.missionStructure.Mission;
import stationtocommand.model.unitStructure.Unit;
import stationtocommand.view.mainStructure.UtilsView;

import java.util.List;
import java.util.Objects;

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

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane pane, MissionStationLink missionStationLink) {
        utilsView.addLabel(pane, "Units");
        for (MissionUnitLink missionUnitLink : missionStationLink.getUnitLinks()) {
            Button button = new Button(missionUnitLink.getUnit().toString());
            button.setOnAction(_ -> missionUnitView.show(breadCrumbBar, pane, missionUnitLink));
            pane.getChildren().add(button);
        }
    }

}
