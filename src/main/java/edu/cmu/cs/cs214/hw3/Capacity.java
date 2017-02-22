package edu.cmu.cs.cs214.hw3;
/**
 * Class to store standing and sitting limits of bus.
 * @author Kathleen
 *
 */
public class Capacity {
    private int standingCap;
    private int sittingCap;
    
    /**
     * Constructor to initialize standing and sitting limits.
     * @param pStandCap Standing limit.
     * @param pSitCap Sitting limit.
     */
    public Capacity(int pStandCap, int pSitCap){
        this.standingCap = pStandCap;
        this.sittingCap = pSitCap;
    }
    /**
     * Method to get standingCap attribute.
     * @return standingCap value.
     */
    public int getStanding(){
        return this.standingCap;
    }
    /**
     * Method to get sittingCap attribute.
     * @return sittingCap value.
     */
    public int getSitting(){
        return this.sittingCap;
    }
    /**
     * Method to get total remaining space on bus.
     * @return standingCap + sittingCap.
     */
    public int getRemaining(){
        return (this.standingCap + this.sittingCap); 
    }
    /**
     * Method to update number of people standing.
     * @param cap New number of people standing.
     */
    public void setStanding(int cap){
        this.sittingCap = cap;
    }
    /**
     * Method to update number of people sitting.
     * @param cap New number of people sitting.
     */
    public void setSitting(int cap){
        this.sittingCap = cap;
    }
    
}
