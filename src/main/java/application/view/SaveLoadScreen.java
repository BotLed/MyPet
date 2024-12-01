package application.view;

import java.util.ArrayList;

import application.GameLauncher;
import application.model.GameState;
import application.model.Pet;
import application.model.Player;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SaveLoadScreen {

    private GameLauncher gameLauncher;
    private Stage stage;

    public SaveLoadScreen(GameLauncher gameLauncher) {
        this.gameLauncher = gameLauncher;
    }

    public Scene getScene() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f5f5f5;");

        HBox slotContainer = createSlotContainer();
        root.setCenter(slotContainer);

        StackPane overlay = createOverlay();
        root.setTop(overlay);

        return new Scene(root, 1200, 800);
    }

    private StackPane createOverlay() {
        StackPane overlay = new StackPane();
        overlay.setPadding(new Insets(20));

        // "X" Button for closing the screen
        Button closeButton = new Button("X");
        closeButton.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        closeButton.setStyle(
                "-fx-background-color: #ffffff; " +
                        "-fx-border-color: #cccccc; " +
                        "-fx-border-radius: 50; " +
                        "-fx-background-radius: 50; " +
                        "-fx-min-width: 70; " +
                        "-fx-min-height: 70; " +
                        "-fx-text-fill: black;");
        closeButton.setOnAction(e -> gameLauncher.showMainMenu()); // Navigate back to the main menu

        StackPane.setAlignment(closeButton, Pos.TOP_LEFT);
        StackPane.setMargin(closeButton, new Insets(20, 0, 0, 20));

        // Title for "Load Game"
        Text title = new Text("Load Game");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 50));
        title.setFill(Color.BLACK);
        StackPane.setAlignment(title, Pos.TOP_CENTER);
        StackPane.setMargin(title, new Insets(20, 0, 0, 0));

        overlay.getChildren().addAll(closeButton, title);

        return overlay;
    }

    private HBox createSlotContainer() {
        HBox slotContainer = new HBox(40);
        slotContainer.setAlignment(Pos.CENTER);
        slotContainer.setPadding(new Insets(50));
        slotContainer.setStyle("-fx-background-color: #ffffff; -fx-border-radius: 10; -fx-background-radius: 10;");

        // Dynamically create save slots for 3 JSON files
        for (int i = 1; i <= 3; i++) {
            GameState gameState = gameLauncher.loadGame(i); // Load game state for the slot
            String petName = gameState != null && gameState.getPlayer() != null
                    ? gameState.getPlayer().getCurrentPet() != null
                            ? gameState.getPlayer().getCurrentPet().getName()
                            : null
                    : null;
            Integer petType = gameState != null && gameState.getPlayer() != null
                    ? gameState.getPlayer().getCurrentPet() != null
                            ? gameState.getPlayer().getCurrentPet().getPetType()
                            : null
                    : null;

            slotContainer.getChildren().add(createSaveSlot(i, petName, petType)); // Pass petType
        }

        return slotContainer;
    }

    private VBox createSaveSlot(int slotNumber, String petName, Integer petType) {
        VBox slotWithLabel = new VBox(10);
        slotWithLabel.setAlignment(Pos.CENTER);

        StackPane slotBox = new StackPane();
        slotBox.setStyle(
                "-fx-background-color: #dcdcdc; -fx-border-color: #aaaaaa; -fx-border-radius: 10; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 3);");
        slotBox.setPrefSize(300, 500);

        Text saveInfo = new Text(petName == null ? "Empty" : petName);
        saveInfo.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        saveInfo.setFill(Color.BLACK);

        ImageView petImageView = new ImageView();
        petImageView.setFitWidth(200);
        petImageView.setFitHeight(200);
        petImageView.setPreserveRatio(true);

        if (petName == null) {
            // Empty slot: show "+" button for a new game
            Button newSaveButton = new Button("+");
            newSaveButton.setFont(Font.font("Arial", FontWeight.BOLD, 80));
            newSaveButton.setStyle(
                    "-fx-background-color: #dcdcdc; -fx-border-color: #aaaaaa; -fx-border-radius: 50; -fx-text-fill: black;");
            newSaveButton.setOnAction(e -> {
                System.out.println("DEBUG: + Button clicked for Slot " + slotNumber);
                openNewGameConfirmation(slotNumber);
            });

            slotBox.getChildren().add(newSaveButton);
        } else {
            // Slot has a saved game: show pet sprite based on petType
            if (petType != null) {
                String imagePath = String.format("/sprite states/pet%d/normal-state.png", petType);
                try {
                    Image petImage = new Image(getClass().getResourceAsStream(imagePath));
                    petImageView.setImage(petImage);
                } catch (Exception e) {
                    System.err.println("Failed to load pet image for petType " + petType + ": " + e.getMessage());
                    // Use a placeholder if the image is not found
                    petImageView
                            .setImage(new Image(getClass().getResourceAsStream("/sprite states/default-state.png")));
                }
            } else {
                // Use a placeholder if petType is null
                petImageView.setImage(new Image(getClass().getResourceAsStream("/sprite states/default-state.png")));
            }

            // Add pet image to the slot
            slotBox.getChildren().add(petImageView);

            // Make the slot clickable to load the game
            slotBox.setOnMouseClicked(e -> {
                System.out.println("DEBUG: Slot clicked for Slot " + slotNumber);
                gameLauncher.showGameplay(false, petName, slotNumber);
            });
        }

        slotWithLabel.getChildren().addAll(slotBox, saveInfo);
        return slotWithLabel;
    }

    private void openNewGameConfirmation(int saveSlot) {

        Stage modalStage = new Stage();
        modalStage.initOwner(stage);
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.setTitle("New Game");

        VBox modalLayout = new VBox(20);
        modalLayout.setPadding(new Insets(20));
        modalLayout.setAlignment(Pos.CENTER);
        modalLayout.setStyle("-fx-background-color: #ffffff;");

        Text modalTitle = new Text("New Game");
        modalTitle.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        modalTitle.setFill(Color.BLACK);

        Text modalMessage = new Text("Click start to begin playing a new game");
        modalMessage.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        modalMessage.setFill(Color.DARKGRAY);

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> modalStage.close());

        Button startButton = new Button("Start");
        startButton.setOnAction(e -> {
            System.out.println("Starting a new game in slot " + saveSlot);

            // Create a new Player without a pet
            Player newPlayer = new Player("Player", null);

            // Create a new GameState with the player and save slot
            GameState newGameState = new GameState(newPlayer, saveSlot);

            // Save the new game state
            gameLauncher.saveGame(newGameState);

            modalStage.close();

            // Show gameplay (no pet assigned yet)
            gameLauncher.showGameplay(true, null, saveSlot);
        });

        HBox buttonBox = new HBox(20, cancelButton, startButton);
        buttonBox.setAlignment(Pos.CENTER);

        modalLayout.getChildren().addAll(modalTitle, modalMessage, buttonBox);

        Scene modalScene = new Scene(modalLayout, 400, 200);
        modalStage.setScene(modalScene);
        modalStage.showAndWait();
    }

    /*
     * private void openLoadGameConfirmation(int saveSlot) {
     * Stage modalStage = new Stage();
     * modalStage.initOwner(stage);
     * modalStage.initModality(Modality.APPLICATION_MODAL);
     * modalStage.setTitle("Continue Game");
     * 
     * VBox modalLayout = new VBox(20);
     * modalLayout.setPadding(new Insets(20));
     * modalLayout.setAlignment(Pos.CENTER);
     * modalLayout.setStyle("-fx-background-color: #ffffff;");
     * 
     * Text modalTitle = new Text("Continue Game");
     * modalTitle.setFont(Font.font("Arial", FontWeight.BOLD, 24));
     * modalTitle.setFill(Color.BLACK);
     * 
     * Text modalMessage = new Text("Click resume to continue with slot " + saveSlot
     * + "!");
     * modalMessage.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
     * modalMessage.setFill(Color.DARKGRAY);
     * 
     * Button cancelButton = new Button("Cancel");
     * cancelButton.setOnAction(e -> modalStage.close());
     * 
     * Button resumeButton = new Button("Resume");
     * resumeButton.setOnAction(e -> {
     * System.out.println("Resuming game from slot " + saveSlot);
     * modalStage.close();
     * gameLauncher.showGameplay(false, null, saveSlot);
     * });
     * 
     * HBox buttonBox = new HBox(20, cancelButton, resumeButton);
     * buttonBox.setAlignment(Pos.CENTER);
     * 
     * modalLayout.getChildren().addAll(modalTitle, modalMessage, buttonBox);
     * 
     * Scene modalScene = new Scene(modalLayout, 400, 200);
     * modalStage.setScene(modalScene);
     * modalStage.showAndWait();
     * }
     */

}