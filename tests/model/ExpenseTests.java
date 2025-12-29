package model;

import org.junit.Test;

import javax.xml.stream.events.EntityReference;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

public class ExpenseTests {
    @Test
    public void testNewExpense(){
        List<Roommate> participants = new ArrayList<>();
        participants.add(new Roommate("David", BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP)));
        participants.add(new Roommate("Nick", BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP)));
        participants.add(new Roommate("Zachary", BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP)));

        Expense expense = new Expense("Groceries", BigDecimal.valueOf(89.99), new Roommate("Kris", BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP)), participants);

        assertEquals("Groceries", expense.getExpenseName());
        assertEquals(BigDecimal.valueOf(89.99), expense.getCost());
        assertEquals(new Roommate("Kris", BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP)), expense.getPayer());
        assertEquals(participants, expense.getParticipants());
    }

    @Test
    public void testNoExpense(){
        List<Roommate> participants = new ArrayList<>();
        participants.add(new Roommate("David", BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP)));
        participants.add(new Roommate("Nick", BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP)));
        participants.add(new Roommate("Zachary", BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP)));

        try{
            Expense expense = new Expense("", BigDecimal.valueOf(89.99), new Roommate("Kris", BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP)), participants);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e){
            assertEquals("No expense described", e.getMessage());
        }
    }

    @Test
    public void testZeroCost(){
        List<Roommate> participants = new ArrayList<>();
        participants.add(new Roommate("David", BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP)));
        participants.add(new Roommate("Nick", BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP)));
        participants.add(new Roommate("Zachary", BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP)));

        try{
            Expense expense = new Expense("Groceries", BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP), new Roommate("Kris", BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP)), participants);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e){
            assertEquals("Cost must be above $0.00", e.getMessage());
        }
    }

    @Test
    public void testNegativeCost(){
        List<Roommate> participants = new ArrayList<>();
        participants.add(new Roommate("David", BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP)));
        participants.add(new Roommate("Nick", BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP)));
        participants.add(new Roommate("Zachary", BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP)));

        try{
            Expense expense = new Expense("Groceries", BigDecimal.valueOf(-3.14), new Roommate("Kris", BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP)), participants);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e){
            assertEquals("Cost must be above $0.00", e.getMessage());
        }
    }

    @Test
    public void testNoPayer(){
        List<Roommate> participants = new ArrayList<>();
        participants.add(new Roommate("David", BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP)));
        participants.add(new Roommate("Nick", BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP)));
        participants.add(new Roommate("Zachary", BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP)));

        try{
            Expense expense = new Expense("Groceries", BigDecimal.valueOf(89.99), null, participants);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e){
            assertEquals("Please provide a payer for this expense", e.getMessage());
        }
    }

    @Test
    public void testNoParticipants(){
        List<Roommate> participants = new ArrayList<>();

        try{
            Expense expense = new Expense("Groceries", BigDecimal.valueOf(89.99), new Roommate("Kris", BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP)), participants);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e){
            assertEquals("Please provide 1 or more participants that contribute to this expense", e.getMessage());
        }
    }

    @Test
    public void testParticipantsCopy(){
        List<Roommate> participants = new ArrayList<>();
        participants.add(new Roommate("David", BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP)));
        participants.add(new Roommate("Nick", BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP)));
        participants.add(new Roommate("Zachary", BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP)));

        Expense expense = new Expense("Groceries", BigDecimal.valueOf(89.99), new Roommate("Kris", BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP)), participants);
        assertEquals(participants, expense.getParticipants());
    }

    @Test
    public void testCostPerPerson(){
        List<Roommate> participants = new ArrayList<>();
        participants.add(new Roommate("David", BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP)));
        participants.add(new Roommate("Nick", BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP)));
        participants.add(new Roommate("Zachary", BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP)));

        Expense expense = new Expense("Groceries", BigDecimal.valueOf(10.00).setScale(2, RoundingMode.HALF_UP), new Roommate("Kris", BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP)), participants);
        assertEquals(BigDecimal.valueOf(3.34), expense.costPerPerson());
        assertEquals(BigDecimal.valueOf(0.02), expense.extraMoney());
    }
}
