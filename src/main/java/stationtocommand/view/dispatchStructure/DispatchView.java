package stationtocommand.view.dispatchStructure;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import stationtocommand.model.missionStructure.Mission;
import stationtocommand.view.View;
import stationtocommand.view.mainStructure.UtilsView;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class DispatchView {

    private final List<Mission> missions;
    private final UtilsView utilsView;
    private final SortedMap<Mission, MissionView> missionViews;

    public DispatchView(List<Mission> missions, View view, UtilsView utilsView) {
        this.missions = missions;
        this.missionViews = new TreeMap<>();
        addMissionViews(view, utilsView);
        this.utilsView = utilsView;
    }

    public SortedMap<Mission, MissionView> getMissionViews() {
        return missionViews;
    }

    public MissionView getMissionView(Mission mission) {
        return missionViews.get(mission);
    }

    public void addMissionViews(View view, UtilsView utilsView) {
        for (Mission mission : missions) {
            addMissionView(view, utilsView, mission);
        }
    }

    public void addMissionView(View view, UtilsView utilsView, Mission mission) {
        System.out.println("X0");
        if (!missionViews.containsKey(mission)) {
            System.out.println("X1");
            MissionView missionView = new MissionView(mission, view, utilsView);
            System.out.println("X2");
            missionViews.put(mission, missionView);
            System.out.println("X3 " + missionView.getMission() + " - " + ((ImageView) missionView.getNode()).getImage().getUrl());
            // TODO: restore after solving app freeze issue
            //view.addToMapDISABLED(missionView.getNode());
            System.out.println("X4");
            System.out.println("X5");
        }
        System.out.println("X6");
    }

    private void addDispatchTitle(View view) {
        Pane horizontalTitlePane = utilsView.createHBox(view.getTitlePane());
        utilsView.addMainTitleLabel(horizontalTitlePane, "Dispatch");
    }

    public void showDispatch(View view) {
        showDispatchDetails(view);
        showDispatchMap(view);
    }

    private void showDispatchDetails(View view) {
        addDispatchTitle(view);
        for (MissionView missionView : missionViews.values()) {
            missionView.addDispatchDetailsMission(view);
        }
    }

    private void showDispatchMap(View view) {
        view.hideMap();
        for (MissionView missionView : missionViews.values()) {
            missionView.setNodeVisible();
        }
    }

}