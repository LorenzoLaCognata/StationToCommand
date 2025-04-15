package stationtocommand.view.unitStructure;

import javafx.scene.layout.Pane;
import stationtocommand.model.unitStructure.Unit;
import stationtocommand.view.View;
import stationtocommand.view.mainStructure.IconColor;
import stationtocommand.view.mainStructure.IconType;
import stationtocommand.view.mainStructure.UtilsView;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UnitListView {

    private final List<Unit> units;
    private final UtilsView utilsView;
    private final Map<Unit, UnitView> unitViews;

    public UnitListView(List<Unit> units, UtilsView utilsView) {
        this.units = units;
        this.utilsView = utilsView;
        this.unitViews = units.stream()
                            .collect(Collectors.toMap(
                                unit -> unit, unit -> new UnitView(unit, utilsView))
                            );
    }

//    public UnitView getUnitView() {
  //      return unitView;
   // }

    public void show(View view) {
        for (Unit unit : units) {
            showSidebar(view, unit);
        }
    }

    private void showSidebar( View view, Unit unit) {
        Pane horizontalDetailsPane = utilsView.createHBox(view.getNavigationPanel().getDetailsPane());
        showUnitIcon(horizontalDetailsPane, unit);
        showUnitButton(view, horizontalDetailsPane, unit);
        showUnitStatus(horizontalDetailsPane, unit);
    }

    private void showUnitIcon(Pane pane, Unit unit) {
        utilsView.addIconToPane(pane, IconType.SMALL, IconColor.EMPTY, unit.getUnitType());
    }

    private void showUnitButton(View view, Pane pane, Unit unit) {
        utilsView.addButtonToPane(pane, unit.toString(), (_ -> unitViews.get(unit).show(view)));
    }

    private void showUnitStatus(Pane pane, Unit unit) {
        utilsView.addIconToPane(pane, IconType.SMALL, IconColor.EMPTY, unit.getUnitStatus());
    }

}
