package edu.sjsu.group_assignment;

import java.time.LocalDate;

/**
 * An appointment has a String description, two LocalDate objects
 * for the starting and ending date, and a {@code String} type.
 * Its constructors initialize those variables by the arguments
 * passed in. The order of the parameters is:
 * {@code String} description, {@code LocalDate} startDate,
 * {@code LocalDate} endDat, {@code String} type.
 *
 *
 * @author Lilou Sicard-Noel
 */
public abstract class Appointment implements Comparable<Appointment> {

    private String description;
    private LocalDate startDate;
    private LocalDate endDate;

    Appointment(String description, LocalDate startDate, LocalDate endDate)
    {
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    Appointment(String description)
    {
        this.description=description;
    }

    /**+
     * Use to get the private variable description
     * @return this.description
     */
    public String getDescription() {
        return description;
    }

    /**+
     * Use to get the private variable startDate
     * @return this.startDate
     */

    public LocalDate getStartDate() {
        return startDate;
    }

    /**+
     * Use to get the private variable endDate
     * @return this.endDate
     */

    public LocalDate getEndDate() {
        return endDate;
    }

    /**+
     * Find if an appointment occurs on a given day
     * @param date day to be check if there is an appointment
     * @return true or false
     */
   public abstract boolean occursOn(LocalDate date);
   public abstract String type();


    /**+
     * This method is use to help find if a meeting is occurring on a specific date. It return true if the date
     * is between the starting and ending date.
     * @param date The date that the user wish to find if it is between the starting and ending date
     * @return true if the parameter is between the starting and ending date or on those date
     */
   public boolean isBetween(LocalDate date){
        if (this.startDate.isBefore(date)&& this.endDate.isAfter(date))
        {
            return true;
        }
       return this.startDate.isEqual(date) || this.endDate.isEqual(date);
   }

    /**+
     * The compareTo method is used to compare appointment. An appointment is consider the same if it as the same
     * description.
     * @param o The appointment the this object will be compare to
     * @return an int, representing the comparison
     */

    @Override
    public int compareTo(Appointment o) {
      // if (this.startDate.equals(o.startDate) &&this.endDate.equals(o.endDate)) return this.description.compareTo(o.description);
      // if(this.startDate.equals(o.startDate)) return this.endDate.compareTo(o.endDate);
      // return this.startDate.compareTo(o.startDate);
        if(this.equals(o)) return 0;
        return this.description.compareTo(o.description);
    }


    /**
     * Check if two appointment are equal, that is they have the same description
     * @param newAppointment appointment to compare the this object with
     * @return true or false
     */
    public boolean equals(Appointment newAppointment)
    {
        return this.description.equals(newAppointment.description);
    }


    /**
     * Create a string that explain the appointment in a way non coder will understand
     * @return the description string
     */
    public String toString()
    {
        //System.out.println(description+", from "+startDate+" to "+endDate+", "+ type());
        return (description+", from "+startDate+" to "+endDate+", "+ type());
    }


}
