package stationtocommand.model.personStructure;

import stationtocommand.model.locationStructure.Location;

public class Civilian extends Person {

    public Civilian(Location location) {
        super(location);
    }

    @Override
    public String toString() {
        return "[CIVILIAN] " + getGender() + " " + getFirstName() + " " + getLastName();
    }

}