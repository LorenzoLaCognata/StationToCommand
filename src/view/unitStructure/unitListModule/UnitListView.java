package view.unitStructure.unitListModule;

import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import model.unitStructure.unitModule.Unit;
import view.unitStructure.unitModule.UnitView;
import view.utilsStructure.utilsModule.UtilsView;

import java.util.List;

public class UnitListView {

    private final UtilsView utilsView;
    private final UnitView unitView;

    public UnitListView(UtilsView utilsView, UnitView unitView) {
        this.utilsView = utilsView;
        this.unitView = unitView;
    }

    public void show(Pane pane, List<Control> controls, List<Unit> units) {
        Label unitsSeparator = new Label("----------------------\nUnits");
        pane.getChildren().addAll(unitsSeparator);

        for (Unit unit : units) {
            Button button = new Button();
            button.setOnAction(_ -> {
                List<Control> newControls = utilsView.setBreadcrumbs(pane, controls, button);
                unitView.show(pane, newControls, unit);
            });
            utilsView.addPaneEntry(pane, button, unit.toString(), unit.getResponders().size() + " responders");
        }
    }

}
