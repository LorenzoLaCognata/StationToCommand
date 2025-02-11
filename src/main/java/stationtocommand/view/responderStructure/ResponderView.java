package stationtocommand.view.responderStructure;

import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.responderStructure.Responder;
import stationtocommand.view.mainStructure.UtilsView;
import stationtocommand.view.skillStructure.SkillListView;
import stationtocommand.view.trainingStructure.TrainingListView;

public class ResponderView {

    private final UtilsView utilsView;
    private final SkillListView skillListView;
    private final TrainingListView trainingListView;

    public ResponderView(UtilsView utilsView) {
        this.utilsView = utilsView;
        this.skillListView = new SkillListView(utilsView);
        this.trainingListView = new TrainingListView(utilsView);
    }

    public SkillListView getSkillListView() {
        return skillListView;
    }

    public TrainingListView getTrainingListView() {
        return trainingListView;
    }

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane pane, Responder responder) {
        utilsView.addBreadCrumb(breadCrumbBar, responder);
        utilsView.clearPane(pane);
        skillListView.show(breadCrumbBar, pane, responder.getSkills());
        trainingListView.show(breadCrumbBar, pane, responder.getTrainings());
    }

}
