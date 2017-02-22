package edu.cmu.cs.cs214.hw3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.LinkedList;

import edu.cmu.cs.cs214.hw2.DefaultRoutePlannerBuilder;
import edu.cmu.cs.cs214.hw2.RoutePlanner;
import edu.cmu.cs.cs214.hw2.RoutePlannerBuilder;
import edu.cmu.cs.cs214.hw2.Stop;
import edu.cmu.cs.cs214.hw2.Itinerary;

/**
 * Class to create riders in transit system.
 * @author Kathleen
 *
 */
public class PersonFactory implements Factory {
    private static final int MAX_WAIT_TIME = 1200;
    private Simulation sim;
    private String filename;
    private RoutePlanner rp;
    private RoutePlannerBuilder builder;
    private int totalRidersPerDay;
    private int totalRidersToAdd;
    private int personCounter;
    private List<Person> riders;
    /**
     * Constructor to initialize PersonFactory object
     * @param s simulation.
     * @param file path to .txt file with stop information
     * @param numRiders Number of riders to be added in one simulation day.
     */
    public PersonFactory(Simulation s, String file, int numRiders){
        this.sim = s;
        this.filename = file;
        this.builder = new DefaultRoutePlannerBuilder();
        this.totalRidersPerDay = numRiders;
        this.totalRidersToAdd = numRiders;
        this.personCounter = 0;
        this.riders = new LinkedList<Person>();
        
        try {
            rp = builder.build(filename, MAX_WAIT_TIME);
        } catch (IOException e) {
            System.out.println("Error");
        }
   
    }
    
    /**
     * Method to create Stop object based on stop name.
     * @param name Stop name.
     * @return Stop object.
     */
    private Stop getStop(String name){
        try{
            FileReader inputFile = new FileReader(filename);
            BufferedReader bufferReader = new BufferedReader(inputFile);
            String fileLine;

            while((fileLine = bufferReader.readLine()) != null ){
                /* Find a stop with the name to get location */
                String[] values = fileLine.split(",");
                if(values[0].equals(name)){
                    String latitude = values[1];
                    String longitude = values[2];
                    Stop stop = new Stop(name, latitude, longitude);
                    bufferReader.close();
                    return stop;
                }
            }
            bufferReader.close();
        }
        catch(Exception e){
            System.out.println("Error");
        }
        return null;
    }
    /**
     * Method to step all person objects in factory once simulation steps.
     */
    public void step(){
        int simulationTime = sim.getTime();
        createRiders();
        for(Person p : this.riders){
            if(p.getStartTime() == simulationTime){
                sim.insertPerson(p);
                
            }
        }
    }
    /**
     * Method to create Person object for each person to be inserted.
     */
    private void createRiders(){
        boolean routeExists, validStops;
        int time = sim.getTime();
        double n = Util.getRidersPerSecond(time, totalRidersPerDay);
        /* Find number of people with certain payment forms are carry ons
         * based on given percentages from simulation. */
        int numRidersToAdd = (int) Math.round(n);
        int numRidersWithLuggage = (int) ((double) numRidersToAdd 
                * sim.getFracLuggage()); 
        int numRidersWithRFID = (int) ((double) numRidersToAdd * sim.getFracRFID());
        
        /* Add riders while there are still people to add */
        while(numRidersToAdd > 0 && totalRidersToAdd > 0){
            String startName, endName;
            Stop source = null;
            Stop dest = null;
            Itinerary it = null;
            routeExists = false;
            validStops = false;
            /* search for random stops until both are valid and a route 
             * exists between them.
             */
            while(!validStops || !routeExists){
                Person p;
                startName = Util.getRandomStop();
                endName = Util.getRandomStop();
                source = getStop(startName);
                dest = getStop(endName);
                /* make sure stops are valid and source and dest are not the
                 * same stop. */
                if(source != null && dest != null && !source.equals(dest)){
                    validStops = true;
                    it = rp.computeRoute(source, dest, time);
                    if(it != null){
                        routeExists = true;
                        /* Create riders based on types they're supposed to be */
                        if(numRidersWithLuggage > 0){
                            p = new PersonWithLuggage(this.sim, it, 
                                    "p"+Integer.toString(this.personCounter));
                            numRidersWithLuggage--;
                        }
                        else if(numRidersWithRFID > 0){
                            p = new PersonWithRFID(this.sim, it, 
                                    "p"+Integer.toString(this.personCounter));
                            numRidersWithRFID--;
                        }
                        else{
                           p = new PersonWithCash(this.sim, it, 
                                   "p"+Integer.toString(this.personCounter));
                        }
                        this.riders.add(p);
                        this.personCounter++;
                    }
                    else{
                        validStops = false;
                    }
                }
            }
            numRidersToAdd--;
            totalRidersToAdd--;
        }
    }
}
