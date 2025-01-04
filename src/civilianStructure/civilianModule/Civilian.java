package civilianStructure.civilianModule;

import locationStructure.locationModule.Location;

public abstract class Civilian {

    private String name;
	private Location location;

    public Civilian() {
        System.out.println("Civilian initializing");
        this.name = "John Doe"; // TODO: random names
		//this.location = new Location(...); // TODO: Location
        System.out.println("Civilian initialized successfully");
    }
	
	public void updateLocation(Location location) {
		// TODO
	}

}