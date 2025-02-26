package stationtocommand.view.equipmentStructure;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.equipmentStructure.Equipment;
import stationtocommand.view.mainStructure.UtilsView;

import java.util.List;

public class EquipmentListView {

    private final UtilsView utilsView;
    private final EquipmentView equipmentView;

    public EquipmentListView(UtilsView utilsView) {
        this.utilsView = utilsView;
        this.equipmentView = new EquipmentView(utilsView);
    }

    public EquipmentView getEquipmentView() {
        return equipmentView;
    }

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane pane, List<Equipment> equipments) {
        utilsView.addSectionTitleLabel(pane, "Equipment");
        for (Equipment equipment : equipments) {
            Button button = new Button(equipment.toString());
            button.setOnAction(_ -> equipmentView.show(breadCrumbBar, pane, equipment));
            pane.getChildren().add(button);
        }
    }

}
