package edu.cmu.cs.cs214.hw3;

import java.util.List;

import javax.swing.ImageIcon;

import edu.cmu.cs.cs214.hw2.Stop;
/**
 * Class that represents a Bus in the transit system.
 * @author Kathleen
 *
 */
public class StandardBus implements Bus{
    private static final int INFINITY = 100000;
    private static final double NINETY_PCT = 0.9;
    /* Rush hour start and end times -
     * morning goes from 7:30-9, night goes from 4:30-6
     */
    private static final int AM_RUSH_START = 27000;
    private static final int PM_RUSH_START = 59400;
    private static final int AM_RUSH_END = 32400;
    private static final int PM_RUSH_END = 64800;
    private static final double RUSH_HOUR_DELAY = 1.0;
    
    /* Values used to set up speed up during late hours of night
     * or early in the morning  */
    private static final int LATE_NIGHT_START = 72000;
    private static final int EARLY_MORN_END = 23400;
    private static final double OFF_HOUR_SPEEDUP = -0.1;
    /* These are going to be used to calculate additional time
     * when a bus experiences delays
     */
    private static final int AVG_WAIT_TIME = 15;
    private static final int AVG_TRAVEL_TIME = 30;
    
    private static final int STD_BUS_SEATS = 45;
    private static final int STD_BUS_STAND = 25;
    private static final int NOONE = 0;
    
    /* lowest value a speedup can get to
     * this ensures that the traveltime won't become negative
     * in which case the bus would get stuck.
     */
    private static final double LO_THRESHOLD = 0.25;
    
    private String name;
    private Location location; /* current location */
    private int startTime;
    private int index; /* helps track current point in route */
    private int nextTime;
    private int currTime;
    private int baseTravelTime;
    private int travelTime;
    private int waitTime;
    private Capacity busCapacity;
    private Capacity currSize;
    private Simulation sim;
    
    private List<LocTime> path;
    
