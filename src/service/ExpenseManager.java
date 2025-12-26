package service;

import model.Expense;
import model.Roommate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Manages the collection of roommates and the expenses between them.
 *
 * @author Kris Bali
 */
public class ExpenseManager {
    /** HashMap that maps a roommate's name to their respective Roommate instance */
    private HashMap<String, Roommate> roommates;
    /** List of expenses the roommates have between them */
    private List<Expense> expenses;

    /**
     * ExpenseManager constructor
     * @param roommates The hashmap of roommate's names mapped to their respective Roommate instances
     * @param expenses The list of expenses the roommates have
     */
    public ExpenseManager(HashMap<String, Roommate> roommates, List<Expense> expenses){
        this.roommates = new HashMap<>(roommates);
        this.expenses = new ArrayList<>(expenses);
    }

    /**
     * @return A copy of this private field "roommates"'s hashmap
     */
    public HashMap<String, Roommate> getRoommates(){
        return new HashMap<>(this.roommates);
    }

    /**
     * @return A copy of this private field "expenses"'s list
     */
    public List<Expense> getExpenses(){
        return new ArrayList<>(this.expenses);
    }

    /**
     * Inserts a Roommate instance into this private field "roommates"
     * @param roommate The roommate to add to the map of roommates
     */
    public void addRoommate(Roommate roommate){
        this.roommates.put(roommate.getName(), roommate);
    }

    /**
     * @param name The name of a roommate
     * @return The Roommate instance that has the specified name
     */
    public Roommate getRoommateByName(String name){
        return this.roommates.get(name);
    }

    /**
     * Main payer pays the cost of the expense, and then any roommate who is participating in paying this expense
     * has their balance updated to reflect if they owe money for paying this expense.
     * @param expense The expense that the payer pays for and distributes the cost equally to participants
     */
    public void applyExpense(Expense expense){
        BigDecimal share = expense.costPerPerson();

        Roommate payer = this.roommates.get(expense.getPayer().getName());
        payer.updateBalance(expense.getCost());

        for (Roommate roommate : expense.getParticipants()){
            Roommate participant = this.roommates.get(roommate.getName());
            participant.updateBalance(share.negate());
        }
    }

    /**
     * Adds an expense to the list of expenses in this private field "expenses", and then applies that expense to
     * the roommates participating in paying that expense
     * @param expense The expense to be added to a list of expenses
     */
    private void addExpense(Expense expense){
        this.expenses.add(expense);
        applyExpense(expense);
    }

    /**
     * @return A list of the roommates who owe money
     */
    public List<Roommate> getRoommatesWhoOweMoney(){
        List<Roommate> owes = new ArrayList<>();

        for (Roommate roommate : this.roommates.values()){
            if (roommate.getBalance().compareTo(BigDecimal.ZERO) < 0){
                owes.add(roommate);
            }
        }

        return owes;
    }

    /**
     * @return A list of the roommates who are owed money
     */
    public List<Roommate> getRoommatesWhoAreOwedMoney(){
        List<Roommate> owed = new ArrayList<>();

        for (Roommate roommate : this.roommates.values()){
            if (roommate.getBalance().compareTo(BigDecimal.ZERO) > 0){
                owed.add(roommate);
            }
        }

        return owed;
    }

    /**
     * @return The monetary sum of all the expenses from this private field "expenses"
     */
    public BigDecimal getTotalExpenses(){
        BigDecimal sum = BigDecimal.ZERO;

        for (Expense expense : this.expenses){
            sum = sum.add(expense.getCost());
        }

        return sum;
    }
}
