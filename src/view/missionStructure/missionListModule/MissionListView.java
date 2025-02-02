package view.missionStructure.missionListModule;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import model.missionStructure.missionModule.Mission;
import view.missionStructure.missionModule.MissionView;
import view.utilsStructure.utilsModule.UtilsView;

import java.util.List;

public class MissionListView {

    private final UtilsView utilsView;

    public MissionListView(UtilsView utilsView) {
        this.utilsView = utilsView;
    }

    public void show(Pane pane, List<Node> nodes, List<Mission> missions) {
        Label separator = new Label("----------------------\nMissions");
        pane.getChildren().addAll(separator);

        for (Mission mission : missions) {
            Button button = new Button();
            button.setOnAction(_ -> {
                List<Node> nextNodes = utilsView.resetAndAddToPane(pane, nodes, button);
                MissionView missionView = new MissionView(utilsView, mission);
                missionView.show(pane, nextNodes, mission);
            });
            String text1 = mission.getMissionType().toString();
            String text2 = mission.getLocation().toString();
            utilsView.addToSidebar(pane, button, text1, text2);
        }
    }

}
