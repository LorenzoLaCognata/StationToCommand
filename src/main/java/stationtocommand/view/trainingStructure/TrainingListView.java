package stationtocommand.view.trainingStructure;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.trainingStructure.Training;
import stationtocommand.view.mainStructure.UtilsView;

import java.util.List;

public class TrainingListView {

    private final UtilsView utilsView;
    private final TrainingView trainingView;

    public TrainingListView(UtilsView utilsView) {
        this.utilsView = utilsView;
        this.trainingView = new TrainingView(utilsView);
    }

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane navigationPanel, List<Training> trainings) {
        utilsView.addSectionTitleLabel(navigationPanel, "Trainings");
        for (Training training : trainings) {
            showSidebar(breadCrumbBar, navigationPanel, training);
        }
    }

    private void showSidebar(BreadCrumbBar<Object> breadCrumbBar, Pane navigationPanel, Training training) {
        Pane labelPane = utilsView.createHBox(navigationPanel);
        showTrainingButton(breadCrumbBar, navigationPanel, labelPane, training);
    }

    private void showTrainingButton(BreadCrumbBar<Object> breadCrumbBar, Pane navigationPanel, Pane labelPane, Training training) {
        utilsView.addButtonToPane(labelPane, training.toString(), (_ -> trainingView.show(breadCrumbBar, navigationPanel, training)));
    }

}