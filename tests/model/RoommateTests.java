package model;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.Assert.*;

public class RoommateTests {

    @Test
    public void testNewRoommateStartsWithZeroBalance(){
        Roommate roommate = new Roommate("Kris", BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP));
        assertEquals(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP), roommate.getBalance());
    }

    @Test
    public void testUpdateBalancePositive(){
        Roommate roommate = new Roommate("Nick", BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP));
        roommate.updateBalance(BigDecimal.valueOf(9.99));
        assertEquals(0, BigDecimal.valueOf(9.99).compareTo(roommate.getBalance()));
    }

    @Test
    public void testUpdateBalanceNegative(){
        Roommate roommate = new Roommate("David", BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP));
        roommate.updateBalance(BigDecimal.valueOf(-3.14));
        assertEquals(0, BigDecimal.valueOf(-3.14).compareTo(roommate.getBalance()));
    }

    @Test
    public void testResetBalance(){
        Roommate roommate = new Roommate("Zachary", BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP));
        roommate.updateBalance(BigDecimal.valueOf(74.99));
        roommate.updateBalance(BigDecimal.valueOf(-14.99));
        roommate.resetBalance();
        assertEquals(0, BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP).compareTo(roommate.getBalance()));
    }
}
