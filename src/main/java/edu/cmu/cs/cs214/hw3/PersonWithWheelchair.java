package edu.cmu.cs.cs214.hw3;

import edu.cmu.cs.cs214.hw2.Itinerary;
/**
 * Class to represent person with wheelchair.
 * @author Kathleen
 *
 */
public class PersonWithWheelchair extends StandardPerson {
    private static final double WAIT_DELAY = 0.1;
    /**
     * Constructor to initialize person with wheelchair.
     * @param s Simulation.
     * @param i Person's itinerary.
     * @param name Person's name.
     */
    public PersonWithWheelchair(Simulation s, Itinerary i, String name){
        super(s, i, name);
    }
    /* person with wheelchair won't take up sitting seats,
     * only standing places (they'll take up more standing space too),
     * also needs to increase waitTime since they take longer to get on. */
    @Override
    public boolean updateCurrCapacity(Bus b, boolean isGettingOn){
        Capacity currSize = b.getCurrentSize();
        Capacity maxCap = b.getCapacity();
        int numSitting = currSize.getSitting();
        int numStanding = currSize.getStanding();
        int maxStanding = maxCap.getStanding();
        if(isGettingOn){
            if(numStanding < maxStanding-1){
                b.setWaitDelay(WAIT_DELAY);
                b.setCurrentSize(new Capacity(numStanding + 2,numSitting));
                return true;
            }
            else{
                return false;
            }
        }
        else{
            b.setWaitDelay(WAIT_DELAY);
            b.setCurrentSize(new Capacity(numStanding-2,numSitting));
            return true;
        }
        
    }
}
