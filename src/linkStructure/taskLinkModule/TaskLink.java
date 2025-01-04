package linkStructure.taskLinkModule;

import taskStructure.taskModule.Task;

public abstract class TaskLink {

    private Task task;

    public TaskLink(Task task) {
        System.out.println("TaskLink initializing");
        this.task = task;
        System.out.println("TaskLink initialized successfully");
    }

}