package stationtocommand.view.unitStructure;

import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.unitStructure.Unit;
import stationtocommand.view.View;
import stationtocommand.view.mainStructure.IconColor;
import stationtocommand.view.mainStructure.IconType;
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

    public void show(BreadCrumbBar<Object> breadCrumbBar, View view, List<Unit> units) {
        utilsView.addSectionTitleLabel(view.getNavigationPanel().getDetailsPane(), "Units");
        for (Unit unit : units) {
            showSidebar(breadCrumbBar, view, unit);
        }
    }

    private void showSidebar(BreadCrumbBar<Object> breadCrumbBar, View view, Unit unit) {
        Pane horizontalDetailsPane = utilsView.createHBox(view.getNavigationPanel().getDetailsPane());
        showUnitIcon(horizontalDetailsPane, unit);
        showUnitButton(breadCrumbBar, view, horizontalDetailsPane, unit);
        showUnitStatus(horizontalDetailsPane, unit);
    }

    private void showUnitIcon(Pane pane, Unit unit) {
        utilsView.addIconToPane(pane, IconType.SMALL, IconColor.EMPTY, unit.getUnitType().getResourcePath(), unit.getUnitType().toString());
    }

    private void showUnitButton(BreadCrumbBar<Object> breadCrumbBar, View view, Pane pane, Unit unit) {
        utilsView.addButtonToPane(pane, unit.toString(), (_ -> unitView.show(breadCrumbBar, view, unit)));
    }

    private void showUnitStatus(Pane pane, Unit unit) {
        utilsView.addIconToPane(pane, IconType.SMALL, IconColor.EMPTY, unit.getUnitStatus().getResourcePath(), unit.getUnitStatus().toString());
    }

}
