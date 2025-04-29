package stationtocommand.model.missionLinkStructure;

import stationtocommand.model.missionStructure.Mission;
import stationtocommand.model.responderStructure.Responder;
import stationtocommand.model.responderStructure.ResponderLink;

public class MissionResponderLink extends ResponderLink {

  private final Mission mission;

  public MissionResponderLink(Mission mission, Responder responder) {
    super(responder);
    this.mission = mission;
  }

  @Override
  public String toString() {
    return this.getResponder().toString();
  }

  public Mission getMission() {
    return mission;
  }
}