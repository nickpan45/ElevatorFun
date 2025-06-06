package org.simulation;

import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Elevator class represents an elevator with concurrency controls.
 * Each elevator operates independently using a scheduled executor service.
 * Secure floors require a security card for access.
 */


// Elevator class with concurrency controls
public class Elevator {
    private final int id;
    private int currentFloor;
    private boolean isMoving;
    private final Lock lock = new ReentrantLock();
    private final ScheduledExecutorService scheduler;
    private static final Set<Integer> secureFloors = Set.of(9, 10, 11, 12);

    private Elevator(int id, int initialFloor) {
        this.id = id;
        this.currentFloor = initialFloor;
        this.scheduler = Executors.newSingleThreadScheduledExecutor();
    }

    /**
     * Gracefully shuts down the elevator's scheduled executor service.
     * Ensures all pending tasks are completed before termination.
     */
    public void shutdown() {
        scheduler.shutdown();
        try {
            if (!scheduler.awaitTermination(3, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
            Thread.currentThread().interrupt();
        }
        System.out.println("[Elevator " + id + "] Shutdown completed.");
    }

    /**
     * Builder pattern for creating Elevator instances with controlled initialization.
     */
    public static class Builder {
        private int id;
        private int initialFloor;

        public Builder setId(int id) { this.id = id; return this; }
        public Builder setInitialFloor(int initialFloor) { this.initialFloor = initialFloor; return this; }
        public Elevator build() { return new Elevator(id, initialFloor); }
    }

    /**
     * Moves the elevator to a specified person's floor and handles access restrictions.
     * Ensures thread safety using locks and processes requests asynchronously.
     *
     * @param person The person requesting an elevator ride
     */
    public void moveToFloor(Person person) {
        lock.lock();
        try {
            if (secureFloors.contains(person.getDestinationFloor()) && !person.hasSecurityCard()) {
                System.out.println("[Elevator " + id + "] Access Denied to Floor " + person.getDestinationFloor());
                return;
            }

            System.out.println("[Elevator " + id + "] Picking up " + person + " (Thread: " + Thread.currentThread().getName() + ")");
            currentFloor = person.getCurrentFloor();
            System.out.println("[Elevator " + id + "] Moving to Destination Floor " + person.getDestinationFloor());
            currentFloor = person.getDestinationFloor();
            isMoving = true;

            scheduler.schedule(() -> {
                isMoving = false;
            }, 1, TimeUnit.SECONDS);
        } finally {
            lock.unlock();
        }
    }

    /**
     * @return The current floor of the elevator.
     */
    public int getCurrentFloor() { return currentFloor; }

    /**
     * @return The unique ID of the elevator.
     */
    public int getCurrentElevator() {return this.id;}

    /**
     * @return Whether the elevator is moving.
     */
    public boolean isMoving() { return isMoving; }
}
