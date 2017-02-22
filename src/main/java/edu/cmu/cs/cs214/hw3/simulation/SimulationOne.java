package edu.cmu.cs.cs214.hw3.simulation;

import javax.swing.SwingUtilities;

import edu.cmu.cs.cs214.hw3.Factory;
import edu.cmu.cs.cs214.hw3.PersonFactory;
import edu.cmu.cs.cs214.hw3.BusFactory;
import edu.cmu.cs.cs214.hw3.Simulation;
import edu.cmu.cs.cs214.hw3.TransitSimulation;
import edu.cmu.cs.cs214.hw3.gui.SimulationUI;

/* The simulations are a little slow at the beginning, with larger files
 * but once the buses start appearing it seems fine.
 */

/**
 * A Main class initializes a simulation and runs it.
 * This one should have the worst distribution of entities,
 * so supposedly the greatest amount of delay.
 */
public class SimulationOne {
    private static final int NUM_RIDERS = 1000;
    private static final String ROUTES_FILE_NAME = "src/main/resources/oakland_stop_times.txt";
    private static final double PCT_RFID = 0.0;
    private static final double PCT_LUGGAGE = 20.0;
    private static final double PCT_DELAYS = 100.0;
    /**
     * Starts up the simulation and the GUI representation
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SimulationOne().createAndShowSimulation();
            }
        });
    }

    /**
     * Sets up the simulation and starts the GUI
     */
    public void createAndShowSimulation() {
        Simulation s = new TransitSimulation(PCT_RFID,PCT_LUGGAGE,PCT_DELAYS);
        Factory pf = new PersonFactory(s, ROUTES_FILE_NAME,NUM_RIDERS);
        s.addFactory(pf);
        Factory bf = new BusFactory(s, ROUTES_FILE_NAME);
        s.addFactory(bf);
        SimulationUI sui = new SimulationUI(s);
        sui.show();
    }

}
