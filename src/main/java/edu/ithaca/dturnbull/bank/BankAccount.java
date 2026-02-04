package edu.ithaca.dturnbull.bank;

public class BankAccount {

    private String email;
    private double balance;


    /**
     * @throws IllegalArgumentException if email is invalid
     */
    public BankAccount(String email, double startingBalance){
        if (isEmailValid(email)){
            this.email = email;
            this.balance = startingBalance;
        }
        else {
            throw new IllegalArgumentException("Email address: " + email + " is invalid, cannot create account");
        }
    }

    public double getBalance(){
        return balance;
    }

    public String getEmail(){
        return email;
    }

    /**
     * @post reduces the balance by amount if amount is non-negative and smaller than balance
     */
    public void withdraw (double amount) throws InsufficientFundsException{

        if (Math.abs(amount) == 0){
            throw new IllegalArgumentException("Withdrawal amount must be greater than 0");
        }

        else if(Math.abs(amount) <= balance && Math.abs(amount) >= 0){
            balance -= Math.abs(amount);
        }
        else {
            throw new InsufficientFundsException("Not enough money");
        }
    }


    public static boolean isEmailValid(String email){
        if(email == null || email.length() == 0){
            return false;
        }
        if (email.contains(" ") ){ //checks for spaces
            return false;
        }

        int atIndex = email.indexOf('@'); //index of @ symbol
        if (atIndex == -1 || atIndex != email.lastIndexOf('@')) { //checks there is an @ symbol & checks first and last are the same
            return false;
        }
        
        String localPart = email.substring(0, atIndex); //local part/user is before the @ symbol
        if (localPart.length() == 0 ){
            return false;
        }
        if (!Character.isLetter(localPart.charAt(0)) && !Character.isDigit(localPart.charAt(0))){ //checks first character of local part is a letter or digit
            return false;
        }

        String domain = email.substring(atIndex+1, email.length()); //domain part is after the @ symbol
        if (domain.length() == 0){ //checks for empty local or domain part
            return false;
        }
        
        int dotIndex = domain.indexOf('.'); //index of first dot in domain
        if (dotIndex == -1 || dotIndex != domain.lastIndexOf('.')) { //checks there is an . symbol & checks first and last are the same
            return false;
        }

        String secondLevel = domain.substring(0, dotIndex);
        String topLevel = domain.substring(dotIndex+1, domain.length());

        if (secondLevel.length() == 0 || topLevel.length() == 0) {
            return false;
        }
        if (!Character.isLetter(secondLevel.charAt(0)) || !Character.isLetter(secondLevel.charAt(secondLevel.length()-1))){ //checks first & last character of second-level domain is a letter
            return false;
        }
        if (!Character.isLetter(topLevel.charAt(0)) || !Character.isLetter(topLevel.charAt(topLevel.length()-1))){ //checks first & last character of top-level domain is a letter
            return false;
        }
        else {
            return true;
        }
    }

    public static boolean isAmountValid(double amount){

        if (amount <= 0){
            return false;
        } 
        double scaledAmount = amount * 100;
        double remainder = scaledAmount - (long) scaledAmount;
       
        return remainder < 0.001;
       
    }

    
}