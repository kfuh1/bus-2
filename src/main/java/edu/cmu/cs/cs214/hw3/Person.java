package edu.cmu.cs.cs214.hw3;

import edu.cmu.cs.cs214.hw2.Stop;
/**
 * Interface for Person which specific Person functionality to Entity.
 * @author Kathleen
 *
 */
public interface Person extends Entity {
    /**
     * Method to check riders for updates when bus arrives at a stop.
     * @param b Bus that has arrived.
     * @param s Stop that bus arrived at.
     */
    void busArrived(Bus b, Stop s);
    /**
     * Method to get rider's itinerary start time.
     * @return start time.
     */
    int getStartTime();
    /**
     * Method to update capacity based on whether person is getting on or off.
     * @param b Bus that is being considered.
     * @param isGettingOn True if person is getting on bus.
     * @return False if rider could not get on bus. True otherwise.
     */
    boolean updateCurrCapacity(Bus b, boolean isGettingOn);
    /**
     * Method to make changes to rider when getting on or off bus.
     * @param b Bus the rider is moving to or from.
     */
    void movePerson(Bus b);
}
