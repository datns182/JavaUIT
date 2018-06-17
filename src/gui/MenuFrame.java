package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MenuFrame extends JFrame{
    private JButton grammarBtn;
    private JButton wordBtn;
    private JButton gameBtn;
    private JButton chartBtn;
    private String username;

    private GrammarFrame grammarFrame;
    private GameFrame gameFrame;
    private ChartFrame chartFrame;


    public MenuFrame(JFrame parent, String username){

        this.username = username;
        grammarBtn = new JButton("Grammar Test");

        wordBtn = new JButton("Learn Words");
        gameBtn = new JButton("Play Game");
        chartBtn = new JButton("Chart");


        Dimension dim = getPreferredSize();
        dim.width = 200;
        setPreferredSize(dim);
        setResizable(false);

        setLayout();
        setSize(new Dimension(400,106));
        setLocationRelativeTo(null);

        chartBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chartFrame = new ChartFrame();
                chartFrame.setVisible(true);
            }
        });

        grammarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                grammarFrame = new GrammarFrame(MenuFrame.this, username);
                grammarFrame.setVisible(true);
                setVisible(false);
            }
        });

        gameBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameFrame = new GameFrame(MenuFrame.this, username);
                gameFrame.setVisible(true);
                setVisible(false);
            }
        });

        wordBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WordFrame wordFrame = new WordFrame(MenuFrame.this, username);
                wordFrame.setVisible(true);
                setVisible(false);
            }
        });

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
                System.gc();
                parent.setVisible(true);
            }
        });

    }

    public void setLayout(){
        JPanel buttonPanel = new JPanel();

        Dimension btnSize = grammarBtn.getPreferredSize();
        wordBtn.setPreferredSize(btnSize);
        gameBtn.setPreferredSize(btnSize);
        chartBtn.setPreferredSize(btnSize);

        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(grammarBtn);
        buttonPanel.add(wordBtn);
        buttonPanel.add(gameBtn);
        buttonPanel.add(chartBtn);

        setLayout(new BorderLayout());
        add(buttonPanel, BorderLayout.CENTER);
    }


}
