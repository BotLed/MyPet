package application.controllers;

import application.model.Player;
import application.model.Pet;
import application.model.Inventory;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class GameplayController {
    private Player player; // Reference to the current player
    private Pet pet; // Reference to the player's current pet
    private Timer statDecayTimer; // Timer to periodically adjust pet stats

    public GameplayController(Player player) {
        this.player = player;
        this.pet = player.getCurrentPet();
    }

    // ----- Gameplay Actions -----

    public void feedPet(String foodItem) {
        try {
            Inventory inventory = player.getInventory();
            inventory.useItem(foodItem, 1);
            pet.interactPet("feed", foodItem, inventory); // Adjust pet stats
            System.out.println("Fed " + pet.getName() + " with " + foodItem);
        } catch (Exception e) {
            System.out.println("Failed to feed pet: " + e.getMessage());
        }
    }

    public void playWithPet() {
        pet.interactPet("play", "", null); // No item needed for playing
        System.out.println("Played with " + pet.getName());
    }

    public void exercisePet() {
        pet.interactPet("exercise", "", null);
        System.out.println("Exercised " + pet.getName());
    }

    public void giftPet(String giftItem) {
        try {
            Inventory inventory = player.getInventory();
            inventory.useItem(giftItem, 1);
            pet.interactPet("give gift", giftItem, inventory);
            System.out.println("Gifted " + pet.getName() + " with " + giftItem);
        } catch (Exception e) {
            System.out.println("Failed to gift pet: " + e.getMessage());
        }
    }

    // ----- Periodic Pet Updates -----

    public void startStatDecay() {
        statDecayTimer = new Timer(true); // Daemon timer
        statDecayTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                pet.adjustStats(); // Adjust stats based on pet type
                System.out.println("Updated stats for " + pet.getName());
            }
        }, 0, 50000); // Update stats every 50 seconds
    }

    public void stopStatDecay() {
        if (statDecayTimer != null) {
            statDecayTimer.cancel();
        }
    }

    // ----- View Integration -----

    // Pet-related methods
    public int getPetHunger() {
        return pet.getFullness();
    }

    public int getPetHappiness() {
        return pet.getHappiness();
    }

    public int getPetSleep() {
        return pet.getSleep();
    }

    public int getPetHealth() {
        return pet.getHealth();
    }

    public String getPetName() {
        return pet.getName();
    }

    public String getMainPetState() {
        return pet.getMainPetState();
    }

    // Player-related methods
    public String getPlayerName() {
        return player.getName();
    }

    public int getPlayerScore() {
        return player.getScore();
    }

    // Inventory-related methods
    public Map<String, Integer> getInventorySummary() {
        Inventory inventory = player.getInventory();
        Map<String, Integer> summary = new HashMap<>();
        summary.put("Food Items", inventory.getFoodItems());
        summary.put("Gift Items", inventory.getGiftItems());
        return summary;
    }

    public Map<String, Integer> getAllPetStats() {
        Map<String, Integer> stats = new HashMap<>();
        stats.put("Hunger", getPetHunger());
        stats.put("Happiness", getPetHappiness());
        stats.put("Sleep", getPetSleep());
        stats.put("Health", getPetHealth());
        return stats;
    }
}
