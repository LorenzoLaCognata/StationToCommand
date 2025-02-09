package stationtocommand.view.responderStructure;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import stationtocommand.model.responderStructure.Responder;
import stationtocommand.view.mainStructure.UtilsView;
import stationtocommand.view.skillStructure.SkillListView;
import stationtocommand.view.trainingStructure.TrainingListView;

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
