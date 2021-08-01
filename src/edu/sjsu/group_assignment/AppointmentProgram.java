/*
    Author  : Yipeng Liu, Lilou Sicard-Noel, Zhuying Wang
    Project : Assignment 4
    Class   : AppointmentProgram
    Date    : 07/24/2021
    Updated : 07/30/2021
 */
package edu.sjsu.group_assignment;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Gives user options to add an appointment, delete an appointment
 * or display all appointments.
 *
 * @author Yipeng Liu
 * @see AppointmentManager
 */
public class AppointmentProgram {
    /**
     * Runs the program.
     */
    public static void runProgram() {
        AppointmentManager manager = new AppointmentManager();
        String prompt = """
                (a) for adding an appointment through console inputs;
                (r) for adding appointments by reading an input file;
                (d) for deleting an appointment;
                (v) for viewing all appointments;
                (s) for saving appointments to file;
                (q) for quitting the program
                Please choose an option:\s""";
        System.out.print(prompt);
        Scanner scanner = new Scanner(System.in);
        boolean done = false;
        while (!done) {
            switch (scanner.nextLine().trim()) {
                case "a" -> {
                    // Get the type
                    System.out.print("Please enter the type (onetime, daily or monthly): ");
                    String type = scanner.nextLine().trim().toLowerCase();
                    // Handle non-supported types
                    if (!type.equals("onetime") && !type.equals("daily") &&
                            !type.equals("monthly")) {
                        System.out.println("Appointment type--" + type + "--is not supported.");
                        System.out.print("Please choose an option: ");
                        break;
                    }
                    // Get description
                    System.out.print("Please enter a description: ");
                    String description = AppointmentManager.capitalize(scanner.nextLine().trim());
                    // Get dates
                    LocalDate startDate;
                    LocalDate endDate = null;
                    try {
                        System.out.print("Please enter the starting date (yyyy-mm-dd): ");
                        startDate = LocalDate.parse(scanner.nextLine().trim());
                        if (!type.equals("onetime")) {
                            System.out.print("Please enter the ending date (yyyy-mm-dd): ");
                            endDate = LocalDate.parse(scanner.nextLine().trim());
                            if (endDate.compareTo(startDate) < 0) {
                                throw new DateTimeException("Error: End date comes before start date.");
                            }
                        }
                    } catch (DateTimeParseException e) {
                        System.out.println("Sorry, the dates you have entered are not valid.");
                        System.out.print("Please choose and option: ");
                        break;
                    } catch (DateTimeException e) {
                        System.out.println(e.getMessage());
                        System.out.print("Please choose and option: ");
                        break;
                    }
                    // A boolean flag to check if an appointment is successfully added.
                    boolean isAdded = false;
                    switch (type) {
                        case "onetime":
                            isAdded = manager.addAppointment(
                                    new OnetimeAppointment(description, startDate));
                            break;
                        case "daily":
                            isAdded = manager.addAppointment(
                                    new DailyAppointment(description, startDate, endDate));
                            break;
                        case "monthly":
                            isAdded = manager.addAppointment(
                                    new MonthlyAppointment(description, startDate, endDate));
                            break;
                        default:
                            break;
                    }
                    if (isAdded) {
                        System.out.println("Appointment added!");
                    } else {
                        System.out.println("Appointment is NOT added!");
                    }
                    System.out.print(prompt);
                }
                case "r" -> {
                    System.out.print("Please enter the full path to the input file: ");
                    String inputFile = scanner.nextLine().trim();
                    if (manager.readFromFile(inputFile)) {
                        System.out.println("Appointments added!");
                    }
                    System.out.print(prompt);
                }
                case "d" -> {
                    System.out.print("Please enter the description of the appointment you want to delete: ");
                    String desLookUp = AppointmentManager.capitalize(scanner.nextLine().trim());
                    boolean isDeleted = true;
                    while (!manager.deleteAppointment(desLookUp)) {
                        System.out.println("Sorry, appointment not found! please enter again!");
                        System.out.print("Please enter the description of the " +
                                "appointment you want to delete, or q (or Q) to return " +
                                "to the main menu: ");
                        desLookUp = AppointmentManager.capitalize(scanner.nextLine().trim());
                        if (desLookUp.equals("Q")) {
                            isDeleted = false;
                            break;
                        }
                    }
                    if (isDeleted) {
                        System.out.println("Appointment deleted!");
                    }
                    System.out.print(prompt);
                }
                case "v" -> {
                    if (!manager.isEmpty()) {
                        System.out.println("plz enter the date you want to pass in for occursOn");
                        Scanner LocalDate_scanner = new Scanner(System.in);  // Create a Scanner object
                        String  Date= LocalDate_scanner.nextLine();  // Read user input
                        LocalDate date= LocalDate.parse(Date);
                        manager.printOccursOn(date);
                    } else {
                        System.out.println("No any appointment now.");
                    }
                    System.out.print(prompt);

                }
                case "s" -> {
                    if (manager.isEmpty()) {
                        System.out.println("No any appointment now; no file has been generated.");
                    } else if (manager.saveToFile("appointments.txt")) {
                        System.out.println("Appointments saved to file!");
                    } else {
                        System.out.println("Appointments are NOT saved to file!");
                    }
                    System.out.print(prompt);
                }
                case "q" -> {
                    done = true;
                    System.out.println("Thank you for using the program. Have a nice day!");
                }
                default -> System.out.print("Sorry, wrong option!\nPlease enter again: " );
            }
        }
    }
}
