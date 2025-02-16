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

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane pane, List<Training> trainings) {
        utilsView.addHeadingLabel(pane, "Trainings");
        for (Training training : trainings) {
            Button button = new Button(training.toString());
            button.setOnAction(_ -> trainingView.show(breadCrumbBar, pane, training));
            pane.getChildren().add(button);
        }
    }

}
