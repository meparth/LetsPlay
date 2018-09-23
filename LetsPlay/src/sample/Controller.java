package sample;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable {
    @FXML
    Button btnServer, btnClient;
    @FXML
    AnchorPane root;
    public static AnchorPane rootp;


    public void initialize(URL url, ResourceBundle rb){

        if(!Main.isSplash){
            loadSplashScreen();
        }

    }


    @FXML
    void onClickServer(){
        Main.changeScene(1);
    }

    @FXML
    void onClickClient(){
        Main.changeScene(2);

    }

    private void loadSplashScreen(){
        try {
            Main.isSplash=true;
            AnchorPane pane= FXMLLoader.load(getClass().getResource("splash.FXML"));
            root.getChildren().setAll(pane);
            FadeTransition fadein=new FadeTransition(Duration.seconds(2),pane);
            fadein.setFromValue(0);
            fadein.setToValue(1);
            fadein.setCycleCount(1);



            FadeTransition fadeout=new FadeTransition(Duration.seconds(1),pane);
            fadeout.setFromValue(1);
            fadeout.setToValue(0);
            fadeout.setCycleCount(1);

            fadein.play();

            fadein.setOnFinished(event -> {
                fadeout.play();

            });


            fadeout.setOnFinished(event -> {
                try {
                    AnchorPane parent=FXMLLoader.load(getClass().getResource("sample.FXML"));
                    root.getChildren().setAll(parent);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
