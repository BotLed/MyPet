package application.model;

import java.util.ArrayList; // Import for handling an empty list of pet states.

public class Patch extends Pet {

    // Default constructor for Ash
    public Patch() {
        super(
                "Patch", // Name of the pet
                100, // Initial health
                100, // Initial sleep level
                100, // Initial fullness
                100, // Initial happiness
                new ArrayList<>(), // Initial states (empty list since the pet starts as normal)
                1);
    }

    /** Adjust's pet's stats by a set interval
     *  -> Different for each child class
     */
    @Override
    public void adjustStats() {
        this.setSleep(this.getSleep() - 5);
        this.setFullness(this.getFullness() - 1);
        this.setHappiness(this.getHappiness() - 3);

        this.setSleep(Math.max(this.getSleep(), 0)); // Used Math.max so that stats don't go below 0
        this.setFullness(Math.max(this.getFullness(), 0));
        this.setHappiness(Math.max(this.getHappiness(), 0));

        this.checkAndAddState(); // Check if any pet states should be added after adjusting
    }
}
