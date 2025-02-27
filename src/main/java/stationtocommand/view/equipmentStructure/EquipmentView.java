package stationtocommand.view.equipmentStructure;

import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.equipmentStructure.Equipment;
import stationtocommand.model.unitStructure.Unit;
import stationtocommand.view.View;
import stationtocommand.view.mainStructure.UtilsView;

public class EquipmentView {

    private final UtilsView utilsView;

    public EquipmentView(UtilsView utilsView) {
        this.utilsView = utilsView;
    }

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane pane, Equipment equipment) {
        View.viewRunnable = () -> show(breadCrumbBar, pane, equipment);
        utilsView.addBreadCrumb(breadCrumbBar, equipment);
        utilsView.clearPane(pane);
        showSidebar(breadCrumbBar, pane, equipment);
    }

    private void showSidebar(BreadCrumbBar<Object> breadCrumbBar, Pane pane, Equipment equipment) {
        showEquipmentDetails(pane, equipment);
    }

    private void showEquipmentDetails(Pane pane, Equipment equipment) {
        HBox hBox = new HBox(10);
        ImageView imageView = utilsView.mediumIcon(utilsView.equipmentIconPath(equipment.getEquipmentType()));
        utilsView.addNodeToPane(hBox, imageView);
        utilsView.addMainTitleLabel(hBox, equipment.toString());
        utilsView.addNodeToPane(pane, hBox);
    }

}