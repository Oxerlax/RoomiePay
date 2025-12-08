package model;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

public class ExpenseTests {
    @Test
    public void testNewExpense(){
        List<Roommate> participants = new ArrayList<>();
        participants.add(new Roommate("David", BigDecimal.ZERO));
        participants.add(new Roommate("Nick", BigDecimal.ZERO));
        participants.add(new Roommate("Zachary", BigDecimal.ZERO));

        Expense expense = new Expense("Groceries", BigDecimal.valueOf(89.99).setScale(2, RoundingMode.HALF_UP), new Roommate("Kris", BigDecimal.ZERO), participants);

        assertEquals("Groceries", expense.getExpense());
        assertEquals(BigDecimal.valueOf(89.99).setScale(2, RoundingMode.HALF_UP), expense.getCost());
        assertEquals(new Roommate("Kris", BigDecimal.ZERO), expense.getPayer());
        assertEquals(participants, expense.getParticipants());
    }

    @Test
    public void testNoExpense(){
        List<Roommate> participants = new ArrayList<>();
        participants.add(new Roommate("David", BigDecimal.ZERO));
        participants.add(new Roommate("Nick", BigDecimal.ZERO));
        participants.add(new Roommate("Zachary", BigDecimal.ZERO));

        try{
            Expense expense = new Expense("", BigDecimal.valueOf(89.99).setScale(2, RoundingMode.HALF_UP), new Roommate("Kris", BigDecimal.ZERO), participants);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e){
            assertEquals("No expense described", e.getMessage());
        }
    }

    @Test
    public void testZeroCost(){
        List<Roommate> participants = new ArrayList<>();
        participants.add(new Roommate("David", BigDecimal.ZERO));
        participants.add(new Roommate("Nick", BigDecimal.ZERO));
        participants.add(new Roommate("Zachary", BigDecimal.ZERO));

        try{
            Expense expense = new Expense("Groceries", BigDecimal.ZERO, new Roommate("Kris", BigDecimal.ZERO), participants);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e){
            assertEquals("Cost must be above $0.00", e.getMessage());
        }
    }

    @Test
    public void testNegativeCost(){
        List<Roommate> participants = new ArrayList<>();
        participants.add(new Roommate("David", BigDecimal.ZERO));
        participants.add(new Roommate("Nick", BigDecimal.ZERO));
        participants.add(new Roommate("Zachary", BigDecimal.ZERO));

        try{
            Expense expense = new Expense("Groceries", BigDecimal.valueOf(-3.14).setScale(2, RoundingMode.HALF_UP), new Roommate("Kris", BigDecimal.ZERO), participants);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e){
            assertEquals("Cost must be above $0.00", e.getMessage());
        }
    }

    @Test
    public void testNoPayer(){
        List<Roommate> participants = new ArrayList<>();
        participants.add(new Roommate("David", BigDecimal.ZERO));
        participants.add(new Roommate("Nick", BigDecimal.ZERO));
        participants.add(new Roommate("Zachary", BigDecimal.ZERO));

        try{
            Expense expense = new Expense("Groceries", BigDecimal.valueOf(89.99).setScale(2, RoundingMode.HALF_UP), null, participants);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e){
            assertEquals("Please provide a payer for this expense", e.getMessage());
        }
    }

    @Test
    public void testNoParticipants(){
        List<Roommate> participants = new ArrayList<>();

        try{
            Expense expense = new Expense("Groceries", BigDecimal.valueOf(89.99).setScale(2, RoundingMode.HALF_UP), new Roommate("Kris", BigDecimal.ZERO), participants);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e){
            assertEquals("Please provide 1 or more participants that contribute to this expense", e.getMessage());
        }
    }

    @Test
    public void testParticipantsCopy(){
        List<Roommate> participants = new ArrayList<>();
        participants.add(new Roommate("David", BigDecimal.ZERO));
        participants.add(new Roommate("Nick", BigDecimal.ZERO));
        participants.add(new Roommate("Zachary", BigDecimal.ZERO));

        Expense expense = new Expense("Groceries", BigDecimal.valueOf(89.99).setScale(2, RoundingMode.HALF_UP), new Roommate("Kris", BigDecimal.ZERO), participants);
        assertEquals(participants, expense.getParticipants());
    }

    @Test
    public void testCostPerPerson(){
        List<Roommate> participants = new ArrayList<>();
        participants.add(new Roommate("David", BigDecimal.ZERO));
        participants.add(new Roommate("Nick", BigDecimal.ZERO));
        participants.add(new Roommate("Zachary", BigDecimal.ZERO));

        Expense expense = new Expense("Groceries", BigDecimal.valueOf(10.00).setScale(2, RoundingMode.HALF_UP), new Roommate("Kris", BigDecimal.ZERO), participants);
        assertEquals(BigDecimal.valueOf(3.34).setScale(2, RoundingMode.HALF_UP), expense.costPerPerson());
        assertEquals(BigDecimal.valueOf(0.02).setScale(2, RoundingMode.HALF_UP), expense.extraMoney());
    }
}
