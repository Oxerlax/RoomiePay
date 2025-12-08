package model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class Roommate{
    private String name;
    private BigDecimal balance;

    public Roommate(String name, BigDecimal balance){
        this.name = name;
        this.balance = balance;
    }

    public String getName(){
        return this.name;
    }

    public BigDecimal getBalance(){
        return this.balance;
    }

    public void updateBalance(double amount){
        BigDecimal moneyAmount = BigDecimal.valueOf(amount);
        this.balance = this.balance.add(moneyAmount).setScale(2, RoundingMode.HALF_UP);
    }

    public void resetBalance(){
        this.balance = BigDecimal.ZERO;
    }

    @Override
    public String toString(){
        return this.name + " Balance: $" + String.format("%.2f", this.balance);
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj){
            return true;
        }

        if (obj == null || getClass() != obj.getClass()){
            return false;
        }

        Roommate otherRoommate = (Roommate) obj;
        return this.name.equals(otherRoommate.name) && this.balance.compareTo(((Roommate) obj).getBalance()) == 0;
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.name, this.balance);
    }
}