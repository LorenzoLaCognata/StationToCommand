package stationtocommand.view.missionStructure;

import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.missionLinkStructure.MissionDepartmentLink;
import stationtocommand.view.View;
import stationtocommand.view.mainStructure.IconColor;
import stationtocommand.view.mainStructure.IconType;
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

    public void show(BreadCrumbBar<Object> breadCrumbBar, View view, MissionDepartmentLink missionDepartmentLink) {
        View.viewRunnable = () -> show(breadCrumbBar, view, missionDepartmentLink);
        utilsView.addBreadCrumb(breadCrumbBar, missionDepartmentLink);
        view.getNavigationPanel().clear();
        view.getWorldMap().clear();
        showSidebar(breadCrumbBar, view, missionDepartmentLink);
    }

    private void showSidebar(BreadCrumbBar<Object> breadCrumbBar, View view, MissionDepartmentLink missionDepartmentLink) {
        showMissionDepartmentDetails(view, missionDepartmentLink);
        missionStationListView.show(breadCrumbBar, view, missionDepartmentLink);
    }

    private void showMissionDepartmentDetails(View view, MissionDepartmentLink missionDepartmentLink) {
        Pane horizontalTitlePane = utilsView.createHBox(view.getNavigationPanel().getTitlePane());
        utilsView.addIconToPane(horizontalTitlePane, IconType.MEDIUM, IconColor.EMPTY, missionDepartmentLink.getMission().getMissionType().getResourcePath(), missionDepartmentLink.getMission().getMissionType().toString());
        utilsView.addMainTitleLabel(horizontalTitlePane, missionDepartmentLink.getMission().toString());
    }

}
