package edu.cmu.cs.cs214.hw3;
/**
 * Class for a larger bus that has greater capacity and different
 * delay times than the StandardBus.
 * @author Kathleen
 *
 */
public class BigBus extends BusDecorator{
    private static final int BIG_STAND_CAP = 50;
    private static final int BIG_SIT_CAP = 90;
    private static final int DELAY_CALC_FREQ = 60;
    private static final double TRAVEL_DELAY_PCT = 0.02;
    private static final double WAIT_DELAY_PCT = -0.02;
    private int counter; /* this will track how often to calculate delays */
    
    /**
     * Constructor to create a BigBus.
     * @param b Bus whose characteristics should be extended by BigBus.
     */
    public BigBus(Bus b){
        super(b);
        counter = 0;
        /* big buses should have almost double the capacity of the standard */
        Capacity c = new Capacity(BIG_STAND_CAP, BIG_SIT_CAP);
        b.setCapacity(c);

    }
    @Override
    public void step(){
        /* since large buses tend to drive slower and take longer to make turns
         * every minute I'm going to cause a slight delay, so this will average
         * to large buses being slower than standard buses. I'm aslo going to
         * cause a slight average speedup to waiting time
         * since big buses have two doors.
         */
        if(counter % DELAY_CALC_FREQ == 0){
            super.setTravelDelay(TRAVEL_DELAY_PCT);
            super.setWaitDelay(WAIT_DELAY_PCT);
        }
        counter++;
        super.step();
    }
    
}
