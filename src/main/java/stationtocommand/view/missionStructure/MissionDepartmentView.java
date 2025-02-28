package stationtocommand.view.missionStructure;

import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.missionLinkStructure.MissionDepartmentLink;
import stationtocommand.view.View;
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
        View.viewRunnable = () -> show(breadCrumbBar, pane1, pane2, missionDepartmentLink);
        utilsView.addBreadCrumb(breadCrumbBar, missionDepartmentLink);
        utilsView.clearPane(pane1);
        utilsView.clearPane(pane2);
        showSidebar(breadCrumbBar, pane1, pane2, missionDepartmentLink);
    }

    private void showSidebar(BreadCrumbBar<Object> breadCrumbBar, Pane pane1, Pane pane2, MissionDepartmentLink missionDepartmentLink) {
        showMissionDepartmentDetails(pane1, missionDepartmentLink);
        missionStationListView.show(breadCrumbBar, pane1, pane2, missionDepartmentLink);
    }

    private void showMissionDepartmentDetails(Pane pane, MissionDepartmentLink missionDepartmentLink) {
        HBox hBox = new HBox(10);
        ImageView imageView = utilsView.mediumIcon(utilsView.missionIconPath(missionDepartmentLink.getMission().getMissionType()));
        utilsView.addNodeToPane(hBox, imageView);
        utilsView.addMainTitleLabel(hBox, missionDepartmentLink.getMission().toString());
        utilsView.addNodeToPane(pane, hBox);
    }

}
