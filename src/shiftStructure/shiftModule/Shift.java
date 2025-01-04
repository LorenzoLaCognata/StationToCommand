package shiftStructure.shiftModule;

import unitStructure.unitModule.Unit;

public abstract class Shift {

    // TODO
    //    private Responder responder;
	private Unit unit;
	private Watch watch;

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