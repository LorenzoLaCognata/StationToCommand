package actionStructure.actionModule;

import actionStructure.actionLinkModule.*;
import equipmentStructure.equipmentModule.Equipment;
import linkStructure.equipmentLinkModule.vehicleLinkModule.EquipmentLink;
import linkStructure.personLinkModule.CivilianLink;
import linkStructure.personLinkModule.ResponderLink;
import linkStructure.taskLinkModule.TaskLink;
import linkStructure.vehicleLinkModule.VehicleLink;
import personStructure.civilianModule.Civilian;
import responderStructure.responderModule.Responder;
import taskStructure.taskModule.Task;
import vehicleStructure.vehicleModule.Vehicle;

public class Action {

	private final ActionType actionType;
	private final ResponderLink responderLink;
	private final VehicleLink vehicleLink;
	private final TaskLink taskLink;
	private final EquipmentLink equipmentLink;
	private final CivilianLink civilianLink;

    public Action(ActionType actionType, Responder responder, Vehicle vehicle, Task task, Equipment equipment, Civilian civilian) {
		this.actionType = actionType;
		this.responderLink = new ActionResponderLink(responder);
		this.vehicleLink = new ActionVehicleLink(vehicle);
		this.taskLink = new ActionTaskLink(task);
		this.equipmentLink = new ActionEquipmentLink(equipment);
		this.civilianLink = new ActionCivilianLink(civilian);
    }

	@Override
	public String toString() {
		return "[ACTION] " + actionType;
	}

	public ActionType getActionType() {
		return actionType;
	}

	public ResponderLink getResponderLink() {
		return responderLink;
	}

}