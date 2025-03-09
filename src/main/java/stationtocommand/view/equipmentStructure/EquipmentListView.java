package stationtocommand.view.equipmentStructure;

import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.equipmentStructure.Equipment;
import stationtocommand.view.mainStructure.IconColor;
import stationtocommand.view.mainStructure.IconType;
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

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane navigationPanel, List<Equipment> equipments) {
        utilsView.addSectionTitleLabel(navigationPanel, "Equipment");
        for (Equipment equipment : equipments) {
            showSidebar(breadCrumbBar, navigationPanel, equipment);
        }
    }

    private void showSidebar(BreadCrumbBar<Object> breadCrumbBar, Pane navigationPanel, Equipment equipment) {
        Pane labelPane = utilsView.createHBox(navigationPanel);
        showEquipmentIcon(labelPane, equipment);
        showEquipmentButton(breadCrumbBar, navigationPanel, labelPane, equipment);
    }

    private void showEquipmentIcon(Pane labelPane, Equipment equipment) {
        utilsView.addIconToPane(labelPane, IconType.SMALL, IconColor.BLANK, equipment.getEquipmentType().getResourcePath());
    }

    private void showEquipmentButton(BreadCrumbBar<Object> breadCrumbBar, Pane navigationPanel, Pane labelPane, Equipment equipment) {
        utilsView.addButtonToPane(labelPane, equipment.toString(), (_ -> equipmentView.show(breadCrumbBar, navigationPanel, equipment)));
    }

}
