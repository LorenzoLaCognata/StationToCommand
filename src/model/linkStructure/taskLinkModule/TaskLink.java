package model.linkStructure.taskLinkModule;

import model.taskStructure.taskModule.Task;

public abstract class TaskLink {

    private final Task task;

    public TaskLink(Task task) {
        this.task = task;
    }

}