package view.missionStructure;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class MissionResponderView {

    public MissionResponderView() {
    }

    public void show(Pane pane) {
        Label separator = new Label("----------------------\n");
        pane.getChildren().addAll(separator);
    }

}
