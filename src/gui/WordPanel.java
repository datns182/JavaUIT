package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class WordPanel extends JPanel implements ActionListener{
    private JLabel lblWord;
    private JLabel lblMeaning;
    private JButton btnReplayWord;
    private JButton btnReplayMeaning;
    private JButton btnNext;
    private JButton btnPrev;
    private JLabel lblLevel;

    private JPanel pnlLearn;

    private JPanel pnlTest;
    private JLabel lblTest;
    private JButton btnTest;

    private JLabel lblImage;
    private JTextField txtAnswer;
    private JButton btnOk;

    private WordPanelListener wordPanelListener;


    public WordPanel() {
        lblWord = new JLabel();
        lblMeaning = new JLabel();

        lblLevel  = new JLabel("WORD");
        lblLevel.setVerticalAlignment(JLabel.CENTER);
        lblLevel.setHorizontalAlignment(JLabel.CENTER);

        btnReplayMeaning = new JButton(setIconButton("Play16.gif"));
        btnReplayWord = new JButton(setIconButton("Play16.gif"));


        btnNext = new JButton(setIconButton("Forward16.gif"));
        btnPrev = new JButton(setIconButton("Back16.gif"));

        pnlLearn = new JPanel();
        pnlTest = new JPanel();
        lblImage = new JLabel();
        lblImage.setHorizontalAlignment(JLabel.CENTER);
        lblImage.setVerticalAlignment(JLabel.CENTER);

        btnTest = new JButton(setIconButton("Play16.gif"));
        btnOk = new JButton("OK");

        btnOk.setEnabled(false);
        btnTest.setEnabled(false);
        txtAnswer = new JTextField(10);
        lblTest = new JLabel("Definition");

        btnNext.addActionListener(this);
        btnPrev.addActionListener(this);
        btnReplayWord.addActionListener(this);
        btnReplayMeaning.addActionListener(this);
        btnTest.addActionListener(this);
        btnOk.addActionListener(this);

        setSize(400, 400);
        setLayout();
    }

    private void setLayout() {
        Dimension dim = getPreferredSize();
        dim.width = 400;
        setMinimumSize(dim);

        JPanel wordPanel = new JPanel();
        wordPanel.setLayout(new FlowLayout());
        wordPanel.add(lblWord);
        wordPanel.add(btnReplayWord);

        JPanel meaningPanel = new JPanel();
        meaningPanel.setLayout(new FlowLayout());
        meaningPanel.add(lblMeaning);
        meaningPanel.add(btnReplayMeaning);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout());
        buttonsPanel.add(btnPrev);
        buttonsPanel.add(btnNext);
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(20,0,40,0));

        pnlLearn.setLayout(new BorderLayout());
        pnlLearn.add(wordPanel, BorderLayout.PAGE_START);
        pnlLearn.add(meaningPanel, BorderLayout.CENTER);
        pnlLearn.add(lblImage, BorderLayout.PAGE_END);


        pnlTest.setLayout(new WrapLayout());
        pnlTest.add(btnTest);
        pnlTest.add(lblTest);
        pnlTest.add(txtAnswer);
        pnlTest.add(btnOk);
        JPanel pnlBottom = new JPanel();
        pnlBottom.setLayout(new BorderLayout());
        pnlBottom.add(lblLevel, BorderLayout.PAGE_START);
        pnlBottom.add(pnlTest, BorderLayout.CENTER);
        //pnlBottom.setBorder(BorderFactory.createTitledBorder("Test Field"));



        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20,0,150,0));
        add(pnlLearn, BorderLayout.PAGE_START);
        add(buttonsPanel, BorderLayout.CENTER);
        add(pnlBottom, BorderLayout.PAGE_END);
    }

    public void setWordPanelListener(WordPanelListener listener){
        this.wordPanelListener = listener;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clicked = (JButton)e.getSource();
        if(clicked == btnReplayWord)
            if(wordPanelListener!=null)
                wordPanelListener.setActionForButton(0);
        if(clicked == btnReplayMeaning)
            if(wordPanelListener!=null)
                wordPanelListener.setActionForButton(1);
        if(clicked == btnNext)
            if(wordPanelListener!=null)
                wordPanelListener.setActionForButton(2);
        if(clicked == btnPrev)
            if(wordPanelListener!=null)
                wordPanelListener.setActionForButton(3);
        if(clicked == btnTest)
            if(wordPanelListener!=null)
                wordPanelListener.setActionForButton(4);
        if(clicked == btnOk)
            if(wordPanelListener!=null)
                wordPanelListener.setActionForButton(5);
    }

    public void showWord(String word, String meaning){
        lblWord.setText(word);
        lblMeaning.setText(meaning);
    }

    public void setBtnPrev(boolean flag){
        btnPrev.setEnabled(flag);
    }

    public void setLabelTest(String text){
        lblTest.setText(text);

        btnPrev.setEnabled(false);
        btnNext.setEnabled(false);
        btnReplayMeaning.setEnabled(false);
        btnReplayWord.setEnabled(false);
        btnTest.setEnabled(true);
        btnOk.setEnabled(true);
    }

    public String getAnswer(){
        return txtAnswer.getText();
    }

    public void setDefault(){
        txtAnswer.setText("");
    }

    public void loadImage(String imageName) {
        ImageIcon imageIcon = null;

        imageIcon = new ImageIcon(imageName);
        Image image = imageIcon.getImage(); // transform it
        Image newimg = image.getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        imageIcon = new ImageIcon(newimg);

        lblImage.setIcon(imageIcon);
    }

    private Icon setIconButton(String icon){
        return new ImageIcon(icon);
    }

    public void setLevel(int level){
        lblLevel.setText("WORD " + level);
    }

    public void setBlank(){
        txtAnswer.setText("");
    }

}

