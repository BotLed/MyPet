package application.components;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.geometry.Insets;

public class StatModal extends StackPane {

    private Runnable onCloseAction; // Action to perform when "Close" is clicked
    private Text modalTitle; // Dynamic title text
    private VBox buttonContainer; // Container for dynamic buttons

    public StatModal() {
        this.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);"); // Dim background
        this.setAlignment(Pos.CENTER); // Center modal on screen
        this.setVisible(false); // Initially hidden

        VBox modalContent = new VBox(20);
        modalContent.setAlignment(Pos.CENTER);
        modalContent.setStyle(
                "-fx-background-color: #ffffff; " +
                        "-fx-border-radius: 15; " + // Rounded corners
                        "-fx-background-radius: 15; " + // Rounded corners for the background
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 20, 0, 0, 10);");
        modalContent.setPadding(new Insets(30));
        modalContent.setMaxWidth(400);
        modalContent.setMaxHeight(300); // Increased height for additional buttons

        modalTitle = new Text("Stat Details");
        modalTitle.setFont(Font.font("Arial", 24));
        modalTitle.setStyle("-fx-font-weight: bold;");

        buttonContainer = new VBox(10); // Container for buttons
        buttonContainer.setAlignment(Pos.CENTER);

        Button closeButton = new Button("Close");
        closeButton.setStyle(
                "-fx-background-color: red; " + // Blue background
                        "-fx-text-fill: #ffffff; " + // White text
                        "-fx-background-radius: 15; " + // Rounded corners
                        "-fx-padding: 10 20;");
        closeButton.setOnAction(e -> {
            this.setVisible(false); // Hide the modal
            if (onCloseAction != null)
                onCloseAction.run(); // Execute the additional close action if set
        });

        modalContent.getChildren().addAll(modalTitle, buttonContainer, closeButton);
        this.getChildren().add(modalContent);
    }

    public void setOnCloseAction(Runnable action) {
        this.onCloseAction = action;
    }

    public void setTitle(String title) {
        modalTitle.setText(title); // Dynamically update title
        populateButtons(title); // Populate buttons based on title
    }

    private void populateButtons(String statName) {
        buttonContainer.getChildren().clear(); // Clear any existing buttons

        switch (statName) {
            case "Hunger":
                addButton("Food 1", () -> System.out.println("Food 1 clicked!"));
                addButton("Food 2", () -> System.out.println("Food 2 clicked!"));
                addButton("Food 3", () -> System.out.println("Food 3 clicked!"));
                break;

            case "Energy":
                addButton("Go to Sleep", () -> System.out.println("Pet is sleeping!"));
                break;

            case "Happiness":
                addButton("Play", () -> System.out.println("Playing with the pet!"));
                addButton("Gift 1", () -> System.out.println("Gift 1 clicked!"));
                addButton("Gift 2", () -> System.out.println("Gift 2 clicked!"));
                addButton("Gift 3", () -> System.out.println("Gift 3 clicked!"));
                break;

            case "Health":
                addButton("Take to Vet", () -> System.out.println("Taking the pet to the vet!"));
                addButton("Exercise", () -> System.out.println("Exercising the pet!"));
                break;

            default:
                System.out.println("Unknown stat: " + statName);
        }
    }

    private void addButton(String label, Runnable action) {
        Button button = new Button(label);
        button.setStyle(
                "-fx-background-color: #638EFB; " +
                        "-fx-text-fill: #ffffff; " +
                        "-fx-background-radius: 15; " +
                        "-fx-padding: 10 20;");
        button.setOnAction(e -> action.run());
        buttonContainer.getChildren().add(button);
    }

    public void show() {
        this.setVisible(true);
    }

    public void hide() {
        this.setVisible(false);
    }
}
