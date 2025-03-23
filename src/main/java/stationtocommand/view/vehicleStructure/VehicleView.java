package stationtocommand.view.vehicleStructure;

import javafx.scene.layout.Pane;
import stationtocommand.model.vehicleStructure.Vehicle;
import stationtocommand.view.View;
import stationtocommand.view.mainStructure.IconColor;
import stationtocommand.view.mainStructure.IconType;
import stationtocommand.view.mainStructure.UtilsView;

public class VehicleView {

    private final UtilsView utilsView;

    public VehicleView(UtilsView utilsView) {
        this.utilsView = utilsView;
    }

    public void show(View view, Vehicle vehicle) {
        View.viewRunnable = () -> show(view, vehicle);
        utilsView.addBreadCrumb(view.getBreadCrumbBar(), vehicle);
        view.getNavigationPanel().clear();
        showSidebar(view, vehicle);
    }

    private void showSidebar(View view, Vehicle vehicle) {
        showVehicleDetails(view, vehicle);
    }

    private void showVehicleDetails(View view, Vehicle vehicle) {
        Pane horizontalTitlePane = utilsView.createHBox(view.getNavigationPanel().getTitlePane());
        utilsView.addIconToPane(horizontalTitlePane, IconType.MEDIUM, IconColor.EMPTY, vehicle.getVehicleType().getResourcePath(), vehicle.getVehicleType().toString());
        utilsView.addMainTitleLabel(horizontalTitlePane, vehicle.toString());
    }

}
