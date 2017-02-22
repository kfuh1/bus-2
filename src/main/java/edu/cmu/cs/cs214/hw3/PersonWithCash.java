package edu.cmu.cs.cs214.hw3;

import edu.cmu.cs.cs214.hw2.Itinerary;
/**
 * Class to represent person who uses cash for payment.
 * @author Kathleen
 *
 */
public class PersonWithCash extends StandardPerson{
    private static final double CASH_DELAY = 0.05;
    /**
     * Constructor to create Person that uses cash.
     * @param s Simulation.
     * @param i Person's itinerary.
     * @param name Person's name.
     */
    public PersonWithCash(Simulation s, Itinerary i, String name){
        super(s, i, name);
    }
    @Override
    public void movePerson(Bus b){
        double waitDelayPct = CASH_DELAY;
        b.setWaitDelay(waitDelayPct);
    }
}
