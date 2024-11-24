package application.view;

import org.kordamp.ikonli.javafx.FontIcon;
import application.GameLauncher;
import application.components.SettingsModal;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.scene.effect.DropShadow;
import javafx.scene.shape.Circle;

public class GameplayScreen {

    private GameLauncher gameLauncher;
    private boolean isNewGame;
    private String petName;
    private VBox root;
    private VBox namingModal;
    private int selectedPetNumber;
    private TextField nameInput;
    private SettingsModal settingsModal;
    private VBox contentLayout;

    public GameplayScreen(GameLauncher gameLauncher, boolean isNewGame, String petName) {
        this.gameLauncher = gameLauncher;
        this.isNewGame = isNewGame;
        this.petName = petName;
        initializeScreen();
    }

    public Scene getScene() {
        return new Scene(root, 1200, 800);
    }

    private void initializeScreen() {
        root = new VBox();
        root.setPadding(new Insets(0));
        root.setStyle("-fx-background-color: #f5f5f5;");
        root.setAlignment(Pos.TOP_CENTER);

        StackPane layeredLayout = new StackPane();
        layeredLayout.setStyle("-fx-background-color: transparent;");
        layeredLayout.setPrefSize(1200, 800);

        contentLayout = new VBox();
        contentLayout.setAlignment(Pos.TOP_CENTER);
        contentLayout.setPadding(new Insets(20));

        if (isNewGame) {
            setupPetSelection(contentLayout);
        } else {
            setupMainGameplay(contentLayout, petName);
        }

        layeredLayout.getChildren().add(contentLayout);

        setupNamingModal();
        layeredLayout.getChildren().add(namingModal);

        // Add settings modal to the layered layout
        if (settingsModal == null) {
            settingsModal = new SettingsModal();
            settingsModal.setVisible(false); // Initially hidden
            settingsModal.setOnDoneAction(() -> {
                System.out.println("Settings modal closed.");
                settingsModal.setVisible(false);
            });
        }
        layeredLayout.getChildren().add(settingsModal);

        Button settingsButton = createSettingsButton();
        layeredLayout.getChildren().add(settingsButton);
        StackPane.setAlignment(settingsButton, Pos.BOTTOM_RIGHT);
        StackPane.setMargin(settingsButton, new Insets(20));

        root.getChildren().add(layeredLayout);
    }

    private void setupPetSelection(VBox contentLayout) {
        // Top bar with Close Button and Title
        HBox topBar = new HBox(10);
        topBar.setAlignment(Pos.TOP_LEFT);
        topBar.setPadding(new Insets(20));

        // Close Button with Circle Background
        StackPane closeButtonContainer = new StackPane();
        Circle closeButtonCircle = new Circle(25, Color.WHITE);
        closeButtonCircle.setEffect(new DropShadow(10, Color.GRAY));

        FontIcon closeIcon = new FontIcon("fas-times");
        closeIcon.setIconSize(20);
        closeIcon.setIconColor(Color.BLACK);

        Button closeButton = new Button();
        closeButton.setGraphic(closeIcon);
        closeButton.setStyle("-fx-background-color: transparent;");
        closeButton.setOnAction(e -> returnToMainMenu());
        closeButtonContainer.getChildren().addAll(closeButtonCircle, closeButton);

        // Title for the top bar
        VBox titleBox = new VBox();
        titleBox.setAlignment(Pos.TOP_LEFT);
        titleBox.setPadding(new Insets(10, 0, 0, 10));
        Text title = new Text("Pet Selection");
        title.setFont(Font.font("Arial", 24));
        title.setStyle("-fx-font-weight: bold;");
        titleBox.getChildren().add(title);

        topBar.getChildren().addAll(closeButtonContainer, titleBox);

        // Pet card selection
        HBox rectangleBox = new HBox(50);
        rectangleBox.setAlignment(Pos.CENTER);
        rectangleBox.setPadding(new Insets(50));

        // Generate pet cards
        for (int i = 1; i <= 3; i++) {
            VBox petCard = createPetCard(i);
            rectangleBox.getChildren().add(petCard);
        }

        // Bottom section with "Choose a Pet!"
        Text chooseText = new Text("Choose a Pet!");
        chooseText.setFont(Font.font("Arial", 40));
        chooseText.setStyle("-fx-font-weight: bold;");
        VBox.setMargin(chooseText, new Insets(0, 0, 20, 0));

        // Add components to the content layout
        contentLayout.getChildren().addAll(topBar, rectangleBox, chooseText);
    }

    private VBox createPetCard(int number) {
        VBox card = new VBox();
        card.setAlignment(Pos.CENTER);
        card.setSpacing(10);
        card.setPadding(new Insets(20));
        card.setStyle(
                "-fx-background-color: #ffffff; " +
                        "-fx-border-radius: 15; " +
                        "-fx-background-radius: 15; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 5);");

        Text petNumber = new Text(String.valueOf(number));
        petNumber.setFont(Font.font("Arial", 20));

        Button selectButton = new Button();
        selectButton.setStyle(
                "-fx-background-color: #e0e0e0; " +
                        "-fx-min-width: 220px; " +
                        "-fx-min-height: 350px; " +
                        "-fx-border-radius: 10; " +
                        "-fx-background-radius: 10;");
        selectButton.setOnAction(e -> {
            selectedPetNumber = number; // Store the selected pet number
            nameInput.clear(); // Clear the input field for a fresh start
            namingModal.setVisible(true);
        });

        card.getChildren().addAll(selectButton, petNumber);
        return card;
    }

