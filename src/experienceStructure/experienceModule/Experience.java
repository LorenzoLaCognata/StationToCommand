package experienceStructure.experienceModule;

public record Experience(int level) {

    @Override
    public String toString() {
        return "[EXPERIENCE] " + Integer.toString(level);
    }

}