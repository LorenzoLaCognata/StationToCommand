package stationtocommand.view.missionStructure;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.missionLinkStructure.MissionDepartmentLink;
import stationtocommand.model.missionStructure.Mission;
import stationtocommand.view.mainStructure.UtilsView;

public class MissionDepartmentListView {

    private final UtilsView utilsView;
    private final MissionDepartmentView missionDepartmentView;

    public MissionDepartmentListView(UtilsView utilsView) {
        this.utilsView = utilsView;
        this.missionDepartmentView = new MissionDepartmentView(utilsView);
    }

    public MissionDepartmentView getMissionDepartmentView() {
        return missionDepartmentView;
    }

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane pane, Mission mission) {
        utilsView.addLabel(pane, "Stations");
        for (MissionDepartmentLink missionDepartmentLink : mission.getDepartmentLinks()) {
            Button button = new Button(missionDepartmentLink.getDepartment().getDepartmentType().toString() + " Department");
            button.setOnAction(_ -> missionDepartmentView.show(breadCrumbBar, pane, missionDepartmentLink));
            pane.getChildren().add(button);
        }
    }

}
