package stationtocommand.view.missionStructure;

import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.missionLinkStructure.MissionUnitLink;
import stationtocommand.view.mainStructure.UtilsView;

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
