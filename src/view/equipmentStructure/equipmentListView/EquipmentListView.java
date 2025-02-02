package view.equipmentStructure.equipmentListView;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import model.equipmentStructure.equipmentModule.Equipment;
import view.equipmentStructure.equipmentModule.EquipmentView;
import view.utilsStructure.utilsModule.UtilsView;

import java.util.List;

public class EquipmentListView {

    private final UtilsView utilsView;
    private final EquipmentView equipmentView;

    public EquipmentListView(UtilsView utilsView) {
        this.utilsView = utilsView;
        this.equipmentView = new EquipmentView();
    }

    public void show(Pane pane, List<Node> nodes, List<Equipment> equipments) {
        Label equipmentsSeparator = new Label("----------------------\nEquipments");
        pane.getChildren().addAll(equipmentsSeparator);

        for (Equipment equipment : equipments) {
            Button button = new Button();
            button.setOnAction(_ -> {
                utilsView.resetAndAddToPane(pane, nodes, button);
                equipmentView.show(pane);
            });
            String integrity = String.format("%.0f%%", equipment.getIntegrity() * 100);
            String condition = String.format("%.0f%%", equipment.getCondition() * 100);
            utilsView.addToSidebar(pane, button, equipment.toString(), "Integrity: " + integrity + " - Condition: " + condition);
        }
    }

}
