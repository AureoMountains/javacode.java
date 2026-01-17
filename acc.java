public Account deposit(BigDecimal amount) {
    return new Account(accountNumber, ownerName, pinHash, balance.add(amount));
}

public Account withdraw(BigDecimal amount) {
    if (balance.compareTo(amount) < 0) {
        throw new BankException("Insufficient funds");
    }
    return new Account(accountNumber, ownerName, pinHash, balance.subtract(amount));
}
