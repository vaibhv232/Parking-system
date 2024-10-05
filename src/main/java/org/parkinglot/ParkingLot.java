package org.parkinglot;

import java.util.HashMap;
import java.util.Map;
import java.time.Duration;
import java.time.LocalDateTime;

public class ParkingLot {
    private int totalFloors;
    private Map<Integer, Floor> floors;

    public ParkingLot(int totalFloors, Map<VehicleType, Integer> capacityPerFloor) {
        this.totalFloors = totalFloors;
        floors = new HashMap<>();
        initializeFloors(capacityPerFloor);
    }

    private void initializeFloors(Map<VehicleType, Integer> capacityPerFloor) {
        for (int i = 1; i <= totalFloors; i++) {
            floors.put(i, new Floor(i, capacityPerFloor));
        }
    }

    public synchronized boolean addVehicle(Vehicle vehicle, int floorNumber) {
        if (floorNumber < 1 || floorNumber > totalFloors) {
            System.out.println("Invalid floor number.");
            return false;
        }

        Floor floor = floors.get(floorNumber);
        Map<VehicleType, Integer> floorCapacity = floor.getCapacity();
        VehicleType vehicleType = vehicle.getType();

        if (!floorCapacity.containsKey(vehicleType)) {
            System.out.println("Invalid vehicle type for this floor.");
            return false;
        }
        for (VehicleSpace space : floor.getSpaces().values()) {
            if (space.getType() == vehicleType && space.isAvailable()) {
                space.occupy(); // Mark space as occupied
                space.setParkedAt(LocalDateTime.now()); // Set the time the vehicle was parked

                System.out.println(vehicleType + " with registration number " + vehicle.getRegistrationNumber() +
            " parked at space " + space.getSpaceNumber() + " on floor " + floorNumber + 
            " for " + vehicle.getParkingDuration() + " seconds.");

    new Thread(() -> {
        try {
            Thread.sleep(vehicle.getParkingDuration() * 1000); // Convert to milliseconds
            System.out.println("Removing vehicle " + vehicle.getRegistrationNumber() + " after " + vehicle.getParkingDuration() + " seconds.");
            removeVehicle(vehicle.getRegistrationNumber(), vehicle.getType());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }).start();

    return true;
            }
        }

        System.out.println("No available spaces for " + vehicleType + " on floor " + floorNumber + ".");
        return false;
    }

    public boolean removeVehicle(String registrationNumber, VehicleType vehicleType) {
        for (Floor floor : floors.values()) {
            for (VehicleSpace space : floor.getSpaces().values()) {
                if (!space.isAvailable() && space.getType() == vehicleType) {
                    LocalDateTime parkedAt = space.getParkedAt(); // Get the parking time
                    long secondsParked = Duration.between(parkedAt, LocalDateTime.now()).getSeconds();
                    
                    space.vacate(); // Vacate the space
                    String notificationMessage = vehicleType + " with registration number " +
                            registrationNumber + " removed from space " + space.getSpaceNumber() +
                            " on floor " + floor.getFloorNumber() + ". Parked for " + secondsParked + " seconds.";
    
                    // Send notification
                    Notification.sendNotification(notificationMessage);
                    
                    // Print the updated status of the floor
                    printAvailability(floor.getFloorNumber(), vehicleType);
                    return true;
                }
            }
        }
    
        System.out.println("Vehicle with registration number " + registrationNumber + " not found in the parking lot.");
        return false;
    }
    
    public void printAvailability(int floorNumber, VehicleType vehicleType) {
        if (floorNumber < 1 || floorNumber > totalFloors) {
            System.out.println("Invalid floor number.");
            return;
        }
    
        Floor floor = floors.get(floorNumber);
    
        if (floor.getCapacity().containsKey(vehicleType)) {
            int totalCapacity = floor.getCapacity().get(vehicleType);
            int availableSpaces = 0;
    
            for (VehicleSpace space : floor.getSpaces().values()) {
                if (space.getType() == vehicleType && space.isAvailable()) {
                    availableSpaces++;
                }
            }
    
            System.out.println("Available spaces for " + vehicleType + " on floor " + floorNumber + ": " +
                    availableSpaces + " out of " + totalCapacity + ".");
        } else {
            System.out.println("Invalid vehicle type for this floor.");
        }
    }
    

    // New method to check the status of each floor
    public void printStatus() {
        System.out.println("\n---- Parking Lot Status ----");
        for (int floorNumber = 1; floorNumber <= totalFloors; floorNumber++) {
            Floor floor = floors.get(floorNumber);
            System.out.println("Floor " + floorNumber + ":");

            for (VehicleType vehicleType : floor.getCapacity().keySet()) {
                int totalCapacity = floor.getCapacity().get(vehicleType);
                int occupiedSpaces = 0;
                int freeSpaces = 0;

                for (VehicleSpace space : floor.getSpaces().values()) {
                    if (space.getType() == vehicleType) {
                        if (space.isAvailable()) {
                            freeSpaces++;
                        } else {
                            occupiedSpaces++;
                        }
                    }
                }

                System.out.println(" - " + vehicleType + ": " + occupiedSpaces + " occupied, " + freeSpaces + " available.");
            }
        }
    }
}
