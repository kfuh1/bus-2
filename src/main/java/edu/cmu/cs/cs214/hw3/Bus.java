package edu.cmu.cs.cs214.hw3;
/**
 * Interface for Bus which adds to Entity functionality.
 * @author Kathleen
 *
 */
public interface Bus extends Entity {
    /**
     * Method to get starting time of bus.
     * @return Time bus should be put into system.
     */
    int getStartTime();
    /**
     * Method to get value for index attribute.
     * @return Index in path list that currentLocation is at.
     */
    int getIndex();
    /**
     * Method to get value for nextTime attribute.
     * @return Schedule time of arrival at next stop. 
     */
    int getNextTime();
    /**
     * Method to get value for currTime attribute.
     * @return Scheduled time of arrival for current stop.
     */
    int getCurrTime();
    
    /**
     * Gets the hard limit of people bus can hold.
     * @return Capacity denoting max people that can be on a bus.
     */
    Capacity getCapacity();
    /**
     * Sets the hard limit of people a bus can hold.
     * @param c Capacity denoting max people that can be on bus.
     */
    void setCapacity(Capacity c);
    
    /**
     * Gets the current number of people on the bus.
     * @return Capacity denoting current number of people.
     */
    Capacity getCurrentSize();
    /**
     * Updates the curren number of people on the bus.
     * @param c Capacity denoting number of people on bus.
     */
    void setCurrentSize(Capacity c);
    /**
     * Updates the travel delay based on a proportion.
     * @param pct Proportion of travel time that will be extra delay.
     */
    void setTravelDelay(double pct);
    /**
     * Updates the waiting delay based on a proportion.
     * @param pct Proportion of waiting time that will be extra delay.
     */
    void setWaitDelay(double pct);
    @Override
    boolean equals(Object obj);
    @Override
    int hashCode();
}
