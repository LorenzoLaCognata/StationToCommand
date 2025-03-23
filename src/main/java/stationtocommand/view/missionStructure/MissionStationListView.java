package stationtocommand.view.missionStructure;

import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.missionLinkStructure.MissionDepartmentLink;
import stationtocommand.model.missionLinkStructure.MissionStationLink;
import stationtocommand.view.View;
import stationtocommand.view.mainStructure.IconColor;
import stationtocommand.view.mainStructure.IconType;
import stationtocommand.view.mainStructure.UtilsView;

public class MissionStationListView {

    private final UtilsView utilsView;
    private final MissionStationView missionStationView;

    public MissionStationListView(UtilsView utilsView) {
        this.utilsView = utilsView;
        this.missionStationView = new MissionStationView(utilsView);
    }

    public MissionStationView getMissionStationView() {
        return missionStationView;
    }

    public void show(BreadCrumbBar<Object> breadCrumbBar, View view, MissionDepartmentLink missionDepartmentLink) {
        utilsView.addSectionTitleLabel(view.getNavigationPanel().getDetailsPane(), "Stations");
        for (MissionStationLink missionStationLink : missionDepartmentLink.getStationLinks()) {
            showSidebar(breadCrumbBar, view, missionStationLink);
        }
    }

    private void showSidebar(BreadCrumbBar<Object> breadCrumbBar, View view, MissionStationLink missionStationLink) {
        Pane horizontalDetailsPane = utilsView.createHBox(view.getNavigationPanel().getDetailsPane());
        showMissionStationIcon(horizontalDetailsPane, missionStationLink);
        showMissionStationButton(breadCrumbBar, view, horizontalDetailsPane, missionStationLink);
    }

    private void showMissionStationIcon(Pane pane, MissionStationLink missionStationLink) {
        utilsView.addIconToPane(pane, IconType.SMALL, IconColor.EMPTY, missionStationLink.getStation().getStationType().getResourcePath(), "");
    }

    private void showMissionStationButton(BreadCrumbBar<Object> breadCrumbBar, View view, Pane pane, MissionStationLink missionStationLink) {
        utilsView.addButtonToPane(pane, missionStationLink.getStation().toString(), (_ -> missionStationView.show(breadCrumbBar, view, missionStationLink)));
    }

}
