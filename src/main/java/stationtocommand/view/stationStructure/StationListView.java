package stationtocommand.view.stationStructure;

import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.departmentStructure.DepartmentType;
import stationtocommand.model.stationStructure.Station;
import stationtocommand.model.unitTypeStructure.FireUnitType;
import stationtocommand.model.unitTypeStructure.MedicUnitType;
import stationtocommand.model.unitTypeStructure.PoliceUnitType;
import stationtocommand.model.unitTypeStructure.UnitType;
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

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane navigationPanel, Pane worldMap, List<Station> stations) {
        utilsView.addSectionTitleLabel(navigationPanel, "Stations");
        for (Station station : stations) {
            showSidebar(breadCrumbBar, navigationPanel, worldMap, station);
            stationView.showMap(worldMap, station);
        }
    }

    private void showSidebar(BreadCrumbBar<Object> breadCrumbBar, Pane navigationPanel, Pane worldMap, Station station) {
        Pane labelPane = utilsView.createHBox(navigationPanel);
        showStationIcon(labelPane, station);
        showStationButton(breadCrumbBar, navigationPanel, worldMap, labelPane, station);
        showStationUnitTypes(labelPane, station);
    }

    private void showStationIcon(Pane labelPane, Station station) {
        utilsView.addIconToPane(labelPane, IconType.SMALL, IconColor.EMPTY, station.getStationType().getResourcePath(), "");
    }

    private void showStationButton(BreadCrumbBar<Object> breadCrumbBar, Pane navigationPanel, Pane worldMap, Pane labelPane, Station station) {
        utilsView.addButtonToPane(labelPane, station.toString(), (_ -> stationView.show(breadCrumbBar, navigationPanel, worldMap, station)));
    }

    private void showStationUnitTypes(Pane labelPane, Station station) {
        List<UnitType> unitTypes;

        switch (station.getDepartment().getDepartmentType()) {
            case DepartmentType.FIRE_DEPARTMENT -> unitTypes = List.of(FireUnitType.values());
            case DepartmentType.POLICE_DEPARTMENT -> unitTypes = List.of(PoliceUnitType.values());
            case DepartmentType.MEDIC_DEPARTMENT -> unitTypes = List.of(MedicUnitType.values());
            default -> unitTypes = new ArrayList<>();
        }

        for (UnitType unitType : unitTypes) {
            if (station.getUnitManager().getUnits(unitType).isEmpty()) {
                utilsView.addIconToPane(labelPane, IconType.SMALL_FADED, IconColor.EMPTY, unitType.getResourcePath(), unitType.toString());

            }
            else {
                utilsView.addIconToPane(labelPane, IconType.SMALL, IconColor.EMPTY, unitType.getResourcePath(), unitType.toString());

            }
        }
    }

}
