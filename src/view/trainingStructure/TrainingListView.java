package view.trainingStructure;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import model.trainingStructure.Training;
import view.mainStructure.UtilsView;

import java.util.List;

public class TrainingListView {

    private final UtilsView utilsView;
    private final TrainingView trainingView;

    public TrainingListView(UtilsView utilsView) {
        this.utilsView = utilsView;
        this.trainingView = new TrainingView();
    }

    public void show(Pane pane, List<Node> nodes, List<Training> trainings) {
        Label trainingsSeparator = new Label("----------------------\nTrainings");
        pane.getChildren().addAll(trainingsSeparator);

        for (Training training : trainings) {
            Button button = new Button();
            button.setOnAction(_ -> {
                utilsView.resetAndAddToPane(pane, nodes, button);
                trainingView.show(pane);
            });
            utilsView.addToSidebar(pane, button, training.toString(), "");
        }
    }

}
