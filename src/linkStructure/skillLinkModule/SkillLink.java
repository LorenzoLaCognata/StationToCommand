package linkStructure.skillLinkModule;

import skillStructure.skillModule.Skill;

public abstract class SkillLink {

    private final Skill skill;

    public SkillLink(Skill skill) {
        this.skill = skill;
    }

    @Override
    public String toString() {
        return "[SKILL] " + skill.getSkillType().toString();
    }

}