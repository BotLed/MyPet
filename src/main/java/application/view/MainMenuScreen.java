package application.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.kordamp.ikonli.javafx.FontIcon;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.stage.StageStyle;

import application.components.SettingsModal;

public class MainMenuScreen {

    public Scene getScene() {
        // Root container
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f5f5f5;"); 

        // Title at the top
        Text title = new Text("My Pet");
        title.setFont(Font.font("Arial", FontWeight.BLACK, 80));
        BorderPane.setAlignment(title, Pos.TOP_LEFT);
        BorderPane.setMargin(title, new Insets(20, 0, 0, 50));
        root.setTop(title);

        // Center layout for menu options
        VBox menuBox = createMenuBox();
        root.setLeft(menuBox);
        BorderPane.setMargin(menuBox, new Insets(30, 0, 0, 50));

        // Placeholder for image on the right
        StackPane imagePlaceholder = createImagePlaceholder(); 
        root.setRight(imagePlaceholder);
        BorderPane.setMargin(imagePlaceholder, new Insets(0, 50, 0, 0));


        // Footer at the bottom
        BorderPane footer = createFooter();

        root.setBottom(footer);

        // Create scene (size is inherited from GameLauncher)
        return new Scene(root);
    }

    private VBox createMenuBox() {
        VBox menuBox = new VBox(10);
        menuBox.setAlignment(Pos.TOP_LEFT);
        menuBox.setPadding(new Insets(20));
        menuBox.setStyle("-fx-background-color: #ffffff; -fx-border-radius: 10; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 5);");
        menuBox.setMinWidth(400); 


        // Menu options
        String[][] menuItems = {
            {"Start", "fas-play"}, 
            {"Load Game", "fas-download"},
            {"Tutorial", "fas-info-circle"},
            {"Parent Zone", "fas-lock"},
            {"Exit", "fas-times"}
        };
        for (String[] item : menuItems) {
            HBox menuItemBox = createMenuItem(item[0], item[1]); 
            menuBox.getChildren().add(menuItemBox);
            VBox.setVgrow(menuItemBox, Priority.ALWAYS); // Even spacing
        }
    
        return menuBox;
    }

    // Method to create a single menu item with a specific icon
    private HBox createMenuItem(String text, String iconLiteral) {
        HBox menuItemBox = new HBox(20); // Horizontal box for icon and text
        menuItemBox.setAlignment(Pos.CENTER_LEFT);

        // Create icon for the menu item
        FontIcon menuIcon = new FontIcon(iconLiteral);
        menuIcon.setIconSize(35); 
        menuIcon.setIconColor(Color.BLACK); 

        // Create menu text
        Text menuText = new Text(text);
        menuText.setFont(Font.font("Arial", 36)); 

        // Add the icon and text to the menu item box
        menuItemBox.getChildren().addAll(menuIcon, menuText);

        return menuItemBox;
    }

    private StackPane createImagePlaceholder() {
        // Placeholder box
        VBox imageBox = new VBox();
        imageBox.setAlignment(Pos.CENTER);
        imageBox.setStyle("-fx-background-color: transparent; -fx-border-radius: 10; -fx-background-radius: 10;");
        imageBox.setPrefSize(400, 600); // Placeholder dimensions
    
        // Image to overlay
        javafx.scene.image.ImageView imageView = new javafx.scene.image.ImageView(
            getClass().getResource("/BoJack.png").toExternalForm() 
        );
        imageView.setFitWidth(500); 
        imageView.setFitHeight(600);
        imageView.setPreserveRatio(true); 
    
        // Stack placeholder and image
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(imageBox, imageView);
    
        return stackPane; 
    }
    

    private BorderPane createFooter() {
        BorderPane footer = new BorderPane();
        footer.setPadding(new Insets(10, 20, 10, 20));
        
        // Footer text aligned to the bottom left
        Text footerText = new Text("CS2212 Fall 2024. Created by: Abdul-Waali Raza Khan, Abdulaslam Shola Ameen, Andrey Velichko, Maximilian Hines Cope, Ryan Frank Wagner");
        footerText.setFont(Font.font("Arial", 12));
        footerText.setFill(Color.GRAY);
        
        
        VBox textWrapper = new VBox(footerText);
        textWrapper.setPadding(new Insets(20, 0, 0, 30)); // Adjust padding to move text lower
        footer.setLeft(textWrapper);
    
        // Settings icon
        FontIcon settingsIcon = new FontIcon("fas-cog");
        settingsIcon.setIconSize(20); 
        settingsIcon.setIconColor(Color.BLACK);
    
        // Add circle background with shadow
        Circle settingsCircle = new Circle(30); 
        settingsCircle.setFill(Color.WHITE); 
        settingsCircle.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 5);"); // Shadow effect
    
        
        StackPane settingsStack = new StackPane(settingsCircle, settingsIcon);

        settingsStack.setOnMouseClicked(event -> {
            // Create and show the modal
            showSettingsModal();
        });
        
        VBox settingsWrapper = new VBox(settingsStack);
        settingsWrapper.setAlignment(Pos.BOTTOM_RIGHT); 
        settingsWrapper.setPadding(new Insets(-30, 0, 0, 0)); 
    
        footer.setRight(settingsWrapper);
    
        return footer;
    }

    private void showSettingsModal() {
        Stage modalStage = new Stage();
        modalStage.initModality(Modality.APPLICATION_MODAL); // Block interactions with the parent stage
        modalStage.initStyle(StageStyle.UNDECORATED); // No window borders
    
        // Modal content
        VBox modalContent = new VBox(20);
        modalContent.setAlignment(Pos.CENTER);
        modalContent.setStyle("-fx-background-color: #ffffff; -fx-border-radius: 10; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0, 0, 5);");
        modalContent.setPrefSize(300, 200);
    
        // Title text for the modal
        Text modalTitle = new Text("Settings");
        modalTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        modalTitle.setFill(Color.BLACK);
    
        // Done button to close the modal
        Button doneButton = new Button("Done");
        doneButton.setFont(Font.font("Arial", 16));
        doneButton.setOnAction(e -> modalStage.close());
    
        modalContent.getChildren().addAll(modalTitle, doneButton);
    
        // Set up the scene and show the modal
        Scene modalScene = new Scene(modalContent);
        modalStage.setScene(modalScene);
        modalStage.showAndWait(); // Wait for the modal to close
    }
    
    
    
    
    
}
