package edu.cmu.cs.cs214.hw3;

import javax.swing.ImageIcon;

import java.util.List;

import edu.cmu.cs.cs214.hw2.Itinerary;
import edu.cmu.cs.cs214.hw2.Stop;
import edu.cmu.cs.cs214.hw2.TripSegment;
/**
 * Class that represents a rider in the transit system.
 * @author Kathleen
 *
 */
public class StandardPerson implements Person{
    private static final int BUS_NAME_INDEX = 2;

    private Simulation sim;
    private String name;
    private Location location;
    private Itinerary itinerary;
    private int startTime;
    private int index;
    private boolean onBus;
    private String busName; /* name of bus person is on */
    /**
     * Constructor to intialize Person object.
     * @param pSim Simulation Person is associated to.
     * @param pIT Itinerary Person should follow.
     * @param pName Person name.
     */
    public StandardPerson(Simulation pSim, Itinerary pIT, String pName){
        this.sim = pSim;
        this.itinerary = pIT;
        this.name = pName;
        Stop s = this.itinerary.getStartLocation();
        this.location = new Location(s.getLatitude(), s.getLongitude());
        this.startTime = this.itinerary.getStartTime();
        this.index = 0;
        this.busName = "";
        this.onBus = false;
    }
    /**
     * Method to update Person object's position when simulation steps.
     */
    public void step(){

    }
    /**
     * Method to update delay caused by person when getting on or off.
     * @param b Bus that person is getting on or off.
     */
    public void movePerson(Bus b){
        /* standard person won't add any extra delays
         * so this method is overridden in the subclasses.
         */
    }
    /**
     * Method to get busname from trip segment instruction.
     * @param t TripSegment. 
     */
    private void setBusName(TripSegment t){
        String instr = t.getInstruction();
        String[] instrVals = instr.split(" ");
        busName = instrVals[BUS_NAME_INDEX];
    }
    @Override
    public boolean updateCurrCapacity(Bus b, boolean isGettingOn){
        Capacity currSize = b.getCurrentSize();
        Capacity maxCap = b.getCapacity();
        int numSitting = currSize.getSitting();
        int numStanding = currSize.getStanding();
        int maxSitting = maxCap.getSitting();
        int maxStanding = maxCap.getStanding();
        if(isGettingOn){
            /* update sitting or standing based on what's available */
            if(numSitting == maxSitting && numStanding < maxStanding){
                b.setCurrentSize(new Capacity(numStanding + 1, numSitting));
                return true;
            }
            else if((numSitting < maxSitting && numStanding == maxStanding)
                    || (numSitting < maxSitting && numStanding < maxStanding)){
                b.setCurrentSize(new Capacity(numStanding, numSitting+1));
                return true;
            }
            /* no room on bus so can't get on */
            else{
                return false;
            }
        }
        /* Person is getting off */
        else{
            /* change whatever number is greater than 0 since when a person
             * leaves the bus it doesn't matter the exact sit/stand number 
             */
            if(numSitting > 0){
                b.setCurrentSize(new Capacity(numStanding, numSitting-1));
                return true;
            }
            else{
                b.setCurrentSize(new Capacity(numStanding-1, numSitting));
                return true;
            }
        }
        
    }
    @Override
    public void busArrived(Bus b, Stop s){
        List<TripSegment> tripSegs = itinerary.getSegments();
        if(index < tripSegs.size()){
            this.step();
            TripSegment t = tripSegs.get(index);
            Stop segEndStop = t.getEndLocation();
            Stop segStartStop = t.getStartLocation();
            setBusName(t);
            Location busLoc = new Location(s.getLatitude(), s.getLongitude());
            
            Location segEndLoc = new Location(segEndStop.getLatitude(),
                    segEndStop.getLongitude());
            Location segStartLoc = new Location(segStartStop.getLatitude(),
                    segStartStop.getLongitude());
            location = busLoc; /* person moves with bus */

            /* dealing with arrival at a stop */
            if(onBus && busName.equals(b.getName()) 
                    && busLoc.equals(segEndLoc)){
                movePerson(b);
                onBus = false; /* rider has gotten of bus */
                updateCurrCapacity(b, onBus);
                index++; /* rider needs to look at next segment */
                
                /* Note: some people are going to get stuck because the
                 * sample route planner implementation treats different
                 * coordinates with the same name as the same. Unless a rider
                 * runs into that problem, these checks will allow a person
                 * to get to their destination.
                 */
                
                /* Person has arrived at route destination */
                if(index >= tripSegs.size()){
                    int time = t.getEndTime();
                    int actualTime = sim.getTime();
                    int delay = actualTime - time;
                    if(delay <= 0){
                        sim.setAnalysisResult
                        (this.name + " did not experience a delay");
                    }
                    else{
                        sim.setAnalysisResult
                        (this.name + " experienced delay of " + 
                        Integer.toString(delay) + " seconds");
                    }
                    sim.removePerson(this);
                }
            }
            /* dealing with departure from a stop */
            if(!onBus && busName.equals(b.getName())
                    && busLoc.equals(segStartLoc)){
                movePerson(b);
                if(updateCurrCapacity(b, true)){
                    onBus = true;
                }
                /* Bus was full so couldn't get on */
                else{
                    onBus = false;
                }
            }
        }
        
    }
    @Override
    public ImageIcon getImage(){
        return null;
    }
    @Override
    public String getName(){
        return this.name;
    }
    @Override
    public Location getLocation(){
        return this.location;
    }
    @Override
    public int getStartTime(){
        return this.startTime;
    }
    
    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;
        if(obj == null) return false;
        if(getClass() != obj.getClass() &&
                !(obj instanceof StandardPerson)) return false;
        final Person other = (Person) obj;
        return (this.name.equals(other.getName()) && 
                this.location.equals(other.getLocation()));
    }
    
    @Override
    public int hashCode(){
        final int prime = 31;
        int result = 1;
        
        result = prime * result;
        
        if(this.name == null) result += 0;
        else result += this.name.hashCode();
        
        if(this.location == null) result += 0;
        else result += this.location.hashCode();

        return result;
    }
}
