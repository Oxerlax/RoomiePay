package model;

public class Roommate{
    private String name;
    private double balance;

    public Roommate(String name){
        this.name = name;
        this.balance = 0.0;
    }

    public String getName(){
        return this.name;
    }

    public double getBalance(){
        return this.balance;
    }

    public void updateBalance(double amount){
        this.balance += amount;
    }

    public void resetBalance(){
        this.balance = 0.0;
    }

    @Override
    public String toString(){
        return this.name + " Balance: $" + String.format("%.2f", this.balance);
    }
}