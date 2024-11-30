package application;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import application.model.GameState;
import application.model.Pet;
import application.model.Player;
import application.view.GameplayScreen;
import application.view.MainMenuScreen;
import application.view.ParentalControlScreen;
import application.view.SaveLoadScreen;
import application.view.TutorialScreen;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.google.gson.Gson;

public class GameLauncher extends Application {
    public static final int WIDTH = 1200;
    public static final int HEIGHT = 800;

    private boolean isNewGame;
    private GameState currentGameState;
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
        saveGame();
        MainMenuScreen mainMenu = new MainMenuScreen(this);
        Scene scene = mainMenu.getScene();
        primaryStage.setScene(scene);
    }

    public void showGameplay(boolean isNewGame) {
        showGameplay(isNewGame, null); // Default: no pet name for new games
    }

    public void showGameplay(boolean isNewGame, String petName) {
        if (isNewGame) {
        // Create default player and pet for the new game
            Pet pet = new Pet("Dog", 100, 100, 100, 100, new ArrayList<>());
            Player player = new Player("Player Name", pet);
            currentGameState = new GameState(player, findEmptySaveSlot());
        }
        
        GameplayScreen gameplayScreen = new GameplayScreen(this, isNewGame, petName); // Pass petName
        primaryStage.setScene(gameplayScreen.getScene());
    }

    public int findEmptySaveSlot(){
        return 1;//Need to implement a find find empty slot but for now 1
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

    public void loadGame(int slotNumber){
        try{
            Gson gson = new Gson();
            //Each save will have it own file
            FileReader reader = new FileReader("save"+ slotNumber + ".json");
            currentGameState = gson.fromJson(reader, GameState.class);
            reader.close();

            //
            showGameplay(false, currentGameState.getPlayer().getCurrentPet().getName());
        }catch (IOException e){
            System.out.println("Error loading save: "+e.getMessage());
        }
    }

    public void saveGame() {
        if(currentGameState != null){
            try {
                Gson gson = new Gson();
                FileWriter writer = new FileWriter("save"+ currentGameState.getSaveSlot() +".json");
                gson.toJson(currentGameState, writer);
                writer.close();
            }catch (IOException e) {
                System.out.println("Error saving game: "+e.getMessage());
            }
        }
    }
    public GameState getCurrentGameState() {
        return currentGameState;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
