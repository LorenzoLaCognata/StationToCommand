package stationtocommand.view.unitStructure;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import stationtocommand.model.unitStructure.Unit;
import stationtocommand.view.equipmentStructure.EquipmentListView;
import stationtocommand.view.mainStructure.UtilsView;
import stationtocommand.view.responderStructure.ResponderListView;
import stationtocommand.view.vehicleStructure.VehicleListView;

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
