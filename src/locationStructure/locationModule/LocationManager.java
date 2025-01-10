package locationStructure.locationModule;

import utilsStructure.utilsModule.Utils;

import java.util.ArrayList;
import java.util.List;

public class LocationManager {

    private final List<Location> locations = new ArrayList<>();

    public LocationManager() {
    }
	
	public Location getLocation(float latitude, float longitude) {
		return locations.stream()
				.filter(item -> item.latitude() == latitude && item.longitude() == longitude)
				.findFirst()
				.orElse(null);
	}

	public void addLocation(Location location) {
		this.locations.add(location);
	}

	public Location generateLocation() {
        float MIN_LATITUDE = 41.6445f;
		float MAX_LATITUDE = 42.0230f;
		float MIN_LONGITUDE = -87.9401f;
		float MAX_LONGITUDE = -87.5237f;
        float randomLatitude = Utils.randomGenerator.nextFloat(MIN_LATITUDE, MAX_LATITUDE);
		float randomLongitude = Utils.randomGenerator.nextFloat(MIN_LONGITUDE, MAX_LONGITUDE);
		Location location  = new Location(randomLatitude, randomLongitude);
		addLocation(location);
		return location;
	}

}