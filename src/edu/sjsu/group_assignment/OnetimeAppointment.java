package edu.sjsu.group_assignment;

import java.time.LocalDate;

/**
 * A subclass of Appointment.
 *
 * @author Lilou Sicard-Noel
 */
public class OnetimeAppointment extends Appointment{

     public OnetimeAppointment(String description, LocalDate startDate) {

        super(description, startDate, startDate);
    }

    /**+
     * This function takes a date as an input an return true if there is an appointment on that day
     * and false otherwise
     * @param date The date to find if there is an appointment on that day
     * @return True or false
     */
    @Override
    public boolean occursOn(LocalDate date) {
        return this.getStartDate().isEqual(date);
    }


    /** To get the type of appointment
     * @return the type of appointment
     */
    @Override
    public String type() {
        return "One Time";
    }
}
