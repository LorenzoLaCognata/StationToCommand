package shiftStructure.shiftModule;

import java.util.Date;

public class Watch {

    private final Date start;
    private final Date end;

    public Watch(Date start, Date end) {
        System.out.println("Watch initializing");
        this.start = start;
		this.end = end;
        System.out.println("Watch initialized successfully");
    }
	
}