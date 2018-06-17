package gui;

import controller.Controller;
import model.Account;
import model.Word;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;

public class WordFrame extends JFrame {

    private InfoPanel infoPanel;
    private WordPanel wordPanel;
    private TestWordPanel testWordPanel;
    private JSplitPane splitPane;
    private ArrayList<Word> words;
    private ResultDialog resultDialog;


    private Controller controller;

    private int index;
    private int descrement;
    private int indexRandom;
    private int point = 0;
    private int second = 1;
    private Timer timer;
    private int level = 1;

    public WordFrame(JFrame parent, String username) {

        testWordPanel = new TestWordPanel();
        infoPanel = new InfoPanel();
        wordPanel = new WordPanel();
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, infoPanel, wordPanel);
        splitPane.setOneTouchExpandable(true);
        resultDialog = new ResultDialog(WordFrame.this);

        words = new ArrayList<>();

        controller = new Controller();

        connect();
        getWordsListFromDatabase();

        index = getAccountByUser(username).getIndexWord();
        descrement = index;

        showWord(index);
        wordPanel.setBtnPrev(false);


        wordPanel.setWordPanelListener(new WordPanelListener() {
            @Override
            public void setActionForButton(int action) {
                if (action == 0)
//                    if (descrement < index)
//                        Utils.playSound(getWordByIndex(descrement).getSound());
//                    else
                        Utils.playSound(getWordByIndex(index).getSound());
                if (action == 1)
//                    if (descrement < index)
//                        Utils.playSound(getWordByIndex(descrement).getSoundMeaning());
//                    else
                        Utils.playSound(getWordByIndex(index).getSoundMeaning());
                if (action == 2) {
                    index++;
                    showWord(index);
                    //descrement = index;
                    wordPanel.setBtnPrev(true);
                }
                if (action == 3) {
//                    descrement--;
//                    showWord(descrement);
//                    if (descrement == getAccountByUser(username).getIndexWord())
//                        wordPanel.setBtnPrev(false);
                    index--;
                    showWord(index);
                    if( index == getAccountByUser(username).getIndexWord())
                        wordPanel.setBtnPrev(false);
                }
                if (action == 4) {
                    Utils.playSound(words.get(indexRandom).getSound());
                }
                if (action == 5) {
                    try{
                        if (wordPanel.getAnswer().equals(words.get(indexRandom).getWord())) {
                            words.remove(indexRandom);
                            indexRandom = Utils.getRandom(0, words.size() - 1);
                            point = point + 20;
                            wordPanel.setLabelTest(words.get(indexRandom).getMeaning());
                            wordPanel.setDefault();

                            wordPanel.setLevel(++level);
                            JOptionPane.showMessageDialog(WordFrame.this, "You're awesome!");
                        }
                        else {
                            JOptionPane.showMessageDialog(WordFrame.this, "Sorry, try again!");
                        }
                        wordPanel.setBlank();

                    }
                    catch (Exception ex){
                        JOptionPane.showMessageDialog(WordFrame.this, "Wonderfull! You can remember all word you learned");
                        timer.stop();
                        resultDialog.setTestName("Word Test");
                        resultDialog.setVisible(true);
                        resultDialog.showResult(point, second);
                    }

                }
            }
        });


        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                disconnect();
                dispose();
                System.gc();
                parent.setVisible(true);
            }
        });

        infoPanel.setInfoPanelListener(new InfoPanelListener() {
            @Override
            public void showTimeArea() {
                timer = new Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        infoPanel.setTimeArea(Utils.getTime(second));
                        infoPanel.setPoint(point);
                        second++;
                    }
                });
                timer.start();
            }

            @Override
            public void startTest() {
                words = getListWordForTest(index);
                indexRandom = Utils.getRandom(0, words.size() - 1);
                wordPanel.setLabelTest(words.get(indexRandom).getMeaning());

            }

            @Override
            public void stopTest() {
                timer.stop();
                resultDialog.setTestName("Word Test");
                resultDialog.setVisible(true);
                resultDialog.showResult(point, second);
            }
        });

        resultDialog.setResultDialogListener(new ResultDialogListener() {
            @Override
            public void setResult() {
                try {
                    controller.updateInfoByUser(username, updatePoint(point, username), updateTime(second, username));
                    updateIndexWordByUser(username, index);
                    resultDialog.dispose();
                    parent.setVisible(true);
                    dispose();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        ///////////
        setSize(711, 600);

        Dimension dim = getPreferredSize();
        dim.width = 711;
        setMinimumSize(dim);

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        setLayout();
        setLocationRelativeTo(null);

    }

    private void setLayout() {
        setLayout(new BorderLayout());
        ;
        add(splitPane, BorderLayout.CENTER);
    }

    private void connect() {
        try {
            controller.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getWordsListFromDatabase() {
        try {
            controller.getWordsListFromDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Word getWordByIndex(int index) {
        return controller.getWordByIndex(index);
    }

    private void showWord(int index) {
        String word = getWordByIndex(index).getWord();
        String meaning = getWordByIndex(index).getMeaning();
        wordPanel.showWord(word, meaning);
        wordPanel.loadImage(getWordByIndex(index).getImage());
        wordPanel.setLevel(level);
    }

    private void disconnect() {
        controller.disconnect();
    }

    private Account getAccountByUser(String user) {
        Account account = null;
        try {
            account = controller.getAccountByUser(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return account;
    }

    private void updateIndexWordByUser(String user, int index) {
        try {
            controller.updateIndexWordByUser(user, index);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Word> getListWordForTest(int index) {
        return controller.getListWordForTest(index);
    }

    private int updatePoint(int value, String user) {
        int point = 0;
        try {
            point = controller.getAccountByUser(user).getPoint();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return point + value;
    }

    private int updateTime(int value, String user) {
        int time = 0;
        try {
            time = controller.getAccountByUser(user).getTime();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return time + value;
    }
}
