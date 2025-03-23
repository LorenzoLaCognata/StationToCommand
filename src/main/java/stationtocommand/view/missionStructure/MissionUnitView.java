package stationtocommand.view.missionStructure;

import javafx.scene.layout.Pane;
import stationtocommand.model.missionLinkStructure.MissionUnitLink;
import stationtocommand.view.View;
import stationtocommand.view.mainStructure.IconColor;
import stationtocommand.view.mainStructure.IconType;
import stationtocommand.view.mainStructure.UtilsView;

public class MissionUnitView {

    private final UtilsView utilsView;
    private final MissionResponderListView missionResponderListView;
    private final MissionVehicleListView missionVehicleListView;

    public MissionUnitView(UtilsView utilsView) {
        this.utilsView = utilsView;
        this.missionResponderListView = new MissionResponderListView(utilsView);
        this.missionVehicleListView = new MissionVehicleListView(utilsView);
    }

    public void show(View view, MissionUnitLink missionUnitLink) {
        View.viewRunnable = () -> show(view, missionUnitLink);
        utilsView.addBreadCrumb(view.getBreadCrumbBar(), missionUnitLink);
        view.getNavigationPanel().clear();
        view.getWorldMap().clear();
        showMissionUnitDetails(view, missionUnitLink);
        missionResponderListView.show(view, missionUnitLink);
        missionVehicleListView.show(view, missionUnitLink);
    }

    private void showMissionUnitDetails(View view, MissionUnitLink missionUnitLink) {
        Pane horizontalTitlePane = utilsView.createHBox(view.getNavigationPanel().getTitlePane());
        utilsView.addIconToPane(horizontalTitlePane, IconType.MEDIUM, IconColor.EMPTY, missionUnitLink.getMission().getMissionType().getResourcePath(), missionUnitLink.getMission().getMissionType().toString());
        utilsView.addMainTitleLabel(horizontalTitlePane, missionUnitLink.getMission().toString());
    }

}