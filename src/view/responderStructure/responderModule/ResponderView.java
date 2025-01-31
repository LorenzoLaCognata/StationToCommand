package view.responderStructure.responderModule;

import javafx.scene.control.Control;
import javafx.scene.layout.Pane;
import model.responderStructure.responderModule.Responder;
import view.skillStructure.skillListModule.SkillListView;
import view.trainingStructure.trainingListModule.TrainingListView;

import java.util.List;

public class ResponderView {

    private final SkillListView skillListView;
    private final TrainingListView trainingListView;

    public ResponderView(SkillListView skillListView, TrainingListView trainingListView) {
        this.skillListView = skillListView;
        this.trainingListView = trainingListView;
    }

    public void show(Pane pane, List<Control> controls, Responder responder) {
        skillListView.show(pane, controls, responder.getSkills());
        trainingListView.show(pane, controls, responder.getTrainings());
    }


}
