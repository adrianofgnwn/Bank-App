package ui;

import Bank.PrivateBank;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Manages loading and switching between scenes in the application.
 */
public class SceneManager {
    private static Stage stage;

    /**
     * Sets the primary stage for the application.
     *
     * @param primaryStage The primary stage.
     */
    public static void setStage(Stage primaryStage) {
        stage = primaryStage;
    }

    /**
     * Loads the main view.
     */
    public static void loadMainView() {
        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource("/MainView.fxml"));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the account view for a specific account.
     *
     * @param privateBank The bank instance.
     * @param account     The account to display.
     */
    public static void loadAccountView(PrivateBank privateBank, String account) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource("/AccountView.fxml"));
            AccountViewController controller = new AccountViewController(privateBank, account);
            loader.setController(controller); // Set the custom controller
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
