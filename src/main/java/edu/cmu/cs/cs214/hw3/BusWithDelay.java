package edu.cmu.cs.cs214.hw3;

import java.util.Random;
/**
 * Class to randomly get delays for buses as they travel.
 * @author Kathleen
 *
 */
public class BusWithDelay extends BusDecorator {
    private static final int MIN = 0;
    /* delay percentages are given here so they can be easily changed */
    private static final double NO_DELAY = 0.0;
    private static final double SOME_GEN_DELAY = 0.07;
    private static final double GREAT_GEN_DELAY = 0.2;
    private static final double SOME_ACC_DELAY = 0.09;
    private static final double GREAT_ACC_DELAY = 0.25;
    private static final double SOME_CON_DELAY = 0.03;
    private static final double GREAT_CON_DELAY = 0.2;
    
    /* Type of delays, to be used when calculating delay percent */
    private static final String[] GEN_TRAFFIC = 
        {"none", "none", "some", "some", "none", "some",
        "too much", "none", "too much", "some", "some", "some"};
    private static final String[] CONSTRUCTION = 
        {"none", "none", "some", "some", "none", "some",
        "too much", "none", "too much", "none", "none", "some", 
        "some"};
    private static final String[] ACCIDENTS = 
        {"none", "none", "ok", "ok", "none", "ok",
        "bad", "none", "bad", "ok", "ok", "ok", "none", "none"};
    
    /* randomly get delays every FREQ_OF_DELAYS seconds */
    private static final int FREQ_OF_DELAYS = 60;
    
    /* these delays won't affect waiting times when people are
     * boarding/unboarding so we only care to change the travel
     * delay percentage.
     */
    private double travelDelayPct;
    private Random rand;
    private int counter;
    /**
     * Constructor to create a decorated bus with delays.
     * @param b Bus to be decorated.
     */
    public BusWithDelay(Bus b){
        super(b);
        rand = new Random();
        travelDelayPct = NO_DELAY;
        counter = 0;
    }
    @Override
    public void step(){
        if(counter % FREQ_OF_DELAYS == 0){
            getGenTrafficDelay();
            getConstructionDelay();
            getAccidentDelay();
        }
        counter++;
        
        super.setTravelDelay(travelDelayPct);
        travelDelayPct = NO_DELAY; /* reset */
        super.step();
       
    }
    /**
     * Method to get random number.
     * @param max Maximum possible number.
     * @return Random number between max and MIN.
     */
    private int getRandomNumber(int max){
        return rand.nextInt((max-MIN)+1)+MIN;
    }
    
    /**
     * Method to set the delay associated with general traffic.
     */
    private void getGenTrafficDelay(){
        int len = GEN_TRAFFIC.length;
        int index = getRandomNumber(len-1);
        String val = GEN_TRAFFIC[index];
        /* Set delay percentage based on random severity gotten above */ 
        if(val.equals("none")){
            travelDelayPct += NO_DELAY;
        }
        else if(val.equals("some")){
            travelDelayPct += SOME_GEN_DELAY;
        }
        else if(val.equals("too much")){
            travelDelayPct += GREAT_GEN_DELAY;
        }
    }
    /**
     * Method to set delay associated with construction.
     */
    private void getConstructionDelay(){     
        int len = CONSTRUCTION.length;
        int index = getRandomNumber(len-1);
        String val = CONSTRUCTION[index];
        /* Set delay percentage based on random severity gotten above */ 
        if(val.equals("none")){
            travelDelayPct += NO_DELAY;
        }
        else if(val.equals("some")){
            travelDelayPct += SOME_CON_DELAY;
        }
        else if(val.equals("too much")){
            travelDelayPct += GREAT_CON_DELAY;
        }
    }
    /**
     * Method to set delay associated with accidents.
     */
    private void getAccidentDelay(){
        int len = ACCIDENTS.length;
        int index = getRandomNumber(len-1);
        String val = ACCIDENTS[index];
        /* Set delay percentage based on random severity gotten above */ 
        if(val.equals("none")){
            travelDelayPct += NO_DELAY;
        }
        else if(val.equals("some")){
            travelDelayPct += SOME_ACC_DELAY;
        }
        else if(val.equals("too much")){
            travelDelayPct += GREAT_ACC_DELAY;
        }
    }

}
