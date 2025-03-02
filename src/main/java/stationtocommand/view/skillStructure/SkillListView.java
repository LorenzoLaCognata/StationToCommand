package stationtocommand.view.skillStructure;

import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.skillStructure.Skill;
import stationtocommand.view.mainStructure.UtilsView;

import java.util.List;

public class SkillListView {

    private final UtilsView utilsView;
    private final SkillView skillView;

    public SkillListView(UtilsView utilsView) {
        this.utilsView = utilsView;
        this.skillView = new SkillView(utilsView);
    }

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane navigationPanel, List<Skill> skills) {
        utilsView.addSectionTitleLabel(navigationPanel, "Skills");
        for (Skill skill : skills) {
            showSidebar(breadCrumbBar, navigationPanel, skill);
        }
    }

    private void showSidebar(BreadCrumbBar<Object> breadCrumbBar, Pane navigationPanel, Skill skill) {
        Pane labelPane = utilsView.createHBox(navigationPanel);
        showSkillButton(breadCrumbBar, navigationPanel, labelPane, skill);
    }

    private void showSkillButton(BreadCrumbBar<Object> breadCrumbBar, Pane navigationPanel, Pane labelPane, Skill skill) {
        utilsView.addButtonToPane(labelPane, skill.toString(), (_ -> skillView.show(breadCrumbBar, navigationPanel, skill)));
    }

}
