package edu.cmu.cs.cs214.hw3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;

/**
 * Class to create Bus objects.
 * @author Kathleen
 *
 */
public class BusFactory implements Factory{
    private static final int ID_LINE_LEN = 2;
    private static final int BUSSTOP_LINE_LEN = 4;
    private static final int BUS_NAME_INDEX = 0;
    private static final int NUM_STOPS_INDEX = 1;
    private static final int STOP_NAME_INDEX = 0;
    private static final int LAT_INDEX = 1;
    private static final int LONG_INDEX = 2;
    private static final int TIME_INDEX = 3;
    private static final int NO_MORE_STOPS = 0;
    private Simulation sim;
    private String filename; 
    private List<Bus> buses;
    
    /**
     * Constructor to intialize BusFactory object's attributes.
     * @param s Simulation.
     * @param pFilename .txt file with stop times.
     */
    public BusFactory(Simulation s, String pFilename){
        this.sim = s;
        this.filename = pFilename;
        this.buses = new LinkedList<Bus>();
        parseFile();
    }
    /**
     * Method to step all buses if needed when simulator steps.
     */
    public void step(){
        int simulationTime = sim.getTime();
        for(Bus b : this.buses){
            if(simulationTime == b.getStartTime()){
                sim.insertBus(b);            
            }
        }
    }
    /* Parse the file to get how many buses should be added. This slows
     * the program down a little at the beginning, but there doesn't seem
     * to be a better way to get the number of stops in a file so that we
     * can actually apply the perentage of delays when creating buses
     * (unless we recorded somewhere in the file the number of buses).
     */
    /**
     * Gets the total number of buses given in the file.
     * @return Number of buses.
     */
    private int getNumBuses(){
        int numBuses = 0;
        try{
            FileReader inputFile = new FileReader(filename);
            BufferedReader bufferReader = new BufferedReader(inputFile);
            String fileLine;

            while((fileLine = bufferReader.readLine()) != null ){
                /* Create array with each index holding stop identifying val */
                String[] values = fileLine.split(",");
                if(values.length == ID_LINE_LEN){
                    numBuses++;
                }
            }
            bufferReader.close();
        }
        catch(Exception e){
            System.out.println("Error");
        }
        return numBuses;
    }
    
    /**
     * Method to read file contents and put together bus stop information.
     */
    private void parseFile(){
        int numBuses = getNumBuses();
        int numDelayedBuses = (int) ((double) numBuses * sim.getFracDelays());
        int remainingStops = -1;
        String busName = "";
        List<LocTime> stops = null;

        try{
            FileReader inputFile = new FileReader(filename);
            BufferedReader bufferReader = new BufferedReader(inputFile);
            String fileLine;

            while((fileLine = bufferReader.readLine()) != null ){
                /* Create array with each index holding stop identifying val */
                String[] values = fileLine.split(",");
                /* Reached the line that identifies the bus name and number */
                if(values.length == ID_LINE_LEN){
                    stops = new LinkedList<LocTime>();
                    busName = getBusName(values);
                    remainingStops = getNumStops(values);
                }
                /* Reached a line that identifies a stop */
                else if(values.length == BUSSTOP_LINE_LEN){
                    String stopName = getStopName(values);
                    Location loc = new Location(getLatitude(values), 
                            getLongitude(values));
                    int time = getTime(values);
                    LocTime lt = new LocTime(stopName, loc, time);
                    stops.add(lt);
                    remainingStops--;
                }
                /* reached the end of the path for this bus */
                if(remainingStops == NO_MORE_STOPS){
                    createBus(busName, stops, numDelayedBuses);
                    numDelayedBuses--;
                }
            }
            bufferReader.close();
        }
        catch(Exception e){
            System.out.println("Error");
        }
    }
    /**
     * Method to create Bus object from file data.
     * @param name Bus name.
     * @param path List of stops the bus travels to on its route; in order.
     * @param numDelayed Number of buses to make delayed.
     */
    private void createBus(String name, List<LocTime> path, int numDelayed){
        LocTime lt = path.get(0); //should always have element
        Bus b = new StandardBus(this.sim, name, lt.getLoc(), 
                lt.getTime(), path);

        Bus bWithDriver = new BusWithDriver(b);
        
        /* make every other bus a big bus */
        if(numDelayed % 2 == 0){
            bWithDriver = new BigBus(bWithDriver);
            this.buses.add(b);
        }
        if(numDelayed > 0){
          
            Bus bWithDelay = new BusWithDelay(bWithDriver);
            this.buses.add(bWithDelay);
        }
        else{
            this.buses.add(bWithDriver);
        }
        
    }
    
    
    /* Functions used while parsing file */
    /**
     * Method to get bus name from file line that is read in.
     * @param values Array of comma-separated values from file line.
     * @return String representing the bus name.
     */
    private String getBusName(String[] values){
        return values[BUS_NAME_INDEX].trim();
    }
    /**
     * Method to get bus stop name from file line that is read in.
     * @param values Array of comma-separated values from file line.
     * @return String representing bus stop name.
     */
    private String getStopName(String[] values){
        return values[STOP_NAME_INDEX].trim();
    }
    /**
     * Method to get time of event at bus stop.
     * @param values Array of comma-separated values from file line.
     * @return NUmber representing time of event at bus stop.
     */
    private int getTime(String[] values){
        return Integer.parseInt(values[TIME_INDEX].trim());
    }
    /**
     * Method to get latitude from file line that is read in.
     * @param values Array of comma-separated values from file line.
     * @return Number representing latitude of bus stop.
     */
    private double getLatitude(String[] values){
        return Double.parseDouble(values[LAT_INDEX].trim());
    }
    /**
     * Method to get longitude from file line that is read in.
     * @param values Array of comma-separated values from file line.
     * @return Number representing longitude of bus stop.
     */
    private double getLongitude(String[] values){
        return Double.parseDouble(values[LONG_INDEX].trim());
    }
    /**
     * Method to get number of stops in one bus route.
     * @param values Array of comma-separated values from file line. 
     * @return Number representing number of stops in one bus route.
     */
    private int getNumStops(String[] values){
        return Integer.parseInt(values[NUM_STOPS_INDEX].trim());
    }
}
