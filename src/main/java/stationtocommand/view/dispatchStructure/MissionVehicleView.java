package stationtocommand.view.dispatchStructure;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import stationtocommand.model.missionLinkStructure.MissionVehicleLink;
import stationtocommand.view.View;
import stationtocommand.view.mainStructure.IconColor;
import stationtocommand.view.mainStructure.IconType;
import stationtocommand.view.mainStructure.UtilsView;

public class MissionVehicleView {

    private final MissionVehicleLink missionVehicleLink;
    private final Node node;
    private final UtilsView utilsView;

    public MissionVehicleView(MissionVehicleLink missionVehicleLink, View view, UtilsView utilsView) {
        System.out.println("Created MissionVehicleView for " + missionVehicleLink.getMission() + " - " + missionVehicleLink.getVehicle());
        this.missionVehicleLink = missionVehicleLink;
        this.node = utilsView.createResourceIconWithLocation(IconType.SMALL, IconColor.EMPTY, missionVehicleLink.getVehicle().getVehicleType(), missionVehicleLink.getVehicle().getLocation());
        this.utilsView = utilsView;
    }

    public MissionVehicleLink getMissionVehicleLink() {
        return missionVehicleLink;
    }

    public Node getNode() {
        return node;
    }

    public void setNodeVisible() {
        node.setVisible(true);
    }

    public void show(View view) {
        View.viewRunnable = () -> show(view);
        utilsView.addBreadCrumb(view.getBreadCrumbBar(), missionVehicleLink);
        view.getNavigationPanel().clearAll();
        view.getWorldMap().setMapElementsNotVisible();
        showSidebar(view, missionVehicleLink);
    }

    private void showSidebar(View view, MissionVehicleLink missionVehicleLink) {
        showMissionVehicleDetails(view);
    }

    private void showMissionVehicleDetails(View view) {
        Pane titleAndSubtitlePane = utilsView.createVBox(view.getNavigationPanel().getTitlePane());
        Pane horizontalTitlePane = utilsView.createHBox(titleAndSubtitlePane);
        utilsView.addIconToPane(horizontalTitlePane, IconType.MEDIUM, IconColor.EMPTY, missionVehicleLink.getMission().getMissionType());
        utilsView.addMainTitleLabel(horizontalTitlePane, missionVehicleLink.getMission().toString());
        Pane horizontalSubtitlePane = utilsView.createHBox(titleAndSubtitlePane);
        utilsView.addIconToPane(horizontalSubtitlePane, IconType.SMALL, IconColor.EMPTY, missionVehicleLink.getVehicle().getVehicleType());
        utilsView.addMainSubtitleLabel(horizontalSubtitlePane, missionVehicleLink.getVehicle().toString());
    }

    public void addMissionStationDetailsVehicle(View view) {
        addMissionUnitDetailsVehicle(view);
    }

    public void addMissionUnitDetailsVehicle(View view) {
        Pane horizontalDetailsPane = utilsView.createHBox(view.getNavigationPanel().getDetailsPane());
        addMissionVehicleIcon(horizontalDetailsPane);
        addMissionVehicleButton(view, horizontalDetailsPane);
    }

    private void addMissionVehicleIcon(Pane pane) {
        utilsView.addIconToPane(pane, IconType.SMALL, IconColor.EMPTY, missionVehicleLink.getVehicle().getVehicleType());
    }

    private void addMissionVehicleButton(View view, Pane pane) {
        utilsView.addButtonToPane(pane, missionVehicleLink.getVehicle().toString(), (_ -> show(view)));
    }

}