    /**
     * Constructor to initialize attributes of Bus object.
     * @param pSim Simulation this bus is related to.
     * @param pName Name of bus.
     * @param pLoc Starting location.
     * @param pStartT Starting time.
     * @param pPath All stops in this bus's schedule the order of travel.
     */
    public StandardBus(Simulation pSim, String pName, Location pLoc, 
            int pStartT, List<LocTime> pPath){
        this.sim = pSim;
        this.name = pName;
        this.location = pLoc;
        this.startTime = pStartT;
        this.currTime = pStartT;
        this.nextTime = pStartT;
        this.index = 0;
        this.path = pPath;
        this.baseTravelTime = (int) ((double) AVG_TRAVEL_TIME * sim.getWeatherDelay());
        this.travelTime = this.baseTravelTime;
        this.busCapacity = new Capacity(STD_BUS_STAND,STD_BUS_SEATS);
        this.currSize = new Capacity(NOONE, NOONE);
        this.waitTime = AVG_WAIT_TIME;
        if(path.size() > 1){
            LocTime next = path.get(index+1);
            int initNextTime = next.getTime();
            double initTravelTime = (double) (initNextTime - this.startTime);
            this.nextTime = this.startTime + (int) (Math.round(NINETY_PCT * initTravelTime));
        }
    }
    /**
     * Method to return filename for bus image.
     * @return path to bus image file.
     */
    public ImageIcon getImage(){
        ImageIcon i = new ImageIcon("src/main/resources/bus.png");
        return i;
    }
    /**
     * Method to update individual bus when simulator steps.
     */
    public void step(){
        int simTime = sim.getTime();
        
        if(simTime == this.nextTime || simTime == this.startTime){
            updateBus(simTime);
            while(this.currTime == this.nextTime){
                updateBus(simTime);
            }
        }
        if(this.currTime == INFINITY || this.nextTime == INFINITY){
            sim.removeBus(this);
        }
    }
    /**
     * Method to update bus attributes to set delays and travel times.
     * @param simTime Current time in simulation.
     */
    private void updateBus(int simTime){
        int totalTravelTime = 0;
        this.index++;
        if(this.index >= path.size()){
            this.currTime = INFINITY; 
        }
        else{
            double pctOfTravelTime;
            LocTime lt = this.path.get(index);
            this.currTime = this.nextTime;
            this.location = lt.getLoc();
            if(index + 1 < path.size()){
                LocTime nextLT = this.path.get(index + 1);
                LocTime currLT = this.path.get(index);
                
                /* calculate travel time between curr and next stops */
                totalTravelTime = nextLT.getTime()-currLT.getTime();
                //this.travelTime = nextLT.getTime()-currLT.getTime();
                pctOfTravelTime = NINETY_PCT * (double) totalTravelTime;
                totalTravelTime = (int) Math.round(pctOfTravelTime);
                this.travelTime = totalTravelTime;
                
                /* create traffic during rush hour - I fixed this kind of
                 * delay since rush hour is pretty much always bound to happen
                 * and you can change the start and end times if you wish. 
                 * Other delays are added in the decorator */
                if((simTime >= AM_RUSH_START && simTime <= AM_RUSH_END) ||
                        (simTime >= PM_RUSH_START && simTime <= PM_RUSH_END)){
                    totalTravelTime += (int) (RUSH_HOUR_DELAY * (double) AVG_TRAVEL_TIME);
                }
                if(simTime >= LATE_NIGHT_START || simTime <= EARLY_MORN_END){
                    totalTravelTime += (int) (OFF_HOUR_SPEEDUP * 
                            (double) AVG_TRAVEL_TIME);
                }
                totalTravelTime += (this.waitTime + this.travelTime); 
                if(totalTravelTime < this.travelTime){
                    totalTravelTime = (int) (LO_THRESHOLD * (double) this.travelTime);
                }
                this.nextTime = this.currTime + totalTravelTime;
                /* We want to reset these to 0 so that delay calculations for
                 * other buses will not be affected
                 */
                this.waitTime = 0;
                this.travelTime = this.baseTravelTime;
            }
            else{
                this.nextTime = INFINITY;
            }
            Location loc = lt.getLoc();
            Stop arriveLoc = new Stop(lt.getName(), 
                    Double.toString(loc.getLat()), 
                    Double.toString(loc.getLng()));
            sim.arriveAt(this, arriveLoc);
        }
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
    public int getIndex(){
        return this.index;
    }
    @Override
    public int getNextTime(){
        return this.nextTime;
    }
    @Override
    public int getCurrTime(){
        return this.currTime;
    }
    @Override
    public Capacity getCapacity() {        
        return this.busCapacity;
    }
    @Override
    public void setCapacity(Capacity c){
        this.busCapacity = c;
    }
    @Override
    public Capacity getCurrentSize(){
        return this.currSize;
    }
    @Override
    public void setCurrentSize(Capacity c){
        this.currSize = c;
    }
    @Override
    public void setTravelDelay(double pct){
        this.travelTime += (int) Math.round(((double) AVG_TRAVEL_TIME * pct));
    }
    @Override
    public void setWaitDelay(double pct){
        this.waitTime += (int) Math.round(((double) AVG_WAIT_TIME * pct));
        if(this.waitTime < 0){
            this.waitTime = 0;
        }
    }
    
    /**
     * Overriding method to test for Bus equality.
     * @return true if two Bus objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;
        if(obj == null) return false;
        
        /* I wasn't quite sure how to get around this equality checking
         * given that the decorator extensions wouldn't be subclasses of
         * the StandardBus, so I just checked the instances like this,
         * but I know it might not be good to check for equality like this.
         */
        if(getClass() != obj.getClass() &&
                !(obj instanceof BusWithDelay) &&
                !(obj instanceof BusWithDriver) &&
                !(obj instanceof BigBus)) return false;
        final Bus other = (Bus) obj;
        return (this.name.equals(other.getName()) && 
                this.location.equals(other.getLocation()) &&
                this.startTime == other.getStartTime());
    }
    /**
     * Overriding method to get hashcode for Bus object.
     * @return Integer hashcode.
     */
    @Override
    public int hashCode(){
        final int prime = 31;
        int result = 1;
        
        result = prime * result;
        
        if(this.name == null) result += 0;
        else result += this.name.hashCode();
        
        if(this.location == null) result += 0;
        else result += this.location.hashCode();
        
        result += this.startTime;

        return result;
    }
    
    
}

