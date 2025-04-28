package stationtocommand.view.dispatchStructure;

import javafx.scene.Node;
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
        System.out.println("MissionVehicleView " + missionVehicleLink.getMission() + " " + missionVehicleLink.getVehicle());
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

    public void showNode() {
        node.setVisible(true);
    }

    public void addListDetails(View view) {
        utilsView.addIconAndButton(view.getDetailsPane(), missionVehicleLink.getVehicle().getVehicleType(), missionVehicleLink.getVehicle().toString(), (_ -> show(view)));
    }

    public void show(View view) {
        View.viewRunnable = () -> show(view);
        showNavigationPanel(view);
        showMap(view);
    }

    private void showNavigationPanel(View view) {
        utilsView.addBreadCrumb(view.getBreadCrumbBar(), missionVehicleLink);
        view.clearNavigationPanel();
        utilsView.addIconAndTitleWithSubtitle(view.getTitlePane(),
                missionVehicleLink.getMission().getMissionType(), missionVehicleLink.getMission().toString(),
                missionVehicleLink.getVehicle().getVehicleType(), missionVehicleLink.getVehicle().toString()
        );
    }

    public void showMap(View view) {
        view.hideMap();
        showNode();
        MissionView missionView = view.getDispatchView().getMissionView(missionVehicleLink.getMission());
        missionView.showNode();
    }

}