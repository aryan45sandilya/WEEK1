import java.util.*;

class WEEK1P8 {

    private String[] parkingSpots;
    private int capacity;

    public ParkingLot(int capacity) {
        this.capacity = capacity;
        parkingSpots = new String[capacity];
    }

    // Hash function
    private int hashFunction(String plate) {
        return Math.abs(plate.hashCode()) % capacity;
    }

    // Park vehicle
    public int parkVehicle(String licensePlate) {

        int index = hashFunction(licensePlate);
        int probes = 0;

        while (parkingSpots[index] != null) {

            index = (index + 1) % capacity; // linear probing
            probes++;

            if (probes == capacity) {
                System.out.println("Parking lot full");
                return -1;
            }
        }

        parkingSpots[index] = licensePlate;

        System.out.println(
                "Vehicle " + licensePlate +
                        " parked at spot #" + index +
                        " (" + probes + " probes)"
        );

        return index;
    }

    // Exit vehicle
    public void exitVehicle(String licensePlate) {

        for (int i = 0; i < capacity; i++) {

            if (licensePlate.equals(parkingSpots[i])) {

                parkingSpots[i] = null;

                System.out.println(
                        "Vehicle " + licensePlate +
                                " exited from spot #" + i
                );

                return;
            }
        }

        System.out.println("Vehicle not found");
    }

    // Show occupancy
    public void showStatus() {

        int occupied = 0;

        for (String spot : parkingSpots) {
            if (spot != null) occupied++;
        }

        double occupancy = (occupied * 100.0) / capacity;

        System.out.println("Parking Occupancy: " + occupancy + "%");
    }
}


public class ParkingSystem {

    public static void main(String[] args) {

        ParkingLot lot = new ParkingLot(10);

        lot.parkVehicle("ABC1234");
        lot.parkVehicle("ABC1235");
        lot.parkVehicle("XYZ9999");

        lot.showStatus();

        lot.exitVehicle("ABC1234");

        lot.showStatus();
    }
}