package edu.ithaca.dturnbull.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class BankAccountTest {

    @Test
    void getBalanceTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals(200, bankAccount.getBalance(), 0.001); // valid equivalence class check the balance after creation

        BankAccount bankAccount3 = new BankAccount("h@i.com", 1000.50); //checks you can make a bank account with decimal starting balance
        assertEquals(1000.50, bankAccount3.getBalance(), 0.001); // valid equivalence class

        BankAccount bankAccount4 = new BankAccount("y@o.com", 50); //accounts for human error (changes -50 to 50)
        assertEquals(50, bankAccount4.getBalance(), 0.001); // valid equivalence class

        //getBalance on its own has no invalid equivalence classes because there are no inputs
        
    

    }

    @Test
    void withdrawTest() throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);

        assertEquals(100, bankAccount.getBalance(), 0.001); // valid equivalence class
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300)); //invalid equivalence class
       
        bankAccount.withdraw(100);
        assertEquals(0, bankAccount.getBalance(), 0.001); // boundary case



        BankAccount bankAccount2 = new BankAccount("c@b.com", 500);
        bankAccount2.withdraw(200);

        assertEquals(300, bankAccount2.getBalance(), 0.001); // valid equivalence class
        

        assertThrows(IllegalArgumentException.class, () -> bankAccount2.withdraw(0)); // boundary case

        BankAccount bankAccount3 = new BankAccount("d@e.com", 1000);
        bankAccount3.withdraw(-500);
        assertEquals(500, bankAccount3.getBalance(), 0.001); // invalid equivalence class

        BankAccount bankAccount4 = new BankAccount("d@e.com", 1000);
        assertThrows(IllegalArgumentException.class, () -> bankAccount4.withdraw(500.678)); // inavalid equivalence class amount with more than 2 decimal places
        bankAccount4.withdraw(50.50);
        assertEquals(949.50, bankAccount4.getBalance(), 0.001); // valid equivalence class amount with 2 decimal places


        

        


    }

    @Test
    void isEmailValidTest(){
        assertFalse(BankAccount.isEmailValid("")); // empty string //boundary case
        assertTrue(BankAccount.isEmailValid( "callahan45@yahoo.com"));   // valid email address // valid equivalence class
        assertTrue(BankAccount.isEmailValid( "omah@keyqaad.com"));   // valid email address // valid equivalence class
        assertTrue(BankAccount.isEmailValid("2mark@yahoo.com")); // local part starts with number //valid equivalence class
        assertTrue(BankAccount.isEmailValid("Apple15@gmail.com")); // valid equivalence class


        assertFalse(BankAccount.isEmailValid("mark")); // no @ symbol or domain // invalid equivalence class
        assertFalse(BankAccount.isEmailValid("mark@")); // no domain // invalid equivalence class
        assertFalse(BankAccount.isEmailValid("@yahoo.com")); // no local part //invalid equivalence class
        assertFalse(BankAccount.isEmailValid("mark@yahoo")); // no top-level domain // invalid equivalence class
        assertFalse(BankAccount.isEmailValid("mark@.com")); // no second-level domain // invalid equivalence class
        assertFalse(BankAccount.isEmailValid("mark@@yahoo.com")); // double @ // invalid equivalence class
        assertFalse(BankAccount.isEmailValid("mark yahoo.com")); // space instead of @ // invalid equivalence class
        assertFalse(BankAccount.isEmailValid(null)); // null string //boundary case
        assertFalse(BankAccount.isEmailValid(".mark@yahoo.com")); // local part starts with special character // invalid equivalence class
        assertFalse(BankAccount.isEmailValid("mark@ya hoo.com")); // any spaces // invalid equivalence class
        // equivalence class test missing: local part starts with special character other than '.'
        assertFalse(BankAccount.isEmailValid("$mark@yahoo.com")); // invalid equivalence class
        // equivalence class test missing: second level domain starts with special character
        assertFalse(BankAccount.isEmailValid("mark@.yahoo.com")); // invalid equivalence class
        assertFalse(BankAccount.isEmailValid("mark@#yahoo.com")); // invalid equivalence class
    }

    @Test
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance(), 0.001);
        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100));

        assertThrows(IllegalArgumentException.class, () -> new BankAccount("d@b.com", 300.567)); // invalid equivalence class amount with more than 2 decimal places

        BankAccount bankAccount2 = new BankAccount("g@h.com", 50.550);
        assertEquals(50.550, bankAccount2.getBalance(), 0.001); // boundary case amount with 3 decimal places but last is 0
       
        assertThrows(IllegalArgumentException.class, () -> new BankAccount("d@d.com", 0)); // valid equivalence class amount of 0
        assertThrows(IllegalArgumentException.class, () -> new BankAccount("d@e.com", 90.578)); // invalid equivalence 
        assertThrows(IllegalArgumentException.class, () -> new BankAccount("d@f.com", -100.56)); // invalid equivalence class 

    }
    

    @Test
    void isAmountValidTest(){
        assertTrue(BankAccount.isAmountValid(100)); // valid equivalence class
        assertTrue(BankAccount.isAmountValid(100.00)); // valid equivalence class
        assertTrue(BankAccount.isAmountValid(50.75)); // valid equivalence class

        assertFalse(BankAccount.isAmountValid(0)); // boundary case
        assertFalse(BankAccount.isAmountValid(0.00)); // boundary case


        assertFalse(BankAccount.isAmountValid(-50)); // invalid equivalence class
        assertFalse(BankAccount.isAmountValid(-50.01)); // invalid equivalence class
    }

    @Test
    void depositTest(){
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.deposit(100);
        assertEquals(300, bankAccount.getBalance(), 0.001); // valid equivalence class
        bankAccount.deposit(50.75);
        assertEquals(350.75, bankAccount.getBalance(), 0.001); // valid equivalence class
        bankAccount.deposit(0.01);
        assertEquals(350.76, bankAccount.getBalance(), 0.001); // boundary case

        bankAccount.deposit(99.99);
        assertEquals(450.75, bankAccount.getBalance(), 0.001); // valid equivalence class
        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(0)); // boundary case amount is invalid
        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(-20)); // invalid equivalence class amount is invalid
        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(30.678)); // invalid equivalence class amount with more than 2 decimal places

    }

    @Test
    void transferToTest() throws InsufficientFundsException{
        BankAccount bankAccount1 = new BankAccount("a@b.com", 5000);
        BankAccount bankAccount2 = new BankAccount("d@e.com", 200);
        BankAccount bankAccount3 = new BankAccount("f@g.com", 100);

        bankAccount1.transferTo(bankAccount2, 1000);
        assertEquals(4000, bankAccount1.getBalance(), 0.001); // valid equivalence class
        assertEquals(1200, bankAccount2.getBalance(), 0.001); // valid equivalence class
        bankAccount2.transferTo(bankAccount3, 200);
        assertEquals(1000, bankAccount2.getBalance(), 0.001); // valid equivalence class
        assertEquals(300, bankAccount3.getBalance(), 0.001); // valid equivalence class

        assertThrows(InsufficientFundsException.class, () -> bankAccount3.transferTo(bankAccount1, 500)); // invalid equivalence class 
        assertThrows(IllegalArgumentException.class, () -> bankAccount1.transferTo(bankAccount2, 0)); // boundary case amount is invalid
        assertThrows(IllegalArgumentException.class, () -> bankAccount2.transferTo(bankAccount3, -50)); // invalid equivalence class amount is invalid
        assertThrows(IllegalArgumentException.class, () -> bankAccount1.transferTo(bankAccount3, 100.789)); // invalid equivalence class amount with more than 2 decimal places
    }   

}
