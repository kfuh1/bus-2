package edu.cmu.cs.cs214.hw3;
/**
 * Interface for Factories that create Entities.
 * @author Kathleen
 *
 */
public interface Factory {
    /**
     * Method to step all entities from the factory every time simulator steps.
     */
    void step();
}
