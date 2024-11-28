package application.model;

import java.util.Random;
import java.util.List;
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

    /* Allows player to interact with pet
     * @param action: the actual action that you want to use, options are in allowedInteractions (line 33)
     * @param item: item you want to gift/feed the pet, allowed options are in allowedFood/Gifts (line 34,35)
     *              PASS EMPTY STRING IF YOU ARE NOT CALLING FEED OR GIFT
     * @param inv: needed to pass the inventory that is being used by the game in order to access items in it
     */
    public void interactPet (String action, String item, Inventory inv) {
        List<String> allowedInteractions = Arrays.asList("go to bed",  "feed", "give gift", "take to the vet", "exercise");
        List<String> allowedFood = Arrays.asList("fruit", "meat", "vegetable");
        List<String> allowedGifts = Arrays.asList("toy", "play place", "ball");

        switch (action) {

            case "go to bed":
                this.caseGoToBed();
                break;

            case "feed":
                try {
                    if(!allowedFood.contains(item)) {
                        System.out.println("ERROR: item is not one of the supported food types");
                        break;
                    }

                    inv.useItem(item, 1); // Try to consume 1 unit of the item
                    this.caseFeed(item); // Line 121
                    this.checkAndAddState(); // checking if states need to be updated
                } catch (Exception e) {
                    System.out.println("Error feeding pet: " + e.getMessage());
                }
                break;

            case "give gift":
            try {
                if(!allowedGifts.contains(item)) {
                    System.out.println("ERROR: item is not one of the supported gift types");
                    break;
                }

                inv.useItem(item, 1); // Try to consume 1 unit of the item
                this.caseGift(item); // Line 126
                this.checkAndAddState(); // checking if states need to be updated
            } catch (Exception e) {
                System.out.println("Error gifting pet: " + e.getMessage());
            }
            break;

            case "take to the vet":
                this.health = Math.min(this.sleep + 15, 100); // Should go on cooldown after using
                break;

            case "play":
                this.happiness = Math.min(this.sleep + 15, 100);
                break;

            case "exercise":
                this.health = Math.min(this.health + 5, 100);
                this.sleep = Math.max(this.sleep - 5, 0);
                this.fullness = Math.max(this.fullness - 5, 0);
                break;

            default:
                System.out.println("ERROR: input is not one of the supported interactions: " + allowedInteractions);
        }

    }

    private void caseGoToBed() {
        while (this.sleep < 100) {
            try {
                // Wait for 2 seconds
                Thread.sleep(2000);

                // Increase sleep by 5
                this.sleep = Math.min(this.sleep + 5, 100); // Ensures sleep doesn't exceed 100

                // Print the current sleep value for debugging
                System.out.println("Sleep is now: " + this.sleep);
            } catch (InterruptedException e) {
                System.out.println("Sleep interrupted: " + e.getMessage());
                break; // Exit the loop if interrupted
            }
        }

        this.removePetState("sleeping");
    }

    
    private void caseFeed(String foodItem) {
                    
        switch(foodItem) {
            case "vegetable":
                this.fullness = Math.min(this.fullness + 5, 100); // Adjust fullness
                break;
            case "fruit":
                this.fullness = Math.min(this.fullness + 10, 100); // Adjust fullness
                break;
            case "meat":
                this.fullness = Math.min(this.fullness + 15, 100); // Adjust fullness
                break;
            default:
                System.out.println("ERROR: something happened during execution of 'feed' switch block");
        }
        
        System.out.println("Fed " + this.name + " with " + foodItem);
    }


    private void caseGift(String giftItem) {
        switch(giftItem) {
            case "toy":
                this.fullness = Math.min(this.happiness + 5, 100); // Adjust fullness
                break;
            case "ball":
                this.fullness = Math.min(this.happiness + 10, 100); // Adjust fullness
                break;
            case "play place":
                this.fullness = Math.min(this.happiness + 15, 100); // Adjust fullness
                break;
            default:
                System.out.println("ERROR: something happened during execution of 'gift' switch block");
        }

        System.out.println("Gifted " + this.name + " with " + giftItem);
    }


    // Changes sleep, fullness, happiness stats of pet by a random number from 0 to 5
    public void adjustStats() {
        Random random = new Random(); // Created so that its possible to call nextInt() below.
        final int UPPER_BOUND = 6; // Upper bound of the random number generation

        // Gets random integer from 0 to 5 and then subtracts that number from the appropriate value
        this.sleep = this.sleep - random.nextInt(UPPER_BOUND);
        this.fullness = this.fullness - random.nextInt(UPPER_BOUND);
        this.happiness = this.happiness - random.nextInt(UPPER_BOUND);

        this.sleep = Math.max(this.sleep, 0);
        this.fullness = Math.max(this.fullness, 0);
        this.happiness = Math.max(this.happiness, 0);

        checkAndAddState();
    }

    private void checkAndAddState() {
        // Clears existing states that depend on stats
        this.clearPetStates();
    
        // Checks each stat and update states
        if (this.health <= 0) {
            this.addPetState("dead");
        } else {
            if (this.sleep <= 0) {
                this.addPetState("sleeping");
            }
            if (this.fullness <= 0) {
                this.addPetState("hungry");
            }
            if (this.happiness <= 0) {
                this.addPetState("angry");
            }
        }
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
}
   