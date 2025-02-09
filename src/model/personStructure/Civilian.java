package model.personStructure;

import model.locationStructure.Location;

public class Civilian extends Person {

    public Civilian(Location location) {
        super(location);
    }

    @Override
    public String toString() {
        return "[CIVILIAN] " + getGender() + " " + getFirstName() + " " + getLastName();
    }

}