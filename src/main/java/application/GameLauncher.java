package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class GameLauncher extends Application {

    private StackPane root;
    private Scene scene;

    @Override
    public void start(Stage primaryStage) {
        root = new StackPane();
        scene = new Scene(root, 1200, 800);
        //scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());

        // Set up the stage
        primaryStage.setTitle("My Pet Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

   

    public static void main(String[] args) {
        launch(args);
    }
}
