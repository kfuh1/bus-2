package edu.cmu.cs.cs214.hw3;

import edu.cmu.cs.cs214.hw2.Itinerary;
/**
 * Class to represent person who uses RFID cards.
 * @author Kathleen
 *
 */
public class PersonWithRFID extends StandardPerson{
    private static final double WAIT_DELAY = -0.04;
    /**
     * Constructor to create person who uses RFID cards.
     * @param s simulation.
     * @param i Person's itinerary.
     * @param name Person's name.
     */
    public PersonWithRFID(Simulation s, Itinerary i, String name){
        super(s,i,name);
    }
    @Override
    public void movePerson(Bus b){
        double waitDelayPct = WAIT_DELAY;
        b.setWaitDelay(waitDelayPct);
    }
}
