package stationtocommand.view.missionStructure;

import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.missionLinkStructure.MissionVehicleLink;
import stationtocommand.view.View;
import stationtocommand.view.mainStructure.UtilsView;

public class MissionVehicleView {

    private final UtilsView utilsView;

    public MissionVehicleView(UtilsView utilsView) {
        this.utilsView = utilsView;
    }

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane pane, MissionVehicleLink missionVehicleLink) {
        View.viewRunnable = () -> show(breadCrumbBar, pane, missionVehicleLink);
        utilsView.addBreadCrumb(breadCrumbBar, missionVehicleLink);
        utilsView.clearPane(pane);
    }

}
