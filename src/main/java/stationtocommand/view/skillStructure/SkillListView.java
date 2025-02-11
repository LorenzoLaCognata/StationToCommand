package stationtocommand.view.skillStructure;

import javafx.scene.control.Button;
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

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane pane, List<Skill> skills) {
        utilsView.addLabel(pane, "Skills");
        for (Skill skill : skills) {
            Button button = new Button(skill.toString());
            button.setOnAction(_ -> skillView.show(breadCrumbBar, pane, skill));
            pane.getChildren().add(button);
        }
    }

}
