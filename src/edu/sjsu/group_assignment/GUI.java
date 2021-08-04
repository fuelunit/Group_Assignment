package edu.sjsu.group_assignment;

import com.github.lgooddatepicker.components.CalendarPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



// Uses LGoodDatePicker from https://github.com/LGoodDatePicker/LGoodDatePicker/releases/tag/v11.2.1-Standard

    public class GUI {
        public static void main(String[] args) {
            JFrame frame = new JFrame("Main page");
            JPanel panel = new JPanel();
            frame.getContentPane();
            JButton view = new JButton("View Appointment");
            CalendarPanel calendar = new CalendarPanel();
            calendar.setSize(200,200);
            calendar.setLocation(0,0);
            view.setBounds(900,150,150,150);
            view.addActionListener(e -> viewOption());


            panel.add(view);
            panel.add(calendar);

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
            JButton alpha = new JButton("View appointment by alphabetical order.");
            JButton startDay = new JButton("View appointment by start date.");
            alpha.setBounds(0,0,150,150);
            startDay.setBounds(150,0,150,150);

            panel.add(alpha);
            panel.add(startDay);

            alpha.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.dispose();
                    System.out.println("Description");
                }
            });

            startDay.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.dispose();
                    System.out.println("start day");
                }
            });


            panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
            frame.add(panel);
            frame.setSize(500,200);
            frame.setVisible(true);

        }
}
