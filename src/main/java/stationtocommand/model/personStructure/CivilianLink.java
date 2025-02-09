package stationtocommand.model.personStructure;

public abstract class CivilianLink {

    private final Civilian civilian;

    public CivilianLink(Civilian civilian) {
        this.civilian = civilian;
    }

    public Civilian getCivilian() {
        return civilian;
    }

}