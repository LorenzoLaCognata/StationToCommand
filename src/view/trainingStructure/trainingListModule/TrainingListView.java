package view.trainingStructure.trainingListModule;

import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import model.trainingStructure.trainingModule.Training;
import view.trainingStructure.trainingModule.TrainingView;
import view.utilsStructure.utilsModule.UtilsView;

import java.util.List;

public class TrainingListView {

    private final UtilsView utilsView;
    private final TrainingView trainingView;

    public TrainingListView(UtilsView utilsView) {
        this.utilsView = utilsView;
        this.trainingView = new TrainingView();
    }

    public void show(Pane pane, List<Control> controls, List<Training> trainings) {
        Label trainingsSeparator = new Label("----------------------\nTrainings");
        pane.getChildren().addAll(trainingsSeparator);

        for (Training training : trainings) {
            Button button = new Button();
            button.setOnAction(_ -> {
                utilsView.setPane(pane, controls, button);
                trainingView.show(pane);
            });
            utilsView.addToSidebar(pane, button, training.toString(), "");
        }
    }

}
