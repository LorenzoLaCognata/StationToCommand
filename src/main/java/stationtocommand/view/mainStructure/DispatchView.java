package stationtocommand.view.mainStructure;

import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.missionStructure.Mission;
import stationtocommand.view.missionStructure.MissionListView;

import java.util.List;

public class DispatchView {

    private final MissionListView missionListView;

    public DispatchView(UtilsView utilsView) {
        this.missionListView = new MissionListView(utilsView);
    }

    public MissionListView getMissionListView() {
        return missionListView;
    }

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane navigationPanel, Pane worldMap, List<Mission> missions) {
        missionListView.show(breadCrumbBar, navigationPanel, worldMap, missions);
    }

}
