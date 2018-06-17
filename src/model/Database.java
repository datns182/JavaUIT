package model;

import gui.Utils;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private ArrayList<Question> questionsList;
    private ArrayList<Image> imagesList;
    private ArrayList<Account> accountsList;
    private ArrayList<Word> wordsList;
    private Connection con;

    public Database() {
        questionsList = new ArrayList<>();
        imagesList = new ArrayList<>();
        accountsList = new ArrayList<>();
        wordsList = new ArrayList<>();
    }

    public void connect() throws Exception {
        if (con != null)
            return;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/quiztest";

            String user = Utils.getInfoDatabaseFromFile("info.txt")[0];
            String pass = Utils.getInfoDatabaseFromFile("info.txt")[1];

            con = DriverManager.getConnection(url, user, pass);

        } catch (Exception e) {
            //throw new Exception("Driver not found");
//            pass = JOptionPane.showInputDialog("Input pass");
//            String url = "jdbc:mysql://localhost:3306/quiztest";
//
//            con = DriverManager.getConnection(url, "root", pass);
            System.out.println(e);

            JOptionPane.showMessageDialog(null,"Please config info database in info.txt file","Can't connect",JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

        System.out.println("Connected: " + con);
    }

    public void disconnect() {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("Can't close connection");
            }
        }
    }


    public ArrayList<Answer> getAnswerByQuestionId(int questionId) throws SQLException {
        ArrayList<Answer> answersOfQuestion = new ArrayList<>();

        String query = "select answer, correct from answer where questionId=?";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setInt(1, questionId);


        ResultSet result = stmt.executeQuery();
        while (result.next()) {

            String content = result.getString(1);
            boolean correct = result.getBoolean(2);

            Answer answer = new Answer(content, correct);
            answersOfQuestion.add(answer);
        }

        stmt.close();
        result.close();
        return answersOfQuestion;
    }

    public Question getQuestion(int questionId) throws SQLException {
        String query = "select content from question where questionId=?";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setInt(1, questionId);

        ResultSet result = stmt.executeQuery();
        result.next();
        String content = result.getString(1);

        stmt.close();
        result.close();
        return new Question(questionId, content, getAnswerByQuestionId(questionId));
    }

    public void getQuestionListFromDatabase() throws SQLException {
        String query = "select questionId from question";
        PreparedStatement preparedStatement = con.prepareStatement(query);

        ResultSet result = preparedStatement.executeQuery();

        while (result.next()) {
            int questionId = result.getInt(1);
            questionsList.add(getQuestion(questionId));
        }

        result.close();
        preparedStatement.close();
    }

    public int countQuestion() {
        return questionsList.size();
    }

    public void removeQuestion(int index) throws SQLException {
        questionsList.remove(index);
    }

    public Question getQuestionByIndex(int index) {
        return questionsList.get(index);

    }

    public ArrayList<Answer> getAnswersByIndex(int index) {
        return getQuestionByIndex(index).getAnswers();
    }

    public void getImageFromDatabase() throws SQLException {

        String query = "select imageName, imageAnswer from image";
        PreparedStatement preparedStatement = con.prepareStatement(query);

        ResultSet result = preparedStatement.executeQuery();

        while (result.next()) {
            String imageName = result.getString(1);
            String imageAnswer = result.getString(2);

            Image temp = new Image(imageName, imageAnswer);
            imagesList.add(temp);
        }
        result.close();
        preparedStatement.close();
    }

    public Image getImageByIndex(int index) {
        return imagesList.get(index);
    }

    public int countImage() {
        return imagesList.size();
    }

    public void removeImage(int index) {
        imagesList.remove(index);
    }

    public String getPasswordByUsername(String username) throws SQLException {
        String query = "select password from account where username =?";
        PreparedStatement preparedStatement = con.prepareStatement(query);

        preparedStatement.setString(1, username);
        ResultSet result = preparedStatement.executeQuery();
        result.next();

        String password = result.getString(1);

        result.close();
        preparedStatement.close();
        return password;
    }

    public String getFullNameByUsername(String username) throws SQLException {
        String query = "select fullname from account where username =?";
        PreparedStatement preparedStatement = con.prepareStatement(query);

        preparedStatement.setString(1, username);
        ResultSet result = preparedStatement.executeQuery();
        result.next();

        String fullname = result.getString(1);

        result.close();
        preparedStatement.close();

        return fullname;
    }

    public String getWordById(int id) throws SQLException {
        String query = "select word from word where wordId =?";
        PreparedStatement preparedStatement = con.prepareStatement(query);

        preparedStatement.setInt(1, id);
        ResultSet result = preparedStatement.executeQuery();
        result.next();

        String word = result.getString(1);
        result.close();
        preparedStatement.close();

        return word;
    }

    public String getMeaningById(int id) throws SQLException {
        String query = "select wordMeaning from word where wordId =?";
        PreparedStatement preparedStatement = con.prepareStatement(query);

        preparedStatement.setInt(1, id);
        ResultSet result = preparedStatement.executeQuery();
        result.next();

        String meaning = result.getString(1);
        result.close();
        preparedStatement.close();

        return meaning;
    }

    public String getSoundById(int id) throws SQLException {
        String query = "select sound from word where wordId =?";
        PreparedStatement preparedStatement = con.prepareStatement(query);

        preparedStatement.setInt(1, id);
        ResultSet result = preparedStatement.executeQuery();
        result.next();

        String sound = result.getString(1);
        result.close();
        preparedStatement.close();

        return sound;
    }

    public String getSoundMeaningById(int id) throws SQLException {
        String query = "select soundMeaning from word where wordId =?";
        PreparedStatement preparedStatement = con.prepareStatement(query);

        preparedStatement.setInt(1, id);
        ResultSet result = preparedStatement.executeQuery();
        result.next();

        String soundMeaning = result.getString(1);

        result.close();
        preparedStatement.close();

        return soundMeaning;
    }

    public void getWordsListFromDatabase() throws SQLException {

        String query = "select word, wordMeaning, sound, soundMeaning, image from word";
        PreparedStatement preparedStatement = con.prepareStatement(query);

        ResultSet result = preparedStatement.executeQuery();

        while (result.next()) {
            String word = result.getString(1);
            String meaning = result.getString(2);
            String sound = result.getString(3);
            String soundMeaning = result.getString(4);
            String image = result.getString(5);

            Word temp = new Word(word, meaning, sound, soundMeaning, image);
            wordsList.add(temp);
        }
    }

    public Word getWordByIndex(int index){
        return wordsList.get(index);
    }

    public void updateResultByUser(String username, int point, int time) throws SQLException {
        String query = "update account set point=?, time =? where username = ?";
        PreparedStatement preparedStatement = con.prepareStatement(query);

        preparedStatement.setInt(1,point);
        preparedStatement.setInt(2,time);
        preparedStatement.setString(3,username);
        preparedStatement.executeUpdate();

        preparedStatement.close();
    }

    public Account getAccountByUser(String username) throws SQLException{
        String query = "select * from account where username =?";
        PreparedStatement preparedStatement = con.prepareStatement(query);

        preparedStatement.setString(1, username);

        ResultSet result = preparedStatement.executeQuery();

        result.next();

        String user = result.getString(1);
        String pass = result.getString(2);
        String fullname = result.getString(3);
        int point = result.getInt(4);
        int time = result.getInt(5);
        int indexWord = result.getInt(6);
        int level = result.getInt(7);

        return new Account(user, pass, fullname, point, time, indexWord, level);

    }

    public void updateIndexWordByUser(String username, int indexWord) throws SQLException {
        String query = "update account set indexWord=? where username = ?";
        PreparedStatement preparedStatement = con.prepareStatement(query);

        preparedStatement.setInt(1, indexWord);
        preparedStatement.setString(2, username);

        preparedStatement.executeUpdate();

        preparedStatement.close();
    }

    public void updateLevelByUser(String username, int level) throws SQLException {
        String query = "update account set level=? where username = ?";
        PreparedStatement preparedStatement = con.prepareStatement(query);

        preparedStatement.setInt(1, level);
        preparedStatement.setString(2, username);

        preparedStatement.executeUpdate();

        preparedStatement.close();
    }

    public void createAccount(String username, String password, String fullname) throws SQLException {
        String query = "insert into account (username, password, fullname) values(?,?,?)";
        PreparedStatement preparedStatement = con.prepareStatement(query);

        preparedStatement.setString(1,username);
        preparedStatement.setString(2, password);
        preparedStatement.setString(3, fullname);

        preparedStatement.executeUpdate();

        preparedStatement.close();
    }

    public int checkUser(String username) throws SQLException{
        String query = "select count(*) as count from account where username =?";
        PreparedStatement preparedStatement = con.prepareStatement(query);

        preparedStatement.setString(1,username);
        ResultSet result = preparedStatement.executeQuery();
        result.next();

        int count = result.getInt(1);

        result.close();
        preparedStatement.close();
        return count;
    }

    public ArrayList<Word> getListWordForTest(int index){
        ArrayList<Word> words = new ArrayList<>();
        for(int i = 0; i < index; i ++){
            words.add(wordsList.remove(i));
        }
        return  words;
    }

    public ArrayList<Account> getAccountsListFromDatabase() throws SQLException {

        String query = "select * from quiztest.account order by point desc, time asc;";
        PreparedStatement preparedStatement = con.prepareStatement(query);

        ResultSet result = preparedStatement.executeQuery();

        while (result.next()) {
            String user = result.getString(1);
            String pass = result.getString(2);
            String fullname = result.getString(3);
            int point = result.getInt(4);
            int time = result.getInt(5);
            int indexWord = result.getInt(6);
            int level = result.getInt(7);

            Account temp = new Account(user, pass, fullname, point, time, indexWord, level);
            accountsList.add(temp);
        }

        return accountsList;
    }


}
