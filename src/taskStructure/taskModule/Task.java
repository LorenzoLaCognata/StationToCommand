package taskStructure.taskModule;

import missionStructure.missionModule.Mission;
import responderStructure.responderModule.Responder;
import linkStructure.missionLinkModule.MissionLink;
import linkStructure.responderLinkModule.ResponderLink;

import java.util.List;

public class Task {

	private String name;
	private List<ResponderLink> responderLinks;
	private MissionLink missionLink;

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