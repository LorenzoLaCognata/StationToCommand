package shiftStructure.shiftModule;

import departmentStructure.departmentModule.Department;
import responderStructure.responderModule.Responder;
import responderStructure.responderModule.ResponderManager;
import shiftStructure.watchModule.Watch;
import shiftStructure.watchModule.WatchManager;
import stationStructure.stationModule.Station;
import unitStructure.unitModule.Unit;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ShiftManager {

    private final List<Shift> shifts = new ArrayList<>();

    public ShiftManager(Department department) {
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

	public void initShifts(Department department, WatchManager watchManager, ResponderManager responderManager) {
		for (Station station : department.getStations()) {
			for (Unit unit : station.getUnits()) {
				for (Responder responder : responderManager.getResponders()) {
					if (responder.getUnitLink().getUnit().equals(unit)) {
						for (Watch watch : watchManager.getWatches()) {
							if (watch.start().getDayOfMonth() == 1 && watch.start().getHour() == 5) {
								addShift(new Shift(responder, unit, watch));
							}
						}
					}
				}
			}
		}
	}

}