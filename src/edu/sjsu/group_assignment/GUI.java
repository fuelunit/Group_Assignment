package edu.sjsu.group_assignment;

import com.github.lgooddatepicker.components.CalendarPanel;
import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;


// Uses LGoodDatePicker from https://github.com/LGoodDatePicker/LGoodDatePicker/releases/tag/v11.2.1-Standard

/**
 * GUI class for the Appointment project
 *
 * @see <a href="https://github.com/LGoodDatePicker/LGoodDatePicker/
 * releases/tag/v11.2.1-Standard">LGoodDatePicker</a>
 * @author Lilou Sicard-Noel, Yipeng Liu, Zhuying Wang
 */
public class GUI {
    enum Action {
        alphabetical, chronological
    }

    public static AppointmentManager manager = new AppointmentManager();

    public static void main(String[] args) {

        //test
        /*
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
        */
        // frame
        JFrame frame = new JFrame("Appointment Assistant");
        JPanel panel = new JPanel();
        frame.getContentPane();
        // Calendar
        CalendarPanel calendar = new CalendarPanel();
        calendar.setSize(200,200);
        calendar.setLocation(0,0);
        /* Grace */
        // Add button
        JButton addApp=new JButton("add Appointment");
        addApp.setBounds(900,150,150,150);
        addApp.addActionListener(e->addOption());
        // Delete button
        JButton deleteApp=new JButton("delete Appointment");
        deleteApp.setBounds(900,150,150,150);
        deleteApp.addActionListener(e->deleteOption());
        // View button - Lilou
        JButton view = new JButton("View Appointment");
        view.setBounds(900,150,150,150);
        view.addActionListener(e -> viewOption());
        // Load button - Yipeng
        JButton load = new JButton("Load from File");
        load.setBounds(900,150,150,150);
        load.addActionListener(e -> loadFromFile(load));
        // Save button - Yipeng
        JButton save = new JButton("Save to File");
        save.setBounds(900,150,150,150);
        // Basic Attempt
        /*
        panel.add(view);
        panel.add(load);
        panel.add(save);
        panel.add(calendar);
        */
        /*
        // GroupLayout Attempt
        GroupLayout apptLayout = new GroupLayout(panel);
        panel.setLayout(apptLayout);
        apptLayout.setAutoCreateGaps(true);
        apptLayout.setAutoCreateContainerGaps(true);
        // horizontal
        apptLayout.setHorizontalGroup(
                apptLayout.createSequentialGroup()
                        // Calendar on the left side
                        .addComponent(calendar)
                        // Buttons on the right side
                        .addGroup(apptLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(view)
                                        .addComponent(load)
                                        .addComponent(save)
                        )
        );
        // vertical
        apptLayout.setVerticalGroup(
                apptLayout.createSequentialGroup()
                        .addGroup(apptLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(calendar)
                                .addComponent(view))
                        .addComponent(load)
                        .addComponent(save)
        );
        */
        // BoxLayout Attempt
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.Y_AXIS));
        // Adding buttons
        buttonPane.add(addApp);
        buttonPane.add(deleteApp);
        buttonPane.add(view);
        buttonPane.add(load);
        buttonPane.add(save);
        // Adding Calendar and buttonPane
        panel.add(calendar);
        panel.add(buttonPane);

        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setSize(500,300);
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

    private static void deleteOption(){
        JFrame frame = new JFrame("delete");
        JPanel panel = new JPanel();
        frame.getContentPane();
        // frame.setPreferredSize(new Dimension(300,300));
        JLabel del = new JLabel("plz enter the description of the appointment that you want to delete") ;
        JTextField delTXT = new JTextField(20);

        panel.add(del);
        panel.add(delTXT);


        JButton button = new JButton("delete");
        // add a listener to button
        button.addActionListener(e -> {
            String del_des= delTXT.getText();
            if (manager.deleteAppointment(del_des)) {
                frame.dispose();
            } else {
                delTXT.setText("This appointment doesn't exist!");
            }
        });
        panel.add(button);

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
        JTextField endDateTXT = new JTextField(20);
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
        button.addActionListener(e -> {
            LocalDate start=LocalDate.parse(startDateTXT.getText().trim());
            LocalDate end=LocalDate.parse(endDateTXT.getText().trim());
            String des= descriptionTXT.getText();
            switch (typeTXT.getText()) {
                case "monthly" -> {
                    manager.addAppointment(
                            new MonthlyAppointment(des, start, end));
                    frame.dispose();
                }
                case "daily" -> {
                    manager.addAppointment(
                            new DailyAppointment(des, start, end));
                    frame.dispose();
                }
                case "onetime" -> {
                    manager.addAppointment(
                            new OnetimeAppointment(des, start));
                    frame.dispose();
                }
                default -> typeTXT.setText("wrong input");
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

    /**
     * Opens up a file choosing window using JFileChooser.
     *
     * @param component
     *      A Component
     *
     * @see Component
     * @see JFileChooser
     */
    private static void loadFromFile(Component component) {
        JFileChooser fileChooser = new JFileChooser();
        //JFrame msgFrame = new JFrame();
        StringBuilder msg = new StringBuilder();
        JOptionPane msgPane = null;
        if (fileChooser.showOpenDialog(component) == JFileChooser.OPEN_DIALOG) {
            // If the user chooses to open
            try {
                manager.readFromFile(fileChooser.getSelectedFile());
                msg.append("Appointments successfully loaded from the input file.\n");
                msg.append("Corrupted entries are discarded");

            } catch (FileNotFoundException e) {
                // System.out.println("File not found. Details:");
                // System.out.println(e.getMessage());
                msg.append(e.getMessage());
            } catch (ArrayIndexOutOfBoundsException e) {
                // System.out.println("File is corrupted. Details:");
                // System.out.println("Format cannot be understood.");
                // System.out.println(e.getMessage());
                msg.append("File is corrupted. Details: Format cannot be understood.\n");
                msg.append(e.getMessage());
            } catch (DateTimeParseException e) {
                // System.out.println("File is corrupted. Details:");
                // System.out.println("Date format cannot be understood.");
                // System.out.println(e.getMessage());
                msg.append("File is corrupted. Details: Date format cannot be understood.\n");
                msg.append(e.getMessage());
            } finally {
                msgPane = new JOptionPane(msg, JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}
