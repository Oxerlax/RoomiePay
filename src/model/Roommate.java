package model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * Represents a roommate and their running balance in an expense-splitting system.
 * A positive balance indicates money owed to the roommate,
 * while a negative balance indicates money the roommate owes.
 *
 * @author Kris Bali
 */
public class Roommate{
    /** The name of the roommate */
    private String name;
    /** The roommate's balance */
    private BigDecimal balance;

    /**
     * Roommate constructor
     * @param name The name of the roommate
     * @param balance The roommate's balance
     */
    public Roommate(String name, BigDecimal balance){
        this.name = name;
        this.balance = balance;
    }

    /**
     * @return The name of the roommate
     */
    public String getName(){
        return this.name;
    }

    /**
     * @return The balance of the roommate
     */
    public BigDecimal getBalance(){
        return this.balance;
    }

    /**
     * Modifies the roommates balance by a certain monetary amount.
     * @param amount The amount to modify the roommate's balance by.
     *               This is a number followed by 2 decimal places. Examples: 2.01, -3.14, 9.99, -120.00, etc.
     */
    public void updateBalance(BigDecimal amount){
        this.balance = this.balance.add(amount).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Converts the roommate's balance to 0, representing a balance of $0.00.
     */
    public void resetBalance(){
        this.balance = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public String toString(){
        return this.name + " Balance: $" + String.format("%.2f", this.balance.setScale(2, RoundingMode.HALF_UP));
    }

    /**
     * Compares this roommate to another object for equality.
     * Two roommates are considered equal if they have the same name
     * and the same monetary balance.
     * @param obj the object to compare against
     * @return true if the objects are equal, false otherwise
     */
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