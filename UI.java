private void session(Account account) {
    while (true) {
        System.out.println("\n1 - Balance");
        System.out.println("2 - Deposit");
        System.out.println("3 - Withdraw");
        System.out.println("4 - Exit");
        System.out.print("Choice: ");

        String choice = scanner.nextLine();

        try {
            switch (choice) {
                case "1" -> displayBalance(account);

                case "2" -> {
                    System.out.print("Amount: ");
                    BigDecimal amount = new BigDecimal(scanner.nextLine());
                    account = account.deposit(amount);
                    bankService.update(account);
                }

                case "3" -> {
                    System.out.print("Amount: ");
                    BigDecimal amount = new BigDecimal(scanner.nextLine());
                    account = account.withdraw(amount);
                    bankService.update(account);
                }

                case "4" -> {
                    System.out.println("Session closed.");
                    return;
                }

                default -> System.out.println("Invalid option");
            }
        } catch (BankException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
Account account = bankService.authenticate(accountNumber, pin);
session(account);
