package org.example;

import javax.swing.*;
import java.awt.*;



public class CustomFrame extends JFrame implements Runnable {
    String message = "Your result will appear here";
    String welcome = "Connecting to server...";

    Thread th;
    public CustomFrame() throws HeadlessException {

        this.th = new Thread(this);
        this.th.start();
    }

    public void paint(Graphics g) {
        super.paint(g); // always call this first - BUT MAY flicker.. on repaint..
        g.drawString(welcome, 50, 50);
        g.drawString(message, 50, 75);


    }



    public void updateMessage(String msg) {
        this.message = msg;
    }
    public void updateWelcome(String msg) {
        this.welcome = "Hello user!";
    }


    @Override
    public void run() {
        while (true) {
            this.repaint();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}