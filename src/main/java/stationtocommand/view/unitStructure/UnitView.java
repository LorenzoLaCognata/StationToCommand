package stationtocommand.view.unitStructure;

import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.unitStructure.Unit;
import stationtocommand.view.equipmentStructure.EquipmentListView;
import stationtocommand.view.mainStructure.UtilsView;
import stationtocommand.view.responderStructure.ResponderListView;
import stationtocommand.view.vehicleStructure.VehicleListView;

public class UnitView {

    private final UtilsView utilsView;
    private final ResponderListView responderListView;
    private final VehicleListView vehicleListView;
    private final EquipmentListView equipmentListView;

    public UnitView(UtilsView utilsView) {
        this.utilsView = utilsView;
        this.responderListView = new ResponderListView(utilsView);
        this.vehicleListView = new VehicleListView(utilsView);
        this.equipmentListView = new EquipmentListView(utilsView);
    }

    public ResponderListView getResponderListView() {
        return responderListView;
    }

    public VehicleListView getVehicleListView() {
        return vehicleListView;
    }

    public EquipmentListView getEquipmentListView() {
        return equipmentListView;
    }

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane pane, Unit unit) {
        utilsView.addBreadCrumb(breadCrumbBar, unit);
        utilsView.clearPane(pane);
        responderListView.show(breadCrumbBar, pane, unit.getResponders());
        vehicleListView.show(breadCrumbBar, pane, unit.getVehicles());
        equipmentListView.show(breadCrumbBar, pane, unit.getEquipments());
    }

}
