package view.missionStructure.missionListModule;

import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.layout.Pane;
import model.departmentStructure.departmentModule.Department;
import model.missionStructure.missionModule.Mission;
import view.departmentStructure.departmentModule.DepartmentView;
import view.missionStructure.missionModule.MissionView;
import view.utilsStructure.utilsModule.UtilsView;

import java.util.List;

public class MissionListView {

    private final UtilsView utilsView;
    private final MissionView missionView;

    public MissionListView(UtilsView utilsView) {
        this.utilsView = utilsView;
        this.missionView = new MissionView(utilsView);
    }

    public void show(Pane pane, List<Control> controls, List<Mission> missions) {
        for (Mission mission : missions) {
            Button button = new Button();
            button.setOnAction(_ -> {
                List<Control> newControls = utilsView.setBreadcrumbs(pane, controls, button);
                missionView.show(pane, newControls, mission);
            });
            String text1 = mission.getMissionType().toString();
            String text2 = mission.getLocation().toString();
            utilsView.addPaneEntry(pane, button, text1, text2);
        }
    }

}
