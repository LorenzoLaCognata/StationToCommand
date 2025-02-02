package view.dispatchStructure.dispatchModule;

import javafx.scene.Node;
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

    public void show(Pane pane1, Pane pane2, List<Node> nodes1, List<Node> nodes2, List<Mission> missions) {
        missionListView.show(pane1, pane2, nodes1, nodes2, missions);
    }

}
