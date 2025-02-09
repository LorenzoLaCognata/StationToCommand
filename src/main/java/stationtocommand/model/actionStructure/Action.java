package stationtocommand.model.actionStructure;

import stationtocommand.model.equipmentStructure.Equipment;
import stationtocommand.model.equipmentStructure.EquipmentLink;
import stationtocommand.model.personStructure.Civilian;
import stationtocommand.model.personStructure.CivilianLink;
import stationtocommand.model.responderStructure.Responder;
import stationtocommand.model.responderStructure.ResponderLink;
import stationtocommand.model.taskStructure.Task;
import stationtocommand.model.taskStructure.TaskLink;
import stationtocommand.model.vehicleStructure.Vehicle;
import stationtocommand.model.vehicleStructure.VehicleLink;

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