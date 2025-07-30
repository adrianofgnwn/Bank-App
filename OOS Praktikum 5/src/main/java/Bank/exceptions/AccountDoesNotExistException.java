package Bank.exceptions;

public class AccountDoesNotExistException extends Exception {
    public AccountDoesNotExistException(String message) {
      super(message);
    }
}
