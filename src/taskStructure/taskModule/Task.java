package taskStructure.taskModule;

import linkStructure.missionLinkModule.MissionLink;
import linkStructure.responderLinkModule.ResponderLink;
import missionStructure.missionModule.Mission;
import responderStructure.responderModule.Responder;

import java.util.List;

public class Task {

	private final String name;
	private final List<ResponderLink> responderLinks;
	private final MissionLink missionLink;

	public Task(String name, List<ResponderLink> responderLinks, MissionLink missionLink) {
		System.out.println("Task initializing");
		this.name = name;
		this.responderLinks = responderLinks;
		this.missionLink = missionLink;
		System.out.println("Task initialized successfully");
	}

	public void linkResponder(Responder responder) {
		// TODO
	}

	public void linkMission(Mission mission) {
		// TODO
	}

}