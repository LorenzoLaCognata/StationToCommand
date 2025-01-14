package experienceStructure.experienceModule;

public class Experience {

	private final int level;

    public Experience(int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return Integer.toString(level);
    }

    public int getLevel() {
        return level;
    }

}