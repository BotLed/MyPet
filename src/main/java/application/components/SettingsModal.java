package application.components;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.geometry.Insets;

public class SettingsModal extends StackPane {

    private Runnable onDoneAction; // Action to perform when "Done" is clicked

    public SettingsModal() {
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
        modalContent.setMaxHeight(200);

        Text modalTitle = new Text("Volume Controls");
        modalTitle.setFont(Font.font("Arial", 24));
        modalTitle.setStyle("-fx-font-weight: bold;");

        Button doneButton = new Button("Done");
        doneButton.setStyle(
                "-fx-background-color: #638EFB; " + // Green background
                        "-fx-text-fill: #ffffff; " + // White text
                        "-fx-background-radius: 15; " + // Rounded corners
                        "-fx-padding: 10 20;");
        doneButton.setOnAction(e -> {
            if (onDoneAction != null)
                onDoneAction.run(); // Execute onDoneAction
        });

        modalContent.getChildren().addAll(modalTitle, doneButton);
        this.getChildren().add(modalContent);
    }

    public void setOnDoneAction(Runnable action) {
        this.onDoneAction = action;
    }

    public void show() {
        this.setVisible(true);
    }

    public void hide() {
        this.setVisible(false);
    }
}
