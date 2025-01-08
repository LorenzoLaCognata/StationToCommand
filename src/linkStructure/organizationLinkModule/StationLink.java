package linkStructure.organizationLinkModule;

import stationStructure.stationModule.Station;

public abstract class StationLink {

    private final Station station;

    public StationLink(Station station) {
        this.station = station;
    }

    public Station getStation() {
        return station;
    }

}