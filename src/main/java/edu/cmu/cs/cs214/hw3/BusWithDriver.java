package edu.cmu.cs.cs214.hw3;

import java.util.Random;
/**
 * Class that adds to Bus behaviors based on bus driver.
 * @author Kathleen
 *
 */
public class BusWithDriver extends BusDecorator{
    /*
     * Bus driver personality (which determines whether or not
     * they speed or wait for people at a bus stop) will be determined
     * when the bus is created. A bus driver can either be 
     * nice - waits longer at stops, drives slow
     * mean - leaves stops early, drives fast
     * impartial - waits and drives at standard time and speed
     */
    
    private static final int MIN = 0;
    private static final double NO_DELAY = 0.0;
    private static final double WAIT_DELAY = 0.2;
    private static final double WAIT_SPEEDUP = -0.25;
    private static final double TRAVEL_SPEEDUP = -0.3;
    private static final int AVG_TRAVEL_TIME = 35;
    
    private static final String[] DEMEANORS = {"nice", "mean", "impartial"};
    private double travelDelayPct;
    private double waitDelayPct;
    private Random rand;
    private int counter;
    
    /**
     * Constructor to create bus decorated with specific type of driver.
     * @param b Bus to be decorated.
     */
    public BusWithDriver(Bus b){
        super(b);
        rand = new Random();
        counter = 0;
        setPersonality();
    }
    /**
     * Method to set bus driver personality.
     */
    private void setPersonality(){
        int max = DEMEANORS.length-1;
        int index = getRandomNumber(max);
        String demeanor = DEMEANORS[index];
        /* Set delay percents based on demeanor */
        if(demeanor.equals("nice")){
            travelDelayPct =  NO_DELAY;
            waitDelayPct = WAIT_DELAY;
        }
        else if(demeanor.equals("mean")){
            travelDelayPct = TRAVEL_SPEEDUP;
            waitDelayPct = WAIT_SPEEDUP;
        }
        else if(demeanor.equals("impartial")){
            travelDelayPct = NO_DELAY;
            waitDelayPct = NO_DELAY;
        }
    }
    /**
     * Method to get random number.
     * @param max Maximum possible number.
     * @return Random number between max and MIN.
     */
    private int getRandomNumber(int max){
        return rand.nextInt((max-MIN)+1)+MIN;
    }
    @Override
    public void step(){
        if(counter % AVG_TRAVEL_TIME == 0){
            super.setTravelDelay(travelDelayPct);
            super.setWaitDelay(waitDelayPct);
        }
        counter++;
        super.step();
    }

}
