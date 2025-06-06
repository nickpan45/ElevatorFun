package org.simulation;

/**
 * Represents a person requesting elevator service in the simulation.
 * Each person has an ID, a current floor, a destination floor, and an access card.
 */
public class Person {
    private final int id;
    private final int currentFloor;
    private final int destinationFloor;
    private final boolean hasSecurityCard;

    /**
     * Constructor to initialize a person's elevator request details.
     *
     * @param id Unique identifier for the person.
     * @param currentFloor The starting floor where the person is located.
     * @param destinationFloor The floor to which the person wants to travel.
     * @param hasSecurityCard Indicates if the person can access secure floors.
     */
    public Person(int id, int currentFloor, int destinationFloor, boolean hasSecurityCard) {
        this.id = id;
        this.currentFloor = currentFloor;
        this.destinationFloor = destinationFloor;
        this.hasSecurityCard = hasSecurityCard;
    }

    /**
     * @return The current floor of the person.
     */
    public int getCurrentFloor() { return currentFloor; }

    /**
     * @return The destination floor where the person wants to travel.
     */
    public int getDestinationFloor() { return destinationFloor; }

    /**
     * @return True if the person has a security card, false otherwise.
     */
    public boolean hasSecurityCard() { return hasSecurityCard; }

    /**
     * Returns a string representation of the person's request.
     *
     * @return A formatted string showing the person's ID, current floor, and destination floor.
     */
    @Override
    public String toString() {
        return "[Person " + id + "] Floor " + currentFloor + " → Destination: " + destinationFloor;
    }
}
