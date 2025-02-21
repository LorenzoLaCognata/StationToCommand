package stationtocommand.model.missionStructure;

import stationtocommand.model.departmentStructure.Department;
import stationtocommand.model.departmentStructure.DepartmentManager;
import stationtocommand.model.departmentStructure.DepartmentType;
import stationtocommand.model.locationStructure.Location;
import stationtocommand.model.locationStructure.LocationManager;
import stationtocommand.model.missionLinkStructure.MissionDepartmentLink;
import stationtocommand.model.missionLinkStructure.MissionStationLink;
import stationtocommand.model.stationStructure.Station;
import stationtocommand.model.stationStructure.StationManager;
import stationtocommand.model.unitStructure.Unit;
import stationtocommand.model.unitStructure.UnitManager;
import stationtocommand.model.unitTypeStructure.FireUnitType;
import stationtocommand.model.unitTypeStructure.MedicUnitType;
import stationtocommand.model.unitTypeStructure.PoliceUnitType;
import stationtocommand.model.unitTypeStructure.UnitType;
import stationtocommand.model.utilsStructure.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MissionManager {

    private final DepartmentManager departmentManager;
    private final List<Mission> missions = new ArrayList<>();

    public MissionManager(DepartmentManager departmentManager) {
        this.departmentManager = departmentManager;
    }

    @Override
    public String toString() {
        return missions.stream()
                .map(item -> "\t- " + item)
                .collect(Collectors.joining("\n"));
    }

    public List<Mission> getMissions() {
        return missions;
    }

    public List<Mission> getMissions(MissionType missionType, Location location) {
        return missions.stream()
                .filter(item -> item.getMissionType().equals(missionType) && item.getLocation().equals(location))
                .toList();
    }

    public void addMission(Mission mission) {
        this.missions.add(mission);
    }

    public Mission generateMission(LocationManager locationManager) {
        MissionType randomMissionType = Utils.getRandomEnumValue(MissionType.class);
        Mission mission = new Mission(randomMissionType, locationManager.generateLocation());
        addMission(mission);
        return mission;
    }

    public void dispatchMissionToDepartment(Mission mission) {

        if (mission.getDepartmentLinks().isEmpty()) {
            List<DepartmentType> departmentTypes = new ArrayList<>();
            switch (mission.getMissionType()) {
                case STRUCTURE_FIRE, VEHICLE_FIRE, WATER_RESCUE ->
                        departmentTypes.add(DepartmentType.FIRE_DEPARTMENT);
                case BURGLARY, ASSAULT, DISTURBANCE, HOMICIDE, DRUG_CRIME, VICE_CRIME, CROWD_CONTROL ->
                        departmentTypes.add(DepartmentType.POLICE_DEPARTMENT);
                case MEDICAL_EMERGENCY, TRAUMA_RESPONSE, CARDIAC_ARREST ->
                        departmentTypes.add(DepartmentType.MEDIC_DEPARTMENT);
                case COLLAPSE_RESCUE -> {
                    departmentTypes.add(DepartmentType.FIRE_DEPARTMENT);
                    departmentTypes.add(DepartmentType.MEDIC_DEPARTMENT);
                }
                case TRAFFIC_INCIDENT -> {
                    departmentTypes.add(DepartmentType.FIRE_DEPARTMENT);
                    departmentTypes.add(DepartmentType.POLICE_DEPARTMENT);
                }
                case POISONING_OVERDOSE -> {
                    departmentTypes.add(DepartmentType.POLICE_DEPARTMENT);
                    departmentTypes.add(DepartmentType.MEDIC_DEPARTMENT);
                }
            }

            for (DepartmentType departmentType : departmentTypes) {
                mission.linkDepartment(departmentManager.getDepartment(departmentType));
            }
        }
    }

    private static List<UnitType> requiredUnitTypes(MissionType missionType, DepartmentType departmentType) {

        List<UnitType> requiredUnitTypes = new ArrayList<>();

        switch (departmentType) {
            case FIRE_DEPARTMENT -> {
                switch (missionType) {
                    case STRUCTURE_FIRE -> {
                        requiredUnitTypes.add(FireUnitType.FIRE_ENGINE);
                        requiredUnitTypes.add(FireUnitType.FIRE_TRUCK);
                        requiredUnitTypes.add(FireUnitType.RESCUE_SQUAD);
                    }
                    case VEHICLE_FIRE -> requiredUnitTypes.add(FireUnitType.FIRE_ENGINE);
                    case WATER_RESCUE -> requiredUnitTypes.add(FireUnitType.RESCUE_SQUAD);
                    case COLLAPSE_RESCUE -> {
                        requiredUnitTypes.add(FireUnitType.FIRE_TRUCK);
                        requiredUnitTypes.add(FireUnitType.RESCUE_SQUAD);
                    }
                    case TRAFFIC_INCIDENT -> {
                        requiredUnitTypes.add(FireUnitType.FIRE_ENGINE);
                        requiredUnitTypes.add(FireUnitType.RESCUE_SQUAD);
                    }
                }
            }
            case POLICE_DEPARTMENT -> {
                switch (missionType) {
                    case BURGLARY, ASSAULT -> {
                        requiredUnitTypes.add(PoliceUnitType.PATROL_UNIT);
                        requiredUnitTypes.add(PoliceUnitType.DETECTIVE_UNIT);
                    }
                    case DISTURBANCE, CROWD_CONTROL, TRAFFIC_INCIDENT  -> requiredUnitTypes.add(PoliceUnitType.PATROL_UNIT);
                    case HOMICIDE -> {
                        requiredUnitTypes.add(PoliceUnitType.PATROL_UNIT);
                        requiredUnitTypes.add(PoliceUnitType.HOMICIDE_UNIT);
                    }
                    case DRUG_CRIME, POISONING_OVERDOSE  -> {
                        requiredUnitTypes.add(PoliceUnitType.PATROL_UNIT);
                        requiredUnitTypes.add(PoliceUnitType.NARCOTICS_UNIT);
                    }
                    case VICE_CRIME -> {
                        requiredUnitTypes.add(PoliceUnitType.PATROL_UNIT);
                        requiredUnitTypes.add(PoliceUnitType.VICE_UNIT);
                    }
                }
            }
            case MEDIC_DEPARTMENT -> {
                switch (missionType) {
                    case MEDICAL_EMERGENCY, TRAUMA_RESPONSE -> requiredUnitTypes.add(MedicUnitType.PRIMARY_CARE_UNIT);
                    case CARDIAC_ARREST, COLLAPSE_RESCUE, POISONING_OVERDOSE -> requiredUnitTypes.add(MedicUnitType.CRITICAL_CARE_UNIT);
                }
            }
        }
        return requiredUnitTypes;
    }

    public void dispatchMissionToUnit(MissionDepartmentLink missionDepartmentLink) {

        if (missionDepartmentLink.getStationLinks().isEmpty()) {
            Mission mission = missionDepartmentLink.getMission();
            MissionType missionType = mission.getMissionType();

            Department department = missionDepartmentLink.getDepartment();
            DepartmentType departmentType = department.getDepartmentType();

            List<UnitType> unitTypes = requiredUnitTypes(missionType, departmentType);

            StationManager stationManager = department.getStationManager();
            List<Station> stationsMatchingAllUnitTypes = stationManager.stationsMatchingAllUnitTypes(unitTypes);

            if (!stationsMatchingAllUnitTypes.isEmpty()) {
                // TODO: select the closest/appropriate station to dispatch the mission to
                int randomStation = Utils.randomGenerator.nextInt(stationsMatchingAllUnitTypes.size());
                Station station = stationsMatchingAllUnitTypes.get(randomStation);
                missionDepartmentLink.linkStation(station);

                MissionStationLink missionStationLink = missionDepartmentLink.getStationLink(station);
                UnitManager unitManager = station.getUnitManager();

                for (UnitType unitType : unitTypes) {
                    Unit unit = unitManager.getUnits(unitType).getFirst();
                    if (unit != null) {
                        missionStationLink.linkUnit(unit);
                    }
                }
            }

            else {
                for (UnitType unitType : unitTypes) {
                    List<Station> stationsMatchingUnitType = stationManager.stationsMatchingUnitType(unitType);
                    if (!stationsMatchingUnitType.isEmpty()) {
                        // TODO: select the closest/appropriate station to dispatch the mission to
                        int randomStation = Utils.randomGenerator.nextInt(stationsMatchingUnitType.size());
                        Station station = stationsMatchingUnitType.get(randomStation);
                        missionDepartmentLink.linkStation(station);

                        MissionStationLink missionStationLink = missionDepartmentLink.getStationLink(station);

                        Unit unit = station.getUnitManager().getUnits(unitType).getFirst();
                        if (unit != null) {
                            missionStationLink.linkUnit(unit);
                        }

                    }
                }
            }
        }

    }

}