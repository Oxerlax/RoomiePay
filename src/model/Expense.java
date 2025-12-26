package model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a typical expense that roommates may share, such as dishes, cutlery, food, etc.
 * One person pays for the entire expense, decides whether they contribute to paying this expense,
 * and then decide among their roommates money owed to the person that paid the expense in full.
 *
 * Example:
 *      Say we have a roommate named Robert.
 *      Robert and his 4 roommates have an expense, named Groceries, costing a total of $20.00.
 *      Robert will be the payer of this expense, meaning he will pay it in full.
 *      Robert has a choice; contribute to this expense or not.
 *      Robert must still pay the $20.00 regardless of whether he will contribute to this expense or not.
 *
 *      Contribute:
 *          If Robert contributes to this expense, each person, including Robert, will pay $4.00 for this expense.
 *          So, Robert pays the expense of $20.00, but since he contributes $4.00, he now has a balance of 16.00.
 *          This means Robert is owed $16.00.
 *          Each roommate, excluding Robert, now has a balance of -$4.00, meaning each roommate owes $4.00 to the system.
 *
 *      Don't contribute:
 *          If Robert does not contribute to this expense, each person, excluding Robert, will pay $5.00 for this expense.
 *          So, Robert pays the expense of $20.00, but since he doesn't contribute, he now has a balance of $20.00.
 *          Robert is owed $20.00.
 *          Each roommate, excluding Robert, now has a balance of -$5.00, meaning each roommate owes $5.00 to the system.
 *
 * Full core money management logic documentation explained in service/ExpenseManager.java
 *
 * @author Kris Bali
 */
public class Expense {
    /** The name of the expense */
    private String expenseName;
    /** The cost of the expense */
    private BigDecimal cost;
    /** The roommate that will pay for the entire expense */
    private Roommate payer;
    /** The list of roommates that will contribute to the payment of this expense, can include the payer */
    private List<Roommate> participants;

    /**
     * Expense constructor
     * @param expenseName The name of the expense
     * @param cost The cost of the expense
     * @param payer The roommate that will pay for the entire expense
     * @param participants The list of roommates that will contribute to the payment of the expense, can include the payer
     */
    public Expense(String expenseName, BigDecimal cost, Roommate payer, List<Roommate> participants){
        if (expenseName == null || expenseName.isBlank()){
            throw new IllegalArgumentException("No expense described");
        }

        if (cost == null || cost.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Cost must be above $0.00");
        }

        if (payer == null){
            throw new IllegalArgumentException("Please provide a payer for this expense");
        }

        if (participants == null || participants.isEmpty()){
            throw new IllegalArgumentException("Please provide 1 or more participants that contribute to this expense");
        }

        this.expenseName = expenseName;
        this.cost = cost.setScale(2, RoundingMode.HALF_UP);
        this.payer = payer;
        this.participants = new ArrayList<>(participants);
    }

    /**
     * @return The name of the expense
     */
    public String getExpenseName() {
        return expenseName;
    }

    /**
     * @return The cost of the expense
     */
    public BigDecimal getCost() {
        return this.cost;
    }

    /**
     * @return The roommate that will pay for the entire expense
     */
    public Roommate getPayer(){
        return this.payer;
    }

    /**
     * @return The list of roommates that will contribute to the payment of the expense
     */
    public List<Roommate> getParticipants(){
        return new ArrayList<>(this.participants);
    }

    /**
     * @return The cost a roommate must pay for this expense
     */
    public BigDecimal costPerPerson(){
        return this.cost.divide(BigDecimal.valueOf(this.participants.size()), 2, RoundingMode.CEILING);
    }

    /**
     * @return The amount of extra funds after over paying this expense
     */
    public BigDecimal extraMoney(){
        return this.cost.subtract(BigDecimal.valueOf(3).multiply(costPerPerson())).abs();
    }

    @Override
    public String toString(){
        return this.expenseName + " - $" + this.cost + " to be paid by " + this.payer.getName() + " split among " + this.participants.size() + " people";
    }
}
