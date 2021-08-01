package edu.sjsu.group_assignment;

import com.github.lgooddatepicker.components.CalendarPanel;
import javax.swing.*;
import java.awt.*;

// Uses LGoodDatePicker from https://github.com/LGoodDatePicker/LGoodDatePicker/releases/tag/v11.2.1-Standard

    public class GUI {
        public static void main(String[] args) {
            JFrame frame = new JFrame();
            frame.add(new CalendarPanel());
            frame.setPreferredSize(new Dimension(600, 300));
            frame.pack();
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        }
}
