package stationtocommand.view.missionStructure;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.departmentStructure.Department;
import stationtocommand.model.missionLinkStructure.MissionDepartmentLink;
import stationtocommand.model.missionStructure.Mission;
import stationtocommand.model.stationStructure.Station;
import stationtocommand.model.stationStructure.StationLink;
import stationtocommand.view.mainStructure.UtilsView;

import java.util.List;

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

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane pane, MissionDepartmentLink missionDepartmentLink) {
        utilsView.addBreadCrumb(breadCrumbBar, missionDepartmentLink);
        utilsView.clearPane(pane);
        missionStationListView.show(breadCrumbBar, pane, missionDepartmentLink);
    }

}
