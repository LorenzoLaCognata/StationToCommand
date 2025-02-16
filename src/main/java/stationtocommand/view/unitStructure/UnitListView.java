package stationtocommand.view.unitStructure;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.unitStructure.Unit;
import stationtocommand.view.mainStructure.UtilsView;

import java.util.List;

public class UnitListView {

    private final UtilsView utilsView;
    private final UnitView unitView;

    public UnitListView(UtilsView utilsView) {
        this.utilsView = utilsView;
        this.unitView = new UnitView(utilsView);
    }

    public UnitView getUnitView() {
        return unitView;
    }

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane pane, List<Unit> units) {
        utilsView.addHeadingLabel(pane, "Units");
        for (Unit unit : units) {
            Button button = new Button(unit.toString());
            button.setOnAction(_ -> unitView.show(breadCrumbBar, pane, unit));
            pane.getChildren().add(button);
        }
    }

}
