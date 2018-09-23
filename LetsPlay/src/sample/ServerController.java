package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.xml.crypto.Data;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ServerController {
    private int port;
    private Socket socket;
//    private ServerSocket serverSocket;
//    private DataInputStream dis;
//    private DataOutputStream dos;

    static ArrayList<Socket> alls;
    static ArrayList<DataInputStream> alldis;
    static ArrayList<DataOutputStream> alldos;



    private boolean stop = false;
    private String str1 = "";
    private String str2 = "";
    private String username = "server";



    @FXML
    TextArea viewer;

    @FXML
    TextField tf_port;

    @FXML
    Button btnGo;

    @FXML
    void initialize(){
        port = 3332;
        alls = new ArrayList<>();
        alldis = new ArrayList<>();
        alldos = new ArrayList<>();
    }


    @FXML
    void go() throws IOException {
//        new LetsHear().start();

        new Server().start();

        tf_port.setEditable(false);
        btnGo.setDisable(true);

    }

    @FXML
    void entered(KeyEvent event) throws IOException {
        String msg = event.getCode().toString();


        String key="";
        switch (msg){
            case "Z":
               // play_c();
                key="c";
                break;
            case "X":
                key="d";
                break;
            case "C":
                key="e";
                break;
            case "V":
                key="f";
                break;

            case "B":
                key="g";
                break;
            case "N":
                key="a";
                break;
            case "M":
                key="b";
                break;

            case "S":
                key="cs";
                break;

            case "D":
                key="ds";
                break;

            case "G":
                key="fs";
                break;

            case "H":
                key="gs";
                break;

            case "J":
                key="as";
                break;

        }



        sendMsg(key);
        viewer.setText(key);


        try {
            play(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        event.consume();
    }

    void sendMsg(String msg){
        viewer.setText(msg);

        try{

            for (DataOutputStream dos:alldos) {
                dos.writeUTF(msg);
                dos.flush();
            }
            
//            dos.writeUTF(msg);
//            dos.flush();
        }catch (Exception e){
//            viewer.setText(": couldn't send");
        }

    }


    @FXML
    void play_c() throws IOException {
        play("c");
    }
    @FXML
    void play_d() throws IOException {
        play("d");
    }
    @FXML
    void play_e() throws IOException {
        play("e");
    }
    @FXML
    void play_f() throws IOException {
        play("f");
    }
    @FXML
    void play_g() throws IOException {
        play("g");
    }
    @FXML
    void play_a() throws IOException {
        play("a");
    }
    @FXML
    void play_b() throws IOException {
        play("b");
    }
    @FXML
    void play_cs() throws IOException {
        play("cs");
    }
    @FXML
    void play_ds() throws IOException {
        play("ds");
    }
    @FXML
    void play_fs() throws IOException {
        play("fs");
    }
    @FXML
    void play_gs() throws IOException {
        play("gs");
    }
    @FXML
    void play_as() throws IOException {
        play("as");
    }



    void play(String key) throws IOException {
        InputStream iAudio;
        iAudio = new FileInputStream(new File("src\\sample\\notes\\"+key+".wav"));
        AudioStream iMusic = new AudioStream(iAudio);
        AudioPlayer.player.start(iMusic);
    }






    private class Server extends Thread{
        @Override
        public void run() {
            port = Integer.parseInt(tf_port.getText().toString());
            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(port);
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Starting server at port: "+port);
            // since pressed once, disable clicking the button


            while(true){
                Socket s = null;
                try{
                    System.out.println("waiting for client");

                    s = serverSocket.accept();
                    System.out.println("new client connected: " + s);

                    DataInputStream dis = new DataInputStream(s.getInputStream());
                    DataOutputStream dos = new DataOutputStream(s.getOutputStream());

                    alls.add(s);
                    alldis.add(dis);
                    alldos.add(dos);

                    System.out.println("assigning new thread");

                    Thread t = new LetsHear(s, dis, dos);
                    t.start();
                }catch (Exception e){
//                s.close();
                    e.printStackTrace();
                }
            }
        }
    }




    private class LetsHear extends Thread{
        DataInputStream dis;
        DataOutputStream dos;
        Socket s;

        public LetsHear(Socket s, DataInputStream dis, DataOutputStream dos) {
            this.s = s;
            this.dis = dis;
            this.dos = dos;
        }

        @Override
        public void run() {
//            try {
//                serverSocket = new ServerSocket(port);
//                socket = serverSocket.accept();
//                dis = new DataInputStream(socket.getInputStream());
//                dos = new DataOutputStream(socket.getOutputStream());
//
//                viewer.setText("connected!");
//            } catch (IOException e) {
//                viewer.setText("hoy nai");
//
//            }


            while(!stop){
                try{
                    String msg = dis.readUTF();
                    viewer.setText(msg);
                    play(msg);

                    for(DataOutputStream dos:alldos){
                        dos.writeUTF(msg);
                        dos.flush();
                    }

                }catch (Exception e){}
            }

            try {
                dos.close();
                socket.close();
            } catch (IOException e) {}
        }
    }


}
