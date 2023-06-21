package org.example;


import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;




/**
 * Hello world!
 *
 */
public class App
{
    static CustomFrame f;
    static PrintWriter out = null;
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();

            }
        });

        startClient();


    }

    private static void createAndShowGUI() {
        System.out.println("Created GUI on EDT? "+
                SwingUtilities.isEventDispatchThread());
        f = new CustomFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(850,750);
        f.setVisible(true);
    }

    public static void startClient() {
        String hostName = "127.0.0.1";
        int portNumber = 1234;
        Socket echoSocket = null;
        try {
            echoSocket = new Socket(hostName, portNumber);
        } catch (IOException e) {
            System.out.println("cannot reach server "+e);
        }

        try {
            out = new PrintWriter(echoSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
        } catch (IOException e) {
            System.out.println("cannot allocate bufferedreader");
        }
        BufferedReader stdIn = new BufferedReader( new InputStreamReader(System.in));
        String userInput = "";

        String s = "";
        while (true) {
            try {
                String msgRec = in.readLine();
                System.out.println("echo: " + msgRec);
                if(!msgRec.equals("Welcome user")) {
                    System.out.println("prova: " + msgRec);
                    f.updateMessage(msgRec);
                }
                System.out.println("echo: " + in.readLine());
                f.updateWelcome(s);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if (!((userInput = stdIn.readLine()) != null)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            out.println(userInput);
            out.flush();
        }

    }

}
