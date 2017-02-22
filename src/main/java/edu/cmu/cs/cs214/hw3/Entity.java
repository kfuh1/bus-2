package edu.cmu.cs.cs214.hw3;

import javax.swing.ImageIcon;

/**
 * An Entity represents a physical object in the Simulator that has a
 * {@link Location} and is (optionally) represented with an image.
 */
public interface Entity {
    /**
     * Method that will update an Entity object if needed, should be called 
     * by the implementing class such as Bus or Person.
     */
    void step();
	/**
	 * The visualization of this Entity in the simulation. By default, you
	 * should not return an image for Person objects. However, if you would like
	 * to use the GUI to verify that Person objects are moving around as you
	 * expect, you can provide a non-null Image object.
	 *
	 *
	 * @return the image of this Entity or null if this entity should not be drawn
	 */
	ImageIcon getImage();

	/**
	 * Gets a String that serves as a description of this entity.
	 * Do not use this method in lieu of using instanceof (which you should
	 * avoid using, too).
	 *
	 * @return the name of this item
	 */
	String getName();

	/**
	 * Gets the {@link Location} of this Entity in the World.
	 *
	 * @return the location in the world
	 */
	Location getLocation();
	
	/**
	 * Overriding method to determine if two Entities are equal
	 * @param obj Entity to be compared
	 * @return true if Entities are equal, false otherwise.
	 */
	@Override
	boolean equals(Object obj);
	
	/**
	 * Overriding method to get hashcode for an Entity.
	 * @return Integer hashcode.
	 */
	@Override
	int hashCode();

}
