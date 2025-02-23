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
import stationtocommand.model.unitStructure.UnitStatus;
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

    public Mission generateMission(LocationManager locationManager, MissionType missionType) {
        Mission mission = new Mission(missionType, locationManager.generateLocation());
        addMission(mission);
        return mission;
    }

    public Mission generateMission(LocationManager locationManager) {
        MissionType randomMissionType = Utils.getRandomEnumValue(MissionType.class);
        return generateMission(locationManager, randomMissionType);
    }

    public void dispatchMissionToDepartment(Mission mission) {

        System.out.println("Start dispatch to department for " + mission);

        if (mission.getDepartmentLinks().isEmpty()) {
            List<DepartmentType> departmentTypes = requiredDepartmentTypes(mission.getMissionType());

            for (DepartmentType departmentType : departmentTypes) {
                mission.linkDepartment(departmentManager.getDepartment(departmentType));
            }
        }

        System.out.println("Finish dispatch to department for " + mission);

    }

    public void dispatchMissionToUnit(MissionDepartmentLink missionDepartmentLink) {

        System.out.println("Start dispatch to unit for " + missionDepartmentLink.getMission());

        if (missionDepartmentLink.getStationLinks().isEmpty()) {

            System.out.println("a " + missionDepartmentLink.getStationLinks().size());
            Mission mission = missionDepartmentLink.getMission();
            MissionType missionType = mission.getMissionType();

            Department department = missionDepartmentLink.getDepartment();
            DepartmentType departmentType = department.getDepartmentType();

            List<UnitType> unitTypes = requiredUnitTypes(missionType, departmentType);

            StationManager stationManager = department.getStationManager();
            List<Station> stationsMatchingAllUnitTypes = stationManager.availableStationsMatchingAllUnitTypes(unitTypes);

            if (!stationsMatchingAllUnitTypes.isEmpty()) {

                System.out.println("b " + stationsMatchingAllUnitTypes.size());
                // TODO: select the closest/appropriate station to dispatch the mission to
                int randomStation = Utils.randomGenerator.nextInt(stationsMatchingAllUnitTypes.size());
                System.out.println("randomStation = " + randomStation);
                Station station = stationsMatchingAllUnitTypes.get(randomStation);
                missionDepartmentLink.linkStation(station);

                MissionStationLink missionStationLink = missionDepartmentLink.getStationLink(station);
                UnitManager unitManager = station.getUnitManager();

                for (UnitType unitType : unitTypes) {

                    System.out.println("c " + unitType);
                    List<Unit> units = unitManager.getAvailableUnits(unitType);

                    if (!units.isEmpty()) {
                        Unit unit = units.getFirst();
                        unit.setUnitStatus(UnitStatus.DISPATCHED);
                        missionStationLink.getMission().linkUnit(unit);
                        System.out.println("Mission " + mission + " dispatched to " + unit + " which has now status " + unit.getUnitStatus());
                    }
                }
            }

            else {

                System.out.println("d");
                for (UnitType unitType : unitTypes) {
                    List<Station> stationsMatchingUnitType = stationManager.availableStationsMatchingUnitType(unitType);
                    if (!stationsMatchingUnitType.isEmpty()) {

                        System.out.println("e");
                        // TODO: select the closest/appropriate station to dispatch the mission to
                        int randomStation = Utils.randomGenerator.nextInt(stationsMatchingUnitType.size());
                        Station station = stationsMatchingUnitType.get(randomStation);
                        missionDepartmentLink.linkStation(station);

                        MissionStationLink missionStationLink = missionDepartmentLink.getStationLink(station);
                        List<Unit> units = station.getUnitManager().getAvailableUnits(unitType);

                        if (!units.isEmpty()) {
                            Unit unit = units.getFirst();
                            unit.setUnitStatus(UnitStatus.DISPATCHED);
                            missionStationLink.getMission().linkUnit(unit);
                            System.out.println("Mission " + mission + " dispatched to " + unit + " which has now status " + unit.getUnitStatus());
                        }

                    }
                }
            }
        }

        System.out.println("Finish dispatch to unit for " + missionDepartmentLink.getMission());

    }

    public List<DepartmentType> requiredDepartmentTypes(MissionType missionType) {

        List<DepartmentType> requiredDepartmentTypes = new ArrayList<>();

        if (!requiredUnitTypes(missionType, DepartmentType.FIRE_DEPARTMENT).isEmpty()) {
            requiredDepartmentTypes.add(DepartmentType.FIRE_DEPARTMENT);
        }

        if (!requiredUnitTypes(missionType, DepartmentType.POLICE_DEPARTMENT).isEmpty()) {
            requiredDepartmentTypes.add(DepartmentType.POLICE_DEPARTMENT);
        }

        if (!requiredUnitTypes(missionType, DepartmentType.MEDIC_DEPARTMENT).isEmpty()) {
            requiredDepartmentTypes.add(DepartmentType.MEDIC_DEPARTMENT);
        }

        return requiredDepartmentTypes;
    }

    public List<UnitType> requiredUnitTypes(MissionType missionType, DepartmentType departmentType) {

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
                    case MEDICAL_EMERGENCY, TRAUMA_RESPONSE, TRAFFIC_INCIDENT -> requiredUnitTypes.add(MedicUnitType.PRIMARY_CARE_UNIT);
                    case CARDIAC_ARREST, COLLAPSE_RESCUE, POISONING_OVERDOSE -> requiredUnitTypes.add(MedicUnitType.CRITICAL_CARE_UNIT);
                }
            }
        }
        return requiredUnitTypes;
    }

}