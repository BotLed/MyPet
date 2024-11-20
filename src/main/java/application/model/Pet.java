package main.java.application.model;

public class Pet {

    private String name;
    private String type;
    private int health;
    private int sleep;
    private int fullness;
    private int happiness;

    public Pet(String pet_name, String pet_type, int pet_health, int pet_sleep, int pet_fullness, int pet_happiness) {
        this.name = pet_name;
        this.name = pet_type;
        this.health = pet_health;
        this.sleep = pet_sleep;
        this.fullness = pet_fullness;
        this.happiness =  pet_happiness;
    }

    public void interactPet (String action) {
        return;
    }

    public void adjustStats() {
        return;
    }

    public void updatePetState() {

    }

    public void warning() {

    }

    public String getName() {
        return this.name;
    }

    public void setName(String new_name) {
        this.name = new_name;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String new_type) {
        this.type = new_type;
    }

    public String getCurrentPetState() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void setCurrentState() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

}
