package view.organizationStructure.skillStructure.skillModule;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class SkillView {

    public void skillView(Pane pane) {
        Label separator = new Label("----------------------\n");
        pane.getChildren().addAll(separator);
    }

}
