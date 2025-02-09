package stationtocommand.model.experienceStructure;

public record Experience(int level) {

    @Override
    public String toString() {
        return "[EXPERIENCE] " + level;
    }

}