package responderStructure.responderModule;

import experienceStructure.experienceModule.Experience;
import linkStructure.organizationLinkModule.UnitLink;
import locationStructure.locationModule.Location;
import personStructure.personModule.Gender;
import personStructure.personModule.Person;
import rankStructure.rankModule.Rank;
import responderStructure.responderLinkModule.ResponderResponderLink;
import responderStructure.responderLinkModule.ResponderUnitLink;
import unitStructure.unitModule.Unit;

import java.util.ArrayList;
import java.util.List;

public class Responder extends Person {

    private final int id;
    private final boolean isPlayer = false;
    private Experience experience;
    private Rank rank;
    // TODO: manage ranks 5+ that are not linked to a unit but to the entire department (e.g. create ad Admin unit?!)
    private UnitLink unitLink;
    private final List<ResponderResponderLink> responderLinks;

    public Responder(String firstName, String lastName, Gender gender, Location location, int id, boolean isPlayer, Experience experience, Rank rank, Unit unit) {
        super(firstName, lastName, gender, location);
        this.id = id;
        this.experience = experience;
        this.rank = rank;
        this.unitLink = new ResponderUnitLink(unit);
        this.responderLinks = new ArrayList<>();
    }

    public Responder(Location location, int id, Experience experience, Rank rank, Unit unit) {
        super(location);
        this.id = id;
        this.experience = experience;
        this.rank = rank;
        this.unitLink = new ResponderUnitLink(unit);
        this.responderLinks = new ArrayList<>();
    }

    @Override
    public String toString() {
        if (this.isPlayer) {
            return "[PLAYER] " + String.format("%06d", id) + " " + getGender() + " " + getFirstName() + " " + getLastName() + " (" + rank + ")";
        }
        else {
            return "[RESPONDER] " + String.format("%06d", id) + " " + getGender() + " " + getFirstName() + " " + getLastName() + " (" + rank + ")";
        }
    }

    public int getId() {
        return id;
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

    public List<ResponderResponderLink> getResponderLinks() {
        return responderLinks;
    }

    public void setExperience(Experience experience) {
        this.experience = experience;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public void linkUnit(Unit unit) {
        this.unitLink = new ResponderUnitLink(unit);
    }

    public void linkResponder(Responder responder, float relationship) {
        if (responderLinks.stream()
                .noneMatch(item -> item.getResponder().equals(responder))) {
            ResponderResponderLink responderResponderLink = new ResponderResponderLink(responder, relationship);
            responderLinks.add(responderResponderLink);
        }
        else
            responderLinks.stream()
                .filter(item -> item.getResponder().equals(responder))
                .findAny()
                .ifPresent(item -> item.setRelationship(relationship));
    }

}