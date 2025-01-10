package main;

import gameStructure.gameModule.Game;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        Game game = new Game();
/*
        BorderPane borderPane = new BorderPane();
        Scene scene = new Scene(borderPane, 400, 400);

        stage.setTitle("Station To Command");
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
*/
        System.exit(0);

    }

}