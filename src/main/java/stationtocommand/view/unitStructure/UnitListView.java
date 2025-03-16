package stationtocommand.view.unitStructure;

import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.unitStructure.Unit;
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

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane navigationPanel, List<Unit> units) {
        utilsView.addSectionTitleLabel(navigationPanel, "Units");
        for (Unit unit : units) {
            showSidebar(breadCrumbBar, navigationPanel, unit);
        }
    }

    private void showSidebar(BreadCrumbBar<Object> breadCrumbBar, Pane navigationPanel, Unit unit) {
        Pane labelPane = utilsView.createHBox(navigationPanel);
        showUnitIcon(labelPane, unit);
        showUnitButton(breadCrumbBar, navigationPanel, labelPane, unit);
        showUnitStatus(labelPane, unit);
    }

    private void showUnitIcon(Pane labelPane, Unit unit) {
        utilsView.addIconToPane(labelPane, IconType.SMALL, IconColor.EMPTY, unit.getUnitType().getResourcePath(), unit.getUnitType().toString());
    }

    private void showUnitButton(BreadCrumbBar<Object> breadCrumbBar, Pane navigationPanel, Pane labelPane, Unit unit) {
        utilsView.addButtonToPane(labelPane, unit.toString(), (_ -> unitView.show(breadCrumbBar, navigationPanel, unit)));
    }

    private void showUnitStatus(Pane labelPane, Unit unit) {
        utilsView.addIconToPane(labelPane, IconType.SMALL, IconColor.EMPTY, unit.getUnitStatus().getResourcePath(), unit.getUnitStatus().toString());
    }

}
