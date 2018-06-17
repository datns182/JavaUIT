package gui;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static int getRandom(int iMin, int iMax) {
        int iRand = 0;
        iRand = (int) Math.round(Math.random() * (iMax - iMin) + iMin);
        return iRand;
    }

    public static String getTime(int sec) {
        //if we have hours minutes and seconds
        int hours = 0;
        int remainderOfHours = 0;
        int minutes = 0;
        int seconds = 0;

        if (sec >= 3600) // if we have an hour or more
        {
            hours = sec / 3600;
            remainderOfHours = sec % 3600;        // could be more or less than a min

            if (remainderOfHours >= 60)   //check if remainder is more or equal to a min
            {
                minutes = remainderOfHours / 60;
                seconds = remainderOfHours % 60;
            } else {                       // if it's less than a min
                seconds = remainderOfHours;
            }
        }
        // if we have a min or more
        else if (sec >= 60) {
            hours = 0;               //62
            minutes = sec / 60;
            seconds = sec % 60;
        }
        //if we have just seconds
        else if (sec < 60) {
            hours = 0;
            minutes = 0;
            seconds = sec;
        }
//i get integer hour minuite second. i want to transform them to strings:

        String strHours;
        String strMins;
        String strSecs;

        if (seconds < 10)
            strSecs = "0" + Integer.toString(seconds);
        else
            strSecs = Integer.toString(seconds);

        if (minutes < 10)
            strMins = "0" + Integer.toString(minutes);
        else
            strMins = Integer.toString(minutes);

        if (hours < 10)
            strHours = "0" + Integer.toString(hours);
        else
            strHours = Integer.toString(hours);


        String time = strHours + ":" + strMins + ":" + strSecs;
        return time;
    }

    public static String suffleString(String input) {
        String output = "";
        List<Character> characters = new ArrayList<Character>();
        for (char c : input.toCharArray()) {
            characters.add(c);
        }

        while (characters.size() != 0) {
            int rand = (int) (Math.random() * characters.size());
            output += characters.remove(rand);
        }
        return output;
    }

    public static String getRandomLetter(int number) {
        String alphabet = "QWERTYUIOPASDFGHJKLZXCVBNM";
        String result = "";
        for (int i = 0; i < number; i++) {
            int index = getRandom(0, alphabet.length() - 1);
            result += alphabet.charAt(index);
        }
        return result;
    }

    public static void playSound(String path){
        try {

            File file = new File(path);
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);

            try {
                Player player = new Player(bis);
                player.play();
            } catch (JavaLayerException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static String[] getInfoDatabaseFromFile(String fileName){
        String[] info= new String[2];


        try {
            File file = new File(fileName);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            info[0] = br.readLine();
            info[1] = br.readLine();

            fr.close();
            br.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }


        return info;
    }

}
