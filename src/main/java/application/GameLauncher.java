package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import application.view.MainMenuScreen;


public class GameLauncher extends Application {

    public static final int WIDTH = 1200; 
    public static final int HEIGHT = 800; 

    @Override
    public void start(Stage primaryStage) {
        
        MainMenuScreen mainMenu = new MainMenuScreen();
        Scene scene = mainMenu.getScene();
        

        // Set up the stage
        primaryStage.setTitle("My Pet Game");
        primaryStage.setScene(scene);
        primaryStage.setWidth(WIDTH);
        primaryStage.setHeight(HEIGHT);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

   

    public static void main(String[] args) {
        launch(args);
    }
}
