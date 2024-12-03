package org;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

import application.model.Inventory;
import application.model.Pet;

public class PetClassTests {
    
    private Pet pet;

    @BeforeEach
    void setUp() {
        pet = new Pet("example pet", 100, 100, 100, 100, new ArrayList<>(), 1);
    }

    @Test
    void testPetInitialization() {
        assertEquals("example pet", pet.getName());
        assertEquals(100, pet.getHealth());
        assertEquals(100, pet.getSleep());
        assertEquals(100, pet.getFullness());
        assertEquals(100, pet.getHappiness());
        assertTrue(pet.getGetAllPetStates().isEmpty());
    }

    @Test
    void testAddPetState() {
        pet.addPetState("hungry");
        assertTrue(pet.isHungry());
        assertFalse(pet.isSleeping());
    }

    @Test
    void testAddInvalidPetState() {
        pet.addPetState("flying");
        assertFalse(pet.getGetAllPetStates().contains("flying"));
    }

    @Test
    void testRemovePetState() {
        pet.addPetState("hungry");
        pet.removePetState("hungry");
        assertFalse(pet.isHungry());
    }

    @Test
    void testRemoveNonExistentState() {
        pet.removePetState("angry");
        assertTrue(pet.getGetAllPetStates().isEmpty());
    }

    @Test
    void testClearPetStates() {
        pet.addPetState("hungry");
        pet.addPetState("angry");
        pet.clearPetStates();
        assertTrue(pet.getGetAllPetStates().isEmpty());
    }

    @Test
    void testInteractPetGoToBed() {
        pet.interactPet("go to bed", "", null);
        assertEquals(100, pet.getSleep());
    }

    @Test
    void testInteractPetFeedInvalidItem() {
        pet.interactPet("feed", "candy", null);
        assertEquals(100, pet.getFullness()); // Fullness should remain unchanged
    }

    @Test
    void testAdjustStats() {
        pet.adjustStats();
        assertTrue(pet.getSleep() >= 95 && pet.getSleep() <= 100);
        assertTrue(pet.getFullness() >= 95 && pet.getFullness() <= 100);
        assertTrue(pet.getHappiness() >= 95 && pet.getHappiness() <= 100);
    }

    @Test
    void testGetMainPetState() {
        pet.setHealth(0);
        assertEquals("dead", pet.getMainPetState());
    }

    @Test
    void testApplyPenalties() {
        pet.setSleep(0);
        pet.checkAndAddState();
        assertTrue(pet.getHealth() < 100);
        assertTrue(pet.getGetAllPetStates().contains("sleeping"));
    }

    @Test
    void testGiftInvalidItem() {
        pet.interactPet("give gift", "book", null);
        assertEquals(100, pet.getHappiness()); // Happiness should remain unchanged
    }

    @Test
    void testExerciseInteraction() {
        pet.interactPet("exercise", "", null);
        assertEquals(100, pet.getHealth());
        assertEquals(95, pet.getSleep());
        assertEquals(95, pet.getFullness());
    }

    @Test
    void testVetInteraction() {
        pet.setHealth(85);
        pet.interactPet("take to the vet", "", null);
        assertEquals(100, pet.getHealth());
    }


    @Test
    void testPlayInteraction() {
        pet.setHappiness(85);
        pet.interactPet("play", "", null);
        assertEquals(100, pet.getHealth());
    }


    @Test
    void testGiftInteraction() {
        Inventory mockInventory = new Inventory();
        try {
            mockInventory.addItem("toy", 1);
        } catch (Exception e) {
            fail("Unexpected exception while adding item to inventory: " + e.getMessage());
        }

        

        pet.interactPet("give gift", "toy", mockInventory);

        assertEquals(100, pet.getHappiness());
        assertThrows(Exception.class, () -> mockInventory.useItem("toy", 1));

    }

    @Test 
    void testFeedInteraction() {
        Inventory mockInventory = new Inventory();
        try {
            mockInventory.addItem("meat", 1);
        } catch (Exception e) {
            fail("Unexpected exception while adding item to inventory: " + e.getMessage());
        }

        pet.interactPet("feed", "meat", mockInventory);

        assertEquals(100, pet.getHappiness());
        assertThrows(Exception.class, () -> mockInventory.useItem("meat", 1));
    }
}
