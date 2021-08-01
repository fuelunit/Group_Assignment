package edu.sjsu.group_assignment;

import java.util.Comparator;

/**
 * An Appointment Comparator that compares start dates only.
 *
 * @author Lilou Sicard-Noel
 */
public class AppComparator implements Comparator<Appointment> {

    /** This method will compare the appointment by Start Date instead of
     * by description
     * @param o1 first appointment to compare
     * @param o2 Second appointment to compare
     * @return the Start date comparaison
     */
    @Override
    public int compare(Appointment o1, Appointment o2) {
        return o1.getStartDate().compareTo(o2.getStartDate());
    }
}
