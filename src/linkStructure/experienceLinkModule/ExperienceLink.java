package linkStructure.experienceLinkModule;

import experienceStructure.experienceModule.Experience;

public abstract class ExperienceLink {

    private final Experience experience;

    public ExperienceLink(Experience experience) {
        this.experience = experience;
    }

    @Override
    public String toString() {
        return experience.toString();
    }

}