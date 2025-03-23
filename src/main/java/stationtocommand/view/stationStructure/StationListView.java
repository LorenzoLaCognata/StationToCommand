package stationtocommand.view.stationStructure;

import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
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

    public void show(BreadCrumbBar<Object> breadCrumbBar, View view, Pane worldMap, List<Station> stations) {
        utilsView.addSectionTitleLabel(view.getNavigationPanel().getDetailsPane(), "Stations");
        for (Station station : stations) {
            showSidebar(breadCrumbBar, view, worldMap, station);
            stationView.showMap(worldMap, station);
        }
    }

    private void showSidebar(BreadCrumbBar<Object> breadCrumbBar, View view, Pane worldMap, Station station) {
        Pane horizontalEntryPane = utilsView.createHBox(view.getNavigationPanel().getDetailsPane());
        showStationIcon(horizontalEntryPane, station);
        showStationButton(breadCrumbBar, view, worldMap, horizontalEntryPane, station);
        showStationUnitTypes(horizontalEntryPane, station);
    }

    private void showStationIcon(Pane horizontalEntryPane, Station station) {
        utilsView.addIconToPane(horizontalEntryPane, IconType.SMALL, IconColor.EMPTY, station.getStationType().getResourcePath(), "");
    }

    private void showStationButton(BreadCrumbBar<Object> breadCrumbBar, View view, Pane worldMap, Pane horizontalEntryPane, Station station) {
        utilsView.addButtonToPane(horizontalEntryPane, station.toString(), (_ -> stationView.show(breadCrumbBar, view, worldMap, station)));
    }

    private void showStationUnitTypes(Pane horizontalEntryPane, Station station) {
        List<UnitType> unitTypes;

        switch (station.getDepartment().getDepartmentType()) {
            case DepartmentType.FIRE_DEPARTMENT -> unitTypes = List.of(FireUnitType.values());
            case DepartmentType.POLICE_DEPARTMENT -> unitTypes = List.of(PoliceUnitType.values());
            case DepartmentType.MEDIC_DEPARTMENT -> unitTypes = List.of(MedicUnitType.values());
            default -> unitTypes = new ArrayList<>();
        }

        for (UnitType unitType : unitTypes) {
            if (station.getUnitManager().getUnits(unitType).isEmpty()) {
                utilsView.addIconToPane(horizontalEntryPane, IconType.SMALL_FADED, IconColor.EMPTY, unitType.getResourcePath(), unitType.toString());

            }
            else {
                utilsView.addIconToPane(horizontalEntryPane, IconType.SMALL, IconColor.EMPTY, unitType.getResourcePath(), unitType.toString());

            }
        }
    }

}
