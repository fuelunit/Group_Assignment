package edu.sjsu.group_assignment;

import java.time.LocalDate;

/**
 * A subclass of Appointment.
 *
 * @author Lilou Sicard-Noel
 */
public class DailyAppointment extends Appointment{


    public DailyAppointment(String description, LocalDate startDate, LocalDate endDate) {
        super(description, startDate, endDate);
    }


    /**+
     * This function returns a boolean telling if there is an appointment the day that as been pass as a parameter
     * @param date The day to find is there is an appointment
     * @return true or false
     */
    @Override
    public boolean occursOn(LocalDate date) {
        return (isBetween(date));
    }

    /** return the type of appointment
     * @return a string for the type
     */
    @Override
    public String type() {
        return "Daily";
    }
}
