package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ImagePanel extends JPanel implements ActionListener {

    private JLabel imageLabel;

    private JPanel answerPanel;
    private JPanel letterPanel;
    private JLabel levelLabel;
    private JPanel bottomPanel;
    private JPanel centerPanel;

    private JButton clearBtn;
    private JButton hintBtn;

    private int letterClick = 0;
    private int hintClick = 0;

    private ImagePanelListener imagePanelListener;

    private ArrayList<JLabel> labelList;
    private ArrayList<JButton> buttonList;
    private ArrayList<Integer> indexHintList;

    public ImagePanel() {

        labelList = new ArrayList<>();
        buttonList = new ArrayList<>();
        indexHintList = new ArrayList<>();

        bottomPanel = new JPanel();
        letterPanel = new JPanel();
        answerPanel = new JPanel();
        centerPanel = new JPanel();
        centerPanel.setVisible(false);

        clearBtn = new JButton("Clear");
        clearBtn.addActionListener(this);
        hintBtn = new JButton("Hint");
        hintBtn.addActionListener(this);

        levelLabel = new JLabel();
        levelLabel.setHorizontalAlignment(JLabel.CENTER);
        levelLabel.setVerticalAlignment(JLabel.CENTER);

        imageLabel = new JLabel();

        imageLabel.setHorizontalAlignment(JLabel.CENTER);

        setLayout();
    }

    private void setLayout() {
        Dimension dim = getPreferredSize();
        dim.width = 520;
        setMinimumSize(dim);

        centerPanel.setLayout(new FlowLayout());
        centerPanel.add(clearBtn);
        centerPanel.add(hintBtn);

        answerPanel.setLayout(new FlowLayout());
        letterPanel.setLayout(new WrapLayout());

        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.add(answerPanel, BorderLayout.PAGE_START);
        bottomPanel.add(centerPanel, BorderLayout.CENTER);
        bottomPanel.add(letterPanel, BorderLayout.PAGE_END);

        setLayout(new BorderLayout());
        add(levelLabel, BorderLayout.PAGE_START);
        add(bottomPanel, BorderLayout.PAGE_END);
        add(imageLabel, BorderLayout.CENTER);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clicked = (JButton) e.getSource();
        if (imagePanelListener != null) {
            if (clicked == clearBtn) {
                imagePanelListener.clearLetter();
                letterClick = 0;
            } else if (clicked != clearBtn && clicked != hintBtn) {
                letterClick++;
                if (letterClick <= labelList.size()) {
                    String letter = clicked.getText();
                    imagePanelListener.showLetter(letter);
                    clicked.setEnabled(false);
                }
            } else if (clicked == hintBtn) {
                int select = JOptionPane.showConfirmDialog(this, "Do you want to use 5 point to get hint?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                if (select == JOptionPane.YES_OPTION) {
                    hintClick++;
                    imagePanelListener.showHint();
                    letterClick = hintClick;
                }
            }
        }
    }

    public void loadImage(String imageName, int level) {
        letterClick = 0;
        levelLabel.setText("LEVEL " + level);
        ImageIcon imageIcon = null;

        imageIcon = new ImageIcon(imageName);
        Image image = imageIcon.getImage(); // transform it
        Image newimg = image.getScaledInstance(500, 300, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        imageIcon = new ImageIcon(newimg);

        imageLabel.setIcon(imageIcon);
    }

    public void setImagePanelListener(ImagePanelListener listener) {
        this.imagePanelListener = listener;
    }

    public void showLabelByIndex(int index, String letter, Color color) {
        JLabel label = labelList.get(index);
        label.setText(letter);
        label.setForeground(color);
    }

    public void createAnswerLabel(int num) {
        for (int i = 0; i < num; i++) {
            JLabel letter = new JLabel("_");
            letter.setFont(new Font("Arial", Font.BOLD, 30));
            letter.setBorder(BorderFactory.createEmptyBorder(3, 5, 5, 3));
            answerPanel.add(letter);
            labelList.add(letter);
        }
    }

    public void createLetterButtons(int num, String letters) {
        for (int i = 0; i < num; i++) {
            JButton letter = new JButton();
            letter.addActionListener(this);
            letter.setPreferredSize(new Dimension(47, 47));
            letter.setFont(new Font("Time News Roman", Font.BOLD, 12));
            letter.setText("" + letters.charAt(i));
            buttonList.add(letter);
            letterPanel.add(letter);
        }
    }

    public String getAnswer() {
        String answer = "";
        for (JLabel label : labelList) {
            answer += label.getText();
        }
        return answer;
    }

    public void removePanel() {
        answerPanel.removeAll();
        answerPanel.repaint();
        answerPanel.revalidate();
        labelList.removeAll(labelList);
        letterPanel.removeAll();
    }

    public void refresh(int index) {
        for(int i = 0; i < buttonList.size(); i++){
            if(indexHintList.indexOf(i) == -1)
                buttonList.get(i).setEnabled(true);

        }
//        for (JButton button : buttonList)
//            button.setEnabled(true);
        for (int i = index; i < labelList.size(); i++) {
            labelList.get(i).setText("_");
        }
    }

    public int countSelectedLetter() {
        int count = 0;
        for (JLabel label : labelList) {
            if (!label.getText().equals("_")) {
                count++;
            }
        }
        return count;
    }

    public void showButtons() {
        centerPanel.setVisible(true);
    }

    public void setButtonAfterHint(String letter) {
        for (int i = 0; i < buttonList.size(); i++) {
            JButton clicked = buttonList.get(i);
            if(clicked.getText().equals(letter) && clicked.isEnabled()){
                clicked.setEnabled(false);
                indexHintList.add(i);
                break;
            }
        }
    }

}
