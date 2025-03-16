package stationtocommand.view.missionStructure;

import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.missionLinkStructure.MissionDepartmentLink;
import stationtocommand.model.missionLinkStructure.MissionStationLink;
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

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane navigationPanel, Pane worldMap, MissionDepartmentLink missionDepartmentLink) {
        utilsView.addSectionTitleLabel(navigationPanel, "Stations");
        for (MissionStationLink missionStationLink : missionDepartmentLink.getStationLinks()) {
            showSidebar(breadCrumbBar, navigationPanel, worldMap, missionStationLink);
        }
    }

    private void showSidebar(BreadCrumbBar<Object> breadCrumbBar, Pane navigationPanel, Pane worldMap, MissionStationLink missionStationLink) {
        Pane labelPane = utilsView.createHBox(navigationPanel);
        showMissionStationIcon(labelPane, missionStationLink);
        showMissionStationButton(breadCrumbBar, navigationPanel, worldMap, labelPane, missionStationLink);
    }

    private void showMissionStationIcon(Pane labelPane, MissionStationLink missionStationLink) {
        utilsView.addIconToPane(labelPane, IconType.SMALL, IconColor.EMPTY, missionStationLink.getStation().getStationType().getResourcePath(), missionStationLink.getStation().getStationType().toString());
    }

    private void showMissionStationButton(BreadCrumbBar<Object> breadCrumbBar, Pane navigationPanel, Pane worldMap, Pane labelPane, MissionStationLink missionStationLink) {
        utilsView.addButtonToPane(labelPane, missionStationLink.getStation().toString(), (_ -> missionStationView.show(breadCrumbBar, navigationPanel, worldMap, missionStationLink)));
    }

}
