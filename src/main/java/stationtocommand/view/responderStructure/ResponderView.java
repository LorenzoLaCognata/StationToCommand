package stationtocommand.view.responderStructure;

import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.equipmentStructure.Equipment;
import stationtocommand.model.responderStructure.Responder;
import stationtocommand.view.View;
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
        View.viewRunnable = () -> show(breadCrumbBar, pane, responder);
        utilsView.addBreadCrumb(breadCrumbBar, responder);
        utilsView.clearPane(pane);
        showSidebar(breadCrumbBar, pane, responder);
    }

    private void showSidebar(BreadCrumbBar<Object> breadCrumbBar, Pane pane, Responder responder) {
        showResponderDetails(pane, responder);
        skillListView.show(breadCrumbBar, pane, responder.getSkills());
        trainingListView.show(breadCrumbBar, pane, responder.getTrainings());
    }

    private void showResponderDetails(Pane pane, Responder responder) {
        HBox hBox = new HBox(10);
        ImageView imageView = utilsView.mediumIcon(utilsView.responderIconPath(responder));
        utilsView.addNodeToPane(hBox, imageView);
        utilsView.addMainTitleLabel(hBox, responder.toString());
        utilsView.addNodeToPane(pane, hBox);
    }

}
