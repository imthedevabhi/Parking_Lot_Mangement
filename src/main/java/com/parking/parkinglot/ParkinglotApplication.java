package com.parking.parkinglot;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.parking.parkinglot.service.ParkingService;

@SpringBootApplication
public class ParkinglotApplication {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		ParkingService parkingLot = null;
		List<String> actions = Arrays.asList("create_parking_lot", "park", "leave", "status",
				"registration_numbers_for_cars_with_colour");

		while (true) {
			System.out.print("Enter command: ");
			String command = scanner.nextLine();

			if (command.equals("exit")) {
				System.out.println("Exiting the application. Goodbye!");
				break;
			}

			String[] parts = command.split(" ");
			String action = parts[0].toLowerCase();

			try {
				switch (action) {
				case "create_parking_lot":
					int capacity = Integer.parseInt(parts[1]);
					if (parkingLot != null && parkingLot.checkIfParkingLotExists()) {
						System.out.println(
								"Warning!!, Parking lot already Exists, Creating new will remove delete existing parking lot!!");
						System.out.println("Do you wish to proceed? yes/no");
						action = scanner.nextLine();
						if (action.toLowerCase().equalsIgnoreCase("yes") && capacity > 0) {
							parkingLot = new ParkingService(capacity);
							System.out.println("Parking lot with " + capacity + " slots created.");
						} else {
							System.out.println("Invalid Size.");
						}
					} else {
						if (capacity <= 0) {
							System.out.println("Invalid Capacity.");
							break;
						}
						parkingLot = new ParkingService(capacity);
						System.out.println("Parking lot with " + capacity + " slots created.");
					}
					break;

				case "park":
					if (parkingLot == null) {
						System.out.println("Please create a parking lot first.");
						break;
					}
					String registrationNumber = parts[1];
					String color = parts[2];
					parkingLot.park(registrationNumber, color);
					break;

				case "leave":
					if (parkingLot == null) {
						System.out.println("Please create a parking lot first.");
						break;
					}
					int slotNumber = Integer.parseInt(parts[1]);
					parkingLot.leave(slotNumber);
					break;

				case "registration_numbers_for_cars_with_colour":
					if (parkingLot == null) {
						System.out.println("Please create a parking lot first.");
						break;
					}
					String colorToSearch = parts[1];
					parkingLot.getRegistrationNumbersByColor(colorToSearch);
					break;

				case "status":
					parkingLot.status();
					break;

				case "help":
					for (String i : actions) {
						System.out.println(i);
					}
					break;

				default:
					System.out.println("Invalid command. Please check your input, type help for commands.");
				}
			}

			catch (ArrayIndexOutOfBoundsException ae) {
				System.out.println("Error Occured!, Please recheck your input details");
			}
			
			catch (Exception e) {
				System.out.println("Error Occured!, Please recheck your input details");
			}

		}

		scanner.close();
	}

}