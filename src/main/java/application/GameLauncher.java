package application;

import application.view.GameplayScreen;
import application.view.MainMenuScreen;
import application.view.ParentalControlScreen;
import application.view.SaveLoadScreen;
import application.view.TutorialScreen;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameLauncher extends Application {

    public static final int WIDTH = 1200;
    public static final int HEIGHT = 800;

    private boolean isNewGame;
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {

        this.primaryStage = primaryStage;

        showMainMenu();

        // Set up the stage
        primaryStage.setTitle("My Pet Game");
        primaryStage.setWidth(WIDTH);
        primaryStage.setHeight(HEIGHT);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void showMainMenu() {
        MainMenuScreen mainMenu = new MainMenuScreen(this);
        Scene scene = mainMenu.getScene();
        primaryStage.setScene(scene);
    }

    public void showGameplay(boolean isNewGame) {
        showGameplay(isNewGame, null); // Default: no pet name for new games
    }

    public void showGameplay(boolean isNewGame, String petName) {
        GameplayScreen gameplayScreen = new GameplayScreen(this, isNewGame, petName); // Pass petName
        primaryStage.setScene(gameplayScreen.getScene());
    }

    public void showSaveLoadScreen() {
        SaveLoadScreen saveLoadScreen = new SaveLoadScreen(this);
        primaryStage.setScene(saveLoadScreen.getScene());
    }

    public void showParentalControlScreen() {
        ParentalControlScreen parentalControlScreen = new ParentalControlScreen(this, primaryStage);
        primaryStage.setScene(parentalControlScreen.getScene());
    }
    
    public void showTutorialScreen() {
        TutorialScreen tutorialScreen = new TutorialScreen(this);
        primaryStage.setScene(tutorialScreen.getScene());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
