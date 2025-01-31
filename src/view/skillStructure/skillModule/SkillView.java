package view.skillStructure.skillModule;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class SkillView {

    public SkillView() {
    }

    public void show(Pane pane) {
        Label separator = new Label("----------------------\n");
        pane.getChildren().addAll(separator);
    }

}
