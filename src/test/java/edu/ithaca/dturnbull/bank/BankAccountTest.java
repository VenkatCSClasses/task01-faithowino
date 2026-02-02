package edu.ithaca.dturnbull.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class BankAccountTest {

    @Test
    void getBalanceTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals(200, bankAccount.getBalance(), 0.001);
    }

    @Test
    void withdrawTest() throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);

        assertEquals(100, bankAccount.getBalance(), 0.001); // valid equivalence class
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300)); //invalid equivalence class
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(-50)); // invalid equivalence class

        BankAccount bankAccount2 = new BankAccount("c@b.com", 500);
        bankAccount2.withdraw(200);

        assertEquals(300, bankAccount2.getBalance(), 0.001); // valid equivalence class
        

        assertThrows(IllegalArgumentException.class, () -> bankAccount2.withdraw(0)); // boundary case

        BankAccount bankAccount3 = new BankAccount("d@e.com", 1000);
        bankAccount3.withdraw(10001);

        assertEquals(500, bankAccount3.getBalance(), 0.001); // valid equivalence class
    

        





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
    }
   

}