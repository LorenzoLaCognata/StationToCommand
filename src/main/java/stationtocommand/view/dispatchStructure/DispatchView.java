package stationtocommand.view.dispatchStructure;

import javafx.scene.image.ImageView;
import stationtocommand.model.missionStructure.Mission;
import stationtocommand.view.View;
import stationtocommand.view.mainStructure.UtilsView;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class DispatchView {

    private final UtilsView utilsView;
    private final SortedMap<Mission, MissionView> missionViews;

    public DispatchView(List<Mission> missions, View view, UtilsView utilsView) {
        this.missionViews = new TreeMap<>();
        for (Mission mission : missions) {
            addMissionView(mission, view, utilsView);
        }
        this.utilsView = utilsView;
    }

    public SortedMap<Mission, MissionView> getMissionViews() {
        return missionViews;
    }

    public MissionView getMissionView(Mission mission) {
        return missionViews.get(mission);
    }

    public void addMissionView(Mission mission, View view, UtilsView utilsView) {
        System.out.println("X1");
        if (!missionViews.containsKey(mission)) {
            System.out.println("X2");
            MissionView missionView = new MissionView(mission, view, utilsView);
            System.out.println("X3");
            missionViews.put(mission, missionView);
            System.out.println("X4 " + missionView.getMission() + " - " + ((ImageView) missionView.getNode()).getImage().getUrl());
            // TODO: restore after solving app freeze issue
            view.addToMapDISABLED(missionView.getNode());
            System.out.println("X5");
        }
        System.out.println("X6");
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