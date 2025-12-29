package service;

import model.Expense;
import model.Roommate;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

public class ExpenseManagerTests {
    private ExpenseManager manager;

    @Before
    public void setUp(){
        manager = new ExpenseManager(new HashMap<>(), new ArrayList<>());
    }

    @Test
    public void testInitialState(){
        assertEquals(0, manager.getRoommates().size());
        assertEquals(0, manager.getExpenses().size());
    }

    @Test
    public void testAddRoommate(){
        Roommate roommate = new Roommate("Kris", BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP));
        manager.addRoommate(roommate);
        assertEquals(roommate, manager.getRoommateByName("Kris"));
    }

    @Test
    public void testAddDuplicateRoommate(){
        Roommate roommate = new Roommate("Kris", BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP));
        manager.addRoommate(roommate);
        assertThrows(IllegalArgumentException.class, () -> {
            manager.addRoommate(roommate);
        });
    }

    @Test
    public void testAddExpense(){
        Roommate roommateKris = new Roommate("Kris", BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP));
        Roommate roommateDavid = new Roommate("David", BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP));
        manager.addRoommate(roommateKris);
        manager.addRoommate(roommateDavid);

        ArrayList<Roommate> participants = new ArrayList<>();
        participants.add(roommateKris);
        participants.add(roommateDavid);

        Expense expense = new Expense("Groceries", BigDecimal.valueOf(19.99), roommateKris, participants);
        manager.addExpense(expense);
        assertEquals(1, manager.getExpenses().size());
    }

    @Test
    public void testEqualSplitExpenseExcludingPayer(){
        Roommate roommateKris = new Roommate("Kris", BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP));
        Roommate roommateDavid = new Roommate("David", BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP));
        Roommate roommateNick = new Roommate("Nick", BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP));
        manager.addRoommate(roommateKris);
        manager.addRoommate(roommateDavid);
        manager.addRoommate(roommateNick);

        ArrayList<Roommate> participants = new ArrayList<>();
        participants.add(roommateDavid);
        participants.add(roommateNick);

        Expense expense = new Expense("Groceries", BigDecimal.valueOf(29.99), roommateKris, participants);
        manager.addExpense(expense);

        assertEquals(0, BigDecimal.valueOf(29.99).compareTo(expense.getPayer().getBalance()));

        List<Roommate> participantsWithoutPayer = expense.getParticipants();

        for (Roommate roommate : participantsWithoutPayer){
            assertEquals(0, BigDecimal.valueOf(-15.00).setScale(2, RoundingMode.HALF_UP).compareTo(roommate.getBalance()));
        }
    }

    @Test
    public void testEqualSplitExpenseIncludingPayer(){
        Roommate roommateKris = new Roommate("Kris", BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP));
        Roommate roommateDavid = new Roommate("David", BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP));
        Roommate roommateNick = new Roommate("Nick", BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP));
        manager.addRoommate(roommateKris);
        manager.addRoommate(roommateDavid);
        manager.addRoommate(roommateNick);

        ArrayList<Roommate> participants = new ArrayList<>();
        participants.add(roommateKris);
        participants.add(roommateDavid);
        participants.add(roommateNick);

        Expense expense = new Expense("Groceries", BigDecimal.valueOf(29.99), roommateKris, participants);
        manager.addExpense(expense);

        assertEquals(0, BigDecimal.valueOf(19.99).compareTo(expense.getPayer().getBalance()));

        HashMap<String, Roommate> participantsWithoutPayer = manager.getRoommates();
        participantsWithoutPayer.remove(roommateKris.getName());
        assert (!participantsWithoutPayer.containsKey(roommateKris.getName()));

        for (Roommate roommate : participantsWithoutPayer.values()){
            assertEquals(0, BigDecimal.valueOf(-10.00).setScale(2, RoundingMode.HALF_UP).compareTo(roommate.getBalance()));
        }
    }

    @Test
    public void testMultipleExpenses(){
        Roommate roommateKris = new Roommate("Kris", BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP));
        Roommate roommateDavid = new Roommate("David", BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP));

        manager.addRoommate(roommateKris);
        manager.addRoommate(roommateDavid);

        ArrayList<Roommate> participants = new ArrayList<>();
        participants.add(roommateKris);
        participants.add(roommateDavid);

        Expense expenseKris = new Expense("Groceries", BigDecimal.valueOf(20.00), roommateKris, participants);
        Expense expenseDavid = new Expense("Groceries", BigDecimal.valueOf(10.00), roommateDavid, participants);
        manager.addExpense(expenseKris);
        manager.addExpense(expenseDavid);

        assertEquals(0, BigDecimal.valueOf(5.00).setScale(2, RoundingMode.HALF_UP).compareTo(manager.getRoommateByName(roommateKris.getName()).getBalance()));
        assertEquals(0, BigDecimal.valueOf(-5.00).setScale(2, RoundingMode.HALF_UP).compareTo(manager.getRoommateByName(roommateDavid.getName()).getBalance()));
    }

    @Test
    public void testNegativeExpense(){
        Roommate roommateKris = new Roommate("Kris", BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP));
        Roommate roommateDavid = new Roommate("David", BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP));

        manager.addRoommate(roommateKris);
        manager.addRoommate(roommateDavid);

        ArrayList<Roommate> participants = new ArrayList<>();
        participants.add(roommateKris);
        participants.add(roommateDavid);

        assertThrows(IllegalArgumentException.class, () -> {
            Expense expense = new Expense("Mars", BigDecimal.valueOf(-9999999999999.99), roommateKris, participants);
            manager.addExpense(expense);
        });
    }

    @Test
    public void testTotalExpenses(){
        Roommate roommateKris = new Roommate("Kris", BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP));
        Roommate roommateDavid = new Roommate("David", BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP));

        manager.addRoommate(roommateKris);
        manager.addRoommate(roommateDavid);

        ArrayList<Roommate> participants = new ArrayList<>();
        participants.add(roommateKris);
        participants.add(roommateDavid);

        Expense expenseDishes = new Expense("Dishes", BigDecimal.valueOf(39.99), roommateKris, participants);
        Expense expenseClothes = new Expense("Clothes", BigDecimal.valueOf(12.99), roommateDavid, participants);
        manager.addExpense(expenseDishes);
        manager.addExpense(expenseClothes);

        assertEquals(0, BigDecimal.valueOf(52.98).compareTo(manager.getTotalExpenses()));
    }
}
