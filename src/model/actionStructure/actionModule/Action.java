package model.actionStructure.actionModule;

import model.actionStructure.actionLinkModule.*;
import model.equipmentStructure.equipmentModule.Equipment;
import model.linkStructure.equipmentLinkModule.vehicleLinkModule.EquipmentLink;
import model.linkStructure.personLinkModule.CivilianLink;
import model.linkStructure.personLinkModule.ResponderLink;
import model.linkStructure.taskLinkModule.TaskLink;
import model.linkStructure.vehicleLinkModule.VehicleLink;
import model.personStructure.civilianModule.Civilian;
import model.responderStructure.responderModule.Responder;
import model.taskStructure.taskModule.Task;
import model.vehicleStructure.vehicleModule.Vehicle;

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