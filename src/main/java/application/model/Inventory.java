package application.model;

import java.util.HashMap;

/**
 * This class represents the Player's Inventory.
 * Each inventory has a specific amount of foodItems and giftItems.
 * 
 * @author CS2212 Group 31
 */

public class Inventory {

    private HashMap<String, Integer> foodItems;
    private HashMap<String, Integer> giftItems;

    // private int foodItems;
    // private int giftItems;

    public Inventory() {
        foodItems = new HashMap<String, Integer>();
        giftItems = new HashMap<String, Integer>();

        foodItems.put("fruit", 0);
        foodItems.put("meat", 0);
        foodItems.put("vegetable", 0);

        giftItems.put("toy", 0);
        giftItems.put("play place", 0);
        giftItems.put("ball", 0);
    }

    public void addItem(String item, int quantity) throws Exception {
        for (String i : foodItems.keySet()) {
            if (i.equals(item)) {// Checks if already stored
                foodItems.put(i, foodItems.get(i) + quantity);
                return;
            }
        }
        for (String i : giftItems.keySet()) {
            if (i.equals(item)) {// Checks if already stored
                giftItems.put(i, giftItems.get(i) + quantity);
                return;
            }
        }
        throw new Exception("No such item found");
    }

    public void useItem(String item, int quantity) throws Exception {
        for (String i : foodItems.keySet()) {
            if (i.equals(item)) {// Checks if already stored
                foodItems.put(i, foodItems.get(i) - quantity);
                return;
            }
        }
        for (String i : giftItems.keySet()) {
            if (i.equals(item)) {// Checks if already stored
                giftItems.put(i, giftItems.get(i) - quantity);
                return;
            }
        }
        // The item not found Error message
        throw new Exception("No such item found");
    }

    public int checkItem(String item) throws Exception {
        for (String i : foodItems.keySet()) {
            if (i.equals(item)) {// Checks if already stored
                foodItems.put(i, foodItems.get(i));
            }
        }
        for (String i : giftItems.keySet()) {
            if (i.equals(item)) {// Checks if already stored
                giftItems.put(i, giftItems.get(i));
            }
        }
        throw new Exception("No such item found");
    }

    public int getFoodItems() {
        int numOfFoodItems = 0;
        for (String i : foodItems.keySet()) {
            numOfFoodItems += foodItems.get(i);
        }
        return numOfFoodItems;
    }

    public int getGiftItems() {
        int numOfGiftItems = 0;
        for (String i : giftItems.keySet()) {
            numOfGiftItems += giftItems.get(i);
        }
        return numOfGiftItems;
    }
}
