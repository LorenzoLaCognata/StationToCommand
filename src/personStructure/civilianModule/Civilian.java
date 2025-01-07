package personStructure.civilianModule;

import locationStructure.locationModule.Location;
import personStructure.personModule.Gender;
import personStructure.personModule.Person;

public class Civilian extends Person {

    public Civilian(String firstName, String lastName, Gender gender, Location location) {
        super(firstName, lastName, gender, location);
    }

    public Civilian(Location location) {
        super(location);
    }

	public void updateLocation(Location location) {
		// TODO
	}

}