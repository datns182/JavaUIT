package controller;

import gui.InfoPanel;
import model.*;

import java.sql.SQLException;
import java.util.ArrayList;

public class Controller {
    Database db = new Database();

    public void connect() throws Exception {
        db.connect();
    }

    public void disconnect() {
        db.disconnect();
    }

    public void getQuestionListFromDatabase() throws SQLException {
        db.getQuestionListFromDatabase();
    }

    public int countQuestion() {
        return db.countQuestion();
    }

    public void removeQuestion(int index) throws SQLException {
        db.removeQuestion(index);
    }

    public Question getQuestionByIndex(int index) {
        return db.getQuestionByIndex(index);
    }

    public ArrayList<Answer> getAnswersByIndex(int index) {
        return db.getAnswersByIndex(index);
    }

    public void getImageListFromDatabase() throws SQLException {
        db.getImageFromDatabase();
    }

    public Image getImageByIndex(int index) {
        return db.getImageByIndex(index);
    }

    public int countImage() {
        return db.countImage();
    }

    public void removeImage(int index) {
        db.removeImage(index);
    }

    public String getPasswordByUsername(String username) throws SQLException {
        return db.getPasswordByUsername(username);
    }

    public String getFullNameByUsername(String username) throws SQLException {
        return db.getFullNameByUsername(username);
    }

    public String getWordByIn(int id) throws SQLException {
        return db.getWordById(id);
    }

    public String getMeaningById(int id) throws SQLException {
        return db.getMeaningById(id);
    }

    public String getSoundById(int id) throws  SQLException{
        return db.getSoundById(id);
    }

    public String getSoungMeaningByid(int id) throws SQLException{
        return  db.getSoundMeaningById(id);
    }

    public void getWordsListFromDatabase()throws SQLException{
        db.getWordsListFromDatabase();
    }

    public Word getWordByIndex(int index){
        return db.getWordByIndex(index);
    }

    public void updateInfoByUser(String username, int point, int time)throws SQLException{
        db.updateResultByUser(username, point, time);
    }

    public Account getAccountByUser(String user) throws SQLException{
        return db.getAccountByUser(user);
    }

    public void updateIndexWordByUser(String user, int indexWord) throws SQLException{
        db.updateIndexWordByUser(user, indexWord);
    }

    public void updateLevelByUser(String user, int level) throws SQLException{
        db.updateLevelByUser(user, level);
    }

    public void createAccount(String username, String password, String fullname)throws  SQLException{
        db.createAccount(username, password, fullname);
    }

    public int checkUser(String username) throws SQLException{
        return db.checkUser(username);
    }

    public ArrayList<Word> getListWordForTest(int index){
        return db.getListWordForTest(index);
    }

    public ArrayList<Account> getAccountsListFromDatabase() throws SQLException{
        return db.getAccountsListFromDatabase();
    }

}
