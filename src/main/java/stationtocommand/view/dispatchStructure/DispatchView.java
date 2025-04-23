package stationtocommand.view.dispatchStructure;

import javafx.scene.layout.Pane;
import stationtocommand.model.missionStructure.Mission;
import stationtocommand.view.View;
import stationtocommand.view.mainStructure.IconColor;
import stationtocommand.view.mainStructure.IconType;
import stationtocommand.view.mainStructure.UtilsView;

import java.util.List;

public class DispatchView {

    private final UtilsView utilsView;
    private final MissionListView missionListView;

    public DispatchView(UtilsView utilsView) {
        this.utilsView = utilsView;
        this.missionListView = new MissionListView(utilsView);
    }

    public MissionListView getMissionListView() {
        return missionListView;
    }

    public void show(View view, List<Mission> missions) {
        showDispatchDetails(view, missions);
        missionListView.show(view, missions);
    }

    private void showDispatchDetails(View view, List<Mission> missions) {
        Pane horizontalTitlePane = utilsView.createHBox(view.getNavigationPanel().getTitlePane());
        // TODO: manage if there is no mission active at the start, it will fail
        utilsView.addIconToPane(horizontalTitlePane, IconType.MEDIUM, IconColor.EMPTY, missions.getFirst().getMissionType());
        utilsView.addMainTitleLabel(horizontalTitlePane, "Dispatch");
    }

}
