package stationtocommand.model.missionStructure;

import stationtocommand.model.departmentStructure.DepartmentManager;
import stationtocommand.model.departmentStructure.DepartmentType;
import stationtocommand.model.locationStructure.Location;
import stationtocommand.model.locationStructure.LocationManager;
import stationtocommand.model.missionLinkStructure.MissionDepartmentLink;
import stationtocommand.model.stationStructure.Station;
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
                case STRUCTURE_FIRE, VEHICLE_FIRE, WATER_RESCUE, ANIMAL_RESCUE ->
                        departmentTypes.add(DepartmentType.FIRE_DEPARTMENT);
                case BURGLARY_IN_PROGRESS, DOMESTIC_DISTURBANCE, SUSPECT_APPREHENSION, CROWD_CONTROL ->
                        departmentTypes.add(DepartmentType.POLICE_DEPARTMENT);
                case MEDICAL_EMERGENCY, TRAUMA_RESPONSE, CARDIAC_ARREST, MATERNITY_EMERGENCY ->
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

    public void dispatchMissionToStation(MissionDepartmentLink missionDepartmentLink) {

        if (missionDepartmentLink.getStationLinks().isEmpty()) {
            // TODO: select appropriately a station to dispatch the mission to
            Station station = missionDepartmentLink.getDepartment().getStations().getFirst();
            missionDepartmentLink.linkStation(station);
        }
    }

}