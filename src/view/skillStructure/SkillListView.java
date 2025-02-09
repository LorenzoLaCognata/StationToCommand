package view.skillStructure;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import model.skillStructure.Skill;
import view.mainStructure.UtilsView;

import java.util.List;

public class SkillListView {

    private final UtilsView utilsView;
    private final SkillView skillView;

    public SkillListView(UtilsView utilsView) {
        this.utilsView = utilsView;
        this.skillView = new SkillView();
    }

    public void show(Pane pane, List<Node> nodes, List<Skill> skills) {
        Label skillsSeparator = new Label("----------------------\nSkills");
        pane.getChildren().addAll(skillsSeparator);

        for (Skill skill : skills) {
            Button button = new Button();
            button.setOnAction(_ -> {
                utilsView.resetAndAddToPane(pane, nodes, button);
                skillView.show(pane);
            });
            utilsView.addToSidebar(pane, button, skill.toString(), "");
        }
    }



}
