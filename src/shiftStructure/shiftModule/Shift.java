package shiftStructure.shiftModule;

import unitStructure.unitModule.Unit;

public class Shift {

    // TODO
//	private final Responder responder;
	private final Unit unit;
	private final Watch watch;

// TODO
//    public Shift(Responder responder, Unit unit, Watch watch) {
    public Shift(Unit unit, Watch watch) {
        System.out.println("Shift initializing");
//        this.responder = responder;
		this.unit = unit;
		this.watch = watch;
		System.out.println("Shift initialized successfully");
    }
	
}