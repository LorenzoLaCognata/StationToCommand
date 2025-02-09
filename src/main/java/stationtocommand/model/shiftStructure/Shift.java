package stationtocommand.model.shiftStructure;

import stationtocommand.model.responderStructure.Responder;
import stationtocommand.model.unitStructure.Unit;
import stationtocommand.model.watchStructure.Watch;

public record Shift(Responder responder, Unit unit, Watch watch) {

    @Override
    public String toString() {
        return "[SHIFT] " + responder + " - " + watch;
    }

}