package missionLinkStructure.missionOrganizationModule;

import linkStructure.organizationLinkModule.UnitLink;
import missionLinkStructure.missionLinkModule.MissionResponderLink;
import missionLinkStructure.missionLinkModule.MissionVehicleLink;
import responderStructure.responderModule.Responder;
import unitStructure.unitModule.Unit;
import vehicleStructure.vehicleModule.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class MissionUnitLink extends UnitLink {

  private final List<MissionResponderLink> responderLinks;
  private final List<MissionVehicleLink> vehicleLinks;

  public MissionUnitLink(Unit unit) {
    super(unit);
    responderLinks = new ArrayList<>();
    vehicleLinks = new ArrayList<>();
  }

  public List<MissionResponderLink> getResponderLinks() {
    return responderLinks;
  }

  public List<MissionVehicleLink> getVehicleLinks() {
    return vehicleLinks;
  }

  public void linkResponder(Responder responder) {
    if (responderLinks.stream()
            .noneMatch(item -> item.getResponder().equals(responder))) {
      MissionResponderLink missionResponderLink = new MissionResponderLink(responder);
      responderLinks.add(missionResponderLink);
    }
  }

  public void linkVehicle(Vehicle vehicle) {
    if (vehicleLinks.stream()
            .noneMatch(item -> item.getVehicle().equals(vehicle))) {
      MissionVehicleLink missionVehicleLink = new MissionVehicleLink(vehicle);
      vehicleLinks.add(missionVehicleLink);
    }
  }

}