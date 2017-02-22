package edu.cmu.cs.cs214.hw3;
/* This is going to be like StopTime from the last homework,
 It's just that looking back now I don't like how I implemented
 it then so I'm only using StopTime for the graph searching */
/**
 * Class to store bus stop name, location, and a bus's scheduled arrival there.
 * @author Kathleen
 *
 */
public class LocTime {
    private String name;
    private Location loc;
    private int time;
    /**
     * Constructor to initialize LocTime object.
     * @param pName Bus stop name.
     * @param pLoc Location of bus stop.
     * @param pTime Time a specific bus is schedule to arrive there.
     */
    public LocTime(String pName, Location pLoc, int pTime){
        this.name = pName;
        this.loc = pLoc;
        this.time = pTime;
    }
    /**
     * Method to get value of name attribute.
     * @return Bus stop name.
     */
    public String getName(){
        return this.name;
    }
    /**
     * Method to get value of location attribute.
     * @return Location of bus stop.
     */
    public Location getLoc(){
        return this.loc;
    }
    /**
     * Method to get a specifc bus's schedule arrival time at location.
     * @return Arrival time.
     */
    public int getTime(){
        return this.time;
    }

}
