package model.shiftStructure;

import model.responderStructure.Responder;
import model.unitStructure.Unit;
import model.watchStructure.Watch;

public record Shift(Responder responder, Unit unit, Watch watch) {

    @Override
    public String toString() {
        return "[SHIFT] " + responder + " - " + watch;
    }

}