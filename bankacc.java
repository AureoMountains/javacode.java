import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class BankApplication {

    public static void main(String[] args) {
        ConsoleUI ui = new ConsoleUI(new BankService());
        ui.start();
    }
}

class ConsoleUI {

    private final BankService bankService;
    private final Scanner scanner = new Scanner(System.in);

    ConsoleUI(BankService bankService) {
        this.bankService = bankService;
    }

    public void start() {
        System.out.println("=== Secure Banking Terminal ===");
        System.out.print("Account Number: ");
        String accountNumber = scanner.nextLine().trim();

        System.out.print("PIN: ");
        String pin = scanner.nextLine().trim();

        try {
            Account account = bankService.authenticate(accountNumber, pin);
            displayBalance(account);
        } catch (BankException e) {
            System.out.println("Access denied: " + e.getMessage());
        }
    }

    private void displayBalance(Account account) {
        System.out.println();
        System.out.println("Welcome, " + account.getOwnerName());
        System.out.println("Account: " + account.getMaskedAccountNumber());
        System.out.println("As of: " + LocalDateTime.now());
        System.out.println("----------------------------------");
        System.out.println("Available Balance: $" + account.getBalance());
        System.out.println("----------------------------------");
    }
}

class BankService {

    private final Map<String, Account> database = new HashMap<>();

    BankService() {
        seedData();
    }

    public Account authenticate(String accountNumber, String pin) {
        Account account = database.get(accountNumber);

        if (account == null) {
            throw new BankException("Account not found");
        }

        if (!account.verifyPin(pin)) {
            throw new BankException("Invalid credentials");
        }

        return account;
    }

    private void seedData() {
        database.put(
            "98341276",
            new Account(
                "98341276",
                "Marcello C.",
                "4321",
                new BigDecimal("15842.73")
            )
        );

        database.put(
            "77129001",
            new Account(
                "77129001",
                "Ana P.",
                "8899",
                new BigDecimal("320.50")
            )
        );
    }
}

class Account {

    private final String accountNumber;
    private final String ownerName;
    private final String pinHash;
    private final BigDecimal balance;

    Account(String accountNumber, String ownerName, String pin, BigDecimal balance) {
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.pinHash = hash(pin);
        this.balance = balance.setScale(2, RoundingMode.HALF_UP);
    }

    public boolean verifyPin(String pin) {
        return hash(pin).equals(pinHash);
    }

    public String getOwnerName() {
        return ownerName;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public String getMaskedAccountNumber() {
        return "****" + accountNumber.substring(accountNumber.length() - 4);
    }

    private String hash(String input) {
        return Integer.toHexString(input.hashCode());
    }
}

class BankException extends RuntimeException {
    BankException(String message) {
        super(message);
    }
}

public Account deposit(BigDecimal amount) {
    return new Account(accountNumber, ownerName, pinHash, balance.add(amount));
}


