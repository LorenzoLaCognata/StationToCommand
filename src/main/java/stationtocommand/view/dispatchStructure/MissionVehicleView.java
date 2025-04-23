package stationtocommand.view.dispatchStructure;

import javafx.scene.layout.Pane;
import stationtocommand.model.missionLinkStructure.MissionVehicleLink;
import stationtocommand.view.View;
import stationtocommand.view.mainStructure.IconColor;
import stationtocommand.view.mainStructure.IconType;
import stationtocommand.view.mainStructure.UtilsView;

public class MissionVehicleView {

    private final UtilsView utilsView;

    public MissionVehicleView(UtilsView utilsView) {
        this.utilsView = utilsView;
    }

    public void show(View view, MissionVehicleLink missionVehicleLink) {
        View.viewRunnable = () -> show(view, missionVehicleLink);
        utilsView.addBreadCrumb(view.getBreadCrumbBar(), missionVehicleLink);
        view.getNavigationPanel().clearAll();
        view.getWorldMap().setMapElementsNotVisible();
        showSidebar(view, missionVehicleLink);
    }

    private void showSidebar(View view, MissionVehicleLink missionVehicleLink) {
        showMissionVehicleDetails(view, missionVehicleLink);
    }

    private void showMissionVehicleDetails(View view, MissionVehicleLink missionVehicleLink) {
        Pane titleAndSubtitlePane = utilsView.createVBox(view.getNavigationPanel().getTitlePane());
        Pane horizontalTitlePane = utilsView.createHBox(titleAndSubtitlePane);
        utilsView.addIconToPane(horizontalTitlePane, IconType.MEDIUM, IconColor.EMPTY, missionVehicleLink.getMission().getMissionType());
        utilsView.addMainTitleLabel(horizontalTitlePane, missionVehicleLink.getMission().toString());
        Pane horizontalSubtitlePane = utilsView.createHBox(titleAndSubtitlePane);
        utilsView.addIconToPane(horizontalSubtitlePane, IconType.SMALL, IconColor.EMPTY, missionVehicleLink.getVehicle().getVehicleType());
        utilsView.addMainSubtitleLabel(horizontalSubtitlePane, missionVehicleLink.getVehicle().toString());
    }

}