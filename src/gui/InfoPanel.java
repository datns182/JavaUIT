package gui;


import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InfoPanel extends JPanel {

    private JLabel fullNameLabel;
    private JLabel gender;
    private JLabel userNameLabel;
    private JLabel result;

    private JTextArea timeArea;

    private JButton startBtn;
    private JButton stopBtn;

    private InfoPanelListener infoPanelListener;


    public InfoPanel() {

        fullNameLabel = new JLabel("Dat Nguyen");
        userNameLabel = new JLabel("user");
        gender = new JLabel("Male");
        result = new JLabel("0");

        timeArea = new JTextArea("00:00:00");
        startBtn = new JButton("Start");
        stopBtn = new JButton("Stop");
        stopBtn.setEnabled(false);

        timeArea.setFont(new Font("Courier New",Font.BOLD,20 ));
        timeArea.setMargin(new Insets(0,50,1,0));
        timeArea.setEditable(false);



        Dimension dim = getPreferredSize();
        dim.width = 200;
        setPreferredSize(dim);
        setMinimumSize(dim);
        setLayout();

        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int select = JOptionPane.showConfirmDialog(InfoPanel.this, "Are you ready","Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                if (select == JOptionPane.YES_OPTION){
                    if (infoPanelListener!=null){
                        infoPanelListener.showTimeArea();
                        infoPanelListener.startTest();
                    }

                    startBtn.setEnabled(false);
                    stopBtn.setEnabled(true);
                }
            }
        });

        stopBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int select = JOptionPane.showConfirmDialog(InfoPanel.this, "Do you really want to stop test?","Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (select == JOptionPane.YES_OPTION){
                    if(infoPanelListener!=null){
                        infoPanelListener.stopTest();
                    }
                }
            }
        });


    }

    private void setLayout() {
        JPanel infoPanel = new JPanel();

//         Border spaceBorder = BorderFactory.createEmptyBorder(15, 0, 15, 0);
//        Border lineBorder = BorderFactory.createLineBorder(Color.BLACK);
//        setBorder(BorderFactory.createCompoundBorder(spaceBorder,lineBorder));

        //infoPanel.setBackground(Color.WHITE);
        infoPanel.setLayout(new GridBagLayout());
        infoPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 1),"User's info"));
        GridBagConstraints gc = new GridBagConstraints();


        gc.gridy = 0;
        ///Line 1


        gc.gridx = 0;

        gc.weightx=1;
        gc.weighty=1;

        gc.insets = new Insets(5,5,5,5);

        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.LINE_START;
        infoPanel.add(new Label("Name: "), gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_END;
        infoPanel.add(fullNameLabel, gc);

        ///Line 2


        gc.gridy++;

        gc.gridx = 0;

        gc.weightx=1;
        gc.weighty=1;

        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.LINE_START;
        infoPanel.add(new Label("Username: "), gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_END;
        infoPanel.add(userNameLabel, gc);

        ///Line 3

        gc.gridy++;

        gc.gridx = 0;

        gc.weightx=1;
        gc.weighty=1;

        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.LINE_START;
        infoPanel.add(new Label("Gender: "), gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_END;
        infoPanel.add(gender, gc);

        ///Line 4

        gc.gridy++;

        gc.gridx=0;
        gc.anchor=GridBagConstraints.LINE_START;
        infoPanel.add(new Label("Point:"),gc);

        gc.gridx=1;
        gc.anchor=GridBagConstraints.LINE_END;
        infoPanel.add(result, gc);
        ///Line 5
        JPanel timePanel = new JPanel();
        timePanel.setLayout(new GridBagLayout());
        //timePanel.setBackground(new Color(0, 102, 204));

        gc.gridy++;

        gc.gridx = 0;

        gc.weightx=1;
        gc.weighty=1;

        gc.gridwidth=2;

        gc.fill= GridBagConstraints.HORIZONTAL;
        gc.anchor = GridBagConstraints.LAST_LINE_START;
        timePanel.add(timeArea, gc);

        gc.gridy++;

        gc.gridx = 0;

        gc.weightx=1;
        gc.weighty=1;

        gc.gridx = 0;
        gc.gridwidth=1;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        timePanel.add(startBtn, gc);

        gc.gridx = 1;
        gc.gridwidth=1;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        timePanel.add(stopBtn, gc);





        Border spaceBorder = BorderFactory.createEmptyBorder(15, 0, 15, 0);
        setBorder(spaceBorder);
        setLayout(new BorderLayout());
        add(infoPanel, BorderLayout.PAGE_START);
        add(timePanel, BorderLayout.CENTER);

    }

    public void setPoint(int point){
        result.setText(point+"");
    }

    public void setInfoPanelListener(InfoPanelListener listener){
        this.infoPanelListener = listener;
    }

    public void setTimeArea(String time){
        timeArea.setText(time);
    }

    public void setInfoLabels(String fullname, String username){
        fullNameLabel.setText(fullname);
        userNameLabel.setText(username);
    }
}
