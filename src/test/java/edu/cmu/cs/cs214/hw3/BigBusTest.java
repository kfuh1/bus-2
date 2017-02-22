package edu.cmu.cs.cs214.hw3;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class BigBusTest {
    private static final double PCT_RFID = 0.0;
    private static final double PCT_LUGGAGE = 20.0;
    private static final double PCT_DELAYS = 10;
    
    private Bus b;
    private Simulation s;
    
    
    @Before
    public void setUp(){
        s = new TransitSimulation(PCT_RFID,PCT_LUGGAGE,PCT_DELAYS);
        List<LocTime> l = new LinkedList<LocTime>();
        Location loc = new Location(1.23123, 123.23);
        b = new StandardBus(s, "bus1", loc ,100, l); 
    }
    @Test
    public void testConstructor(){
        Capacity c = b.getCapacity();
        assertTrue(c.getSitting() == 45);
        assertTrue(c.getStanding() == 25);
        Bus bigbus = new BigBus(b);
        c = bigbus.getCapacity();
        assertTrue(c.getSitting() == 90);
        assertTrue(c.getStanding() == 50);
    }
}
