package linkStructure.vehicleLinkModule;

import vehicleStructure.vehicleModule.Vehicle;

public abstract class VehicleLink {

    private Vehicle vehicle;

    public VehicleLink(Vehicle vehicle) {
        System.out.println("VehicleLink initializing");
        this.vehicle = vehicle;
        System.out.println("VehicleLink initialized successfully");
    }

}