package stationtocommand.view.responderStructure;

import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.responderStructure.Responder;
import stationtocommand.view.View;
import stationtocommand.view.mainStructure.IconColor;
import stationtocommand.view.mainStructure.IconType;
import stationtocommand.view.mainStructure.UtilsView;
import stationtocommand.view.skillStructure.SkillListView;

public class ResponderView {

    private final UtilsView utilsView;
    private final SkillListView skillListView;

    public ResponderView(UtilsView utilsView) {
        this.utilsView = utilsView;
        this.skillListView = new SkillListView(utilsView);
    }

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane navigationPanel, Responder responder) {
        View.viewRunnable = () -> show(breadCrumbBar, navigationPanel, responder);
        utilsView.addBreadCrumb(breadCrumbBar, responder);
        utilsView.clearPane(navigationPanel);
        showSidebar(breadCrumbBar, navigationPanel, responder);
    }

    private void showSidebar(BreadCrumbBar<Object> breadCrumbBar, Pane navigationPanel, Responder responder) {
        showResponderDetails(navigationPanel, responder);
        skillListView.show(breadCrumbBar, navigationPanel, responder.getSkills());
    }

    private void showResponderDetails(Pane navigationPanel, Responder responder) {
        Pane labelPane = utilsView.createHBox(navigationPanel);
        utilsView.addIconToPane(navigationPanel, IconType.MEDIUM, IconColor.BLANK, utilsView.responderIconPath(responder));
        utilsView.addMainTitleLabel(labelPane, responder.toString());
    }

}
