/*
    Author  : Yipeng Liu, Lilou Sicard-Noel, Zhuying Wang
    Project : Group Assignment
    Class   : Main
    Date    : 07/15/2021
    Updated : 07/30/2021
 */
package edu.sjsu.group_assignment;

import java.time.LocalDate;

public class Main {
/*
    public static void main(String[] args) {
        LocalDate startDate = LocalDate.parse("2021-06-01");
        LocalDate endDate = LocalDate.parse("2021-08-05");
        LocalDate inBetween = LocalDate.parse("2021-07-01");
        LocalDate before = LocalDate.parse("2021-03-05");
        LocalDate after = LocalDate.parse("2021-10-01");
        AppointmentManager AppMan = new AppointmentManager();
        AppComparator compare = new AppComparator();

	    Appointment daily = new DailyAppointment("Coffee",startDate,endDate);
	    Appointment monthly = new MonthlyAppointment("Arrival",inBetween,after);
	    Appointment oneTime = new OnetimeAppointment("Zoo", before);

	    AppMan.addAppointment(daily);
	    AppMan.addAppointment(monthly);
	    AppMan.addAppointment(oneTime);

	    AppMan.printAppointment(null);
	    System.out.println();
	    AppMan.printAppointment(compare);

    }

 */
  // Test for compatibility by Yipeng Liu
    public static void main(String[] args) {
        AppointmentProgram.runProgram();
    }



    public static void main2(String[] args) {

    }
}
