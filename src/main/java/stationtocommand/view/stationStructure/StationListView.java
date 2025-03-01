package stationtocommand.view.stationStructure;

import javafx.scene.image.ImageView;
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

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane pane1, Pane pane2, List<Station> stations) {
        utilsView.addSectionTitleLabel(pane1, "Stations");
        for (Station station : stations) {
            showSidebar(breadCrumbBar, pane1, pane2, station);
            stationView.showMap(pane2, station);
        }
    }

    private void showSidebar(BreadCrumbBar<Object> breadCrumbBar, Pane navigationPanel, Pane worldMap, Station station) {
        Pane labelPane = utilsView.createHBox(navigationPanel);
        showStationIcon(labelPane, station);
        showStationButton(breadCrumbBar, navigationPanel, worldMap, labelPane, station);
        showStationUnitTypes(labelPane, station);
    }

    private void showStationIcon(Pane navigationPanel, Station station) {
        utilsView.addIconToPane(navigationPanel, IconType.SMALL, IconColor.BLANK, utilsView.stationIconPath(station.getDepartment().getDepartmentType()));
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
            ImageView unitImageView = utilsView.addIconToPane(labelPane, IconType.SMALL, IconColor.BLANK, utilsView.unitIconPath(unitType));
            if (station.getUnitManager().getUnits(unitType).isEmpty()) {
                unitImageView.setOpacity(0.2);
            }
        }
    }

}
