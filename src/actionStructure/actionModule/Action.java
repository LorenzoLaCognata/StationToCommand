package actionStructure.actionModule;

import personStructure.civilianModule.Civilian;
import equipmentStructure.equipmentModule.Equipment;
import responderStructure.responderModule.Responder;
import taskStructure.taskModule.Task;
import vehicleStructure.vehicleModule.Vehicle;
import linkStructure.responderLinkModule.ResponderLink;
import linkStructure.taskLinkModule.TaskLink;

public class Action {

	private Vehicle vehicle;
	private Equipment equipment;
	private Civilian civilian;
	private ResponderLink responderLink;
	private TaskLink taskLink;

    public Action(Vehicle vehicle, Equipment equipment, Civilian civilian, Responder responder, Task task) {
        System.out.println("Action initializing");
		this.vehicle = vehicle;
		this.equipment = equipment;
		this.civilian = civilian;
		linkResponder(responder);
		linkTask(task);
        System.out.println("Action initialized successfully");
    }

	public void updateVehicle(Vehicle vehicle) {
		// TODO
	}
	
	public void updateEquipment(Equipment equipment) {
		// TODO
	}
	
	public void updateCivilian(Civilian civilian) {
		// TODO
	}
	
	public void linkResponder(Responder responder) {
		// TODO
	}

	public void linkTask(Task task) {
		// TODO
	}

	
}