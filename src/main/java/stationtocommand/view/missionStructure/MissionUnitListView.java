package stationtocommand.view.missionStructure;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import stationtocommand.model.missionStructure.Mission;
import stationtocommand.model.unitStructure.Unit;
import stationtocommand.view.mainStructure.UtilsView;

import java.util.List;

public class MissionUnitListView {

    private final UtilsView utilsView;
    private final MissionUnitView missionUnitView;

    public MissionUnitListView(UtilsView utilsView, Mission mission) {
        this.utilsView = utilsView;
        this.missionUnitView = new MissionUnitView(utilsView, mission);
    }

    public void show(Pane pane, List<Node> nodes, Mission mission, List<Unit> units) {
        Label unitsSeparator = new Label("----------------------\nUnits");
        pane.getChildren().addAll(unitsSeparator);

        for (Unit unit : units) {
            Button button = new Button();
            button.setOnAction(_ -> {
                List<Node> nextNodes = utilsView.resetAndAddToPane(pane, nodes, button);
                missionUnitView.show(pane, nextNodes, mission, unit);
            });
            utilsView.addToSidebar(pane, button, unit.toString(), "");
        }
    }

}
