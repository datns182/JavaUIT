package gui;


import controller.Controller;
import model.Account;
import model.Image;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;


public class GameFrame extends JFrame {
    private InfoPanel infoPanel;
    private ImagePanel imagePanel;
    private JSplitPane splitPane;
    private Controller controller;

    private int level;
    private int indexLabel = 0;
    private int indexHint = 0;

    private int second = 1;
    private int point;
    private Timer timer;
    private ResultDialog resultDialog;

    private static final int NUM_OF_BUTTONS = 18;

    public GameFrame(JFrame parent, String username) {

        infoPanel = new InfoPanel();
        imagePanel = new ImagePanel();
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, infoPanel, imagePanel);
        splitPane.setOneTouchExpandable(true);

        resultDialog = new ResultDialog(GameFrame.this);

        controller = new Controller();

        connect();

        getImageListFromDatabase();
        setInfoByUser(username);
        point = getAccountByUser(username).getPoint();
        infoPanel.setPoint(point);
        level = getAccountByUser(username).getLevel();

        infoPanel.setInfoPanelListener(new InfoPanelListener() {
            @Override
            public void showTimeArea() {
                timer = new Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        infoPanel.setTimeArea(Utils.getTime(second));
                        second++;
                    }
                });
                timer.start();
            }

            @Override
            public void startTest() {
                showQuestion(level);
            }

            @Override
            public void stopTest() {
                timer.stop();
                resultDialog.setVisible(true);
                resultDialog.showResult(point, second);
                resultDialog.setTestName("Catch the word");

            }
        });

        imagePanel.setImagePanelListener(new ImagePanelListener() {
            @Override
            public void showLetter(String letter) {
                indexLabel = imagePanel.countSelectedLetter();
                if (indexLabel < getImageByIndex(level).getImageAnswer().length()) {
                    imagePanel.showLabelByIndex(indexLabel, letter, Color.BLACK);
                    try {

                        if (indexLabel == getImageByIndex(level).getImageAnswer().length() - 1) {

                            if (checkAnswer(level)) {
                                JOptionPane.showMessageDialog(GameFrame.this, "You catch the word. Good job!");
                                removeImage(level);
                                imagePanel.removePanel();


                                point = point + 20;
                                infoPanel.setPoint(point);

                                level++;
                                showQuestion(level);
                                indexHint = 0;
                            }
                        }

                    }
                    catch (Exception ex){
                        JOptionPane.showMessageDialog(GameFrame.this, "Awesome! You completed the game!");
                        timer.stop();
                        resultDialog.setVisible(true);
                        resultDialog.showResult(point, second);
                        resultDialog.setTestName("Catch the word");
                    }

                }
            }

            @Override
            public void clearLetter() {
                imagePanel.refresh(indexHint);
                indexLabel = indexHint;
                //indexHint = 0;
            }

            @Override
            public void showHint() {
                point = point - 5;
                infoPanel.setPoint(point);
                String answer = getImageByIndex(level).getImageAnswer();
                String letter = answer.charAt(indexHint) + "";
                imagePanel.showLabelByIndex(indexHint, letter, Color.RED);
                imagePanel.setButtonAfterHint(letter);
                if (indexHint == getImageByIndex(level).getImageAnswer().length() - 1) {
                    if (checkAnswer(level)) {

                        removeImage(level);
                        imagePanel.removePanel();
                        infoPanel.setPoint(point);
                        level++;
                        showQuestion(level);
                        indexHint = -1;
                    }
                }
                indexHint++;
            }
        });

        resultDialog.setResultDialogListener(new ResultDialogListener() {
            @Override
            public void setResult() {
                try {
                    controller.updateInfoByUser(username, point, updateTime(username, second));
                    controller.updateLevelByUser(username, level);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                resultDialog.dispose();
                parent.setVisible(true);
                dispose();
            }
        });

        ///////////////////////////
        setSize(738, 600);

        Dimension dim = getPreferredSize();
        dim.width = 738;
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
                parent.setVisible(true);
            }
        });
    }

    public void setLayout() {
        setLayout(new BorderLayout());
        add(splitPane, BorderLayout.CENTER);
    }

    public void connect() {
        try {
            controller.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        controller.disconnect();
    }

    public void getImageListFromDatabase() {
        try {
            controller.getImageListFromDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Image getImageByIndex(int index) {
        return controller.getImageByIndex(index);
    }

    private boolean checkAnswer(int index) {
        return imagePanel.getAnswer().equals(getImageByIndex(index).getImageAnswer());
    }

    private void removeImage(int index) {
        controller.removeImage(index);
    }

    private void showQuestion(int level) {
        int length = getImageByIndex(level).getImageAnswer().length();
        String temp = getImageByIndex(level).getImageAnswer() + Utils.getRandomLetter(NUM_OF_BUTTONS - length);
        String letters = Utils.suffleString(temp);

        imagePanel.createAnswerLabel(length);
        imagePanel.createLetterButtons(NUM_OF_BUTTONS, letters);
        imagePanel.loadImage(getImageByIndex(level).getImageName(), level+1);
        imagePanel.showButtons();
    }

    private void setInfoByUser(String user) {
        try {
            infoPanel.setInfoLabels(controller.getFullNameByUsername(user), user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Account getAccountByUser(String username) {
        Account account = null;
        try {
            account = controller.getAccountByUser(username);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return account;
    }

    private int updateTime(String user, int value) {
        int time = 0;
        try {
            time = controller.getAccountByUser(user).getTime();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return time + value;
    }

}
