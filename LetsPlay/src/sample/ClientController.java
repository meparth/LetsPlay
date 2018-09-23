package sample;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.*;
import java.net.Socket;

public class ClientController {
    private int port;
    private String ip;
    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;

    private boolean stop = false;
    private String str1="", str2="";
    private String username = "client";



    @FXML
    TextArea viewer;

    @FXML
    TextField tf_port;

    @FXML
    TextField tf_ip;

    @FXML
    Button btnGo;

    @FXML
    void initialize(){
        port = 3332;
        viewer.setText("client side");
    }



    @FXML
    void go(){
        port = Integer.parseInt(tf_port.getText().toString());
        ip = tf_ip.getText().toString();
        new LetsHear().start();

        btnGo.setDisable(true);
        tf_ip.setEditable(false);
        tf_port.setEditable(false);

    }



    void sendMsg(String msg){
        viewer.setText(msg);

        try{
            dos.writeUTF(msg);
            dos.flush();
        }catch (Exception e){
            viewer.setText(" couldn't send");
        }

    }

    @FXML
    void entered(KeyEvent event){
        String msg=event.getCode().toString();
        String key="";
        switch (msg){
            case "Z":
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

        try {
            play(key);
        } catch (IOException e) {
            e.printStackTrace();
        }

        event.consume();

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
//        viewer.setText(notes.get(key).toString());
//        String name = notes.get(key).toString();
//        key = "c";



        InputStream iAudio;
        iAudio = new FileInputStream(new File("src\\sample\\notes\\"+key+".wav"));
        AudioStream iMusic = new AudioStream(iAudio);
        AudioPlayer.player.start(iMusic);
    }




    private class LetsHear extends Thread{
        @Override
        public void run() {

            try{
                socket = new Socket(ip, port);
                dis = new DataInputStream(socket.getInputStream());
                dos = new DataOutputStream(socket.getOutputStream());

                viewer.setText("connected!");
            }catch (Exception e){
                viewer.setText("problem arised!");

            }

            while(!stop){
                try{
                    String msg = dis.readUTF();
                    viewer.setText(msg);
                    play(msg);
                }catch (Exception e){}
            }

        }
    }

}
