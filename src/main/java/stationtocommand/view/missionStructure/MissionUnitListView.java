package stationtocommand.view.missionStructure;

import javafx.scene.control.Button;
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
        utilsView.addLabel(pane1, "Units");
        for (MissionUnitLink missionUnitLink : missionStationLink.getUnitLinks()) {
            Button button = new Button(missionUnitLink.getUnit().toString());
            button.setOnAction(_ -> missionUnitView.show(breadCrumbBar, pane1, pane2, missionUnitLink));
            pane1.getChildren().add(button);
        }
    }

}
