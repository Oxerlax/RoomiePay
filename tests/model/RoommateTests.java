package model;

import org.junit.Test;
import static org.junit.Assert.*;

public class RoommateTests {

    @Test
    public void testNewRoommateStartsWithZeroBalance(){
        Roommate roommate = new Roommate("Kris");
        assertEquals(0.0, roommate.getBalance(), 0.001);
    }

    @Test
    public void testUpdateBalancePositive(){
        Roommate roommate = new Roommate("Nick");
        roommate.updateBalance(9.99);
        assertEquals(9.99, roommate.getBalance(), 0.001);
    }

    @Test
    public void testUpdateBalanceNegative(){
        Roommate roommate = new Roommate("David");
        roommate.updateBalance(-3.14);
        assertEquals(-3.14, roommate.getBalance(), 0.001);
    }

    @Test
    public void testResetBalance(){
        Roommate roommate = new Roommate("Zachary");
        roommate.updateBalance(74.99);
        roommate.updateBalance(-14.99);
        roommate.resetBalance();
        assertEquals(0.0, roommate.getBalance(), 0.001);
    }
}
