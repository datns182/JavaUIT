package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class LoginFrame extends JFrame {
    private JTextField userField;
    private JPasswordField passField;
    private JButton okBtn;
    private JButton registerBtn;
    private JLabel userLabel;
    private JLabel passLabel;
    private RegisterFrame registerFrame;

    private JLabel introImage;

    private Controller controller;

    public LoginFrame() {
        super("Exam System");

        introImage = new JLabel();
        introImage.setHorizontalAlignment(JLabel.CENTER);
        introImage.setVerticalAlignment(JLabel.CENTER);
        loadImage("intro.png");
        controller = new Controller();

        connect();

        userField = new JTextField(20);
        passField = new JPasswordField(20);

        userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Time News Roman",Font.BOLD, 15));
        userLabel.setForeground(Color.GRAY);

        passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Time News Roman",Font.BOLD, 15));
        passLabel.setForeground(Color.GRAY);

        okBtn = new JButton("OK");
        registerBtn = new JButton("Register");

        Dimension btnSize = registerBtn.getPreferredSize();
        okBtn.setPreferredSize(btnSize);

        setResizable(false);
        setSize(new Dimension(818, 500));
        setLayout();
        setLocationRelativeTo(null);
        setVisible(true);


        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("Window closing");
                dispose();
                System.gc();
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        registerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerFrame = new RegisterFrame(LoginFrame.this);
                registerFrame.setVisible(true);
            }
        });

        okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText();
                String password = String.valueOf(passField.getPassword());
                try {
                    if (controller.getPasswordByUsername(username).equals(password)) {
                        MenuFrame menuFrame = new MenuFrame(LoginFrame.this, username);
                        menuFrame.setVisible(true);
                        setVisible(false);
                    } else {
                        JOptionPane.showMessageDialog(LoginFrame.this, "Password is wrong, please try", "", JOptionPane.ERROR_MESSAGE);
                        passField.setText("");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(LoginFrame.this, "Username is not validated", "", JOptionPane.ERROR_MESSAGE);
                    userField.setText("");
                    passField.setText("");
                    ex.printStackTrace();
                }


            }
        });

    }

    private void setLayout() {
        JPanel introPanel = new JPanel();
        introPanel.setPreferredSize(new Dimension(400, 500));
        introPanel.setBackground(new Color(0, 102, 204));
        introPanel.setLayout(new BorderLayout());
        introPanel.add(introImage, BorderLayout.CENTER);

        JPanel infoPanel = new JPanel();
//        infoPanel.setPreferredSize(new Dimension(400, 500));
        infoPanel.setLayout(new GridBagLayout());
        infoPanel.setBackground(Color.WHITE);

        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(400, 500));
        leftPanel.setBackground(Color.WHITE);

        GridBagConstraints gc = new GridBagConstraints();

        Insets rightPadding = new Insets(0, 20, 0, 20);
        Insets noPadding = new Insets(0, 0, 0, 0);

        JLabel login = new JLabel("Login");
        login.setHorizontalAlignment(JLabel.CENTER);
        login.setFont(new Font("Serif", Font.BOLD, 35));
        login.setForeground(Color.GRAY);


        ///// first row /////
        gc.gridy = 0;

        gc.weightx = 1;
        gc.weighty = 1;

        gc.gridx = 0;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = rightPadding;
        infoPanel.add(userLabel, gc);

        //// second row /////
        gc.gridy++;

        gc.weightx = 1;
        gc.weighty = 1;


        gc.gridx = 0;
        gc.gridwidth = 2;
        gc.insets = rightPadding;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        infoPanel.add(userField, gc);


        ////third row

        gc.gridwidth = 1;
        gc.gridy++;

        gc.weightx = 1;
        gc.weighty = 1;

        gc.gridx = 0;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = rightPadding;
        infoPanel.add(passLabel, gc);

        ////fourth row
        gc.gridy++;
        gc.gridwidth = 2;

        gc.weightx = 1;
        gc.weighty = 1;

        gc.insets = rightPadding;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        infoPanel.add(passField, gc);

        //// fifth row ////
        gc.gridwidth = 1;
        gc.gridy++;

        gc.weightx = 1;
        gc.weighty = 1;

        gc.gridx = 1;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.LINE_START;
        infoPanel.add(okBtn, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_END;
        infoPanel.add(registerBtn, gc);

        leftPanel.setLayout(new BorderLayout());
        leftPanel.add(infoPanel, BorderLayout.CENTER);
        leftPanel.add(login, BorderLayout.PAGE_START);
        setLayout(new BorderLayout());
        add(introPanel, BorderLayout.LINE_START);
        add(leftPanel, BorderLayout.LINE_END);

    }

    private void connect() {
        try {
            controller.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadImage(String imageName) {
        ImageIcon imageIcon = null;

        imageIcon = new ImageIcon(imageName);
        Image image = imageIcon.getImage(); // transform it
        Image newimg = image.getScaledInstance(210, 220, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        imageIcon = new ImageIcon(newimg);

        introImage.setIcon(imageIcon);
    }
}
