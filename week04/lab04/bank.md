1. As precondition check the correstness of the argument and postcondition checks the result of the computation, then if the precondition and the postcondition does not fail, we then confirm that the code is consistent.

2. When withdrawing balance, the precondition (withdraw <= currentBalance) in the BankAccount class will keep the currentBalance to be >= 0.

3. My classes are following the Liskov Substitution Principle as the only difference between BankAcoount and LoggedBankAccount is that LoggedBankAccount will print out the actions when withdarwing and depositing.