package gui;

import controller.Controller;
import model.Answer;
import model.Question;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;


public class GrammarFrame extends JFrame {

    private InfoPanel infoPanel;
    private JSplitPane splitPane;
    private QuestionPanel questionPanel;
    private ResultDialog resultDialog;


    private int point = 0;
    private int index;
    private int number = 1;
    private int second = 1;

    private Timer timer;

    private Controller controller;


    public GrammarFrame(JFrame parent, String username) {

        /// init

        questionPanel = new QuestionPanel();
        infoPanel = new InfoPanel();

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, infoPanel, questionPanel);
        splitPane.setOneTouchExpandable(true);

        resultDialog = new ResultDialog(GrammarFrame.this);

        controller = new Controller();

        connect();
        getQuestionListFromDatabase();
        setInfoByUser(username);

        index = getRandomIndex();

        setSize(711, 600);

        Dimension dim = getPreferredSize();
        dim.width = 711;
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


        questionPanel.setButtonsListener(new ButtonsListener() {
            @Override
            public void setIndexAnswer(int choice) {
                try {
                    ArrayList<Answer> answers = getAnswersByIndex(index);
                    Answer answer = answers.get(choice);

                    if (answer.isCorrect()) {
                        JOptionPane.showMessageDialog(GrammarFrame.this, "Greate well done");
                        point += 10;
                    } else {
                        JOptionPane.showMessageDialog(GrammarFrame.this, "Wrong anwser");
                    }
                    number++;
                    infoPanel.setPoint(point);
                    if (number > 10) {
                        timer.stop();

                        questionPanel.setEnableButton(false);

                        resultDialog.setVisible(true);
                        resultDialog.showResult(point, second);
                        resultDialog.setTestName("Grammar Test");

                    } else {
                        removeQuestion(index);
                        index = getRandomIndex();

                        questionPanel.showQuestion(getQuestionByIndex(index).toString(), number);
                    }


                } catch (Exception e) {
                    System.out.println(e.toString());
                }
            }
        });

        infoPanel.setInfoPanelListener(new InfoPanelListener() {
            @Override
            public void showTimeArea() {
                timer = new javax.swing.Timer(1000, new ActionListener() {

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
                questionPanel.showQuestion(getQuestionByIndex(index).toString(), number);
                questionPanel.setEnableButton(true);
            }

            @Override
            public void stopTest() {
                timer.stop();

                questionPanel.setEnableButton(false);

                resultDialog.setVisible(true);
                resultDialog.showResult(point, second);
                resultDialog.setTestName("Grammar Test");
            }

        });

        resultDialog.setResultDialogListener(new ResultDialogListener() {
            @Override
            public void setResult() {
                try {
                    controller.updateInfoByUser(username, updatePoint(point,username), updateTime(second, username));
                    resultDialog.dispose();
                    parent.setVisible(true);
                    dispose();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

    }


    private void disconnect() {
        controller.disconnect();
    }

    private void connect() {
        try {
            controller.connect();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(GrammarFrame.this, "Cannot connect to database", "Database Connection Problem", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void getQuestionListFromDatabase() {
        try {
            controller.getQuestionListFromDatabase();
        } catch (SQLException e) {
            System.out.print(e.toString());
        }
    }

    private int getRandomIndex() {
        return Utils.getRandom(0, controller.countQuestion() - 1);
    }

    private void removeQuestion(int index) throws SQLException {
        controller.removeQuestion(index);
    }

    private ArrayList<Answer> getAnswersByIndex(int index) {
        return controller.getAnswersByIndex(index);
    }

    private Question getQuestionByIndex(int index) {
        return controller.getQuestionByIndex(index);
    }

    private int updatePoint(int value, String user){
        int point = 0;
        try {
            point = controller.getAccountByUser(user).getPoint();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return point + value;
    }

    private int updateTime(int value, String user){
        int time = 0;
        try {
            time = controller.getAccountByUser(user).getTime();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return time + value;
    }

    /////   SET LAYOUT /////
    private void setLayout() {

        setLayout(new BorderLayout());

        add(splitPane, BorderLayout.CENTER);

    }

    private void setInfoByUser(String username){
        try {
            infoPanel.setInfoLabels(controller.getFullNameByUsername(username), username);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
