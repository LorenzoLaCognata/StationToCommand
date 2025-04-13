package stationtocommand.model.unitStructure;

import stationtocommand.model.equipmentStructure.Equipment;
import stationtocommand.model.equipmentStructure.EquipmentLink;
import stationtocommand.model.responderStructure.Responder;
import stationtocommand.model.responderStructure.ResponderLink;
import stationtocommand.model.stationStructure.Station;
import stationtocommand.model.unitTypeStructure.UnitType;
import stationtocommand.model.vehicleStructure.Vehicle;
import stationtocommand.model.vehicleStructure.VehicleLink;

import java.util.ArrayList;
import java.util.List;

public abstract class Unit {

    private final Station station;
    private final UnitType unitType;
    private UnitStatus unitStatus;
    private final int number;
    private final List<UnitResponderLink> responderLinks;
    private final List<UnitVehicleLink> vehicleLinks;
    private final List<UnitEquipmentLink> equipmentLinks;

    public Unit(Station station, UnitType unitType, int number) {
        this.station = station;
        this.unitType = unitType;
        this.unitStatus = UnitStatus.AVAILABLE;
		this.number = number;
        this.responderLinks = new ArrayList<>();
        this.vehicleLinks = new ArrayList<>();
        this.equipmentLinks = new ArrayList<>();
    }

    @Override
    public String toString() {
        return unitType + " " + number;
    }

    public Station getStation() {
        return station;
    }

    public UnitType getUnitType() {
        return unitType;
    }

    public UnitStatus getUnitStatus() {
        return unitStatus;
    }

    public int getNumber() {
        return number;
    }

    public List<UnitResponderLink> getResponderLinks() {
        return responderLinks;
    }

    public List<UnitVehicleLink> getVehicleLinks() {
        return vehicleLinks;
    }

    public List<UnitEquipmentLink> getEquipmentLinks() {
        return equipmentLinks;
    }

    public void setUnitStatus(UnitStatus unitStatus) {
        this.unitStatus = unitStatus;
    }

    public List<Responder> getResponders() {
        return responderLinks.stream()
                .map(ResponderLink::getResponder)
                .toList();
    }

    public List<Vehicle> getVehicles() {
        return vehicleLinks.stream()
                .map(VehicleLink::getVehicle)
                .toList();
    }

    public List<Equipment> getEquipments() {
        return equipmentLinks.stream()
                .map(EquipmentLink::getEquipment)
                .toList();
    }

    public void linkResponder(Responder responder) {
        if (responderLinks.stream()
                .noneMatch(item -> item.getResponder().equals(responder))) {
            UnitResponderLink unitResponderLink = new UnitResponderLink(responder);
            responderLinks.add(unitResponderLink);
        }
    }

    public void linkVehicle(Vehicle vehicle) {
        if (vehicleLinks.stream()
                .noneMatch(item -> item.getVehicle().equals(vehicle))) {
            UnitVehicleLink unitVehicleLink = new UnitVehicleLink(vehicle);
            vehicleLinks.add(unitVehicleLink);
        }
    }

    public void linkEquipment(Equipment equipment) {
        if (equipmentLinks.stream()
                .noneMatch(item -> item.getEquipment().equals(equipment))) {
            UnitEquipmentLink unitEquipmentLink = new UnitEquipmentLink(equipment);
            equipmentLinks.add(unitEquipmentLink);
        }
    }

}