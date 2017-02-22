package edu.cmu.cs.cs214.hw3;

import java.util.List;
import java.util.LinkedList;
import java.util.Random;

import edu.cmu.cs.cs214.hw2.Stop;
/**
 * Class to implement simulation of transit system.
 * @author Kathleen
 *
 */
public class TransitSimulation implements Simulation {
    private static final int START = 14400;
    private static final int MIDNIGHT = 86400;
    private static final double HUNDRED_PCT = 100.0;
    public static final int AM_RUSH_START = 27000;
    public static final int PM_RUSH_START = 59400;
    public static final int AM_RUSH_END = 32400;
    public static final int PM_RUSH_END = 64800;
    
    /* Values associated with weather forecasts */
    private static final String[] FORECASTS =
        {"Sun", "Rain", "Snow", "Snow", "Rain", "Rain", "Rain"};
    private static final double NO_DELAY = 0.0;
    private static final double RAIN_DELAY = 0.2;
    private static final double SNOW_DELAY = 0.5;
    private List<Factory> factories;
    private List<Bus> buses;
    private List<Bus> busesToRemove;
    private List<Person> riders;
    private List<Person> ridersToRemove;
    private List<Entity> entities;
    private String analysisResult;
    private String finalAnalysisResult;
    private String weather;
    private double fracRFID;
    private double fracLuggage;
    private double fracDelays;
    private boolean displayResult = true;
    private int time;
    /**
     * Constructor to initialize TransitSimulation object.
     * @param pctRFID percentage of people with RFID card.
     * @param pctLuggage percentage of people with luggage.
     * @param pctDelays percentage of buses that will have possible
     *  non standard delays.
     */
    public TransitSimulation(double pctRFID, double pctLuggage, 
            double pctDelays){
        factories = new LinkedList<Factory>();
        buses = new LinkedList<Bus>();
        busesToRemove = new LinkedList<Bus>();
        ridersToRemove = new LinkedList<Person>();
        riders = new LinkedList<Person>();
        entities = new LinkedList<Entity>();
        finalAnalysisResult = "";
        weather = getRandomWeather();
        fracRFID = pctRFID/HUNDRED_PCT;
        fracLuggage = pctLuggage/HUNDRED_PCT;
        fracDelays = pctDelays/HUNDRED_PCT;
        
        setAnalysisResult("Forecast today is " + weather + " \n" +
                "distribution of entities: \n" +
                Double.toString(pctRFID) + "% with RFID cards \n" +
                Double.toString(pctLuggage) + "% with luggage \n" +
                Double.toString(pctDelays) + 
                "% buses will have unexpected delays");
        
        this.time = START; /* simulation starts at 4am */
    }
    /**
     * Method to get random weather.
     * @return String representing type of weather.
     */
    private String getRandomWeather(){
        Random rand = new Random();
        int max = FORECASTS.length-1;
        int index = rand.nextInt((max)+1);
        return FORECASTS[index];
    }
    @Override
    public double getWeatherDelay(){
        if(weather.equals("Sun")){
            return NO_DELAY;
        }
        else if(weather.equals("Rain")){
            return RAIN_DELAY;
        }
        else if(weather.equals("Snow")){
            return SNOW_DELAY;
        }
        else{
            return NO_DELAY;
        }
    }
    @Override
    public void step(){
        /* Not sure if this actually helping. I can't remove from buses or 
         * people in the removeBus and removePerson methods because that 
         * would cause concurrent modification of the buses and riders lists,
         *  so I keep track of the buses to remove and then removing them here.
         */
        for(Bus b : busesToRemove){
            buses.remove(b);
        }
        busesToRemove.clear();
        for(Person p : ridersToRemove){
            riders.remove(p);
        }
        ridersToRemove.clear();
        /* Update analysis string */
        if(time == AM_RUSH_START){
            setAnalysisResult("AM rush hour start");
        }
        else if(time == AM_RUSH_END){
            setAnalysisResult("AM rush hour end");
        }
        else if(time == PM_RUSH_START){
            setAnalysisResult("PM rush hour start");
        }
        else if(time == PM_RUSH_END){
            setAnalysisResult("PM rush hour end");
        }
        if(this.time < MIDNIGHT){
            this.time++;
            for(Factory f : factories){
                f.step();
            }
            for(Bus b : buses){
                b.step();
            }
        }
        else{
            if(displayResult){
                System.out.println(finalAnalysisResult);
                displayResult = false;
            }
        }
    }
    @Override
    public void arriveAt(Bus b, Stop s){
        for(Person p : riders){
            p.busArrived(b, s);
        }
    }
    @Override
    public int getTime(){
        return this.time;
    }
    @Override
    public Iterable<Entity> getEntities(){
        return this.entities;
        
    }
    @Override
    public void insertBus(Bus b){
        buses.add(b);
        entities.add(b);
    }
    @Override
    public void removeBus(Bus b){
        entities.remove(b);
        busesToRemove.add(b);
    }
    @Override
    public void insertPerson(Person p){
        riders.add(p);
        entities.add(p);
    }
    @Override
    public void removePerson(Person p){
        entities.remove(p);
        ridersToRemove.add(p);
    }
    
    @Override
    public String getAnalysisResult(){
        return this.analysisResult;
    }

    @Override
    public void setAnalysisResult(String s){
        finalAnalysisResult += (s + "\n");
        this.analysisResult = s;
    }
    @Override
    public double getFracRFID(){
        return this.fracRFID;
    }
    @Override
    public double getFracLuggage(){
        return this.fracLuggage;
    }
    @Override
    public double getFracDelays(){
        return this.fracDelays;
    }
    @Override
    public void addFactory(Factory f){
        factories.add(f);
    }
    
    /* The following are for testing purposes */
    /**
     * Method to return factories.
     * @return List of factories.
     */
    public List<Factory> getFactories(){
        return this.factories;
    }
    /**
     * Method to get buses in simulation.
     * @return List of buses.
     */
    public List<Bus> getBuses(){
        return this.buses;
    }
    /**
     * Method to get riders in simulation.
     * @return List of riders.
     */
    public List<Person> getRiders(){
        return this.riders;
    }
}
