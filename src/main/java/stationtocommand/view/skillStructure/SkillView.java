package stationtocommand.view.skillStructure;

import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.skillStructure.Skill;
import stationtocommand.view.mainStructure.UtilsView;

public class SkillView {

    private final UtilsView utilsView;

    public SkillView(UtilsView utilsView) {
        this.utilsView = utilsView;
    }

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane pane, Skill skill) {
        utilsView.addBreadCrumb(breadCrumbBar, skill);
        utilsView.clearPane(pane);
    }

}
