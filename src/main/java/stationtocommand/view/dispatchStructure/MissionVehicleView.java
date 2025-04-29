package stationtocommand.view.dispatchStructure;

import stationtocommand.model.missionLinkStructure.MissionVehicleLink;
import stationtocommand.view.View;
import stationtocommand.view.mainStructure.IconColor;
import stationtocommand.view.mainStructure.IconType;
import stationtocommand.view.mainStructure.UtilsView;
import stationtocommand.view.mainStructure.ViewWithNode;

public class MissionVehicleView extends ViewWithNode {

    private final MissionVehicleLink missionVehicleLink;
    private final UtilsView utilsView;

    public MissionVehicleView(MissionVehicleLink missionVehicleLink, View view, UtilsView utilsView) {
        super(utilsView.createResourceIconWithLocation(IconType.SMALL, IconColor.EMPTY, missionVehicleLink.getVehicle().getVehicleType(), missionVehicleLink.getVehicle().getLocation()));
        this.missionVehicleLink = missionVehicleLink;
        this.utilsView = utilsView;
    }

    public MissionVehicleLink getMissionVehicleLink() {
        return missionVehicleLink;
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