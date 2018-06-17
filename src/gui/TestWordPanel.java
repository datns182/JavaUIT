package gui;

import javax.swing.*;
import java.awt.*;

public class TestWordPanel extends JPanel {
    private JLabel lblTest;
    private JButton btnTest;

    public TestWordPanel(){
        lblTest = new JLabel("Test");
        btnTest = new JButton("Test");

        setSize(400, 400);
        Dimension dim = getPreferredSize();
        dim.width = 400;
        setMinimumSize(dim);

        setLayout(new BorderLayout());
        add(lblTest, BorderLayout.CENTER);
        add(btnTest, BorderLayout.PAGE_START);
    }
}
