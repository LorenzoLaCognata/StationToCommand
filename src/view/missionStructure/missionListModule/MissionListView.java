package view.missionStructure.missionListModule;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import model.missionStructure.missionModule.Mission;
import view.missionStructure.missionModule.MissionView;
import view.utilsStructure.utilsModule.UtilsView;

import java.util.List;

public class MissionListView {

    private final UtilsView utilsView;

    public MissionListView(UtilsView utilsView) {
        this.utilsView = utilsView;
    }

    public void show(Pane pane1, Pane pane2, List<Node> nodes1, List<Node> nodes2, List<Mission> missions) {
        Label separator = new Label("----------------------\nMissions");
        pane1.getChildren().addAll(separator);

        for (Mission mission : missions) {
            Button button = new Button();
            button.setOnAction(_ -> {
                List<Node> nextNodes1 = utilsView.resetAndAddToPane(pane1, nodes1, button);
                MissionView missionView = new MissionView(utilsView, mission);
                missionView.show(pane1, nextNodes1, mission);
            });
            String text1 = mission.getMissionType().toString();
            String text2 = mission.getLocation().toString();
            utilsView.addToSidebar(pane1, button, text1, text2);

            Point2D point = utilsView.locationToPoint(mission.getLocation());
            Circle circle = new Circle(point.getX(), point.getY(), 10, Color.RED);
            circle.setOnMouseClicked(_ -> {
                System.out.println(text1 + " clicked!");
            });

            Tooltip tooltip = new Tooltip(text2);
            Tooltip.install(circle, tooltip);

            utilsView.addToMap(pane2, circle);
        }
    }

}
