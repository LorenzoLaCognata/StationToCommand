package stationtocommand.view.missionStructure;

import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.missionLinkStructure.MissionUnitLink;
import stationtocommand.view.View;
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

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane pane1, Pane pane2, MissionUnitLink missionUnitLink) {
        View.viewRunnable = () -> show(breadCrumbBar, pane1, pane2, missionUnitLink);
        utilsView.addBreadCrumb(breadCrumbBar, missionUnitLink);
        utilsView.clearPane(pane1);
        utilsView.clearPane(pane2);
        missionResponderListView.show(breadCrumbBar, pane1, pane2, missionUnitLink);
        missionVehicleListView.show(breadCrumbBar, pane1, missionUnitLink);    }

}
