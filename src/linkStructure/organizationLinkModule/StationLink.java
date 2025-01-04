package linkStructure.organizationLinkModule;

import stationStructure.stationModule.Station;

public abstract class StationLink {

    private Station station;

    public StationLink(Station station) {
        System.out.println("StationLink initializing");
        this.station = station;
		System.out.println("StationLink initialized successfully");
    }
	
}