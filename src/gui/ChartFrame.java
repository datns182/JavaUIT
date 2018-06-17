package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

public class ChartFrame extends JFrame {
    private ChartPanel chartPanel;
    private Controller controller;
    public ChartFrame(){

        chartPanel = new ChartPanel();
        controller = new Controller();

        connect();
        try {
            chartPanel.setData(controller.getAccountsListFromDatabase());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        setSize(711, 600);

        Dimension dim = getPreferredSize();
        dim.width = 711;
        setMinimumSize(dim);

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout();
        setLocationRelativeTo(null);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                disconnect();
                dispose();
                System.gc();
                //parent.setVisible(true);
            }
        });
    }

    private void setLayout(){
        setLayout(new BorderLayout());
        add(chartPanel, BorderLayout.CENTER);
    }

    private void connect(){
        try {
            controller.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void disconnect(){
        controller.disconnect();
    }

}
