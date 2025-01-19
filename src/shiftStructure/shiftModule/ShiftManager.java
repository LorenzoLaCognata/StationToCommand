package shiftStructure.shiftModule;

import responderStructure.responderModule.Responder;
import shiftStructure.watchModule.Watch;
import unitStructure.unitModule.Unit;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ShiftManager {

    private final List<Shift> shifts = new ArrayList<>();

    public ShiftManager() {
        System.out.println("ShiftManager initializing");
        System.out.println("ShiftManager initialized successfully");
    }

	@Override
	public String toString() {
		return shifts.stream()
				.map(item -> "\t- " + item)
				.collect(Collectors.joining("\n"));
	}

	public List<Shift> getShifts(Responder responder) {
		return shifts.stream()
				.filter(item -> item.responder().equals(responder))
				.collect(Collectors.toList());
	}

	public List<Shift> getShifts(Unit unit) {
		return shifts.stream()
				.filter(item -> item.unit().equals(unit))
				.collect(Collectors.toList());
	}

	public List<Shift> getShifts(Watch watch) {
		return shifts.stream()
				.filter(item -> item.watch().equals(watch))
				.collect(Collectors.toList());
	}

	public void addShift(Shift Shift) {
		this.shifts.add(Shift);
	}

}