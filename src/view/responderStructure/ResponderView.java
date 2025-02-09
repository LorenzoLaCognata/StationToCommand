package view.responderStructure;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import model.responderStructure.Responder;
import view.mainStructure.UtilsView;
import view.skillStructure.SkillListView;
import view.trainingStructure.TrainingListView;

import java.util.List;

public class ResponderView {

    private final SkillListView skillListView;
    private final TrainingListView trainingListView;

    public ResponderView(UtilsView utilsView) {
        this.skillListView = new SkillListView(utilsView);
        this.trainingListView = new TrainingListView(utilsView);
    }

    public void show(Pane pane, List<Node> nodes, Responder responder) {
        skillListView.show(pane, nodes, responder.getSkills());
        trainingListView.show(pane, nodes, responder.getTrainings());
    }


}
