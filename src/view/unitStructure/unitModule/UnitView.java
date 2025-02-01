package view.unitStructure.unitModule;

import javafx.scene.control.Control;
import javafx.scene.layout.Pane;
import model.unitStructure.unitModule.Unit;
import view.equipmentStructure.equipmentListView.EquipmentListView;
import view.responderStructure.responderListModule.ResponderListView;
import view.utilsStructure.utilsModule.UtilsView;
import view.vehicleStructure.vehicleListModule.VehicleListView;

import java.util.List;

public class UnitView {

    private final ResponderListView responderListView;
    private final VehicleListView vehicleListView;
    private final EquipmentListView equipmentListView;

    public UnitView(UtilsView utilsView) {
        this.responderListView = new ResponderListView(utilsView);
        this.vehicleListView = new VehicleListView(utilsView);
        this.equipmentListView = new EquipmentListView(utilsView);
    }

    public void show(Pane pane, List<Control> controls, Unit unit) {
        responderListView.show(pane, controls, unit.getResponders());
        vehicleListView.show(pane, controls, unit.getVehicles());
        equipmentListView.show(pane, controls, unit.getEquipments());
    }

}
