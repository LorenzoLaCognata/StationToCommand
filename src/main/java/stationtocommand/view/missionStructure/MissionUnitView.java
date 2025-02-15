package stationtocommand.view.missionStructure;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.missionLinkStructure.MissionStationLink;
import stationtocommand.model.missionLinkStructure.MissionUnitLink;
import stationtocommand.model.missionStructure.Mission;
import stationtocommand.model.responderStructure.Responder;
import stationtocommand.model.responderStructure.ResponderLink;
import stationtocommand.model.unitStructure.Unit;
import stationtocommand.model.vehicleStructure.Vehicle;
import stationtocommand.model.vehicleStructure.VehicleLink;
import stationtocommand.view.mainStructure.UtilsView;

import java.util.List;

public class MissionUnitView {

    private UtilsView utilsView;
    private final MissionResponderListView missionResponderListView;
    private final MissionVehicleListView missionVehicleListView;

    public MissionUnitView(UtilsView utilsView) {
        this.utilsView = utilsView;
        this.missionResponderListView = new MissionResponderListView(utilsView);
        this.missionVehicleListView = new MissionVehicleListView(utilsView);
    }

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane pane, MissionUnitLink missionUnitLink) {
        utilsView.addBreadCrumb(breadCrumbBar, missionUnitLink);
        utilsView.clearPane(pane);
        missionResponderListView.show(breadCrumbBar, pane, missionUnitLink);
        missionVehicleListView.show(breadCrumbBar, pane, missionUnitLink);    }

}
