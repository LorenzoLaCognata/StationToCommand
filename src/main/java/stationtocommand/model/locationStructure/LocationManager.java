package stationtocommand.model.locationStructure;

import stationtocommand.model.utilsStructure.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LocationManager {

	public static final float MIN_LATITUDE = 40.083649f;
	public static final float MAX_LATITUDE = 40.130553f;
	public static final float MIN_LONGITUDE = -88.286304f;
	public static final float MAX_LONGITUDE = -88.190303f;
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