package view.unitStructure.unitListModule;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import model.unitStructure.unitModule.Unit;
import view.unitStructure.unitModule.UnitView;
import view.utilsStructure.utilsModule.UtilsView;

import java.util.List;

public class UnitListView {

    private final UtilsView utilsView;
    private final UnitView unitView;

    public UnitListView(UtilsView utilsView) {
        this.utilsView = utilsView;
        this.unitView = new UnitView(utilsView);
    }

    public void show(Pane pane, List<Node> nodes, List<Unit> units) {
        Label unitsSeparator = new Label("----------------------\nUnits");
        pane.getChildren().addAll(unitsSeparator);

        for (Unit unit : units) {
            Button button = new Button();
            button.setOnAction(_ -> {
                List<Node> nextNodes = utilsView.resetAndAddToPane(pane, nodes, button);
                unitView.show(pane, nextNodes, unit);
            });
            utilsView.addToSidebar(pane, button, unit.toString(), unit.getResponders().size() + " responders");
        }
    }

}
