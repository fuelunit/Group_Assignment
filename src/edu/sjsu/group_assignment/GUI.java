package edu.sjsu.group_assignment;

import com.github.lgooddatepicker.components.CalendarPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;


// Uses LGoodDatePicker from https://github.com/LGoodDatePicker/LGoodDatePicker/releases/tag/v11.2.1-Standard

/**
 * GUI class for the Appointment project
 *
 * @see <a href="https://github.com/LGoodDatePicker/LGoodDatePicker/
 * releases/tag/v11.2.1-Standard">LGoodDatePicker</a>
 * @author Lilou Sicard-Noel, Zhuying Wang, Yipeng Liu
 */
public class GUI {
    enum Action {
        alphabetical, chronological
    }

    public static AppointmentManager manager = new AppointmentManager();

    public static void main(String[] args) {

        //test
        LocalDate startDate = LocalDate.parse("2021-06-01");
        LocalDate endDate = LocalDate.parse("2021-08-05");
        LocalDate inBetween = LocalDate.parse("2021-07-01");
        LocalDate before = LocalDate.parse("2021-03-05");
        LocalDate after = LocalDate.parse("2021-10-01");


        Appointment daily = new DailyAppointment("Coffee",startDate,endDate);
        Appointment monthly = new MonthlyAppointment("Arrival",inBetween,after);
        Appointment oneTime = new OnetimeAppointment("Zoo", before);

        manager.addAppointment(daily);
        manager.addAppointment(monthly);
        manager.addAppointment(oneTime);

        JFrame frame = new JFrame("Main page");
        JPanel panel = new JPanel();
        frame.getContentPane();
        JButton view = new JButton("View Appointment");
        CalendarPanel calendar = new CalendarPanel();
        calendar.setSize(200,200);
        calendar.setLocation(0,0);
        view.setBounds(900,150,150,150);
        view.addActionListener(e -> viewOption());

        /* Grace */
        JButton addApp=new JButton("add Appointment");
        addApp.setBounds(900,150,150,150);

        addApp.addActionListener(e->addOption());

        panel.add(view);
        panel.add(calendar);
        panel.add(addApp);

        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setSize(1100,300);
        frame.setVisible(true);
    }

    private static void viewOption() {
        JFrame frame = new JFrame("Options");
        JPanel panel = new JPanel();
        frame.getContentPane();
       // frame.setPreferredSize(new Dimension(300,300));
        JButton alpha = new JButton("View appointments by alphabetical order.");
        JButton startDay = new JButton("View appointments by start date.");
        alpha.setBounds(0,0,150,150);
        startDay.setBounds(150,0,150,150);

        panel.add(alpha);
        panel.add(startDay);

        alpha.addActionListener(e -> {
            frame.dispose();
            pickAllOrDay(Action.alphabetical);
            System.out.println("Description");
        });

        startDay.addActionListener(e -> {
            frame.dispose();
            pickAllOrDay(Action.chronological);
            System.out.println("start day");
        });


        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        frame.add(panel);
        frame.setSize(500,200);
        frame.setVisible(true);

    }
   
    private static void addOption() {
        JFrame frame = new JFrame("Add an appointment");
        JPanel panel = new JPanel();
        frame.getContentPane();
        frame.setPreferredSize(new Dimension(300,300));
        JLabel startDate = new JLabel("plz enter your start date:");
        JLabel endDate = new JLabel("plz enter your end date");
        JLabel description = new JLabel("plz enter your description :");
        JLabel type = new JLabel("plz enter your appointment type(monthly/daily/onetime):");

        JTextField startDateTXT = new JTextField(20);
        JTextField endDateTXT = new JPasswordField(20);
        JTextField descriptionTXT = new JTextField(20);
        JTextField typeTXT = new JTextField(20);

        panel.add(startDate);
        panel.add(startDateTXT);
        panel.add(endDate);
        panel.add(endDateTXT);
        panel.add(description);
        panel.add(descriptionTXT);
        panel.add(type);
        panel.add(typeTXT);


        JButton button = new JButton("add");
        // add a listener to button
        button.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                LocalDate start=LocalDate.parse(startDateTXT.getText().toString().trim());
                LocalDate end=LocalDate.parse(endDateTXT.getText().toString().trim());
                String des= description.getText().toString();
                if(typeTXT.getText().equals("monthly")){
                    manager.addAppointment(
                            new MonthlyAppointment(des, start, end));
                }
                else if(typeTXT.getText().equals("daily")){
                    manager.addAppointment(
                            new DailyAppointment(des, start, end));
                }
                else if(typeTXT.getText().equals("onetime")){
                    manager.addAppointment(
                            new OnetimeAppointment(des, start));
                }
                else {
                    typeTXT.setText("wrong input");
                }
            }
        });
        panel.add(button);


        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        frame.add(panel);
        frame.setSize(500,200);
        frame.setVisible(true);

    }

    private static void pickAllOrDay(Action action)
    {

        JFrame frame = new JFrame("Options");
        JPanel panel = new JPanel();
        JButton dateInput = new JButton("Search this date");
        JButton allAppoint = new JButton("Show all appointments");
        JTextField field = new JTextField(10);
        JTextArea errorMessage = new JTextArea();
        frame.getContentPane();

        panel.add(field);
        panel.add(errorMessage);
        panel.add(dateInput);
        panel.add(allAppoint);

        dateInput.addActionListener(e->{
            String input = field.getText();
            try {
                LocalDate parse = LocalDate.parse(input);
                printOut(parse,action);
                System.out.println(action);
                frame.dispose();

            } catch (Exception exception) {
                errorMessage.setText("Sorry, wrong input!");
                System.out.println("error");
            }


        });

        allAppoint.addActionListener(e->{
            printOut(null,action);
            frame.dispose();
        });




        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        frame.add(panel);
        frame.setSize(500,200);
        frame.setVisible(true);

    }

    private static void printOut(LocalDate parse, Action action) {
        JFrame frame = new JFrame("Appointment display");
        JPanel panel = new JPanel();
        JTextArea listDisplay = new JTextArea();
        frame.getContentPane();
        panel.add(listDisplay);
        AppComparator comparator = null;
        String list;
        if (action.toString().equals("chronological"))
        {
           comparator  = new AppComparator();
        }
        if (parse == null)
        {
            //Print all appointment
           list = manager.printAppointment(comparator);
        }
        else {
            //Print appointment on specific date
            list = manager.printOccursOn(parse,comparator);
        }
        listDisplay.setText(list);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        frame.add(panel);
        frame.setSize(500,200);
        frame.setVisible(true);
    }

}
