package stationtocommand.view.dispatchStructure;

import stationtocommand.model.missionStructure.Mission;
import stationtocommand.view.View;
import stationtocommand.view.mainStructure.UtilsView;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DispatchView {

    private final UtilsView utilsView;
    private final Map<Mission, MissionView> missionViews;

    public DispatchView(List<Mission> missions, View view, UtilsView utilsView) {
        this.missionViews = new LinkedHashMap<>();
        for (Mission mission : missions) {
            addMissionView(mission, view, utilsView);
        }
        this.utilsView = utilsView;
    }

    public Map<Mission, MissionView> getMissionViews() {
        return missionViews;
    }

    public MissionView getMissionView(Mission mission) {
        return missionViews.get(mission);
    }

    public void addMissionView(Mission mission, View view, UtilsView utilsView) {
        if (!missionViews.containsKey(mission)) {
            MissionView missionView = new MissionView(mission, view, utilsView);
            missionViews.put(mission, missionView);
            view.addToMap(missionView.getNode());
        }
    }

    public void show(View view) {
        showNavigationPanel(view);
        showMap(view);
    }

    private void showNavigationPanel(View view) {
        utilsView.addTitle(view.getTitlePane(), "Dispatch");
        for (MissionView missionView : missionViews.values()) {
            missionView.addListDetails(view);
        }
    }

    private void showMap(View view) {
        view.hideMap();
        for (MissionView missionView : missionViews.values()) {
            missionView.showNode();
        }
    }

}