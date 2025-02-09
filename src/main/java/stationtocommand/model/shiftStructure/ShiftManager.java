package stationtocommand.model.shiftStructure;

import stationtocommand.model.departmentStructure.Department;
import stationtocommand.model.responderStructure.Responder;
import stationtocommand.model.responderStructure.ResponderManager;
import stationtocommand.model.stationStructure.Station;
import stationtocommand.model.unitStructure.Unit;
import stationtocommand.model.watchStructure.Watch;
import stationtocommand.model.watchStructure.WatchManager;

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
				.toList();
	}

	public List<Shift> getShifts(Unit unit) {
		return shifts.stream()
				.filter(item -> item.unit().equals(unit))
				.toList();
	}

	public List<Shift> getShifts(Watch watch) {
		return shifts.stream()
				.filter(item -> item.watch().equals(watch))
				.toList();
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