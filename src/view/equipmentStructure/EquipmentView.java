package view.equipmentStructure;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class EquipmentView {

    public EquipmentView() {
    }

    public void show(Pane pane) {
        Label separator = new Label("----------------------\n");
        pane.getChildren().addAll(separator);
    }

}
