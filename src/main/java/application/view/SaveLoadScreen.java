package application.view;

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

    private Stage stage;

    public SaveLoadScreen(Stage stage) {
        this.stage = stage;
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
            "-fx-text-fill: black;"
        );
        closeButton.setOnAction(e -> {
            System.out.println("DEBUG: X Button clicked");
            stage.setScene(new MainMenuScreen().getScene());
        });
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

        slotContainer.getChildren().add(createSaveSlot(1, null, null)); // Empty slot
        slotContainer.getChildren().add(createSaveSlot(2, "Fluffy", new Image("BoJack.png"))); // Slot with saved game
        slotContainer.getChildren().add(createSaveSlot(3, "Buddy", new Image("BoJack.png"))); // Another slot with saved game

        return slotContainer;
    }

    private VBox createSaveSlot(int slotNumber, String petName, Image petImage) {
        VBox slotWithLabel = new VBox(10);
        slotWithLabel.setAlignment(Pos.CENTER);

        StackPane slotBox = new StackPane();
        slotBox.setStyle("-fx-background-color: #dcdcdc; -fx-border-color: #aaaaaa; -fx-border-radius: 10; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 3);");
        slotBox.setPrefSize(300, 500);

        Text saveInfo = new Text(petName == null ? "Empty" : petName);
        saveInfo.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        saveInfo.setFill(Color.BLACK);

        if (petName == null) {
            Button newSaveButton = new Button("+");
            newSaveButton.setFont(Font.font("Arial", FontWeight.BOLD, 80));
            newSaveButton.setStyle("-fx-background-color: #dcdcdc; -fx-border-color: #aaaaaa; -fx-border-radius: 50; -fx-text-fill: black;");
            newSaveButton.setOnAction(e -> {
                System.out.println("DEBUG: + Button clicked for Slot " + slotNumber);
                openNewGameConfirmation(slotNumber);
            });

            slotBox.getChildren().add(newSaveButton);
        } else {
            ImageView petImageView = new ImageView(petImage);
            petImageView.setFitWidth(250);
            petImageView.setFitHeight(250);
            petImageView.setPreserveRatio(true);
            petImageView.setOnMouseClicked(e -> {
                System.out.println("DEBUG: Pet image clicked for Slot " + slotNumber);
                openLoadGameConfirmation(petName);
            });

            slotBox.getChildren().add(petImageView);
        }

        slotWithLabel.getChildren().addAll(slotBox, saveInfo);
        return slotWithLabel;
    }

    private void openNewGameConfirmation(int slotNumber) {
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
            System.out.println("Starting a new game in slot " + slotNumber);
            modalStage.close();
        });

        HBox buttonBox = new HBox(20, cancelButton, startButton);
        buttonBox.setAlignment(Pos.CENTER);

        modalLayout.getChildren().addAll(modalTitle, modalMessage, buttonBox);

        Scene modalScene = new Scene(modalLayout, 400, 200);
        modalStage.setScene(modalScene);
        modalStage.showAndWait();
    }

    private void openLoadGameConfirmation(String petName) {
        Stage modalStage = new Stage();
        modalStage.initOwner(stage);
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.setTitle("Continue Game");

        VBox modalLayout = new VBox(20);
        modalLayout.setPadding(new Insets(20));
        modalLayout.setAlignment(Pos.CENTER);
        modalLayout.setStyle("-fx-background-color: #ffffff;");

        Text modalTitle = new Text("Continue Game");
        modalTitle.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        modalTitle.setFill(Color.BLACK);

        Text modalMessage = new Text("Click resume to continue with " + petName + "!");
        modalMessage.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        modalMessage.setFill(Color.DARKGRAY);

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> modalStage.close());

        Button resumeButton = new Button("Resume");
        resumeButton.setOnAction(e -> {
            System.out.println("Resuming game for " + petName);
            modalStage.close();
        });

        HBox buttonBox = new HBox(20, cancelButton, resumeButton);
        buttonBox.setAlignment(Pos.CENTER);

        modalLayout.getChildren().addAll(modalTitle, modalMessage, buttonBox);

        Scene modalScene = new Scene(modalLayout, 400, 200);
        modalStage.setScene(modalScene);
        modalStage.showAndWait();
    }
}