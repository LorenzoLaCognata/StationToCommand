package locationStructure.locationModule;

public class Location {

    private float latitude;
	private float longitude;

    public Location(float latitude, float longitude) {
        System.out.println("Location initializing");
        this.latitude = latitude;
        this.longitude = longitude;
        System.out.println("Location initialized successfully");
    }
	
}