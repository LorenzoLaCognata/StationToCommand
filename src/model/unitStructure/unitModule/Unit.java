package model.unitStructure.unitModule;

import model.equipmentStructure.equipmentModule.Equipment;
import model.linkStructure.equipmentLinkModule.vehicleLinkModule.EquipmentLink;
import model.linkStructure.personLinkModule.ResponderLink;
import model.linkStructure.vehicleLinkModule.VehicleLink;
import model.responderStructure.responderModule.Responder;
import model.stationStructure.stationModule.Station;
import model.unitStructure.unitLinkModule.UnitEquipmentLink;
import model.unitStructure.unitLinkModule.UnitResponderLink;
import model.unitStructure.unitLinkModule.UnitVehicleLink;
import model.unitStructure.unitTypeModule.UnitType;
import model.vehicleStructure.vehicleModule.Vehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Unit {

    private final Station station;
    private final UnitType unitType;
    private final int number;
    private final List<UnitResponderLink> responderLinks;
    private final List<UnitVehicleLink> vehicleLinks;
    private final List<UnitEquipmentLink> equipmentLinks;

    public Unit(Station station, UnitType unitType, int number) {
        this.station = station;
        this.unitType = unitType;
		this.number = number;
        this.responderLinks = new ArrayList<>();
        this.vehicleLinks = new ArrayList<>();
        this.equipmentLinks = new ArrayList<>();
    }

    @Override
    public String toString() {
        return unitType + " Unit " + number;
    }

    public Station getStation() {
        return station;
    }

    public UnitType getUnitType() {
        return unitType;
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

    public List<Responder> getResponders() {
        return responderLinks.stream()
                .map(ResponderLink::getResponder)
                .collect(Collectors.toList());
    }

    public List<Vehicle> getVehicles() {
        return vehicleLinks.stream()
                .map(VehicleLink::getVehicle)
                .collect(Collectors.toList());
    }

    public List<Equipment> getEquipments() {
        return equipmentLinks.stream()
                .map(EquipmentLink::getEquipment)
                .collect(Collectors.toList());
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