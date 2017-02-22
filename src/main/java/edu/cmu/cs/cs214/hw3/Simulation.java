package edu.cmu.cs.cs214.hw3;

import java.util.List;

import edu.cmu.cs.cs214.hw2.Stop;


/**
 * The city transportation simulation. The simulation interface is meant to be a
 * subset of the methods that will be needed for a correct implementation. A
 * single run of the simulation should represent a full day of a bus schedule
 * starting at approximately 3:30am (03:30 hrs) and ending at 12:00am (00:00
 * hrs) the following day.
 */
public interface Simulation {

	/**
	 * Performs a single step in the simulation. Each step should represent one
	 * second in the day's simulation.
	 */
	void step();

	/**
	 * Returns to the caller the seconds that have elapsed since midnight.
	 * 
	 * @return The seconds since midnight
	 */
	int getTime();

	/**
	 * Returns the collection of {@link Entity}s in this Simulation. The
	 * <code>Iterable</code> interface enables this collection to be used in a
	 * "for each" loop:
	 *
	 * <pre>
	 *   e.g. <code> for(Entity entity : simulation.getEntities()) {...}
	 * </pre>
	 *
	 * @return a collection of all {@link Entity}s in this Simulation
	 */
	Iterable<Entity> getEntities();
	
	/**
	 * Add a factory to simulation.
	 * @param f Factory to be added.
	 */
	void addFactory(Factory f);
	/**
	 * Insert bus into simulation.
	 * @param b Bus to be added.
	 */
	void insertBus(Bus b);
	/**
	 * Insert person into simulation.
	 * @param p Person to be added.
	 */
	void insertPerson(Person p);
	/**
	 * Remove bus from simulation.
	 * @param b Bus to be removed.
	 */
	void removeBus(Bus b);
	/**
	 * Remove person from simulation.
	 * @param p Person to be removed.
	 */
	void removePerson(Person p);
	/**
	 * Runs updates when a bus has arrived at a stop.
	 * @param b Bus of interest.
	 * @param s Stop that bus arrived at.
	 */
	void arriveAt(Bus b, Stop s);
	
	/**
	 * Get proportion of delay due to weather.
	 * @return proportion of delay.
	 */
	double getWeatherDelay();
	
	/**
	 * Get proportion of people with RFID cards.
	 * @return proportion of people.
	 */
	double getFracRFID();
	/**
	 * Get proportion of people with luggage.
	 * @return proportion of people.
	 */
	double getFracLuggage();
	/**
	 * Get proportion of people with delays.
	 * @return proportion of people.
	 */
    double getFracDelays();

	/**
	 * A method that a user of the simulation will use to display an analysis of
	 * the current (or final) state of the simulation. The returned string
	 * should be a summary of important statistics of the simulation. An example
	 * return value could be:
	 * 
	 * "Busses on time: 67%  | People on time: 45%"
	 * 
	 * @return a string with an analysis of the current state of the simulation
	 */
	String getAnalysisResult();
	/**
	 * Method to set analysis result value
	 * @param s Value to set analysis to.
	 */
	void setAnalysisResult(String s);
	
	/* The following are for testing purposes */
    /**
     * Method to return factories.
     * @return List of factories.
     */
    List<Factory> getFactories();
    /**
     * Method to get buses in simulation.
     * @return List of buses.
     */
    List<Bus> getBuses();
    /**
     * Method to get riders in simulation.
     * @return List of riders.
     */
    List<Person> getRiders();

}
