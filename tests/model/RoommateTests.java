package model;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class RoommateTests {

    @Test
    public void testNewRoommateStartsWithZeroBalance(){
        Roommate roommate = new Roommate("Kris", BigDecimal.ZERO);
        assertEquals(BigDecimal.ZERO, roommate.getBalance());
    }

    @Test
    public void testUpdateBalancePositive(){
        Roommate roommate = new Roommate("Nick", BigDecimal.ZERO);
        roommate.updateBalance(9.99);
        assertEquals(BigDecimal.valueOf(9.99), roommate.getBalance());
    }

    @Test
    public void testUpdateBalanceNegative(){
        Roommate roommate = new Roommate("David", BigDecimal.ZERO);
        roommate.updateBalance(-3.14);
        assertEquals(BigDecimal.valueOf(-3.14), roommate.getBalance());
    }

    @Test
    public void testResetBalance(){
        Roommate roommate = new Roommate("Zachary", BigDecimal.ZERO);
        roommate.updateBalance(74.99);
        roommate.updateBalance(-14.99);
        roommate.resetBalance();
        assertEquals(BigDecimal.ZERO, roommate.getBalance());
    }
}
