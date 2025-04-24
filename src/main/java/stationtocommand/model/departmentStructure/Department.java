package stationtocommand.model.departmentStructure;

import stationtocommand.model.personStructure.AppearanceType;
import stationtocommand.model.rankStructure.RankManager;
import stationtocommand.model.responderStructure.Responder;
import stationtocommand.model.shiftStructure.ShiftManager;
import stationtocommand.model.stationStructure.Station;
import stationtocommand.model.stationStructure.StationManager;
import stationtocommand.model.unitTypeStructure.FireUnitType;
import stationtocommand.model.unitTypeStructure.MedicUnitType;
import stationtocommand.model.unitTypeStructure.PoliceUnitType;
import stationtocommand.model.unitTypeStructure.UnitType;
import stationtocommand.model.vehicleStructure.FireVehicleType;
import stationtocommand.model.vehicleStructure.MedicVehicleType;
import stationtocommand.model.vehicleStructure.PoliceVehicleType;
import stationtocommand.model.vehicleStructure.VehicleType;

import java.util.Comparator;
import java.util.List;

public class Department implements Comparable<Department> {

    private final DepartmentType departmentType;
    private final StationManager stationManager;
    private final RankManager rankManager;
    private final ShiftManager shiftManager;

    public Department(DepartmentType departmentType) {
        this.departmentType = departmentType;
        this.stationManager = new StationManager(this);
        this.rankManager = new RankManager(this);
        this.shiftManager = new ShiftManager(this);
    }

    @Override
    public String toString() {
        return departmentType + " Department";
    }

    @Override
    public int compareTo(Department other) {
        return Integer.compare(this.departmentType.ordinal(), other.getDepartmentType().ordinal());
    }

    public DepartmentType getDepartmentType() {
        return departmentType;
    }

    public StationManager getStationManager() {
        return stationManager;
    }

    public List<Station> getStations() {
        return stationManager.getStations();
    }

    public RankManager getRankManager() {
        return rankManager;
    }

    public ShiftManager getShiftManager() {
        return shiftManager;
    }

    public UnitType defaultUnitType() {
        return switch(departmentType) {
            case FIRE_DEPARTMENT -> FireUnitType.values()[0];
            case POLICE_DEPARTMENT -> PoliceUnitType.values()[0];
            case MEDIC_DEPARTMENT -> MedicUnitType.values()[0];
        };
    }

    public VehicleType defaultVehicleType() {
        return switch(departmentType) {
            case FIRE_DEPARTMENT -> FireVehicleType.values()[0];
            case POLICE_DEPARTMENT -> PoliceVehicleType.values()[0];
            case MEDIC_DEPARTMENT -> MedicVehicleType.values()[0];
        };
    }

    public AppearanceType defaultAppearanceType() {
        Responder chiefResponder = getStations().stream()
                .flatMap(station -> station.getUnits().stream())
                .flatMap(unit -> unit.getResponders().stream())
                .min(Comparator.naturalOrder())
                .orElse(null);
        return chiefResponder != null ? chiefResponder.getAppearanceType() : AppearanceType.MALE_01;
    }

    public List<UnitType> unitTypesList() {
        return switch (departmentType) {
            case DepartmentType.FIRE_DEPARTMENT -> List.of(FireUnitType.values());
            case DepartmentType.POLICE_DEPARTMENT -> List.of(PoliceUnitType.values());
            case DepartmentType.MEDIC_DEPARTMENT -> List.of(MedicUnitType.values());
        };
    }

}