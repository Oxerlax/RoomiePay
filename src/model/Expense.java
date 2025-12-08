package model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Expense {
    private String expense;
    private BigDecimal cost;
    private Roommate payer;
    private List<Roommate> participants;

    public Expense(String expense, BigDecimal cost, Roommate payer, List<Roommate> participants){
        if (expense == null || expense.isBlank()){
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

        this.expense = expense;
        this.cost = cost.setScale(2, RoundingMode.HALF_UP);
        this.payer = payer;
        this.participants = new ArrayList<>(participants);
    }

    public String getExpense() {
        return expense;
    }

    public BigDecimal getCost() {
        return this.cost;
    }

    public Roommate getPayer(){
        return this.payer;
    }

    public List<Roommate> getParticipants(){
        return new ArrayList<>(this.participants);
    }

    public BigDecimal costPerPerson(){
        return this.cost.divide(BigDecimal.valueOf(this.participants.size()), 2, RoundingMode.CEILING);
    }

    public BigDecimal extraMoney(){
        return this.cost.subtract(BigDecimal.valueOf(3).multiply(costPerPerson())).abs();
    }

    @Override
    public String toString(){
        return this.expense + " - $" + this.cost + " to be paid by " + this.payer.getName() + " split among " + this.participants.size() + " people";
    }
}
