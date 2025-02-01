package view.skillStructure.skillListModule;

import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import model.skillStructure.skillModule.Skill;
import view.skillStructure.skillModule.SkillView;
import view.utilsStructure.utilsModule.UtilsView;

import java.util.List;

public class SkillListView {

    private final UtilsView utilsView;
    private final SkillView skillView;

    public SkillListView(UtilsView utilsView) {
        this.utilsView = utilsView;
        this.skillView = new SkillView();
    }

    public void show(Pane pane, List<Control> controls, List<Skill> skills) {
        Label skillsSeparator = new Label("----------------------\nSkills");
        pane.getChildren().addAll(skillsSeparator);

        for (Skill skill : skills) {
            Button button = new Button();
            button.setOnAction(_ -> {
                utilsView.setBreadcrumbs(pane, controls, button);
                skillView.show(pane);
            });
            utilsView.addPaneEntry(pane, button, skill.toString(), "");
        }
    }



}
