package gui;

import javax.swing.*;

public class App {

    public static void main(String[] args) {
	// write your code here
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

               new LoginFrame();

            }
        });
    }
}
