package stationtocommand.view.dispatchStructure;

import javafx.scene.Node;
import stationtocommand.model.missionLinkStructure.MissionResponderLink;
import stationtocommand.view.View;
import stationtocommand.view.mainStructure.IconColor;
import stationtocommand.view.mainStructure.IconType;
import stationtocommand.view.mainStructure.UtilsView;

public class MissionResponderView {

    private final MissionResponderLink missionResponderLink;
    private final Node node;
    private final UtilsView utilsView;

    public MissionResponderView(MissionResponderLink missionResponderLink, View view, UtilsView utilsView) {
        System.out.println("MissionResponderView " + missionResponderLink.getMission() + " " + missionResponderLink.getResponder());
        this.missionResponderLink = missionResponderLink;
        this.node = utilsView.createResourceIconWithLocation(IconType.SMALL, IconColor.EMPTY, missionResponderLink.getResponder().getAppearanceType(), missionResponderLink.getResponder().getLocation());
        this.utilsView = utilsView;
    }

    public MissionResponderLink getMissionResponderLink() {
        return missionResponderLink;
    }

    public Node getNode() {
        return node;
    }

    public void showNode() {
        node.setVisible(true);
    }

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