package stationtocommand.view.stationStructure;

import javafx.scene.layout.Pane;
import stationtocommand.model.departmentStructure.DepartmentType;
import stationtocommand.model.stationStructure.Station;
import stationtocommand.model.unitTypeStructure.FireUnitType;
import stationtocommand.model.unitTypeStructure.MedicUnitType;
import stationtocommand.model.unitTypeStructure.PoliceUnitType;
import stationtocommand.model.unitTypeStructure.UnitType;
import stationtocommand.view.View;
import stationtocommand.view.mainStructure.IconColor;
import stationtocommand.view.mainStructure.IconType;
import stationtocommand.view.mainStructure.UtilsView;

import java.util.ArrayList;
import java.util.List;

public class StationListView {

    private boolean isSelected = false;

    private final UtilsView utilsView;
    private final StationView stationView;

    public StationListView(UtilsView utilsView) {
        this.utilsView = utilsView;
        this.stationView = new StationView(utilsView);
    }

    public StationView getStationView() {
        return stationView;
    }

    public void show(View view, List<Station> stations) {
        utilsView.addSeparatorToPane(view.getNavigationPanel().getDetailsPane());
        for (Station station : stations) {
            showSidebar(view, station);
            stationView.showMap(view, station);
        }
    }

    private void showSidebar(View view, Station station) {
        Pane horizontalDetailsPane = utilsView.createHBox(view.getNavigationPanel().getDetailsPane());
        showStationIcon(horizontalDetailsPane, station);
        showStationButton(view, horizontalDetailsPane, station);
        showStationUnitTypes(horizontalDetailsPane, station);
    }

    private void showStationIcon(Pane pane, Station station) {
        utilsView.addIconToPane(pane, IconType.SMALL, IconColor.EMPTY, station.getStationType().getResourcePath(), "");
    }

    private void showStationButton(View view, Pane pane, Station station) {
        utilsView.addButtonToPane(pane, station.toString(), (_ -> stationView.show(view, station)));
    }

    private void showStationUnitTypes(Pane pane, Station station) {
        List<UnitType> unitTypes;

        switch (station.getDepartment().getDepartmentType()) {
            case DepartmentType.FIRE_DEPARTMENT -> unitTypes = List.of(FireUnitType.values());
            case DepartmentType.POLICE_DEPARTMENT -> unitTypes = List.of(PoliceUnitType.values());
            case DepartmentType.MEDIC_DEPARTMENT -> unitTypes = List.of(MedicUnitType.values());
            default -> unitTypes = new ArrayList<>();
        }

        for (UnitType unitType : unitTypes) {
            if (station.getUnitManager().getUnits(unitType).isEmpty()) {
                utilsView.addIconToPane(pane, IconType.SMALL_FADED, IconColor.EMPTY, unitType.getResourcePath(), unitType.toString());

            }
            else {
                utilsView.addIconToPane(pane, IconType.SMALL, IconColor.EMPTY, unitType.getResourcePath(), unitType.toString());

            }
        }
    }

}
