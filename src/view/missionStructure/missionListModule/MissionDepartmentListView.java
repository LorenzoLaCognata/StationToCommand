package view.missionStructure.missionListModule;

import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import model.departmentStructure.departmentModule.Department;
import model.missionStructure.missionModule.Mission;
import view.missionStructure.missionModule.MissionDepartmentView;
import view.utilsStructure.utilsModule.UtilsView;

import java.util.List;

public class MissionDepartmentListView {

    private final UtilsView utilsView;
    private final MissionDepartmentView missionDepartmentView;

    public MissionDepartmentListView(UtilsView utilsView, Mission mission) {
        this.utilsView = utilsView;
        this.missionDepartmentView = new MissionDepartmentView(utilsView, mission);
    }

    public void show(Pane pane, List<Control> controls, Mission mission, List<Department> departments) {
        Label stationsSeparator = new Label("----------------------\nDepartments");
        pane.getChildren().addAll(stationsSeparator);
        
        for (Department department : departments) {
            Button button = new Button();
            button.setOnAction(_ -> {
                List<Control> newControls = utilsView.resetAndAddToPane(pane, controls, button);
                missionDepartmentView.show(pane, newControls, mission, department);
            });
            String text1 = department.getDepartmentType().toString() + " Department";
            String text2 = "";
            utilsView.addToSidebar(pane, button, text1, text2);
        }
    }

}
