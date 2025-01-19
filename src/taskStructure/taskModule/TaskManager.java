package taskStructure.taskModule;

import missionStructure.missionModule.Mission;
import missionStructure.missionModule.MissionType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TaskManager {

	private final List<Task> tasks = new ArrayList<>();

	@Override
	public String toString() {
		return tasks.stream()
				.map(item -> "\t- " + item)
				.collect(Collectors.joining("\n"));
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public Task getTask(TaskType taskType) {
		return tasks.stream()
				.filter(item -> item.getTaskType().equals(taskType))
				.findAny()
				.orElse(null);
	}

	public void addTask(Task task) {
		this.tasks.add(task);
	}

	public void addTasks(List<Task> tasks) {
		this.tasks.addAll(tasks);
	}

	public List<Task> generateTasks(Mission mission) {
		List<Task> missionTasks = new ArrayList<>();
		switch (mission.getMissionType()) {
			case MissionType.STRUCTURE_FIRE -> {
				missionTasks.add(new Task(TaskType.EXTINGUISH_FIRE, mission));
				missionTasks.add(new Task(TaskType.ASSESS_FIRE_DAMAGE, mission));
			}
			default -> {
				missionTasks.add(new Task(TaskType.SECURE_PERIMETER, mission));
				missionTasks.add(new Task(TaskType.PROVIDE_SUPPORT_TO_VICTIM, mission));
			}
		}

		addTasks(missionTasks);
		return missionTasks;
	}

}