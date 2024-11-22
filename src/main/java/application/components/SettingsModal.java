package application.components;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SettingsModal {

    public void show(Stage parentStage) {
        // Create a new stage for the modal
        Stage modalStage = new Stage();
        modalStage.initModality(Modality.APPLICATION_MODAL); // Block interactions with the parent
        modalStage.initOwner(parentStage);
        modalStage.initStyle(StageStyle.UNDECORATED); // Remove default window borders

        // Modal content layout
        VBox modalContent = new VBox(20);
        modalContent.setStyle("-fx-background-color: #ffffff; -fx-border-radius: 10; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0, 0, 5);");
        modalContent.setAlignment(Pos.CENTER);
        modalContent.setPrefSize(300, 200);

        // "Done" button
        Button doneButton = new Button("Done");
        doneButton.setFont(Font.font("Arial", 16));
        doneButton.setOnAction(event -> modalStage.close());

        // Add content to modal
        Text title = new Text("Settings");
        title.setFont(Font.font("Arial", 20));
        title.setFill(Color.BLACK);

        modalContent.getChildren().addAll(title, doneButton);

        // Create and set the scene for the modal
        Scene scene = new Scene(modalContent);
        modalStage.setScene(scene);
        modalStage.showAndWait(); // Display the modal and wait until it is closed
    }
}
