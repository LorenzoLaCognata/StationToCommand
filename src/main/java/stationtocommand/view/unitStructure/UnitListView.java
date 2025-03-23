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
        Pane horizontalEntryPane = utilsView.createHBox(view.getNavigationPanel().getDetailsPane());
        showUnitIcon(horizontalEntryPane, unit);
        // TODO: restore and fix
//        showUnitButton(breadCrumbBar, view, horizontalEntryPane, unit);
        showUnitStatus(horizontalEntryPane, unit);
    }

    private void showUnitIcon(Pane horizontalEntryPane, Unit unit) {
        utilsView.addIconToPane(horizontalEntryPane, IconType.SMALL, IconColor.EMPTY, unit.getUnitType().getResourcePath(), unit.getUnitType().toString());
    }

    private void showUnitButton(BreadCrumbBar<Object> breadCrumbBar, Pane navigationPanel, Pane horizontalEntryPane, Unit unit) {
        utilsView.addButtonToPane(horizontalEntryPane, unit.toString(), (_ -> unitView.show(breadCrumbBar, navigationPanel, unit)));
    }

    private void showUnitStatus(Pane horizontalEntryPane, Unit unit) {
        utilsView.addIconToPane(horizontalEntryPane, IconType.SMALL, IconColor.EMPTY, unit.getUnitStatus().getResourcePath(), unit.getUnitStatus().toString());
    }

}
