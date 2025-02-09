package view.trainingStructure;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class TrainingView {

    public TrainingView() {
    }

    public void show(Pane pane) {
        Label separator = new Label("----------------------\n");
        pane.getChildren().addAll(separator);
    }

}
