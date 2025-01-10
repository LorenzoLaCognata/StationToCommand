package shiftStructure.shiftModule;

import responderStructure.responderModule.Responder;
import unitStructure.unitModule.Unit;

import java.util.ArrayList;
import java.util.List;

public class ShiftManager {

    private final List<Shift> shifts = new ArrayList<>();

    public ShiftManager() {
        System.out.println("ShiftManager initializing");
        System.out.println("ShiftManager initialized successfully");
    }

	// TODO	
	public List<Shift> getShift(Responder responder) {
		// TODO
		return shifts;
	}

	public List<Shift> getShift(Unit unit) {
		// TODO
		return shifts;
	}

	public void addShift(Shift Shift) {
		this.shifts.add(Shift);
	}

}