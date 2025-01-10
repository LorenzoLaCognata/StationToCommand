package linkStructure.skillLinkModule;

import skillStructure.skillModule.Skill;

public abstract class SkillLink {

    private final Skill skill;

    public SkillLink(Skill skill) {
        System.out.println("SkillLink initializing");
        this.skill = skill;
        System.out.println("SkillLink initialized successfully");
    }

}