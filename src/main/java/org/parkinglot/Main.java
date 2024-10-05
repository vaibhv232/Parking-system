package org.parkinglot;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Initialize the parking lot
        System.out.println("Welcome to the Parking Lot System!");
        System.out.print("Enter the number of floors in the parking lot: ");
        int numFloors = scanner.nextInt();
        
        Map<VehicleType, Integer> capacityPerFloor = new HashMap<>();
        System.out.print("Enter the number of CAR spaces per floor: ");
        int carCapacity = scanner.nextInt();
        capacityPerFloor.put(VehicleType.CAR, carCapacity);
        
        ParkingLot parkingLot = new ParkingLot(numFloors, capacityPerFloor);

        boolean running = true;
        while (running) {
            System.out.println("\nChoose an action:");
            System.out.println("1. Add Vehicle");
            System.out.println("2. Remove Vehicle");
            System.out.println("3. Check Availability for a Floor");
            System.out.println("4. Exit");
            System.out.println("5. Check Parking Availability for All Floors");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    // Add Vehicle
                    System.out.print("Enter Vehicle Registration Number: ");
                    String regNumber = scanner.nextLine();
                    System.out.print("Enter Vehicle Color: ");
                    String color = scanner.nextLine();
                    System.out.print("Enter Floor Number to Park: ");
                    int floorToPark = scanner.nextInt();
                    
                    System.out.print("Enter Parking Duration in seconds: "); // New input
                    int parkingDuration = scanner.nextInt(); // Capture parking duration

                    Vehicle vehicle = new Vehicle(regNumber, color, VehicleType.CAR, parkingDuration);
                    try {
                        parkingLot.addVehicle(vehicle, floorToPark);
                        System.out.println("Vehicle added successfully.");
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;


                case 2:
                    // Remove Vehicle
                    System.out.print("Enter Vehicle Registration Number to Remove: ");
                    String regToRemove = scanner.nextLine();
                    try {
                        parkingLot.removeVehicle(regToRemove, VehicleType.CAR);
                        System.out.println("Vehicle removed successfully.");
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 3:
                    // Check Availability for a Specific Floor
                    System.out.print("Enter Floor Number to Check Availability: ");
                    int floorToCheck = scanner.nextInt();
                    parkingLot.printAvailability(floorToCheck, VehicleType.CAR);
                    break;

                case 4:
                    // Exit
                    running = false;
                    System.out.println("Exiting the system. Thank you!");
                    break;

                case 5:
                    // Check Parking Availability for All Floors
                    parkingLot.printStatus();
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }
}
