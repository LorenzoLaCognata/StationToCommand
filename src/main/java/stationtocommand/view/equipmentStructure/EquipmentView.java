package stationtocommand.view.equipmentStructure;

import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.equipmentStructure.Equipment;
import stationtocommand.view.View;
import stationtocommand.view.mainStructure.IconColor;
import stationtocommand.view.mainStructure.IconType;
import stationtocommand.view.mainStructure.UtilsView;

public class EquipmentView {

    private final UtilsView utilsView;

    public EquipmentView(UtilsView utilsView) {
        this.utilsView = utilsView;
    }

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane navigationPanel, Equipment equipment) {
        View.viewRunnable = () -> show(breadCrumbBar, navigationPanel, equipment);
        utilsView.addBreadCrumb(breadCrumbBar, equipment);
        utilsView.clearPane(navigationPanel);
        showSidebar(breadCrumbBar, navigationPanel, equipment);
    }

    private void showSidebar(BreadCrumbBar<Object> breadCrumbBar, Pane navigationPanel, Equipment equipment) {
        showEquipmentDetails(navigationPanel, equipment);
    }

    private void showEquipmentDetails(Pane navigationPanel, Equipment equipment) {
        Pane labelPane = utilsView.createHBox(navigationPanel);
        utilsView.addIconToPane(navigationPanel, IconType.MEDIUM, IconColor.BLANK, utilsView.equipmentIconPath(equipment.getEquipmentType()));
        utilsView.addMainTitleLabel(labelPane, equipment.toString());
    }

}