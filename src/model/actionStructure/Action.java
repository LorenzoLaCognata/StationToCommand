package model.actionStructure;

import model.equipmentStructure.Equipment;
import model.equipmentStructure.EquipmentLink;
import model.personStructure.Civilian;
import model.personStructure.CivilianLink;
import model.responderStructure.Responder;
import model.responderStructure.ResponderLink;
import model.taskStructure.Task;
import model.taskStructure.TaskLink;
import model.vehicleStructure.Vehicle;
import model.vehicleStructure.VehicleLink;

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