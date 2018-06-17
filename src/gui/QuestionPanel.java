package gui;

import model.Question;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class QuestionPanel extends JPanel implements ActionListener{
    private JLabel questionLabel;

    private JTextArea displayArea;

    private JButton buttonA;
    private JButton buttonB;
    private JButton buttonC;
    private JButton buttonD;

    private ButtonsListener buttonsListener;


    public QuestionPanel(){
        questionLabel = new JLabel();

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setLineWrap(true);
        displayArea.setWrapStyleWord(true);

        buttonA = new JButton("A");
        buttonB = new JButton("B");
        buttonC = new JButton("C");
        buttonD = new JButton("D");

        buttonA.addActionListener(this);
        buttonB.addActionListener(this);
        buttonC.addActionListener(this);;
        buttonD.addActionListener(this);



        setEnableButton(false);
        setLayout();

    }

    private void setLayout(){

        Dimension dim = getPreferredSize();
        dim.width=400;
        setMinimumSize(dim);

        JPanel buttonsPanel = new JPanel();

        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        displayArea.setMargin(new Insets(5, 10, 5, 10));

        questionLabel.setVerticalAlignment(JLabel.CENTER);
        questionLabel.setHorizontalAlignment(JLabel.CENTER);


        buttonA.setPreferredSize(new Dimension(100, 30));
        buttonB.setPreferredSize(new Dimension(100, 30));
        buttonC.setPreferredSize(new Dimension(100, 30));
        buttonD.setPreferredSize(new Dimension(100, 30));


        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonsPanel.add(buttonA);
        buttonsPanel.add(buttonB);
        buttonsPanel.add(buttonC);
        buttonsPanel.add(buttonD);

        setLayout(new BorderLayout());
        add(questionLabel, BorderLayout.PAGE_START);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
        buttonsPanel.setPreferredSize(new Dimension(400, 38));
    }

    public void showQuestion(String question, int number){
        displayArea.setText(question);
        questionLabel.setText("QUESTION " + number);
    }


    public void setButtonsListener(ButtonsListener listener){
        this.buttonsListener = listener;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clicked = (JButton)e.getSource();
        if(clicked == buttonA) {
            if(buttonsListener!=null){
                buttonsListener.setIndexAnswer(0);
            }
        }
        if(clicked == buttonB) {
            if(buttonsListener!=null){
                buttonsListener.setIndexAnswer(1);
            }
        }
        if(clicked == buttonC) {
            if(buttonsListener!=null){
                buttonsListener.setIndexAnswer(2);
            }
        }
        if(clicked == buttonD) {
            if(buttonsListener!=null){
                buttonsListener.setIndexAnswer(3);
            }
        }

    }

    public void setEnableButton(boolean flag){
        buttonA.setEnabled(flag);
        buttonB.setEnabled(flag);
        buttonC.setEnabled(flag);
        buttonD.setEnabled(flag);
    }
}
