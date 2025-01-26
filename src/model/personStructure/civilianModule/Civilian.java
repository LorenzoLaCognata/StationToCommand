package model.personStructure.civilianModule;

import model.locationStructure.locationModule.Location;
import model.personStructure.personModule.Person;

public class Civilian extends Person {

    public Civilian(Location location) {
        super(location);
    }

    @Override
    public String toString() {
        return "[CIVILIAN] " + getGender() + " " + getFirstName() + " " + getLastName();
    }

}