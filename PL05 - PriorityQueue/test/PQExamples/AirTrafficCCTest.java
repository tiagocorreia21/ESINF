package PQExamples;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AirTrafficCCTest {

    AirTrafficCC instance;

    @Before
    public void setUp() {
        Integer[] priorities = {20, 15, 10, 13, 8, 12, 40, 30, 5, 21};
        String[] flights = {"vinte", "quinze", "dez", "treze", "oito", "doze", "quarenta", "trinta", "cinco", "vinteeum"};
        instance = new AirTrafficCC(priorities, flights);
    }

    @After
    public void tearDown() {
        instance = null;
    }

    @org.junit.Test
    public void testAddPlane2Queue() {
        instance.addPlane2Queue("newPlane", 1);
        assertEquals(11, instance.nrPlanesWaiting().intValue());
    }

    @org.junit.Test
    public void testClearPlane4Landing() {
        instance.clearPlane4Landing();
        assertEquals(9, instance.nrPlanesWaiting().intValue());
    }

    @org.junit.Test
    public void testNextPlaneLanding() {
        String nextPlane = instance.nextPlaneLanding();
        assertEquals("cinco", nextPlane);
    }

    @Test
    public void testNrPlanesWaiting() {
        int waitingPlanes = instance.nrPlanesWaiting();
        assertEquals(10, waitingPlanes);
    }
}
