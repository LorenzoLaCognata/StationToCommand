package stationtocommand.view.departmentStructure;

import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.departmentStructure.Department;
import stationtocommand.model.unitStructure.Unit;
import stationtocommand.model.unitStructure.UnitStatus;
import stationtocommand.model.unitTypeStructure.FireUnitType;
import stationtocommand.model.unitTypeStructure.MedicUnitType;
import stationtocommand.model.unitTypeStructure.PoliceUnitType;
import stationtocommand.model.unitTypeStructure.UnitType;
import stationtocommand.view.View;
import stationtocommand.view.mainStructure.IconColor;
import stationtocommand.view.mainStructure.IconType;
import stationtocommand.view.mainStructure.UtilsView;
import stationtocommand.view.stationStructure.StationListView;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public class DepartmentView {

    private final UtilsView utilsView;
    private final StationListView stationListView;

    public DepartmentView(UtilsView utilsView) {
        this.utilsView = utilsView;
        this.stationListView = new StationListView(utilsView);
    }

    public StationListView getStationListView() {
        return stationListView;
    }

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane navigationPanel, Pane worldMap, Department department) {
        View.viewRunnable = () -> show(breadCrumbBar, navigationPanel, worldMap, department);
        utilsView.addBreadCrumb(breadCrumbBar, department);
        utilsView.clearPane(navigationPanel);
        utilsView.clearPane(worldMap);
        showDepartmentDetails(navigationPanel, department);
        stationListView.show(breadCrumbBar, navigationPanel, worldMap, department.getStations());
    }

    private void showDepartmentDetails(Pane navigationPanel, Department department) {
        Pane labelPane = utilsView.createHBox(navigationPanel);
        utilsView.addIconToPane(navigationPanel, IconType.MEDIUM, IconColor.BLANK, department.getDepartmentType().getResourcePath());
        utilsView.addMainTitleLabel(labelPane, department.toString());
        Map<UnitType, Map<UnitStatus, Long>> unitCounts = department.getStations().stream()
                .flatMap(station -> station.getUnits().stream())
                .collect(Collectors.groupingBy(
                                Unit::getUnitType,
                                Collectors.groupingBy(
                                        Unit::getUnitStatus,
                                        Collectors.counting())
                        )
                );

        switch (department.getDepartmentType()) {
            case FIRE_DEPARTMENT -> {
                addUnitTypeCount(navigationPanel, unitCounts, FireUnitType.FIRE_ENGINE);
                addUnitTypeCount(navigationPanel, unitCounts, FireUnitType.FIRE_TRUCK);
                addUnitTypeCount(navigationPanel, unitCounts, FireUnitType.RESCUE_SQUAD);
            }
            case POLICE_DEPARTMENT -> {
                addUnitTypeCount(navigationPanel, unitCounts, PoliceUnitType.PATROL_UNIT);
                addUnitTypeCount(navigationPanel, unitCounts, PoliceUnitType.DETECTIVE_UNIT);
                addUnitTypeCount(navigationPanel, unitCounts, PoliceUnitType.HOMICIDE_UNIT);
                addUnitTypeCount(navigationPanel, unitCounts, PoliceUnitType.NARCOTICS_UNIT);
                addUnitTypeCount(navigationPanel, unitCounts, PoliceUnitType.VICE_UNIT);
            }
            case MEDIC_DEPARTMENT -> {
                addUnitTypeCount(navigationPanel, unitCounts, MedicUnitType.PRIMARY_CARE_UNIT);
                addUnitTypeCount(navigationPanel, unitCounts, MedicUnitType.CRITICAL_CARE_UNIT);
            }
        }
    }

    private void addUnitTypeCount(Pane navigationPanel, Map<UnitType, Map<UnitStatus, Long>> unitCounts, UnitType unitType) {
        Pane unitCountPane = utilsView.createHBox(navigationPanel);
        Map<UnitStatus, Long> units = unitCounts.getOrDefault(unitType, Collections.emptyMap());
        utilsView.addIconToPane(unitCountPane, IconType.SMALL, IconColor.BLANK, unitType.getResourcePath());
        addUnitTypeStatusCount(unitCountPane, units, UnitStatus.AVAILABLE);
        addUnitTypeStatusCount(unitCountPane, units, UnitStatus.DISPATCHED);
        addUnitTypeStatusCount(unitCountPane, units, UnitStatus.ON_SCENE);
        addUnitTypeStatusCount(unitCountPane, units, UnitStatus.RETURNING);
        addUnitTypeStatusCount(unitCountPane, units, UnitStatus.UNAVAILABLE);
    }

    private void addUnitTypeStatusCount(Pane unitCountPane, Map<UnitStatus, Long> units, UnitStatus unitStatus) {
        String count = " ";
        if (units.getOrDefault(unitStatus, 0L) > 0L ) {
            count = String.valueOf(units.getOrDefault(unitStatus, 0L));
        }
        utilsView.addBodyLabel(unitCountPane, count);
        utilsView.addIconToPane(unitCountPane, IconType.SMALL, IconColor.BLANK, unitStatus.getResourcePath());
    }

}
