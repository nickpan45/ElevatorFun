package org.simulation;
import java.util.Random;

/*
 * This class runs a simulated elevator system, initializing elevators,
 * generating random requests from people, and processing those requests.
 */


// Main class for simulation
public class ElevatorSim {
    public static void main(String[] args) throws InterruptedException {
        ElevatorSystem system = new ElevatorSystem();
        Random random = new Random();

        // Initialize 4 elevators at random floors
        for (int i = 1; i <= 4; i++) {
            system.addElevator(new Elevator.Builder().setId(i).setInitialFloor(random.nextInt(12) + 1).build());
        }

        // Display initial elevator status
        system.showStatus();

        // Simulate 8 people requesting elevators randomly
        for (int i = 1; i <= 8; i++) {
            int startFloor = random.nextInt(12) + 1;
            int destinationFloor = random.nextInt(12) + 1;
            boolean hasSecurityCard = random.nextBoolean();

            // Create a person instance with the generated details
            Person person = new Person(i, startFloor, destinationFloor, hasSecurityCard);
            system.requestElevator(person);
            System.out.println("[Request] " + person);
        }

        // Allow requests to be processed
        Thread.sleep(5000);

        // Proper shutdown of threads
        System.out.println("\nShutting down elevator system...");
        system.shutdown();
        system.showStatus();
    }
}


