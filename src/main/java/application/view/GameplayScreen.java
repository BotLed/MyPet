package application.view;

import org.kordamp.ikonli.javafx.FontIcon;
import application.GameLauncher;
import application.components.SettingsModal;
import application.components.StatModal;
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
import javafx.scene.shape.Rectangle;

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
    private StackPane statsContainer;
    private StatModal statModal;

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
        // Create the root container
        root = new VBox();
        root.setPadding(new Insets(0));
        root.setStyle("-fx-background-color: #f5f5f5;");
        root.setAlignment(Pos.TOP_CENTER);

        // StackPane for layered layout
        StackPane layeredLayout = new StackPane();
        layeredLayout.setStyle("-fx-background-color: transparent;");
        layeredLayout.setPrefSize(1200, 800);

        // Initialize content layout (main game area)
        contentLayout = new VBox();
        contentLayout.setAlignment(Pos.TOP_CENTER);
        contentLayout.setPadding(new Insets(20));

        // Add content layout to the layered layout
        layeredLayout.getChildren().add(contentLayout);

        // Initialize stats container
        statsContainer = createStatsContainer();
        statsContainer.setVisible(false); // Hidden by default
        statsContainer.setMouseTransparent(true); // Allow interaction
        layeredLayout.getChildren().add(statsContainer); // Add statsContainer to the StackPane
        StackPane.setAlignment(statsContainer, Pos.BOTTOM_LEFT);
        StackPane.setMargin(statsContainer, new Insets(20, 0, 20, 20)); // Positioning for stats bars

        // Initialize the naming modal
        setupNamingModal();
        namingModal.setVisible(false); // Initially hidden
        layeredLayout.getChildren().add(namingModal);

        // Initialize and add the settings modal
        settingsModal = new SettingsModal();
        settingsModal.setVisible(false); // Hidden initially
        settingsModal.setOnDoneAction(() -> {
            System.out.println("Settings modal closed.");
            settingsModal.setVisible(false);
        });
        layeredLayout.getChildren().add(settingsModal);

        // Initialize and add the stat modal
        statModal = new StatModal();
        statModal.setVisible(false); // Hidden initially
        layeredLayout.getChildren().add(statModal);

        // Add the settings button
        Button settingsButton = createSettingsButton();
        layeredLayout.getChildren().add(settingsButton);
        StackPane.setAlignment(settingsButton, Pos.BOTTOM_RIGHT);
        StackPane.setMargin(settingsButton, new Insets(20));

        // Add the layered layout to the root
        root.getChildren().add(layeredLayout);

        // Handle new game or resume game
        if (isNewGame) {
            setupPetSelection(contentLayout); // Add pet selection for new games
        } else {
            // Properly handle existing games
            setupMainGameplay(contentLayout, petName);
            statsContainer.setVisible(true); // Show stats for existing games
        }
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

    public void setupMainGameplay(VBox root, String petName) {
        root.getChildren().clear(); // Clear previous content

        // Top bar with back button and welcome message
        HBox topBar = new HBox();
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(10));
        topBar.setSpacing(10);

        Button backButton = new Button();
        backButton.setStyle("-fx-background-color: #ffffff; -fx-border-radius: 50; -fx-background-radius: 50;");
        backButton.setGraphic(new FontIcon("fas-chevron-left"));
        backButton.setOnAction(e -> gameLauncher.showMainMenu());

        Label welcomeLabel = new Label("Welcome, " + petName + "!");
        welcomeLabel.setStyle("-fx-font-size: 24; -fx-font-weight: bold;");

        topBar.getChildren().addAll(backButton, welcomeLabel);
        root.getChildren().add(topBar);

        // Main content area
        HBox contentArea = new HBox();
        contentArea.setAlignment(Pos.CENTER);
        contentArea.setPadding(new Insets(20));
        contentArea.setSpacing(50);

        // Reuse statsContainer
        statsContainer.setVisible(true);
        // statsContainer.setMouseTransparent(true);

        // Right side for pet image
        VBox rightSide = new VBox();
        rightSide.setAlignment(Pos.CENTER);

        Rectangle petImagePlaceholder = new Rectangle(250, 500);
        petImagePlaceholder.setFill(Color.LIGHTGRAY);
        petImagePlaceholder.setStroke(Color.DARKGRAY);
        petImagePlaceholder.setArcWidth(20);
        petImagePlaceholder.setArcHeight(20);

        rightSide.getChildren().add(petImagePlaceholder);

        contentArea.getChildren().addAll(rightSide);
        root.getChildren().add(contentArea);
    }

    private StackPane createStatsContainer() {
        VBox statsBox = new VBox(10); // Space between bars
        statsBox.setAlignment(Pos.BOTTOM_LEFT);
        statsBox.setPadding(new Insets(10)); // Padding inside the container

        String[] statNames = { "Health", "Hunger", "Happiness", "Energy" };
        String[] fontAwesomeIcon = { "fas-heart", "fas-utensils", "fas-smile", "fas-bolt" };

        for (int i = 0; i < fontAwesomeIcon.length; i++) {
            VBox statBar = createStatBar(statNames[i], fontAwesomeIcon[i]);
            statsBox.getChildren().add(statBar);
        }

        StackPane statsContainer = new StackPane();
        statsContainer.getChildren().add(statsBox);
        StackPane.setAlignment(statsBox, Pos.BOTTOM_LEFT);
        return statsContainer;
    }

    // Helper method to create a stat bar without fill
    private VBox createStatBar(String title, String fontAwesomeIcon) {
        VBox statContainer = new VBox(5); // Space between title and bar
        statContainer.setAlignment(Pos.BOTTOM_LEFT);

        // Title for the stat
        Label statTitle = new Label(title);
        statTitle.setStyle("-fx-font-size: 14; -fx-font-weight: bold; -fx-text-fill: black;");

        // Stat bar container
        HBox statBar = new HBox();
        statBar.setAlignment(Pos.CENTER_LEFT);
        statBar.setStyle(
                "-fx-background-color: #f0f0f0; " + // Background color for the bar
                        "-fx-border-radius: 20; " + // Rounded corners for the border
                        "-fx-background-radius: 20; " + // Rounded corners for the background
                        "-fx-border-color: black; " + // Black border
                        "-fx-border-width: 1px;" // Thin border (1 pixel)
        );
        statBar.setPrefHeight(40);
        statBar.setPrefWidth(250); // Width of the stat bar
        statBar.setMaxWidth(250); // Constrain the max width

        // Prevent horizontal stretching
        HBox.setHgrow(statBar, Priority.NEVER);

        // Font Awesome icon with padding
        FontIcon statIcon = new FontIcon();
        statIcon.setIconLiteral(fontAwesomeIcon); // Use the icon literal here
        statIcon.setIconSize(16); // Set the desired size
        statIcon.setIconColor(Color.BLACK); // Set the color

        // Wrap the icon in a StackPane to apply padding
        StackPane iconWrapper = new StackPane(statIcon);
        iconWrapper.setPadding(new Insets(5, 10, 5, 10)); // Add padding around the icon

        // Add the icon wrapper to the stat bar
        statBar.getChildren().add(iconWrapper);

        statBar.setOnMouseClicked(event -> {
            System.out.println("Stat bar clicked: " + title); // Log the click
            statModal.setTitle(title); // Set modal title based on clicked bar
            statModal.setVisible(true);
            statModal.setMouseTransparent(false); // Make modal interactive

        });

        // Add to the container
        statContainer.getChildren().addAll(statTitle, statBar);
        return statContainer;
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
