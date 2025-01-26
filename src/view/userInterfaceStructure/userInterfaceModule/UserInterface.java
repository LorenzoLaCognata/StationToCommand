package view.userInterfaceStructure.userInterfaceModule;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import main.Main;
import view.organizationStructure.skillStructure.skillModule.SkillView;

public class UserInterface {

    private final BorderPane parentScene = new BorderPane();
    private final ToolBar topBar = new ToolBar();
    private final VBox leftPanel = new VBox(10);
    private final Pane centerArea = new Pane();

    private final SkillView skillView;

    public UserInterface(Main main) {
        parentScene.setTop(topBar);
        parentScene.setLeft(leftPanel);
        parentScene.setCenter(centerArea);
        topBar.setMinHeight(60);

        Button exitButton = new Button("Quit");
        exitButton.setOnAction(_ -> Platform.exit());

        Button organizationButton = new Button("Organization");
        topBar.getItems().addAll(organizationButton, exitButton);

        organizationButton.setOnAction(_ -> main.organizationButtonAction(leftPanel));

        leftPanel.setMinWidth(400);
        leftPanel.setPadding(new Insets(10));
        leftPanel.setStyle("-fx-background-color: #f0f0f0;");

        centerArea.setStyle("-fx-background-color: #ffffff;");

        ImageView mapView = new ImageView(new Image("file:C:\\Users\\vodev\\OneDrive\\Desktop\\map.jpg"));
        mapView.fitWidthProperty().bind(centerArea.widthProperty());
        mapView.fitHeightProperty().bind(centerArea.heightProperty());
        mapView.setPreserveRatio(true);
        centerArea.getChildren().add(mapView);

        Circle station = new Circle(100, 100, 12, Color.BLUE);
        Circle responder = new Circle(200, 200, 8, Color.RED);
        centerArea.getChildren().addAll(station, responder);

        this.skillView = new SkillView();
    }

    public BorderPane getParentScene() {
        return parentScene;
    }

    public ToolBar getTopBar() {
        return topBar;
    }

    public VBox getLeftPanel() {
        return leftPanel;
    }

    public Pane getCenterArea() {
        return centerArea;
    }

    public SkillView getSkillView() {
        return skillView;
    }

}
