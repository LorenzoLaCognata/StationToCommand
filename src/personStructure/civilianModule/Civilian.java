package personStructure.civilianModule;

import locationStructure.locationModule.Location;
import personStructure.personModule.Person;

public class Civilian extends Person {

    public Civilian(Location location) {
        super(location);
    }

    @Override
    public String toString() {
        return "[CIVILIAN] " + getGender() + " " + getFirstName() + " " + getLastName();
    }

}