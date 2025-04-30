package stationtocommand.view.dispatchStructure;

import javafx.scene.Node;
import stationtocommand.model.locationStructure.Location;
import stationtocommand.model.missionLinkStructure.MissionResponderLink;
import stationtocommand.model.missionLinkStructure.MissionUnitLink;
import stationtocommand.model.missionLinkStructure.MissionVehicleLink;
import stationtocommand.view.View;
import stationtocommand.view.mainStructure.IconColor;
import stationtocommand.view.mainStructure.IconType;
import stationtocommand.view.mainStructure.UtilsView;
import stationtocommand.view.mainStructure.ViewWithNode;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MissionUnitView extends ViewWithNode {

    private final MissionUnitLink missionUnitLink;
    private final UtilsView utilsView;
    private final Map<MissionVehicleLink, MissionVehicleView> missionVehicleViews;
    private final Map<MissionResponderLink, MissionResponderView> missionResponderViews;

    // Constructor

    public MissionUnitView(MissionUnitLink missionUnitLink, View view, UtilsView utilsView) {
        super(utilsView.createResourceIconWithLocation(IconType.SMALL, IconColor.EMPTY, missionUnitLink.getUnit().getUnitType(), missionUnitLink.getUnit().getStation().getLocation()));
        this.missionUnitLink = missionUnitLink;
        this.utilsView = utilsView;

        this.missionVehicleViews = new LinkedHashMap<>();
        this.missionResponderViews = new LinkedHashMap<>();

        for (MissionVehicleLink missionVehicleLink : missionUnitLink.getVehicleLinks()) {
            addMissionVehicleView(missionVehicleLink, view, utilsView);
        }
        for (MissionResponderLink missionResponderLink : missionUnitLink.getResponderLinks()) {
            addMissionResponderView(missionResponderLink, view, utilsView);
        }
    }

    public void addMissionVehicleView(MissionVehicleLink missionVehicleLink, View view, UtilsView utilsView) {
        if (!missionVehicleViews.containsKey(missionVehicleLink)) {
            MissionVehicleView missionVehicleView = new MissionVehicleView(missionVehicleLink, view, utilsView);
            missionVehicleViews.put(missionVehicleLink, missionVehicleView);
            view.addToMap(missionVehicleView.getNode());
        }
    }

    public void addMissionResponderView(MissionResponderLink missionResponderLink, View view, UtilsView utilsView) {
        if (!missionResponderViews.containsKey(missionResponderLink)) {
            MissionResponderView missionResponderView = new MissionResponderView(missionResponderLink, view, utilsView);
            missionResponderViews.put(missionResponderLink, missionResponderView);
            view.addToMap(missionResponderView.getNode());
        }
    }


    // Getter

    public MissionUnitLink getMissionUnitLink() {
        return missionUnitLink;
    }

    public Map<MissionVehicleLink, MissionVehicleView> getMissionVehicleViews() {
        return missionVehicleViews;
    }

    public MissionVehicleView getMissionVehicleView(MissionVehicleLink missionVehicleLink) {
        return missionVehicleViews.get(missionVehicleLink);
    }

    public Map<MissionResponderLink, MissionResponderView> getMissionResponderViews() {
        return missionResponderViews;
    }

    public MissionResponderView getMissionResponderView(MissionResponderLink missionResponderLink) {
        return missionResponderViews.get(missionResponderLink);
    }


    // Methods
    public void addListDetails(View view) {
        utilsView.addIconAndButton(view.getDetailsPane(), missionUnitLink.getUnit().getUnitType(), missionUnitLink.getUnit().toString(), (_ -> show(view)));
    }

    public void show(View view) {
        View.viewRunnable = () -> show(view);
        utilsView.addBreadCrumb(view.getBreadCrumbBar(), missionUnitLink);
        view.clearNavigationPanel();
        utilsView.addIconAndTitleWithSubtitle(
                view.getTitlePane(),
                missionUnitLink.getMission().getMissionType(), missionUnitLink.getMission().toString(),
                missionUnitLink.getUnit().getUnitType(), missionUnitLink.getUnit().toString()
        );
        utilsView.addSelectedButtonWithGraphic(view.getButtonsPane(), missionUnitLink.getUnit().getStation().getDepartment().defaultVehicleType(), "Vehicles", () -> showVehicles(view));
        utilsView.addButtonWithGraphic(view.getButtonsPane(), missionUnitLink.getUnit().getStation().getDepartment().defaultAppearanceType(), "Responders", () -> showResponders(view));
    }


    // Vehicles

    public Map<Location, List<Node>> missionVehicleNodesByLocation() {
        return utilsView.nodesByLocation(missionVehicleViews, v -> v.getMissionVehicleLink().getVehicle().getLocation());
    }

    private void showVehicles(View view) {
        View.viewRunnable = () -> showVehicles(view);
        showNavigationPanelVehicles(view);
        showMapVehicles(view);
    }

    private void showNavigationPanelVehicles(View view) {
        view.clearDetailsPane();
        utilsView.addTotalResources(view.getDetailsPane(), missionUnitLink.missionVehiclesByStatus(), missionUnitLink.missionVehiclesByTypeAndStatus());
        for (MissionVehicleView missionVehicleView : missionVehicleViews.values()) {
            missionVehicleView.addListDetails(view);
        }
    }

    public void showMapVehicles(View view) {
        view.hideMap();
        missionVehicleNodesByLocation().forEach(utilsView::distributeResourceIconsByLocation);
        for (MissionVehicleView missionVehicleView : missionVehicleViews.values()) {
            missionVehicleView.showNode();
        }
        MissionView missionView = view.getDispatchView().getMissionView(missionUnitLink.getMission());
        missionView.showNode();
    }


    // Responders

    public Map<Location, List<Node>> missionResponderNodesByLocation() {
        return utilsView.nodesByLocation(missionResponderViews, v -> v.getMissionResponderLink().getResponder().getLocation());
    }

    private void showResponders(View view) {
        View.viewRunnable = () -> showResponders(view);
        showNavigationPanelResponders(view);
        showMapResponders(view);
    }

    private void showNavigationPanelResponders(View view) {
        view.clearDetailsPane();
        utilsView.addTotalResources(view.getDetailsPane(), missionUnitLink.missionRespondersByStatus(), missionUnitLink.missionRespondersByRankAndStatus());
        for (MissionResponderView missionResponderView : missionResponderViews.values()) {
            missionResponderView.addListDetails(view);
        }
    }

    private void showMapResponders(View view) {
        view.hideMap();
        missionResponderNodesByLocation().forEach(utilsView::distributeResourceIconsByLocation);
        for (MissionResponderView missionResponderView : missionResponderViews.values()) {
            missionResponderView.showNode();
        }
        MissionView missionView = view.getDispatchView().getMissionView(missionUnitLink.getMission());
        missionView.showNode();
    }

}