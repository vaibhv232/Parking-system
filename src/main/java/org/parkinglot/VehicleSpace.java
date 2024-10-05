package org.parkinglot;

import java.time.LocalDateTime;

public class VehicleSpace {
    private boolean available;
    private VehicleType type;
    private int spaceNumber;
    private LocalDateTime parkedAt; // Add this line

    public VehicleSpace(int spaceNumber, VehicleType type) {
        this.available = true;
        this.type = type;
        this.spaceNumber = spaceNumber;
    }

    public boolean isAvailable() {
        return available;
    }

    public void occupy() {
        this.available = false;
    }

    public void vacate() {
        this.available = true;
        this.parkedAt = null; // Reset parkedAt when vacated
    }

    public void setParkedAt(LocalDateTime parkedAt) { // Implement this method
        this.parkedAt = parkedAt;
    }

    public LocalDateTime getParkedAt() { // Add this getter method
        return parkedAt;
    }

    public VehicleType getType() {
        return type;
    }

    public int getSpaceNumber() {
        return spaceNumber;
    }
}
