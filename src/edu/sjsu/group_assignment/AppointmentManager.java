package edu.sjsu.group_assignment;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

/**
 * AppointmentManager class manages a collection of appointments
 * by using {@code TreeMap} where the key is a description
 * {@code String} and value is an {@code Appointment} object.
 *
 * @author Lilou Sicard-Noel, Yipeng Liu, Zhuying Wang
 * @see java.util.TreeMap
 */
public class AppointmentManager {
    private Map <String,Appointment> apptMap = new TreeMap<>();

    /**
     * add an appointment to the treemap. The appointment was already created. If the appointment already is in the
     * treemap, the duplicate is not added
     * @param appointment  The appointment to add.
     * @return true or false depending on the success
     */
    public boolean addAppointment(Appointment appointment)
    {
        if (!apptMap.containsKey(appointment.getDescription()))
        {
            apptMap.put(appointment.getDescription(),appointment);
            return true;
        }
        return false;

    }

    /**
     * A helper method that capitalizes the first letter
     * of the {@code String} and force the rest to the lower
     * case.
     *
     * @param inputStr
     *      A raw {@code String}.
     *
     * @return
     *      A  capitalized {@code String}.
     */
    public static String capitalize(String inputStr) {
        return inputStr.substring(0,1).toUpperCase()
                + inputStr.substring(1).toLowerCase();
    }

    /**
     * The method delete an appointment from the treemap using the description of the appointment to
     * find it.
     * @param appointment The description of the appointment to delete
     * @return true or false depending on the success
     */
    public boolean deleteAppointment (String appointment)
    {
        if (apptMap.containsKey(appointment))
        {
            apptMap.remove(appointment);
            return true;
        }

        return false;

    }

    public Map<String, Appointment> getApptMap() {
        return this.apptMap;
    }

    /**
     * Checks if apptMap is empty or not.
     *
     * @return
     *      True if it is empty, and vice versa.
     */
    public boolean isEmpty() {
        return this.apptMap.isEmpty();
    }

    /**
     * Print all appointment in the treemap in there natural order or an error message if the treemap is empty
     * This use a comparator to sort the printed item. The sortage can be by Start Date or Description.
     * Remember that an appointment's description is unique.
     * Right now, compare to return the comparison of the description
     * @param compare The comparator object
     */
    public String printAppointment(Comparator<Appointment> compare)
    {
        /*
        if(apptMap.isEmpty())
        {
            System.out.println("No appointment now.");
        }
        else
        {
            List <Appointment> toPrint = new ArrayList<>();
            toPrint.addAll(apptMap.values());
            toPrint.sort(compare);
            for(Appointment entry: toPrint)
            {
               System.out.println(entry.toString());
            }
        }*/
        // Modified due to the implementation of toString. -- Yipeng
        //System.out.print(this.toString(compare));
        return this.toString(compare);
    }

    /**
     * A method to load the data from the file.
     *
     * @param inputFile
     *      A File object for the input file.
     *
     * @return A boolean value.
     *      True if all Appointments have been read and stored in
     *      apptMap, or false otherwise.
     */
    public boolean readFromFile(String inputFile) {
        try {
            readFromFile(new File(inputFile));
        } catch (FileNotFoundException e) {
            System.out.println("File not found. Details:");
            System.out.println(e.getMessage());
            return false;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("File is corrupted. Details:");
            System.out.println("Format cannot be understood.");
            System.out.println(e.getMessage());
            return false;
        } catch (DateTimeParseException e) {
            System.out.println("File is corrupted. Details:");
            System.out.println("Date format cannot be understood.");
            System.out.println(e.getMessage());
            return false;
        }
        // Catches skipped lines occurred in the while loop above.
        return !this.isEmpty();
    }

