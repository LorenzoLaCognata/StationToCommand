package view.dispatchStructure.dispatchModule;

import javafx.scene.control.Control;
import javafx.scene.layout.Pane;
import model.missionStructure.missionModule.Mission;
import view.missionStructure.missionListModule.MissionListView;
import view.utilsStructure.utilsModule.UtilsView;

import java.util.List;

public class DispatchView {

    private final MissionListView missionListView;

    public DispatchView(UtilsView utilsView) {
        this.missionListView = new MissionListView(utilsView);
    }

    public void show(Pane pane, List<Control> controls, List<Mission> missions) {
        missionListView.show(pane, controls, missions);
    }

}
