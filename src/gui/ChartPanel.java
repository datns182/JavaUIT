package gui;

import model.Account;
import model.ChartModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ChartPanel extends JPanel {
    private JTable table;
    private ChartModel chartModel;
    private JLabel lblTilte;

    public ChartPanel(){

        chartModel = new ChartModel();
        table = new JTable(chartModel);
        lblTilte = new JLabel("CHARTS");
        lblTilte.setVerticalAlignment(JLabel.CENTER);
        lblTilte.setHorizontalAlignment(JLabel.CENTER);

        setLayout(new BorderLayout());
        add(lblTilte, BorderLayout.PAGE_START);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    public void setData(ArrayList<Account> accounts){
        chartModel.setData(accounts);
    }
}
