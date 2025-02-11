package stationtocommand.view.equipmentStructure;

import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.equipmentStructure.Equipment;
import stationtocommand.view.mainStructure.UtilsView;

public class EquipmentView {

    private final UtilsView utilsView;

    public EquipmentView(UtilsView utilsView) {
        this.utilsView = utilsView;
    }

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane pane, Equipment equipment) {
        utilsView.addBreadCrumb(breadCrumbBar, equipment);
        utilsView.clearPane(pane);
    }

}
