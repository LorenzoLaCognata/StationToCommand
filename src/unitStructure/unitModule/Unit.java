package unitStructure.unitModule;

import linkStructure.personLinkModule.ResponderLink;
import linkStructure.skillLinkModule.SkillLink;
import missionLinkStructure.missionLinkModule.MissionCivilianLink;
import personStructure.civilianModule.Civilian;
import responderStructure.responderLinkModule.ResponderSkillLink;
import responderStructure.responderModule.Responder;
import skillStructure.skillModule.Skill;
import stationStructure.stationModule.Station;
import unitStructure.unitLinkModule.UnitResponderLink;
import unitStructure.unitTypeModule.UnitType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Unit {

    private final Station station;
    private final UnitType unitType;
    private final int number;
    private final List<UnitResponderLink> responderLinks;

    public Unit(Station station, UnitType unitType, int number) {
        this.station = station;
        this.unitType = unitType;
		this.number = number;
        this.responderLinks = new ArrayList<>();
    }

    @Override
    public String toString() {
        return unitType + " Unit " + number;
    }

    public Station getStation() {
        return station;
    }

    public UnitType getUnitType() {
        return unitType;
    }

    public int getNumber() {
        return number;
    }

    public List<UnitResponderLink> getResponderLinks() {
        return responderLinks;
    }

    public List<Responder> getResponders() {
        return responderLinks.stream()
                .map(ResponderLink::getResponder)
                .collect(Collectors.toList());
    }

    public void linkResponder(Responder responder) {
        if (responderLinks.stream()
                .noneMatch(item -> item.getResponder().equals(responder))) {
            UnitResponderLink unitResponderLink = new UnitResponderLink(responder);
            responderLinks.add(unitResponderLink);
        }
    }

}