package stationtocommand.view.unitStructure;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
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
        utilsView.addSectionTitleLabel(pane, "Units");
        for (Unit unit : units) {
            showSidebar(breadCrumbBar, pane, unit);
        }
    }

    private void showSidebar(BreadCrumbBar<Object> breadCrumbBar, Pane pane, Unit unit) {
        HBox hBox = new HBox(10);
        pane.getChildren().add(hBox);

        showUnitIcon(unit, hBox);
        showUnitButton(breadCrumbBar, pane, unit, hBox);
        showUnitStatus(unit, hBox);
    }

    private void showUnitIcon(Unit unit, HBox hBox) {
        ImageView imageView = utilsView.smallIcon(utilsView.unitIconPath(unit.getUnitType()));
        hBox.getChildren().add(imageView);
    }

    private void showUnitButton(BreadCrumbBar<Object> breadCrumbBar, Pane pane, Unit unit, HBox hBox) {
        Button button = new Button(unit.toString());
        button.setOnAction(_ -> unitView.show(breadCrumbBar, pane, unit));
        hBox.getChildren().add(button);
    }

    private void showUnitStatus(Unit unit, HBox hBox) {
        ImageView statusImageView = utilsView.smallIcon(utilsView.unitStatusIconPath(unit.getUnitStatus()));
        hBox.getChildren().add(statusImageView);
    }

}
