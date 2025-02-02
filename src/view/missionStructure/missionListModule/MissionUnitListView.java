package view.missionStructure.missionListModule;

import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import model.missionStructure.missionModule.Mission;
import model.unitStructure.unitModule.Unit;
import view.missionStructure.missionModule.MissionUnitView;
import view.utilsStructure.utilsModule.UtilsView;

import java.util.List;

public class MissionUnitListView {

    private final UtilsView utilsView;
    private final MissionUnitView missionUnitView;

    public MissionUnitListView(UtilsView utilsView, Mission mission) {
        this.utilsView = utilsView;
        this.missionUnitView = new MissionUnitView(utilsView, mission);
    }

    public void show(Pane pane, List<Control> controls, Mission mission, List<Unit> units) {
        Label unitsSeparator = new Label("----------------------\nUnits");
        pane.getChildren().addAll(unitsSeparator);

        for (Unit unit : units) {
            Button button = new Button();
            button.setOnAction(_ -> {
                List<Control> newControls = utilsView.setBreadcrumbs(pane, controls, button);
                missionUnitView.show(pane, newControls, mission, unit);
            });
            utilsView.addPaneEntry(pane, button, unit.toString(), "");
        }
    }

}
