package ui;

import Bank.*;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.Optional;

/**
 * Controller for managing the account view.
 * Displays transactions and allows users to add, delete, and sort/filter transactions.
 */
public class AccountViewController {

    @FXML
    private ListView<Transaction> transactionListView; // ListView to display transactions
    @FXML
    private Label accountNameLabel, balanceLabel; // Labels for account name and balance

    private final ObservableList<Transaction> transactions = FXCollections.observableArrayList(); // Observable list for dynamic updates
    private final PrivateBank privateBank; // Backend bank object
    private final String account; // Current account name

    /**
     * Constructor for AccountViewController.
     *
     * @param privateBank The bank instance managing accounts and transactions.
     * @param account     The account name being managed.
     */
    public AccountViewController(PrivateBank privateBank, String account) {
        this.privateBank = privateBank;
        this.account = account;
        transactions.addAll(privateBank.getTransactions(account)); // Load transactions for the account
    }

    /**
     * Initializes the account view.
     * Sets up the ListView and context menu for transactions.
     */
    @FXML
    public void initialize() {
        accountNameLabel.setText("Account: " + account);
        updateBalance(); // Display initial balance
        transactionListView.setItems(transactions);

        // Add right-click context menu for transactions
        transactionListView.setCellFactory(lv -> {
            ListCell<Transaction> cell = new ListCell<>() {
                @Override
                protected void updateItem(Transaction transaction, boolean empty) {
                    super.updateItem(transaction, empty);
                    setText(empty || transaction == null ? "" : transaction.toString());
                }
            };

            // Create context menu for deleting transactions
            ContextMenu contextMenu = new ContextMenu();
            MenuItem deleteItem = new MenuItem("Delete Transaction");

            deleteItem.setOnAction(event -> {
                Transaction selectedTransaction = cell.getItem();
                if (selectedTransaction != null) {
                    handleDeleteTransaction(selectedTransaction);
                }
            });

            contextMenu.getItems().add(deleteItem);
            cell.setContextMenu(contextMenu);

            return cell;
        });
    }

