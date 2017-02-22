package edu.cmu.cs.cs214.hw3;

import java.util.Random;

import edu.cmu.cs.cs214.hw2.Itinerary;
/**
 * Class to represent person with luggage.
 * @author Kathleen
 *
 */
public class PersonWithLuggage extends StandardPerson {
    /* can set the maximum and minimum pieces of luggage somebody with
     * luggage should have. */
    private static final int MAX = 2;
    private static final int MIN = 1;
    private static final double WAIT_DELAY = 0.05;
    private int numLuggages;
    
    /**
     * Constructor to create person with luggage.
     * @param s simulation .
     * @param i Person's itinerary.
     * @param name Person's name.
     */
    public PersonWithLuggage(Simulation s, Itinerary i, String name){
        super(s,i,name);
        Random rand = new Random();
        numLuggages = rand.nextInt((MAX-MIN)+1)+MIN;
    }
    @Override
    public boolean updateCurrCapacity(Bus b, boolean isGettingOn){ 
        
        Capacity currSize = b.getCurrentSize();
        Capacity maxCap = b.getCapacity();
        int numSitting = currSize.getSitting();
        int numStanding = currSize.getStanding();
        int maxSitting = maxCap.getSitting();
        int maxStanding = maxCap.getStanding();
        /* These checks would assume that at least one of the person's luggage
         * will take up a person space.
         */
        if(isGettingOn){
            if(numSitting == maxSitting 
                    && numStanding < maxStanding - numLuggages){
                b.setCurrentSize(new Capacity(numStanding + numLuggages + 1,
                        numSitting));
                return true;
            }
            else if((numSitting < maxSitting - numLuggages && numStanding == maxStanding)
                    || (numSitting < maxSitting && numStanding < maxStanding)){
                b.setCurrentSize(new Capacity(numStanding,
                        numSitting+ numLuggages + 1));
                return true;
            }
            else{
                return false;
            }
        }
        else{
            if(numSitting > 0){
                b.setCurrentSize(new Capacity(numStanding,
                        numSitting-numLuggages-1));
                return true;
            }
            else{
                b.setCurrentSize(new Capacity(numStanding-numLuggages-1,
                        numSitting));
                return true;
            }
        }
    }
    @Override
    public void movePerson(Bus b){
        double waitDelayPct = WAIT_DELAY;
        b.setWaitDelay(waitDelayPct);
    }    
}
