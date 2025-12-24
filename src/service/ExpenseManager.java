package service;

import model.Expense;
import model.Roommate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpenseManager {
    private HashMap<String, Roommate> roommates;
    private List<Expense> expenses;

    public ExpenseManager(HashMap<String, Roommate> roommates, List<Expense> expenses){
        this.roommates = new HashMap<>(roommates);
        this.expenses = new ArrayList<>(expenses);
    }

    public HashMap<String, Roommate> getRoommates(){
        return new HashMap<>(this.roommates);
    }

    public List<Expense> getExpenses(){
        return new ArrayList<>(this.expenses);
    }

    public void addRoommate(Roommate roommate){
        this.roommates.put(roommate.getName(), roommate);
    }

    public Roommate getRoommateByName(String name){
        return this.roommates.get(name);
    }

    public void applyExpense(Expense expense){
        BigDecimal share = expense.costPerPerson();

        Roommate payer = this.roommates.get(expense.getPayer().getName());
        payer.updateBalance(expense.getCost());

        for (Roommate roommate : expense.getParticipants()){
            Roommate participant = this.roommates.get(roommate.getName());
            participant.updateBalance(share.negate());
        }
    }

    private void addExpense(Expense expense){
        this.expenses.add(expense);
        applyExpense(expense);
    }

    public List<Roommate> getRoommatesWhoOweMoney(){
        List<Roommate> owes = new ArrayList<>();

        for (Roommate roommate : this.roommates.values()){
            if (roommate.getBalance().compareTo(BigDecimal.ZERO) < 0){
                owes.add(roommate);
            }
        }

        return owes;
    }

    public List<Roommate> getRoommatesWhoAreOwedMoney(){
        List<Roommate> owed = new ArrayList<>();

        for (Roommate roommate : this.roommates.values()){
            if (roommate.getBalance().compareTo(BigDecimal.ZERO) > 0){
                owed.add(roommate);
            }
        }

        return owed;
    }

    public BigDecimal getTotalExpenses(){
        BigDecimal sum = BigDecimal.ZERO;

        for (Expense expense : this.expenses){
            sum = sum.add(expense.getCost());
        }

        return sum;
    }
}
