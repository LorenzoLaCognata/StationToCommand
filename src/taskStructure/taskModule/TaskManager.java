package taskStructure.taskModule;

import java.util.ArrayList;
import java.util.List;

public class TaskManager {

	private final List<Task> tasks = new ArrayList<>();

	public Task getTask(String name) {
		// TODO
		return tasks.get(0);
	}

	public void addTask(Task task) {
		this.tasks.add(task);
	}

}