package stationtocommand.view.missionStructure;

import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.missionLinkStructure.MissionDepartmentLink;
import stationtocommand.view.mainStructure.UtilsView;

public class MissionDepartmentView {

    private final UtilsView utilsView;
    private final MissionStationListView missionStationListView;

    public MissionDepartmentView(UtilsView utilsView) {
        this.utilsView = utilsView;
        this.missionStationListView = new MissionStationListView(utilsView);
    }

    public MissionStationListView getMissionStationListView() {
        return missionStationListView;
    }

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane pane1, Pane pane2, MissionDepartmentLink missionDepartmentLink) {
        utilsView.addBreadCrumb(breadCrumbBar, missionDepartmentLink);
        utilsView.clearPane(pane1);
        missionStationListView.show(breadCrumbBar, pane1, pane2, missionDepartmentLink);
    }

}
