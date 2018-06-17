package gui;

import controller.Controller;
import model.DateLabelFormatter;


import javax.naming.ldap.Control;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.DateFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Properties;

public class RegisterFrame extends JFrame {

    private JTextField fullName;
    private JTextField userName;
    private JPasswordField passWord;
    private JPasswordField confirmPassWord;
    private JComboBox genderCombo;
    private JButton okBtn;
    private JButton cancelBtn;
    //private JDatePickerImpl datePicker;

    private Controller controller;


    public RegisterFrame(JFrame parent) {
        super("Register");

        fullName = new JTextField(20);
        userName = new JTextField(20);
        passWord = new JPasswordField(20);
        confirmPassWord = new JPasswordField(20);
        genderCombo = new JComboBox();

        controller = new Controller();

        connect();
        okBtn = new JButton("OK");
        cancelBtn = new JButton("Cancel");

        setLayout();
        setSize(400, 400);
        setLocationRelativeTo(parent);

        okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userName.getText();
                String fullname = fullName.getText();
                String password = new String(passWord.getPassword());
                String confirm = new String(confirmPassWord.getPassword());

                if (!password.equals(confirm)) {
                    JOptionPane.showMessageDialog(RegisterFrame.this, "Password and confirm are not match", "Error", JOptionPane.ERROR_MESSAGE);
                    passWord.setText("");
                    confirmPassWord.setText("");
                } else {
                    if (checkUser(username) != 0) {
                        JOptionPane.showMessageDialog(RegisterFrame.this, "Username have been used", "Error", JOptionPane.ERROR_MESSAGE);
                        userName.setText("");
                    } else {
                        creatAccount(username, password, fullname);
                        JOptionPane.showMessageDialog(RegisterFrame.this, "Create account successfully", "", JOptionPane.INFORMATION_MESSAGE);
                        setVisible(false);
                    }
                }
            }
        });



        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
    }

    private void setLayout() {
        JPanel controlPanel = new JPanel();
        JPanel buttonsPanel = new JPanel();

        Insets rightPadding = new Insets(0, 0, 0, 15);

        int space = 10;
        Border spaceBorder = BorderFactory.createEmptyBorder(space, space, space, space);
        Border titleBorder = BorderFactory.createTitledBorder("Infomation");

        controlPanel.setBorder(BorderFactory.createCompoundBorder(spaceBorder, titleBorder));
        controlPanel.setLayout(new GridBagLayout());

        GridBagConstraints gc = new GridBagConstraints();

        //// first row /////
        gc.gridy = 0;

        gc.weightx = 1;
        gc.weighty = 0.1;

        gc.gridx = 0;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = rightPadding;
        controlPanel.add(new JLabel("Full Name:"), gc);

        gc.gridx = 1;
        gc.gridy = 0;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.LINE_START;
        controlPanel.add(fullName, gc);

        //// second row /////
        gc.gridy++;

        gc.weightx = 1;
        gc.weighty = 0.1;

        gc.gridx = 0;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = rightPadding;
        controlPanel.add(new JLabel("Username:"), gc);

        gc.gridx = 1;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.LINE_START;
        controlPanel.add(userName, gc);

        //// third row /////
        gc.gridy++;

        gc.weightx = 1;
        gc.weighty = 0.1;

        gc.gridx = 0;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = rightPadding;
        controlPanel.add(new JLabel("Password:"), gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        controlPanel.add(passWord, gc);

        //// fourth row /////
        gc.gridy++;

        gc.weightx = 1;
        gc.weighty = 0.1;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = rightPadding;
        controlPanel.add(new JLabel("Confirm:"), gc);

        gc.gridx = 1;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.LINE_START;
        controlPanel.add(confirmPassWord, gc);

//        //// fifth row /////
//        gc.gridy ++;
//
//        gc.weightx = 1;
//        gc.weighty = 0.1;
//
//        gc.gridx = 0;
//        gc.anchor = GridBagConstraints.LINE_END;
//        gc.insets = rightPadding;
//        controlPanel.add(new JLabel("Gender:"),gc);
//
//        gc.gridx = 1;
//        gc.fill = GridBagConstraints.NONE;
//        gc.anchor = GridBagConstraints.LINE_START;
//        controlPanel.add(genderCombo, gc);
//
//        //// fifth row /////
//        gc.gridy ++;
//
//        gc.weightx = 1;
//        gc.weighty = 0.1;
//
//        gc.gridx = 0;
//        gc.anchor = GridBagConstraints.LINE_END;
//        gc.insets = rightPadding;
//        controlPanel.add(new JLabel("Date of birth:"),gc);
//
//        gc.gridx = 1;
//        gc.fill = GridBagConstraints.NONE;
//        gc.anchor = GridBagConstraints.LINE_START;
////        controlPanel.add(datePicker, gc);

        //// button panel

        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.add(okBtn, gc);
        buttonsPanel.add(cancelBtn, gc);

        Dimension btnSize = cancelBtn.getPreferredSize();
        okBtn.setPreferredSize(btnSize);

        ////;
        setLayout(new BorderLayout());
        add(controlPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);

    }

    private void connect() {
        try {
            controller.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void creatAccount(String username, String password, String fullname) {
        try {
            controller.createAccount(username, password, fullname);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int checkUser(String username) {
        int count = -1;
        try {
            count = controller.checkUser(username);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
}
