package stationtocommand.view.mainStructure;

import stationtocommand.model.missionStructure.Mission;
import stationtocommand.view.View;
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

    public void show(View view, List<Mission> missions) {
        missionListView.show(view, missions);
    }

}
