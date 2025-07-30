package ui;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main application entry point for the JavaFX bank application.
 */
public class FxApplication extends Application {

    /**
     * Launches the JavaFX application.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Sets up the primary stage and loads the main view.
     *
     * @param primaryStage The main application window.
     */
    @Override
    public void start(Stage primaryStage) {
        SceneManager.setStage(primaryStage); // Initialize SceneManager with the primary stage
        SceneManager.loadMainView(); // Load the main view
        primaryStage.setTitle("Bank Application");
        primaryStage.show(); // Display the application
    }
}
