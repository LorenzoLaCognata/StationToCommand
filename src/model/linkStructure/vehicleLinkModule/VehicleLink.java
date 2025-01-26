package model.linkStructure.vehicleLinkModule;

import model.vehicleStructure.vehicleModule.Vehicle;

public abstract class VehicleLink {

    private final Vehicle vehicle;

    public VehicleLink(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

}