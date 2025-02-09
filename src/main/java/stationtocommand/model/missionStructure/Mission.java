package stationtocommand.model.missionStructure;

import stationtocommand.model.departmentStructure.Department;
import stationtocommand.model.locationStructure.Location;
import stationtocommand.model.missionLinkStructure.MissionCivilianLink;
import stationtocommand.model.missionLinkStructure.MissionDepartmentLink;
import stationtocommand.model.missionLinkStructure.MissionObjectiveLink;
import stationtocommand.model.objectiveStructure.Objective;
import stationtocommand.model.personStructure.Civilian;
import stationtocommand.model.responderStructure.Responder;
import stationtocommand.model.stationStructure.Station;
import stationtocommand.model.unitStructure.Unit;
import stationtocommand.model.vehicleStructure.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class Mission {

    private final MissionType missionType;
    private final Location location;
    private final List<MissionDepartmentLink> departmentLinks;
    private final List<MissionCivilianLink> civilianLinks;
    private final List<MissionObjectiveLink> objectiveLinks;

    public Mission(MissionType missionType, Location location) {
        this.missionType = missionType;
        this.location = location;
        this.departmentLinks = new ArrayList<>();
        this.civilianLinks = new ArrayList<>();
        this.objectiveLinks = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "[MISSION] " + this.missionType;
    }

    public MissionType getMissionType() {
        return missionType;
    }

    public Location getLocation() {
        return location;
    }

    public List<MissionDepartmentLink> getDepartmentLinks() {
        return departmentLinks;
    }

    public List<MissionCivilianLink> getCivilianLinks() {
        return civilianLinks;
    }

    public List<MissionObjectiveLink> getObjectiveLinks() {
        return objectiveLinks;
    }

    public void linkDepartment(Department department) {
        if (departmentLinks.stream()
                .noneMatch(item -> item.getDepartment().equals(department))) {
            MissionDepartmentLink missionDepartmentLink = new MissionDepartmentLink(department);
            departmentLinks.add(missionDepartmentLink);
        }
    }

    public void linkStation(Station station) {
        this.departmentLinks.stream()
            .filter(item -> item.getDepartment().equals(station.getDepartment()))
            .findAny().ifPresent(missionDepartmentLink -> missionDepartmentLink.linkStation(station));

    }

    public void linkUnit(Unit unit) {
        this.departmentLinks.stream()
            .filter(item -> item.getDepartment().equals(unit.getStation().getDepartment()))
            .findAny().flatMap(missionDepartmentLink -> missionDepartmentLink.getStationLinks().stream()
                .filter(item -> item.getStation().equals(unit.getStation()))
                .findAny()).ifPresent(missionStationLink -> missionStationLink.linkUnit(unit));

    }

    public void linkCivilian(Civilian civilian) {
        if (civilianLinks.stream()
                .noneMatch(item -> item.getCivilian().equals(civilian))) {
            MissionCivilianLink missionCivilianLink = new MissionCivilianLink(civilian);
            civilianLinks.add(missionCivilianLink);
        }
    }

    public void linkResponder(Responder responder) {
        this.departmentLinks.stream()
            .filter(item -> item.getDepartment().equals(responder.getUnitLink().getUnit().getStation().getDepartment()))
            .findAny().flatMap(missionDepartmentLink -> missionDepartmentLink.getStationLinks().stream()
                .filter(item -> item.getStation().equals(responder.getUnitLink().getUnit().getStation()))
                .findAny()).flatMap(missionStationLink -> missionStationLink.getUnitLinks().stream()
                    .filter(item -> item.getUnit().equals(responder.getUnitLink().getUnit()))
                    .findAny()).ifPresent(missionUnitLink -> missionUnitLink.linkResponder(responder));

    }

    public void linkVehicle(Vehicle vehicle) {
        this.departmentLinks.stream()
            .filter(item -> item.getDepartment().equals(vehicle.getUnitLink().getUnit().getStation().getDepartment()))
            .findAny().flatMap(missionDepartmentLink -> missionDepartmentLink.getStationLinks().stream()
                .filter(item -> item.getStation().equals(vehicle.getUnitLink().getUnit().getStation()))
                .findAny()).flatMap(missionStationLink -> missionStationLink.getUnitLinks().stream()
                    .filter(item -> item.getUnit().equals(vehicle.getUnitLink().getUnit()))
                    .findAny()).ifPresent(missionUnitLink -> missionUnitLink.linkVehicle(vehicle));

    }

    public void linkObjective(Objective objective) {
        if (objectiveLinks.stream()
                .noneMatch(item -> item.getObjective().equals(objective))) {
            MissionObjectiveLink missionObjectiveLink = new MissionObjectiveLink(objective);
            objectiveLinks.add(missionObjectiveLink);
        }
    }

}