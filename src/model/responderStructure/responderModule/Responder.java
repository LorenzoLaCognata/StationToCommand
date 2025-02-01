package model.responderStructure.responderModule;

import model.experienceStructure.experienceModule.Experience;
import model.linkStructure.organizationLinkModule.UnitLink;
import model.linkStructure.skillLinkModule.SkillLink;
import model.linkStructure.trainingLinkModule.TrainingLink;
import model.locationStructure.locationModule.Location;
import model.personStructure.personModule.Gender;
import model.personStructure.personModule.Person;
import model.rankStructure.rankModule.Rank;
import model.responderStructure.responderLinkModule.ResponderResponderLink;
import model.responderStructure.responderLinkModule.ResponderSkillLink;
import model.responderStructure.responderLinkModule.ResponderTrainingLink;
import model.responderStructure.responderLinkModule.ResponderUnitLink;
import model.skillStructure.skillModule.Skill;
import model.trainingStructure.trainingModule.Training;
import model.unitStructure.unitModule.Unit;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Responder extends Person {

    private final int id;
    private final boolean isPlayer;
    private Experience experience;
    private Rank rank;
    // TODO: manage ranks 5+ that are not linked to a unit but to the entire department (e.g. create ad Admin unit?!)
    private UnitLink unitLink;
    private final List<ResponderSkillLink> skillLinks;
    private final List<ResponderTrainingLink> trainingLinks;
    private final List<ResponderResponderLink> responderLinks;

    public Responder(String firstName, String lastName, Gender gender, Location location, int id, boolean isPlayer, Experience experience, Rank rank, Unit unit) {
        super(firstName, lastName, gender, location);
        this.id = id;
        this.isPlayer = isPlayer;
        this.experience = experience;
        this.rank = rank;
        this.unitLink = new ResponderUnitLink(unit);
        unit.linkResponder(this);
        this.skillLinks = new ArrayList<>();
        this.trainingLinks = new ArrayList<>();
        this.responderLinks = new ArrayList<>();
    }

    public Responder(Location location, int id, Experience experience, Rank rank, Unit unit) {
        super(location);
        this.id = id;
        this.isPlayer = false;
        this.experience = experience;
        this.rank = rank;
        this.unitLink = new ResponderUnitLink(unit);
        unit.linkResponder(this);
        this.skillLinks = new ArrayList<>();
        this.trainingLinks = new ArrayList<>();
        this.responderLinks = new ArrayList<>();
    }

    @Override
    public String toString() {
        return String.format("%06d", id) + " " + getGender() + " " + getFirstName() + " " + getLastName();
    }

    public int getId() {
        return id;
    }

    public boolean isPlayer() {
        return isPlayer;
    }

    public Experience getExperience() {
        return experience;
    }

    public Rank getRank() {
        return rank;
    }

    public UnitLink getUnitLink() {
        return unitLink;
    }

    public List<ResponderResponderLink> getResponderLinks() {
        return responderLinks;
    }

    public void setExperience(Experience experience) {
        this.experience = experience;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public void linkUnit(Unit unit) {
        this.unitLink = new ResponderUnitLink(unit);
        unit.linkResponder(this);
    }

    public void linkResponder(Responder responder, float relationship) {
        if (responderLinks.stream()
                .noneMatch(item -> item.getResponder().equals(responder))) {
            ResponderResponderLink responderResponderLink = new ResponderResponderLink(responder, relationship);
            responderLinks.add(responderResponderLink);
        }
        else
            responderLinks.stream()
                .filter(item -> item.getResponder().equals(responder))
                .findAny()
                .ifPresent(item -> item.setRelationship(relationship));
    }

    public List<ResponderSkillLink> getSkillLinks() {
        return skillLinks;
    }

    public List<Skill> getSkills() {
        return skillLinks.stream()
                .map(SkillLink::getSkill)
                .toList();
    }

    public List<ResponderTrainingLink> getTrainingLinks() {
        return trainingLinks;
    }

    public List<Training> getTrainings() {
        return trainingLinks.stream()
                .map(TrainingLink::getTraining)
                .toList();
    }

    public void linkSkill(Skill skill) {
        if (skillLinks.stream()
                .noneMatch(item -> item.getSkill().equals(skill))) {
            ResponderSkillLink responderSkillLink = new ResponderSkillLink(skill);
            skillLinks.add(responderSkillLink);
        }
    }

    public void linkTraining(Training training) {
        if (trainingLinks.stream()
                .noneMatch(item -> item.getTraining().equals(training))) {
            ResponderTrainingLink responderTrainingLink = new ResponderTrainingLink(training);
            trainingLinks.add(responderTrainingLink);
        }
    }

}