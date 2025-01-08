package missionLinkStructure.missionOrganizationModule;

import missionStructure.missionObjectiveModule.MissionObjective;
import missionStructure.missionObjectiveModule.MissionObjectiveLink;
import responderStructure.responderModule.Responder;
import unitStructure.unitModule.Unit;
import vehicleStructure.vehicleModule.Vehicle;
import linkStructure.organizationLinkModule.UnitLink;
import linkStructure.responderLinkModule.ResponderLink;
import linkStructure.vehicleLinkModule.VehicleLink;

import java.util.ArrayList;
import java.util.List;

public class MissionUnitLink extends UnitLink {

  private final List<ResponderLink> responderLinks;
  private final List<VehicleLink> vehicleLinks;
  private final List<MissionObjectiveLink> missionObjectiveLink;

  public MissionUnitLink(Unit unit) {
    super(unit);
    responderLinks = new ArrayList<>();
    vehicleLinks = new ArrayList<>();
    missionObjectiveLink = new ArrayList<>();
  }

  public void linkResponder(Responder responder) {
    // TODO
  }

  public void linkVehicle(Vehicle vehicle) {
    // TODO
  }

  public void linkMissionObjective(MissionObjective missionObjective) {
    // TODO
  }

}