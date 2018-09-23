package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
    Stage primaryStage;
    static AnchorPane roots;
    static List<AnchorPane> panes= new ArrayList<>();
    private static int indCurr=0;
    public static boolean isSplash = false;

    @Override
    public void start(Stage ps) throws Exception{
        primaryStage = ps;
        roots = FXMLLoader.load(getClass().getResource("sample.fxml"));



        panes.add(FXMLLoader.load(getClass().getResource("sample.fxml")));
        panes.add(FXMLLoader.load(getClass().getResource("server.fxml")));
        panes.add(FXMLLoader.load(getClass().getResource("client.fxml")));



        primaryStage.setTitle("Lets Play");
        primaryStage.setScene(new Scene(roots));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }


    public static void changeScene(int ind){
//        root.getChildren().remove(panes.get(indCurr));
        roots.getChildren().add(panes.get(ind));
        indCurr = ind;
    }





}
