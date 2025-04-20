package stationtocommand.model.missionLinkStructure;

import stationtocommand.model.missionStructure.Mission;
import stationtocommand.model.responderStructure.Responder;
import stationtocommand.model.responderStructure.ResponderLink;

public class MissionResponderLink extends ResponderLink implements Comparable<MissionResponderLink>  {

  private final Mission mission;

  public MissionResponderLink(Mission mission, Responder responder) {
    super(responder);
    this.mission = mission;
  }

  @Override
  public String toString() {
    return this.getResponder().toString();
  }

  @Override
  public int compareTo(MissionResponderLink other) {
    int result = this.mission.compareTo(other.getMission());
    if (result != 0) {
      return result;
    }
    result = this.getResponder().compareTo(other.getResponder());
    if (result != 0) {
      return result;
    }
    return Integer.compare(System.identityHashCode(this), System.identityHashCode(other));
  }

  public Mission getMission() {
    return mission;
  }
}