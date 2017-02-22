package edu.cmu.cs.cs214.hw3;

import javax.swing.ImageIcon;
/**
 * Class that acts as the point of extension for adding characterstics to Bus.
 * @author Kathleen
 *
 */
public abstract class BusDecorator implements Bus{
    private Bus modifiedBus;
    /**
     * Constructor to set modifiedBus to the decorated bus.
     * @param bus Decorated bus
     */
    public BusDecorator(Bus bus){
        this.modifiedBus = bus;
    }
    @Override
    public void step(){
        modifiedBus.step();
    }
    @Override
    public ImageIcon getImage(){
        return modifiedBus.getImage();
    }
    @Override
    public String getName(){
        return modifiedBus.getName();
    }
    @Override
    public Location getLocation(){
        return modifiedBus.getLocation();
    }
    @Override
    public int getCurrTime(){
        return modifiedBus.getCurrTime();
    }
    @Override
    public int getNextTime(){
        return modifiedBus.getNextTime();
    }
    @Override
    public int getStartTime(){
        return modifiedBus.getStartTime();
    }
    @Override
    public int getIndex(){
        return modifiedBus.getIndex();
    }
    @Override
    public Capacity getCapacity(){
        return modifiedBus.getCapacity();
    }
    @Override
    public void setCapacity(Capacity c){
        modifiedBus.setCapacity(c);
    }
    @Override
    public Capacity getCurrentSize(){
        return modifiedBus.getCurrentSize();
    }
    @Override
    public void setCurrentSize(Capacity c){
        modifiedBus.setCurrentSize(c);
    }
    @Override
    public void setTravelDelay(double pct){
        modifiedBus.setTravelDelay(pct);
    }
    @Override
    public void setWaitDelay(double pct){
        modifiedBus.setWaitDelay(pct);
    }
    @Override
    public boolean equals(Object obj){
        return modifiedBus.equals(obj);
    }
    @Override
    public int hashCode(){
        return modifiedBus.hashCode();
    }

}
