package stationtocommand.view.dispatchStructure;

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
        if (!missionViews.containsKey(mission)) {
            MissionView missionView = new MissionView(mission, view, utilsView);
            missionViews.put(mission, missionView);
            view.getWorldMap().addMapElement(missionView.getNode());
        }
    }

    private void addDispatchTitle(View view) {
        Pane horizontalTitlePane = utilsView.createHBox(view.getNavigationPanel().getTitlePane());
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
        view.getWorldMap().setMapElementsNotVisible();
        for (MissionView missionView : missionViews.values()) {
            missionView.setNodeVisible();
        }
    }

}