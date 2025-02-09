package view.unitStructure;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import model.unitStructure.Unit;
import view.equipmentStructure.EquipmentListView;
import view.mainStructure.UtilsView;
import view.responderStructure.ResponderListView;
import view.vehicleStructure.VehicleListView;

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

    public void show(Pane pane, List<Node> nodes, Unit unit) {
        responderListView.show(pane, nodes, unit.getResponders());
        vehicleListView.show(pane, nodes, unit.getVehicles());
        equipmentListView.show(pane, nodes, unit.getEquipments());
    }

}
