package taskStructure.taskModule;

import linkStructure.missionLinkModule.MissionLink;
import missionStructure.missionModule.Mission;
import responderStructure.responderModule.Responder;
import taskStructure.taskLinkModule.TaskMissionLink;
import taskStructure.taskLinkModule.TaskResponderLink;

import java.util.ArrayList;
import java.util.List;

public class Task {

	private final TaskType taskType;
	private final MissionLink missionLink;
	private final List<TaskResponderLink> responderLinks;

	public Task(TaskType taskType, Mission mission) {
		this.taskType = taskType;
		this.missionLink = new TaskMissionLink(mission);
		this.responderLinks = new ArrayList<>();
	}

	@Override
	public String toString() {
		return "[TASK] " + this.taskType;
	}

	public TaskType getTaskType() {
		return taskType;
	}

	public void linkResponder(Responder responder) {
		if (responderLinks.stream()
				.noneMatch(item -> item.getResponder().equals(responder))) {
			TaskResponderLink taskResponderLink = new TaskResponderLink(responder);
			responderLinks.add(taskResponderLink);
		}
	}

}