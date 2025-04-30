package stationtocommand.view.dispatchStructure;

import stationtocommand.model.missionLinkStructure.MissionResponderLink;
import stationtocommand.view.View;
import stationtocommand.view.mainStructure.IconColor;
import stationtocommand.view.mainStructure.IconType;
import stationtocommand.view.mainStructure.UtilsView;
import stationtocommand.view.mainStructure.ViewWithNode;

public class MissionResponderView extends ViewWithNode {

    private final MissionResponderLink missionResponderLink;
    private final UtilsView utilsView;

    // Constructor

    public MissionResponderView(MissionResponderLink missionResponderLink, View view, UtilsView utilsView) {
        super(utilsView.createResourceIconWithLocation(IconType.SMALL, IconColor.EMPTY, missionResponderLink.getResponder().getAppearanceType(), missionResponderLink.getResponder().getLocation()));
        this.missionResponderLink = missionResponderLink;
        this.utilsView = utilsView;
    }


    // Getter

    public MissionResponderLink getMissionResponderLink() {
        return missionResponderLink;
    }


    // Methods

    public void addListDetails(View view) {
        utilsView.addIconAndButton(view.getDetailsPane(), missionResponderLink.getResponder().getAppearanceType(), missionResponderLink.getResponder().toString(), (_ -> show(view)));
    }

    public void show(View view) {
        View.viewRunnable = () -> show(view);
        showNavigationPanel(view);
        showMap(view);
    }

    private void showNavigationPanel(View view) {
        utilsView.addBreadCrumb(view.getBreadCrumbBar(), missionResponderLink);
        view.clearNavigationPanel();
        utilsView.addIconAndTitleWithSubtitle(view.getTitlePane(),
                missionResponderLink.getMission().getMissionType(), missionResponderLink.getMission().toString(),
                missionResponderLink.getResponder().getAppearanceType(), missionResponderLink.getResponder().toString()
        );
    }

    public void showMap(View view) {
        view.hideMap();
        showNode();
        MissionView missionView = view.getDispatchView().getMissionView(missionResponderLink.getMission());
        missionView.showNode();
    }

}