    /**
     * Handles the deletion of a transaction.
     * Asks for confirmation before removing the transaction.
     *
     * @param transaction The transaction to be deleted.
     */
    private void handleDeleteTransaction(Transaction transaction) {
        Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmDialog.setTitle("Delete Transaction");
        confirmDialog.setHeaderText("Are you sure you want to delete this transaction?");
        confirmDialog.setContentText(transaction.toString());

        confirmDialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    privateBank.removeTransaction(account, transaction); // Remove from backend
                    transactions.remove(transaction); // Remove from UI
                    updateBalance(); // Update balance
                } catch (Exception e) {
                    showError("Failed to delete transaction: " + e.getMessage());
                }
            }
        });
    }

    /**
     * Handles adding a new transaction.
     * Prompts the user to choose a type and fill in details.
     */
    @FXML
    private void handleAddTransaction() {
        ChoiceDialog<String> choiceDialog = new ChoiceDialog<>("Payment", "Payment", "Transfer");
        choiceDialog.setTitle("Add Transaction");
        choiceDialog.setHeaderText("Choose Transaction Type");
        choiceDialog.setContentText("Type:");

        Optional<String> result = choiceDialog.showAndWait();
        result.ifPresent(choice -> {
            if ("Payment".equals(choice)) {
                showPaymentDialog(); // Open dialog for Payment
            } else {
                showTransferDialog(); // Open dialog for Transfer
            }
        });
    }

    /**
     * Opens a dialog for creating a Payment transaction.
     */
    private void showPaymentDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Add Payment");
        dialog.setHeaderText("Enter Payment Details");

        // Input fields
        TextField dateField = new TextField();
        dateField.setPromptText("DD.MM.YYYY");

        TextField amountField = new TextField();
        amountField.setPromptText("Amount");

        TextField descriptionField = new TextField();
        descriptionField.setPromptText("Description");

        TextField incomingInterestField = new TextField();
        incomingInterestField.setPromptText("Incoming Interest");

        TextField outgoingInterestField = new TextField();
        outgoingInterestField.setPromptText("Outgoing Interest");

        // Layout
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("Date:"), 0, 0);
        grid.add(dateField, 1, 0);
        grid.add(new Label("Amount:"), 0, 1);
        grid.add(amountField, 1, 1);
        grid.add(new Label("Description:"), 0, 2);
        grid.add(descriptionField, 1, 2);
        grid.add(new Label("Incoming Interest:"), 0, 3);
        grid.add(incomingInterestField, 1, 3);
        grid.add(new Label("Outgoing Interest:"), 0, 4);
        grid.add(outgoingInterestField, 1, 4);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Handle Input
        dialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    if (dateField.getText().isEmpty() || amountField.getText().isEmpty()
                            || descriptionField.getText().isEmpty() || incomingInterestField.getText().isEmpty()
                            || outgoingInterestField.getText().isEmpty()) {
                        showError("All fields must be filled!");
                        return;
                    }

                    String date = dateField.getText();
                    double amount = Double.parseDouble(amountField.getText());
                    String description = descriptionField.getText();
                    double incomingInterest = Double.parseDouble(incomingInterestField.getText());
                    double outgoingInterest = Double.parseDouble(outgoingInterestField.getText());

                    Payment payment = new Payment(date, amount, description, incomingInterest, outgoingInterest);
                    privateBank.addTransaction(account, payment);
                    transactions.add(payment);
                    updateBalance();
                } catch (NumberFormatException e) {
                    showError("Invalid input. Please ensure all numeric fields are valid.");
                } catch (Exception e) {
                    showError("An error occurred: " + e.getMessage());
                }
            }
        });
    }

    /**
     * Opens a dialog for creating a Transfer transaction.
     */
    private void showTransferDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Add Transfer");
        dialog.setHeaderText("Enter Transfer Details");

        // Input fields
        TextField dateField = new TextField();
        dateField.setPromptText("DD.MM.YYYY");

        TextField amountField = new TextField();
        amountField.setPromptText("Amount");

        TextField descriptionField = new TextField();
        descriptionField.setPromptText("Description");

        TextField senderField = new TextField();
        senderField.setPromptText("Sender");

        TextField recipientField = new TextField();
        recipientField.setPromptText("Recipient");

        // Layout
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("Date:"), 0, 0);
        grid.add(dateField, 1, 0);
        grid.add(new Label("Amount:"), 0, 1);
        grid.add(amountField, 1, 1);
        grid.add(new Label("Description:"), 0, 2);
        grid.add(descriptionField, 1, 2);
        grid.add(new Label("Sender:"), 0, 3);
        grid.add(senderField, 1, 3);
        grid.add(new Label("Recipient:"), 0, 4);
        grid.add(recipientField, 1, 4);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Handle Input
        dialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    if (dateField.getText().isEmpty() || amountField.getText().isEmpty()
                            || descriptionField.getText().isEmpty() || senderField.getText().isEmpty()
                            || recipientField.getText().isEmpty()) {
                        showError("All fields must be filled!");
                        return;
                    }

                    String date = dateField.getText();
                    double inputAmount = Double.parseDouble(amountField.getText());
                    double amount = Math.abs(inputAmount);
                    String description = descriptionField.getText();
                    String sender = senderField.getText();
                    String recipient = recipientField.getText();

                    Transaction transfer;
                    if (inputAmount < 0) {
                        transfer = new OutgoingTransfer(date, amount, description, sender, recipient);
                    } else {
                        transfer = new IncomingTransfer(date, amount, description, sender, recipient);
                    }

                    privateBank.addTransaction(account, transfer);
                    transactions.add(transfer);
                    updateBalance();
                } catch (NumberFormatException e) {
                    showError("Invalid input. Please ensure all numeric fields are valid.");
                } catch (Exception e) {
                    showError("An error occurred: " + e.getMessage());
                }
            }
        });
    }

    /**
     * Sorts the transactions in ascending order.
     */
    @FXML
    private void handleSortAsc() {
        transactions.setAll(privateBank.getTransactionsSorted(account, true));
    }

    /**
     * Sorts the transactions in descending order.
     */
    @FXML
    private void handleSortDesc() {
        transactions.setAll(privateBank.getTransactionsSorted(account, false));
    }

    /**
     * Filters the transactions to display only positive amounts.
     */
    @FXML
    private void handleFilterPositive() {
        transactions.setAll(privateBank.getTransactionsByType(account, true));
    }

    /**
     * Filters the transactions to display only negative amounts.
     */
    @FXML
    private void handleFilterNegative() {
        transactions.setAll(privateBank.getTransactionsByType(account, false));
    }

    /**
     * Navigates back to the main view.
     */
    @FXML
    private void handleBack() {
        SceneManager.loadMainView();
    }

    /**
     * Updates the displayed account balance.
     */
    private void updateBalance() {
        balanceLabel.setText("Balance: " + privateBank.getAccountBalance(account));
    }

    /**
     * Displays an error alert.
     *
     * @param message The error message to show.
     */
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
