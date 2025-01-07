package responderStructure.responderModule;

import experienceStructure.experienceModule.Experience;
import locationStructure.locationModule.Location;
import personStructure.personModule.Gender;
import personStructure.personModule.Person;
import rankStructure.rankModule.Rank;
import responderStructure.responderLinkModule.ResponderUnitLink;
import unitStructure.unitModule.Unit;
import linkStructure.organizationLinkModule.UnitLink;
import linkStructure.responderLinkModule.ResponderLink;

import java.util.List;

public class Responder extends Person {
    private boolean isPlayer = false;
    private Experience experience;
    private Rank rank;
    // TODO: manage ranks 5+ that are not linked to a unit but to the entire department (e.g. create ad Admin unit?!)
    private final UnitLink unitLink;
    private final List<ResponderLink> responderLinks;

    public Responder(String firstName, String lastName, Gender gender, Location location, Experience experience, Rank rank, Unit unit, List<ResponderLink> responderLinks) {
        super(firstName, lastName, gender, location);
        this.experience = experience;
        this.rank = rank;
        this.unitLink = new ResponderUnitLink(unit);
        this.responderLinks = responderLinks;
    }

    public Responder(Location location, Experience experience, Rank rank, Unit unit, List<ResponderLink> responderLinks) {
        super(location);
        this.experience = experience;
        this.rank = rank;
        this.unitLink = new ResponderUnitLink(unit);
        this.responderLinks = responderLinks;
    }

    @Override
    public String toString() {
        if (this.isPlayer) {
            return "[PLAYER] " + getGender() + " " + getFirstName() + " " + getLastName() + " (" + rank + ")";
        }
        else {
            return "[RESPONDER] " + getGender() + " " + getFirstName() + " " + getLastName() + " (" + rank + ")";
        }
    }

    public boolean isPlayer() {
        return isPlayer;
    }

    public Experience getExperience() {
        return experience;
    }

    public Rank getRank() {
        return rank;
    }

    public UnitLink getUnitLink() {
        return unitLink;
    }

    public void setPlayer(boolean player) {
        isPlayer = player;
    }

    public void setExperience(Experience experience) {
        this.experience = experience;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public void setUnitLink(Unit unit) {
        unitLink.setUnit(unit);
    }

    // TODO find if a link with that responder exists and update it, if not create it
    public void linkResponder(Responder responder, float relationship) {
    }

}