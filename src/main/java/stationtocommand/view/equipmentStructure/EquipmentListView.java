package stationtocommand.view.equipmentStructure;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
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
            showSidebar(breadCrumbBar, pane, equipment);
        }
    }

    private void showSidebar(BreadCrumbBar<Object> breadCrumbBar, Pane pane, Equipment equipment) {
        HBox hBox = new HBox(10);
        pane.getChildren().add(hBox);

        showEquipmentIcon(equipment, hBox);
        showEquipmentButton(breadCrumbBar, pane, equipment, hBox);
    }

    private void showEquipmentIcon(Equipment equipment, HBox hBox) {
        ImageView imageView = utilsView.smallIcon(utilsView.equipmentIconPath(equipment.getEquipmentType()));
        hBox.getChildren().add(imageView);
    }

    private void showEquipmentButton(BreadCrumbBar<Object> breadCrumbBar, Pane pane, Equipment equipment, HBox hBox) {
        Button button = new Button(equipment.toString());
        button.setOnAction(_ -> equipmentView.show(breadCrumbBar, pane, equipment));
        hBox.getChildren().add(button);
    }

}
