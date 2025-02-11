package stationtocommand.view.vehicleStructure;

import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.vehicleStructure.Vehicle;
import stationtocommand.view.mainStructure.UtilsView;

public class VehicleView {

    private final UtilsView utilsView;

    public VehicleView(UtilsView utilsView) {
        this.utilsView = utilsView;
    }

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane pane, Vehicle vehicle) {
        utilsView.addBreadCrumb(breadCrumbBar, vehicle);
        utilsView.clearPane(pane);
    }

}
