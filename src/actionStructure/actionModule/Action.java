package actionStructure.actionModule;

import actionStructure.actionLinkModule.ActionResponderLink;
import actionStructure.actionLinkModule.ActionTaskLink;
import personStructure.civilianModule.Civilian;
import equipmentStructure.equipmentModule.Equipment;
import responderStructure.responderModule.Responder;
import taskStructure.taskModule.Task;
import vehicleStructure.vehicleModule.Vehicle;
import linkStructure.responderLinkModule.ResponderLink;
import linkStructure.taskLinkModule.TaskLink;

import java.util.ArrayList;

public class Action {

	private final Vehicle vehicle;
	private final Equipment equipment;
	private final Civilian civilian;
	private final ResponderLink responderLink;
	private final TaskLink taskLink;

    public Action(Vehicle vehicle, Equipment equipment, Civilian civilian, Responder responder, Task task) {
        System.out.println("Action initializing");
		this.vehicle = vehicle;
		this.equipment = equipment;
		this.civilian = civilian;
		this.responderLink = new ActionResponderLink(responder);
		this.taskLink = new ActionTaskLink(task);
	    System.out.println("Action initialized successfully");
    }

}