package org;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import application.model.Ash;
import application.model.Gunchi;
import application.model.Patch;

public class ChildPetTests {

    private Ash ash;
    private Gunchi gunchi;
    private Patch patch;

    @BeforeEach
    void setUp() {
        ash = new Ash();
        gunchi = new Gunchi();
        patch = new Patch();
    }

    @Test
    void testAshInitialization() {
        assertEquals("Ash", ash.getName());
        assertEquals(100, ash.getHealth());
        assertEquals(100, ash.getSleep());
        assertEquals(100, ash.getFullness());
        assertEquals(100, ash.getHappiness());
        assertTrue(ash.getGetAllPetStates().isEmpty());
        assertEquals(3, ash.getPetType());
    }

    @Test
    void testGunchiInitialization() {
        assertEquals("Gunchi", gunchi.getName());
        assertEquals(100, gunchi.getHealth());
        assertEquals(100, gunchi.getSleep());
        assertEquals(100, gunchi.getFullness());
        assertEquals(100, gunchi.getHappiness());
        assertTrue(gunchi.getGetAllPetStates().isEmpty());
        assertEquals(2, gunchi.getPetType());
    }

    @Test
    void testPatchInitialization() {
        assertEquals("Patch", patch.getName());
        assertEquals(100, patch.getHealth());
        assertEquals(100, patch.getSleep());
        assertEquals(100, patch.getFullness());
        assertEquals(100, patch.getHappiness());
        assertTrue(patch.getGetAllPetStates().isEmpty());
        assertEquals(1, patch.getPetType());
    }

    @Test
    void ashAdjustStats() {
        ash.adjustStats();
        assertEquals(99, ash.getSleep());
        assertEquals(98, ash.getFullness());
        assertEquals(94, ash.getHappiness());
    }

    @Test
    void gunchiAdjustStats() {
        gunchi.adjustStats();
        assertEquals(98, gunchi.getSleep());
        assertEquals(96, gunchi.getFullness());
        assertEquals(97, gunchi.getHappiness());
    }

    @Test
    void patchAdjustStats() {
        patch.adjustStats();
        assertEquals(95, patch.getSleep());
        assertEquals(99, patch.getFullness());
        assertEquals(97, patch.getHappiness());
    }
}
