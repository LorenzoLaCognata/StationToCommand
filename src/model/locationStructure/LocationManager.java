package model.locationStructure;

import model.utilsStructure.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LocationManager {

	public static float MIN_LATITUDE = 41.6445f;
	public static float MAX_LATITUDE = 42.0230f;
	public static float MIN_LONGITUDE = -87.9401f;
	public static float MAX_LONGITUDE = -87.5237f;
    private final List<Location> locations = new ArrayList<>();

    public LocationManager() {
    }

	@Override
	public String toString() {
		return locations.stream()
				.map(item -> "\t- " + item)
				.collect(Collectors.joining("\n"));
	}

	public List<Location> getLocations(float latitude, float longitude) {
		return locations.stream()
				.filter(item -> item.latitude() == latitude && item.longitude() == longitude)
				.toList();
	}

	public void addLocation(Location location) {
		this.locations.add(location);
	}

	public Location generateLocation() {
        float randomLatitude = Utils.randomGenerator.nextFloat(MIN_LATITUDE, MAX_LATITUDE);
		float randomLongitude = Utils.randomGenerator.nextFloat(MIN_LONGITUDE, MAX_LONGITUDE);
		Location location  = new Location(randomLatitude, randomLongitude);
		addLocation(location);
		return location;
	}

}