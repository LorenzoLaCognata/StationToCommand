package stationtocommand.view.responderStructure;

import javafx.scene.layout.Pane;
import stationtocommand.model.responderStructure.Responder;
import stationtocommand.view.View;
import stationtocommand.view.mainStructure.IconColor;
import stationtocommand.view.mainStructure.IconType;
import stationtocommand.view.mainStructure.UtilsView;

import java.util.List;

public class ResponderListView {

    private final UtilsView utilsView;
    private final ResponderView responderView;

    public ResponderListView(UtilsView utilsView) {
        this.utilsView = utilsView;
        this.responderView = new ResponderView(utilsView);
    }

    public ResponderView getResponderView() {
        return responderView;
    }

    public void show(View view, List<Responder> responders) {
        utilsView.addSectionTitleLabel(view.getNavigationPanel().getDetailsPane(), "Responders");

        // TODO: review different approach to avoid showing too many responders
        if (responders.size()<20) {
            for (Responder responder : responders) {
                showSidebar(view, responder);
            }
        }
    }

    private void showSidebar(View view, Responder responder) {
        Pane horizontalDetailsPane = utilsView.createHBox(view.getNavigationPanel().getDetailsPane());
        showResponderIcon(horizontalDetailsPane, responder);
        showResponderButton(view, horizontalDetailsPane, responder);
        showResponderStatus(horizontalDetailsPane, responder);
    }

    private void showResponderIcon(Pane pane, Responder responder) {
        utilsView.addIconToPane(pane, IconType.SMALL, IconColor.EMPTY, responder.getAppearanceType().getResourcePath(), "");
    }

    private void showResponderButton(View view, Pane pane, Responder responder) {
        utilsView.addButtonToPane(pane, responder.toString(), (_ -> responderView.show(view, responder)));
    }

    private void showResponderStatus(Pane pane, Responder responder) {
        utilsView.addIconToPane(pane, IconType.SMALL, IconColor.EMPTY, responder.getResponderStatus().getResourcePath(), responder.getResponderStatus().toString());
    }

}
