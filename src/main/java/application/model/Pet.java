package application.model;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class Pet {
    private String name;
    private String type;
    private List<String> currentPetStates; // Changed from String to List because its possible for the pet to be in multiple states at once
    private int health;
    private int sleep;
    private int fullness;
    private int happiness;

    public Pet(String pet_name, String pet_type, int pet_health, int pet_sleep, int pet_fullness, int pet_happiness, List<String> currentPetState) {
        this.name = pet_name;
        this.name = pet_type;
        this.health = pet_health;
        this.sleep = pet_sleep;
        this.fullness = pet_fullness;
        this.happiness =  pet_happiness;
        this.currentPetStates = currentPetState; // Should be initiliazed with empty list if the pet is normal and has no negative states
    }

    // Idk what to do with this method, its late asf I'll figure it out later
    public void interactPet (String action) {
        return;
    }


    // Changes sleep, fullness, happiness stats of pet by a random number from 0 to 5
    public void adjustStats() {
        Random random = new Random(); // Created so that its possible to call nextInt() below.
        final int UPPER_BOUND = 6; // Upper bound of the random number generation

        // Gets random integer from 0 to 5 and then subtracts that number from the appropriate value
        this.sleep = this.sleep - random.nextInt(UPPER_BOUND);
        this.fullness = this.fullness - random.nextInt(UPPER_BOUND);
        this.happiness = this.happiness - random.nextInt(UPPER_BOUND);
    }


    public String getMainPetState() { // CHANGED FROM updatePetState() to instead return the main pet state that the pet is currently in
        // Define the order of precedence
        List<String> precedenceOrder = Arrays.asList("dead", "sleeping", "angry", "hungry"); // Sorted by order of precedence
    
        // Check each state in the order of precedence
        for (String state : precedenceOrder) {
            if (this.currentPetStates.contains(state)) {
                return state; // Return the first matching state
            }
        }
    
        // Default to "normal" if no other states are found
        return "normal";
    }
    
    // CAN PROBABLY REMOVE THIS METHOD IN FAVOUR OF CALLING: if getMainPetState() != "normal", then throw some warning
    public boolean warning() { 
       if (this.sleep < 25 || this.fullness < 25 || this.happiness < 25 || this.health < 25) {
            return true;
       }

       return false;
    }


    // METHODS RELATING TO STATES
    // Helper function to check if the pet is in the current 'state'. True if it is, False if not.
    private boolean getPetState(String state) { // HELPER METHOD, CHANGED FROM UML CLASS DIAGRAM, CHANGED NAME

        if (state.equals("normal") && this.currentPetStates.isEmpty()) { 
            return true; // currentPetStates contains no negative states so the pet must be normal
        }

        for (String i : currentPetStates) {
            if (i.equals(state)) {
                return true; // currentPetStates contains the given state
            }
        }

        return false;
    }


    public void addPetState(String state) { // CHANGED RETURN TYPE TO BOOLEAN, CHANGED NAME
        List<String> allowedStates = Arrays.asList("dead", "sleeping", "angry", "hungry");
        
        // Checks if input is a valid state
        if (!allowedStates.contains(state)) {
            System.out.println("ERROR: '" + state + "' is not one of the allowed states -> " + allowedStates);
            return;
        }

        // Checks if state is already in the currentPetStates
        if (this.currentPetStates.contains(state)) {
            System.out.println("ERROR: Failed to ADD state since the pet is already in the state '" + state + "'");
            return;
        }

        // Add the state to currentPetStates
        this.currentPetStates.add(state);
        System.out.println("State '" + state + "' added successfully.");
    }


    public void removePetState(String state) { // CHANGED RETURN TYPE, CHANGED NAME
        if (!this.currentPetStates.contains(state)) { // If pet is not in the state
            System.out.println("ERROR: Failed to REMOVE state since the pet is not in the state '" + state + "'");
            return;
        }

        this.currentPetStates.remove(state);
        System.out.println("State '" + state + "' removed succesffully");
    }

    
    public void clearPetStates() {
        if (this.currentPetStates.isEmpty()) {
            System.out.println("ERROR: Pet is not in any states");
            return;
        }

        this.currentPetStates.clear();
        System.out.println("All pet states have been cleared.");
    }

    public List<String> getGetAllPetStates() {
        return this.currentPetStates;
    }
    // ------

    
    // MASSIVE BLOCK TO CHECK STATES
    public boolean isDead() {
        return this.getPetState("dead");
    }


    public boolean isSleeping() {
        return this.getPetState("sleeping");
    }


    public boolean isAngry() {
        return this.getPetState("angry");
    }


    public boolean isHungry() {
        return this.getPetState("hungry");
    }


    public boolean isNormal() {
        return this.getPetState("normal");
    }
    // ------


    // NAME
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
    // ------


    // TYPE
    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }
    // ------


    // HEALTH
    public int getHealth() {
        return this.health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
    // ------


    // SLEEP
    public int getSleep() {
        return this.sleep;
    }

    public void setSleep(int sleep) {
        this.sleep = sleep;
    }
    // -------


    // FULLNESS
    public int getFullness() {
        return this.fullness;
    }

    public void setFullness(int fullness) {
        this.fullness = fullness;
    }
    // ------

    
    // HAPPINESS
    public int getHappiness() {
        return this.happiness;
    }

    public void setHappiness(int happiness) {
        this.happiness = happiness;
    }
    // ------


    // Main method with a couple tests I ran, feel free to add your own and check if everything works ok
    public static void main(String[] args) {

        List<String> states = new ArrayList<>();
        Pet myPet = new Pet("my pet", "pet 1", 100, 100, 100, 100, states);

        System.out.println("\nTest to check if states are adjusted properly");
        myPet.adjustStats();
        System.out.println("Sleep: " + myPet.getSleep());
        System.out.println("Fullness: " + myPet.getFullness());
        System.out.println("Happiness: " + myPet.getHappiness());

        myPet.adjustStats();
        System.out.println("Sleep: " + myPet.getSleep());
        System.out.println("Fullness: " + myPet.getFullness());
        System.out.println("Happiness: " + myPet.getHappiness());

        System.out.println("\nTest to check if duplicate states can be added");
        myPet.addPetState("dead");
        myPet.addPetState("dead");

        System.out.println("\nTest to check if duplicate state can be added");
        myPet.addPetState("invalid input");

        System.out.println("\nTest to check if state is succesfully detected");
        myPet.addPetState("hungry");
        System.out.println(myPet.isHungry());
        myPet.removePetState("hungry");
        System.out.println(myPet.isHungry());

        System.out.println("\nTest to check if the main state is correctly found");
        myPet.clearPetStates();
        myPet.addPetState("hungry");
        myPet.addPetState("angry");
        System.out.println(myPet.getMainPetState());
        
        System.out.println(myPet.getGetAllPetStates());

        myPet.setHappiness(10);
        System.out.println(myPet.warning());
        
    }
}
