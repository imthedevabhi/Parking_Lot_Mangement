package com.parking.parkinglot.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.parking.parkinglot.vehicle.VehicleDetails;

public class ParkingService {
	
	/**
	 *  @Author Abhishek.khandelwal
	 */

	private  int capacity = -1;
	private Map<Integer, VehicleDetails> parkingSlots;
	private Map<String, Integer> vehicleSlot;
	private Map<String, List<Integer>> colorToSlots;

	/**
	 * 
	 * @param capacity the parameter is used to create the parking lot with given capacity
	 */
	public ParkingService(int capacity) {
		this.capacity = capacity;
		this.parkingSlots = new HashMap<>(capacity);
		this.vehicleSlot = new HashMap<>(capacity);
		this.colorToSlots = new HashMap<>(capacity);
	}
	
	/**
	 * This method is used to park a vehicle in the parkinglot with the
	 * following input registrationNumber & color
	 * @param registrationNumber the parameter is used to identify vehicles
	 * @param color this parameter is used to identify color of the vehicles
	 */

	public void park(String registrationNumber, String color) {
		if (parkingSlots.size() < capacity) {
			if(!checkIfVinExists(registrationNumber)) {
			int slotNumber = findVacantSlot();
			VehicleDetails vehicle = new VehicleDetails(registrationNumber, color);
			parkingSlots.put(slotNumber, vehicle);
			vehicleSlot.put(registrationNumber, slotNumber);
			colorToSlots.computeIfAbsent(color, k -> new ArrayList<>()).add(slotNumber); // adding compute function to
																							// reduce data redundancy
			System.out.println("Allocated slot number: " + slotNumber);
			}
			else {
				System.out.println("Vehicle Already Present! Check the vehicle registration number again.");
			}
		} else {
			System.out.println("Sorry, the parking lot is full.");
		}
	}
	
	/**
	 * This method checks if user is trying to park the already vehicle again
	 * @param vin it uses vehicle registration number to return the results
	 * @return
	 */
	
	private boolean checkIfVinExists(String vin) {
		boolean status = vehicleSlot.containsKey(vin) ? true : false;
		return status;
		
	}
	
	/**
	 * This method checks if parking lot do have any vacant slots
	 * @return
	 */
	private int findVacantSlot() {
        for (int i = 1; i <= capacity; i++) {
            if (!parkingSlots.containsKey(i)) {
                return i;
            }
        }
        return -1; // No vacant slot found
    }

	/**
	 * This method is used to vacant the slots once any car leaves and it frees the slot number 
	 * for another vehicle
	 * @param slotNumber it is used to delete the entries from the system
	 */
	public void leave(int slotNumber) {
		VehicleDetails vehicle = parkingSlots.get(slotNumber);
		if (vehicle != null) {
			parkingSlots.remove(slotNumber);
			vehicleSlot.remove(vehicle.vin);
			colorToSlots.get(vehicle.color).remove(Integer.valueOf(slotNumber));
			System.out.println("Slot number " + slotNumber + " is free.");
		} else {
			System.out.println("Slot number " + slotNumber + " is already empty.");
		}
	}
	
	/**
	 * This function provides the status of the parking lot
	 */

	public void status() {
	    System.out.println("Slot No.\tRegistration No.\tColor");
	   

	    for (Map.Entry<Integer, VehicleDetails> entry : parkingSlots.entrySet()) {
	    	int slotNumber = entry.getKey();
	        VehicleDetails vehicle = entry.getValue();
	        System.out.println(slotNumber + "\t\t" + vehicle.vin + "\t\t" + vehicle.color);
	    }


	}

	/**
	 * This method provides the details of vehicle registration numbers 
	 * which do have same color of the vehicle
	 * @param color it is used to identify the group of vehicles
	 */

	public void getRegistrationNumbersByColor(String color) {
		List<Integer> slots = colorToSlots.get(color);
		if (slots != null) {
			slots.stream().map(slot -> parkingSlots.get(slot).vin).forEach(System.out::println);
		} else {
			System.out.println("No Vehicle found with color " + color);
		}
	}

	/**
	 * this method checks if any parking lot is already exists or not
	 * @return
	 */


	public boolean checkIfParkingLotExists() {
		return (capacity!=-1) ? true:false;
		
	}

}
