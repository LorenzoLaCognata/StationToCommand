package stationtocommand;

import stationtocommand.controller.Controller;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import stationtocommand.model.Model;
import stationtocommand.view.View;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private final Model model = new Model();
    private final View view = new View();
    private final Controller controller = new Controller(model, view);

    private final BorderPane borderPane = new BorderPane();

    @Override
    public void start(Stage primaryStage) {

        Scene scene = new Scene(view.getGridPane(), 800, 600);

        primaryStage.setTitle("Station To Command");
        primaryStage.setMaximized(true);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}