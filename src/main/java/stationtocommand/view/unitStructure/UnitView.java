package stationtocommand.view.unitStructure;

import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
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

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane navigationPanel, Unit unit) {
        View.viewRunnable = () -> show(breadCrumbBar, navigationPanel, unit);
        utilsView.addBreadCrumb(breadCrumbBar, unit);
        utilsView.clearPane(navigationPanel);
        showSidebar(breadCrumbBar, navigationPanel, unit);
    }

    private void showSidebar(BreadCrumbBar<Object> breadCrumbBar, Pane navigationPanel, Unit unit) {
        showUnitDetails(navigationPanel, unit);
        responderListView.show(breadCrumbBar, navigationPanel, unit.getResponders());
        vehicleListView.show(breadCrumbBar, navigationPanel, unit.getVehicles());
    }

    private void showUnitDetails(Pane navigationPanel, Unit unit) {
        Pane labelPane = utilsView.createHBox(navigationPanel);
        utilsView.addIconToPane(navigationPanel, IconType.MEDIUM, IconColor.BLANK, unit.getUnitType().getResourcePath());
        utilsView.addMainTitleLabel(labelPane, unit.toString());
    }

}