    /**
     * A method to load the data from the file.
     *
     * @param inputFile
     *      A File object for the input file.
     *
     * @throws FileNotFoundException
     * @throws ArrayIndexOutOfBoundsException
     * @throws DateTimeParseException
     */
    public void readFromFile(File inputFile) throws FileNotFoundException,
            ArrayIndexOutOfBoundsException, DateTimeParseException {
        Scanner scanner = new Scanner(inputFile);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] apptStrArr = line.split(",");
            String description = apptStrArr[0].trim();
            String dateStr = apptStrArr[1].trim();
            String type = apptStrArr[2].trim();
            String[] dateStrArr = dateStr.split("\s");
            LocalDate startDate = LocalDate.parse(dateStrArr[1]);
            LocalDate endDate = LocalDate.parse(dateStrArr[3]);
            if (endDate.compareTo(startDate) < 0) {
                System.out.println("Skipping conflicting end dates:");
                System.out.println(line);
            } else if (type.equals("One Time")) {
                this.addAppointment(new OnetimeAppointment(description, startDate));
            } else if (type.equals("Daily")) {
                this.addAppointment(new DailyAppointment(description,
                        startDate, endDate));
            } else if (type.equals("Monthly")) {
                this.addAppointment(new MonthlyAppointment(description,
                        startDate, endDate));
            } else {
                System.out.println("Skipping unsupported appointment type:");
                System.out.println(line);
            }
        }
    }

    /**
     * A method to save the appointments to a file.
     * Each line includes the information of one appointment.
     *
     * @param outputFile
     *      A String for the output file path.
     *
     * @param comparator
     *      An Appointment Comparator.
     *
     * @param isAppending
     *      A boolean flag for appending check: true for appending,
     *      false for not.
     *
     * @return A boolean value.
     *      True if Appointments have been successfully saved to the output file,
     *      or false otherwise.
     */
    public boolean saveToFile(String outputFile, Comparator<Appointment> comparator,
                           boolean isAppending) {
        try {
            File outFile = new File(outputFile);
            saveToFile(outFile, comparator, isAppending);
            return true;
        } catch (IOException e) {
            System.out.println("File not found. Details:");
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * A method to save the appointments to a file.
     * Each line includes the information of one appointment.
     * Default comparator to null and isAppending to false.
     * In other words, using the natural order and overwriting
     * the save file.
     *
     * @param outputFile
     *      A String for the output file path.
     *
     * @return A boolean value.
     *      True if Appointments have been successfully saved to the output file,
     *      or false otherwise.
     */
    public boolean saveToFile(String outputFile) {
        return this.saveToFile(outputFile, null, false);
    }

    /**
     * A method to save the appointments to a file.
     * Each line includes the information of one appointment.
     * Default comparator to null and isAppending to false.
     * In other words, using the natural order and overwriting
     * the save file.
     *
     * @param outputFile
     *      A File object.
     */
    public void saveToFile(File outputFile, Comparator<Appointment> comparator,
                           boolean isAppending) throws IOException {
        FileWriter writer = new FileWriter(outputFile, isAppending);
        writer.write(this.toString(comparator));
        writer.close();
    }

    /**
     * A toString method utilizing StringBuilder.
     *
     * @return
     *      An optimized {@code String}.
     *
     * @see StringBuilder
     */
    public String toString(Comparator<Appointment> compare) {
        StringBuilder result = new StringBuilder();
        if(this.apptMap.isEmpty()) {
            result = new StringBuilder("No appointment now.\n");
        } else {
            List<Appointment> toPrint = new ArrayList<>(apptMap.values());
            toPrint.sort(compare);
            for(Appointment entry: toPrint)
            {
                result.append(entry.toString());
                result.append("\n");
            }
        }
        return result.toString();
    }

    /**
     * A method for printing the appointments that occurs on the date
     * passed in. That is, go over your appointment collection, only
     * prints the appointment if occursOn returns true.
     *
     * @param date
     *      A LocalDate object
     *
     * @see LocalDate
     */
    public void printOccursOn(LocalDate date){
        for(Appointment app:apptMap.values()){
            if(app.occursOn(date)){
                System.out.println(app);
            }
        }
    }

    /**
     * A public method that returns a String of ordered Appointments
     * based on the Comparator that is passed in.
     *
     * @param date
     *      A LocalDate object
     *
     * @param compare
     *      An Appointment Comparator
     *
     * @return result
     *      A String
     *
     * @see LocalDate
     * @see Comparator
     */
    public String printOccursOn (LocalDate date,Comparator<Appointment> compare) {
        StringBuilder result = new StringBuilder();
        if(this.apptMap.isEmpty()) {
            result = new StringBuilder("No appointment now.\n");
        } else {
            for (Appointment app : apptMap.values()) {
                if (app.occursOn(date)) {
                    result.append(app);
                    result.append("/n");
                }
            }
        }
        return result.toString();
    }
}
