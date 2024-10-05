package org.parkinglot;

public class Vehicle {
    private String registrationNumber;
    private String color;
    private VehicleType type;
    private int parkingDuration; // Duration in seconds

    public Vehicle(String registrationNumber, String color, VehicleType type, int parkingDuration) {
        this.registrationNumber = registrationNumber;
        this.color = color;
        this.type = type;
        this.parkingDuration = parkingDuration;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public String getColor() {
        return color;
    }

    public VehicleType getType() {
        return type;
    }

    public int getParkingDuration() {
        return parkingDuration;
    }
}
