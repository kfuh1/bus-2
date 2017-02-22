package edu.cmu.cs.cs214.hw3;

import org.junit.Before;
import org.junit.Test;

import edu.cmu.cs.cs214.hw2.Itinerary;
import edu.cmu.cs.cs214.hw2.TripSegment;

import java.util.List;
import java.util.LinkedList;

import static org.junit.Assert.assertTrue;


public class SimulationTest {
    private static final int NUM_RIDERS = 1000;
    private static final double PCT_RFID = 0.0;
    private static final double PCT_LUGGAGE = 20.0;
    private static final double PCT_DELAYS = 10;
    private static final String ROUTES_FILE_NAME = "src/main/resources/Test2.txt";
    
    private Simulation s;
    private Factory pf;
    private Factory bf;
    private Bus b;
    @Before
    public void setUp(){
        s = new TransitSimulation(PCT_RFID,PCT_LUGGAGE,PCT_DELAYS);
        pf = new PersonFactory(s, ROUTES_FILE_NAME,NUM_RIDERS);
        bf = new BusFactory(s, ROUTES_FILE_NAME);
        List<LocTime> l = new LinkedList<LocTime>();
        Location loc = new Location(1.23123, 123.23);
        b = new StandardBus(s, "bus1", loc ,100, l); 
    }
    /* I wasn't quite sure how to test any further than this since the other
     * classes and methods rely on the simualation to be running and the output
     * often changes due to the randomness.
     */
    @Test
    public void testAddFactory(){
        s.addFactory(pf);
        assertTrue(s.getFactories().size() == 1);
        s.addFactory(bf);
        assertTrue(s.getFactories().size() == 2);
    }
    @Test
    public void testInsertBus(){
        assertTrue(s.getBuses().size() == 0);
        s.insertBus(b);
        assertTrue(s.getBuses().size() == 1);
    }
}
