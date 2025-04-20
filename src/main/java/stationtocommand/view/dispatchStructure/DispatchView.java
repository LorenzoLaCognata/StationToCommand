package stationtocommand.view.dispatchStructure;

import javafx.scene.layout.Pane;
import stationtocommand.model.missionStructure.Mission;
import stationtocommand.view.View;
import stationtocommand.view.mainStructure.UtilsView;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class DispatchView {

    private final UtilsView utilsView;
    private final SortedMap<Mission, MissionView> missionViews;

    public DispatchView(List<Mission> missions, View view, UtilsView utilsView) {
        this.utilsView = utilsView;
        this.missionViews = missions.stream()
                .collect(Collectors.toMap(
                        mission -> mission,
                        mission -> new MissionView(mission, utilsView),
                        (_, b) -> b,
                        TreeMap::new
                ));
    }

    public MissionView getMissionView(Mission mission) {
        return missionViews.get(mission);
    }

    public void addMissionView(Mission mission) {
        missionViews.put(mission, new MissionView(mission, utilsView));
    }

    public void showDispatch(View view) {
        addDispatchTitle(view);
        view.getWorldMap().setMapElementsNotVisible();
        for (MissionView missionView : missionViews.values()) {
            missionView.addDispatchDetailsMission(view);
            // TODO: restore for map
            //utilsView.setGroupVisible(view.getWorldMap().getMapElementsLayer(), departmentView.getStationIcons());
        }

    }

    private void addDispatchTitle(View view) {
        Pane horizontalTitlePane = utilsView.createHBox(view.getNavigationPanel().getTitlePane());
        utilsView.addMainTitleLabel(horizontalTitlePane, "Dispatch");
    }

}