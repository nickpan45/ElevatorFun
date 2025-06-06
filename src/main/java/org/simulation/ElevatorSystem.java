package org.simulation;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * The ElevatorSystem class manages multiple elevators dynamically.
 * It assigns the most efficient elevator to requests while ensuring
 * smooth concurrent execution.
 */
public class ElevatorSystem {
    private final List<Elevator> elevators;
    private final ExecutorService executor;

    /**
     * Constructor to initialize the elevator system.
     * Creates an empty list of elevators and sets up a thread pool.
     */
    public ElevatorSystem() {
        elevators = new ArrayList<>();
        executor = Executors.newFixedThreadPool(4);

    }

    /**
     * Shuts down the elevator system gracefully.
     * Ensures all pending tasks are completed before termination.
     */
    public void shutdown() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(3, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }

        for (Elevator e : elevators) {
            e.shutdown();  // Ensure individual elevator shutdown
        }

        System.out.println("[Elevator System] Shutdown complete.");
    }

    /**
     * Adds an elevator to the system.
     * @param elevator The elevator instance to be added.
     */
    public void addElevator(Elevator elevator) { elevators.add(elevator); }

    /**
     * Handles elevator request for a person.
     * Finds the best available elevator and assigns the task to the thread pool.
     *
     * @param person The individual requesting an elevator.
     */
    public void requestElevator(Person person) {

        Elevator bestElevator = findBestElevator(person.getCurrentFloor());

        if (bestElevator != null) {
            executor.execute(() -> bestElevator.moveToFloor(person));
        }
    }
    /**
     * Finds the most efficient elevator for the given floor request.
     * Prioritizes the closest available elevator.
     *
     * @param floor The floor where the request originated.
     * @return The best available elevator or null if none are available.
     */
    public Elevator findBestElevator(int floor) {
        return elevators.stream()
                .filter(e -> !e.isMoving())
                .min(Comparator.comparingInt(e -> Math.abs(e.getCurrentFloor() - floor)))
                .orElse(null);
    }

    /**
     * Displays the current status of all elevators.
     * Shows elevator IDs and their current floors.
     */
    public void showStatus() {
        System.out.println("\n[Current Status]");
        for (Elevator e : elevators) {
            System.out.println("[Elevator " + e.getCurrentElevator() + "] at Floor " + e.getCurrentFloor());
        }

    }
}