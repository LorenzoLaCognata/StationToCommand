package responderStructure.responderModule;

import experienceStructure.experienceModule.Experience;
import locationStructure.locationModule.Location;
import rankStructure.rankModule.Rank;
import unitStructure.unitModule.Unit;
import linkStructure.organizationLinkModule.UnitLink;
import linkStructure.responderLinkModule.ResponderLink;

import java.util.ArrayList;
import java.util.List;

public class Responder {
    private boolean isPlayer;
    private String name;
    private Location location;
    private Experience experience;
    private Rank rank;
    private List<UnitLink> unitLinks;
    private List<ResponderLink> responderLinks;

    public Responder(String name, Location location, Experience experience, Rank rank, List<UnitLink> unitLinks) {
        System.out.println("Responder initializing");
        this.name = name;
        this.location = location;
        this.experience = experience;
        this.rank = rank;
        this.unitLinks = unitLinks;
        this.responderLinks = new ArrayList<>();
        System.out.println("Responder initialized successfully");
    }

    public void linkUnit(Unit unit) {
        // TODO
    }

    public void linkResponder(Responder responder, float relationship) {
        // TODO
    }

}