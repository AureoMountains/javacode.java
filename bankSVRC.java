public void update(Account account) {
    database.put(account.getAccountNumber(), account);
}

public String getAccountNumber() {
    return accountNumber;
}
