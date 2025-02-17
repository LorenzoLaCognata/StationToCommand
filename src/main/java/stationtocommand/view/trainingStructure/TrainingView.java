package stationtocommand.view.trainingStructure;

import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.trainingStructure.Training;
import stationtocommand.view.View;
import stationtocommand.view.mainStructure.UtilsView;

public class TrainingView {

    private final UtilsView utilsView;

    public TrainingView(UtilsView utilsView) {
        this.utilsView = utilsView;
    }

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane pane, Training training) {
        View.viewRunnable = () -> show(breadCrumbBar, pane, training);
        utilsView.addBreadCrumb(breadCrumbBar, training);
        utilsView.clearPane(pane);
    }

}
