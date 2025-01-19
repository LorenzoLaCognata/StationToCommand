package shiftStructure.shiftModule;

import responderStructure.responderModule.Responder;
import shiftStructure.watchModule.Watch;
import unitStructure.unitModule.Unit;

public record Shift(Responder responder, Unit unit, Watch watch) {

}