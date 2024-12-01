package application;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

import com.google.gson.Gson;

import application.model.GameState;
import application.model.Pet;
import application.model.Player;

import application.controllers.FeedbackController;
import application.controllers.GameplayController;
import application.model.Feedback;

import application.view.GameplayScreen;
import application.view.MainMenuScreen;
import application.view.ParentalControlScreen;
import application.view.SaveLoadScreen;
import application.view.TutorialScreen;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.nio.file.Path;

public class GameLauncher extends Application {

    public static final int WIDTH = 1200;
    public static final int HEIGHT = 800;

    // private boolean isNewGame;
    private Stage primaryStage;
    private GameState currentGameState;
    private Feedback feedbackModel;
    private FeedbackController feedbackController;

    @Override
    public void start(Stage primaryStage) {

        this.primaryStage = primaryStage;

        // Initialize feedback model and controller
        feedbackModel = new Feedback(true, true);
        feedbackController = new FeedbackController(feedbackModel);

        // Preload sound effects
        feedbackController.preloadSoundEffects();

        showMainMenu();

        // Set up the stage
        primaryStage.setTitle("My Pet Game");
        primaryStage.setWidth(WIDTH);
        primaryStage.setHeight(HEIGHT);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void showMainMenu() {
        MainMenuScreen mainMenu = new MainMenuScreen(this, feedbackController);
        Scene scene = mainMenu.getScene();
        primaryStage.setScene(scene);
    }

    public void showGameplay(boolean isNewGame, String petName, int saveSlot) {
        GameState gameState;

        if (isNewGame) {
            // Create a new player with no pet initially
            Player newPlayer = new Player("Player", null);
            // Create a new game state with the new player and save slot
            gameState = new GameState(newPlayer, saveSlot);
            // Save the new game state
            saveGame(gameState);
        } else {
            // Load the existing GameState
            gameState = loadGame(saveSlot);

            if (gameState == null) {
                System.err.println("Error: Failed to load game for slot " + saveSlot);
                return;
            }
        }

        // Initialize the GameplayController with the loaded GameState
        GameplayController gameplayController = new GameplayController(gameState);

        // Pass the GameState and other required values to the GameplayScreen
        GameplayScreen gameplayScreen = new GameplayScreen(this, feedbackController, gameplayController, gameState,
                petName);
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

    public GameState loadGame(int slotNumber) {
        try {
            Path savePath = Path.of("saves/save" + slotNumber + ".json");
            if (!Files.exists(savePath)) {
                return null; // Return null if the file doesn't exist
            }

            String jsonContent = Files.readString(savePath);
            Gson gson = new Gson();
            return gson.fromJson(jsonContent, GameState.class);
        } catch (Exception e) {
            System.out.println("Error loading game state from slot " + slotNumber + ": " + e.getMessage());
            return null;
        }
    }

    public void saveGame(GameState gameState) {
        try {
            Path savePath = Path.of("saves/save" + gameState.getSaveSlot() + ".json");
            Files.createDirectories(savePath.getParent()); // Ensure directory exists

            Gson gson = new Gson();
            FileWriter writer = new FileWriter(savePath.toFile());
            gson.toJson(gameState, writer);
            writer.close();

            System.out.println("Game saved to " + savePath);
        } catch (IOException e) {
            System.err.println("Error saving game: " + e.getMessage());
        }
    }

    public GameState getCurrentGameState() {
        return currentGameState;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
