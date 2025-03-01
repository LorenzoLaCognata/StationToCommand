package stationtocommand.view.mainStructure;

public enum IconColor {
    BLANK("00ffffff"),
    PERSIAN_RED("#cc3333 "),
    DARK_BLUE("#111184"),
    CRIMSON_RED("#990000"),
    WHITE("#ffffff");

    private final String name;

    IconColor(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