    private void setupNamingModal() {
        namingModal = new VBox(20);
        namingModal.setAlignment(Pos.CENTER);
        namingModal.setPadding(new Insets(30));
        namingModal.setStyle(
                "-fx-background-color: #ffffff; " +
                        "-fx-border-radius: 20; " +
                        "-fx-background-radius: 20; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 20, 0, 0, 10);" +
                        "-fx-focus-color: transparent; " +
                        "-fx-faint-focus-color: transparent;");

        namingModal.setVisible(false);

        namingModal.setMaxWidth(400);
        namingModal.setMaxHeight(300);

        // Modal Title
        Text modalTitle = new Text("Naming Time!");
        modalTitle.setFont(Font.font("Arial", 36));
        modalTitle.setStyle("-fx-font-weight: bold;");

        // Modal Subtitle
        Text modalSubtitle = new Text("Give your pet a name and click confirm.");
        modalSubtitle.setFont(Font.font("Arial", 16));

        // TextField for input
        nameInput = new TextField();
        nameInput.setPromptText("Name me...");
        nameInput.setMaxWidth(400);
        nameInput.setStyle(
                "-fx-background-radius: 20; " +
                        "-fx-border-radius: 20; " +
                        "-fx-border-color: #dcdcdc; " +
                        "-fx-border-width: 1; " +
                        "-fx-padding: 10;");

        // Buttons
        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);

        Button cancelButton = new Button("Cancel");
        cancelButton.setStyle("-fx-background-color: transparent;");
        cancelButton.setOnAction(e -> namingModal.setVisible(false));

        Button confirmButton = new Button("Confirm");
        confirmButton.setStyle(
                "-fx-background-color: #638EFB; " +
                        "-fx-text-fill: #ffffff; " +
                        "-fx-background-radius: 15; " +
                        "-fx-padding: 10 20;");
        confirmButton.setOnAction(e -> {
            String petName = nameInput.getText(); // Get the pet name
            if (!petName.trim().isEmpty()) { // Ensure the user entered a name
                namingModal.setVisible(false);
                setupMainGameplay(contentLayout, petName); // Proceed to main gameplay
            } else {

                nameInput.setPromptText("Please enter a name!");
            }
        });

        buttonBox.getChildren().addAll(cancelButton, confirmButton);

        // Add all components to the modal
        namingModal.getChildren().addAll(modalTitle, modalSubtitle, nameInput, buttonBox);

        // Add the modal to the root layout inside a StackPane
        StackPane modalContainer = new StackPane();
        modalContainer.setAlignment(Pos.CENTER);
        modalContainer.getChildren().add(namingModal);
        modalContainer.setStyle("-fx-background-color: rgba(0,0,0,0.5);");
        modalContainer.setVisible(false);

        StackPane.setMargin(namingModal, new Insets(-30, 0, 0, 0));

        root.getChildren().add(modalContainer);

        namingModal.setVisible(false);
    }

    private void setupMainGameplay(VBox contentLayout, String petName) {
        contentLayout.getChildren().clear(); // Clear existing content in the layout

        // Top bar with back button and pet info
        HBox topBar = new HBox(10);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(20));

        // Back button with circle background and shadow
        StackPane backButtonContainer = new StackPane();
        Circle backButtonCircle = new Circle(25, Color.WHITE);
        backButtonCircle.setEffect(new DropShadow(10, Color.GRAY));

        FontIcon backIcon = new FontIcon("fas-chevron-left");
        backIcon.setIconSize(20);
        backIcon.setIconColor(Color.BLACK);

        Button backButton = new Button();
        backButton.setGraphic(backIcon);
        backButton.setStyle("-fx-background-color: transparent;");
        backButton.setOnAction(e -> returnToMainMenu());
        backButtonContainer.getChildren().addAll(backButtonCircle, backButton);

        // Pet info (displaying petName)
        Text petInfo = new Text("Welcome, " + petName + "!");
        petInfo.setFont(Font.font("Arial", 24));
        petInfo.setStyle("-fx-font-weight: bold;");

        topBar.getChildren().addAll(backButtonContainer, petInfo);

        // Gameplay content (example placeholder text)
        VBox gameplayContainer = new VBox();
        gameplayContainer.setAlignment(Pos.CENTER);
        gameplayContainer.setSpacing(20);
        gameplayContainer.setStyle("-fx-background-color: #f5f5f5;");
        gameplayContainer.setPadding(new Insets(20));

        Text gameplayText = new Text("Enjoy your adventure with " + petName + "!");
        gameplayText.setFont(Font.font("Arial", 20));

        gameplayContainer.getChildren().add(gameplayText);

        // Add top bar and gameplay container to the content layout
        contentLayout.getChildren().addAll(topBar, gameplayContainer);
    }

    private Button createSettingsButton() {
        Button settingsButton = new Button();
        FontIcon settingsIcon = new FontIcon("fas-cog");
        settingsIcon.setIconSize(20);
        settingsIcon.setIconColor(Color.BLACK);

        Circle settingsCircle = new Circle(25);
        settingsCircle.setFill(Color.WHITE);
        settingsCircle.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 5);");

        StackPane buttonContent = new StackPane(settingsCircle, settingsIcon);
        settingsButton.setGraphic(buttonContent);
        settingsButton.setStyle("-fx-background-color: transparent;");

        settingsButton.setOnAction(e -> {
            System.out.println("Settings button clicked!");

            // Show the settings modal
            if (settingsModal == null) {
                settingsModal = new SettingsModal();
            }
            settingsModal.setVisible(true);
        });

        return settingsButton;
    }

    private void returnToMainMenu() {
        gameLauncher.showMainMenu();
    }
}
