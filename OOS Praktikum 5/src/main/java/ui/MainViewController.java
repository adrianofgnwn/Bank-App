package ui;

import Bank.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Controller for the main view.
 * Manages accounts and navigates to account views.
 */
public class MainViewController {

    @FXML
    private ListView<String> accountListView; // ListView to display account names

    private final ObservableList<String> accounts = FXCollections.observableArrayList(); // Observable list of accounts
    private final PrivateBank privateBank; // Backend bank object

    /**
     * Constructor for MainViewController.
     * Initializes the PrivateBank instance and loads accounts.
     */
    public MainViewController() {
        privateBank = new PrivateBank("MyBank", 0.05, 0.1); // Create the bank instance
        try {
            privateBank.readAccounts(); // Load accounts from persistence
            accounts.addAll(privateBank.getAllAccounts());
        } catch (Exception e) {
            showError("Error loading accounts: " + e.getMessage());
        }
    }

    /**
     * Initializes the main view.
     * Sets up the ListView and context menu for accounts.
     */
    @FXML
    public void initialize() {
        accountListView.setItems(accounts);

        ContextMenu contextMenu = new ContextMenu();
        MenuItem selectItem = new MenuItem("Select");
        selectItem.setOnAction(e -> {
            String selectedAccount = accountListView.getSelectionModel().getSelectedItem();
            if (selectedAccount != null) {
                handleSelectAccount(selectedAccount);
            }
        });

        MenuItem deleteItem = new MenuItem("Delete");
        deleteItem.setOnAction(e -> {
            String selectedAccount = accountListView.getSelectionModel().getSelectedItem();
            if (selectedAccount != null) {
                handleDeleteAccount(selectedAccount);
            }
        });

        contextMenu.getItems().addAll(selectItem, deleteItem);
        accountListView.setContextMenu(contextMenu); // Attach context menu to ListView
    }

    /**
     * Handles selecting an account.
     *
     * @param account The account name to select.
     */
    private void handleSelectAccount(String account) {
        SceneManager.loadAccountView(privateBank, account);
    }

    /**
     * Handles deleting an account.
     *
     * @param account The account name to delete.
     */
    private void handleDeleteAccount(String account) {
        Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete the account: " + account + "?", ButtonType.YES, ButtonType.NO);
        confirmDialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                try {
                    privateBank.deleteAccount(account); // Delete from backend
                    accounts.remove(account); // Update ListView
                } catch (Exception e) {
                    showError("Failed to delete account: " + e.getMessage());
                }
            }
        });
    }

    /**
     * Handles adding a new account.
     *
     * @param event The event triggered by the add account button.
     */
    @FXML
    public void handleAddAccount(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Account");
        dialog.setHeaderText("Enter the name of the new account:");
        dialog.showAndWait().ifPresent(name -> {
            try {
                privateBank.createAccount(name); // Add account to backend
                accounts.add(name); // Update ListView
            } catch (Exception e) {
                showError("Failed to add account: " + e.getMessage());
            }
        });
    }

    /**
     * Displays an error alert.
     *
     * @param message The error message to show.
     */
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.show();
    }
}
