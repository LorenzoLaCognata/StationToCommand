package model.shiftStructure.shiftModule;

import model.responderStructure.responderModule.Responder;
import model.shiftStructure.watchModule.Watch;
import model.unitStructure.unitModule.Unit;

public record Shift(Responder responder, Unit unit, Watch watch) {

    @Override
    public String toString() {
        return "[SHIFT] " + responder + " - " + watch;
    }

}