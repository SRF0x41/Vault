package com.srf;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class gui {
    public gui(){
        JFrame frame = new JFrame("Bad Example");  // UI created on the main thread
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.add(new JLabel("Hello, Swing!"));
        frame.setVisible(true);
    }
}
