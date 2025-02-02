package view.responderStructure.responderModule;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import model.responderStructure.responderModule.Responder;
import view.skillStructure.skillListModule.SkillListView;
import view.trainingStructure.trainingListModule.TrainingListView;
import view.utilsStructure.utilsModule.UtilsView;

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
