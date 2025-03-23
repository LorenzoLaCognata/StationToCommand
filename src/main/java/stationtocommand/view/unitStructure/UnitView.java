package stationtocommand.view.unitStructure;

import javafx.scene.layout.Pane;
import stationtocommand.model.unitStructure.Unit;
import stationtocommand.view.View;
import stationtocommand.view.mainStructure.IconColor;
import stationtocommand.view.mainStructure.IconType;
import stationtocommand.view.mainStructure.UtilsView;
import stationtocommand.view.responderStructure.ResponderListView;
import stationtocommand.view.vehicleStructure.VehicleListView;

public class UnitView {

    private final UtilsView utilsView;
    private final ResponderListView responderListView;
    private final VehicleListView vehicleListView;

    public UnitView(UtilsView utilsView) {
        this.utilsView = utilsView;
        this.responderListView = new ResponderListView(utilsView);
        this.vehicleListView = new VehicleListView(utilsView);
    }

    public ResponderListView getResponderListView() {
        return responderListView;
    }

    public VehicleListView getVehicleListView() {
        return vehicleListView;
    }

    public void show(View view, Unit unit) {
        View.viewRunnable = () -> show(view, unit);
        utilsView.addBreadCrumb(view.getBreadCrumbBar(), unit);
        view.getNavigationPanel().clear();
        showSidebar(view, unit);
    }

    private void showSidebar(View view, Unit unit) {
        showUnitDetails(view, unit);
        responderListView.show(view, unit.getResponders());
        vehicleListView.show(view, unit.getVehicles());
    }

    private void showUnitDetails(View view, Unit unit) {
        Pane horizontalTitlePane = utilsView.createHBox(view.getNavigationPanel().getTitlePane());
        utilsView.addIconToPane(horizontalTitlePane, IconType.MEDIUM, IconColor.EMPTY, unit.getUnitType().getResourcePath(), unit.getUnitType().toString());
        utilsView.addMainTitleLabel(horizontalTitlePane, unit.toString());
    }

}
