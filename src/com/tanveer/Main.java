package com.tanveer;

import com.tanveer.Controllers.LoginController;
import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Optional;

public class Main extends Application {
    private boolean flag=false;
    private boolean outh = true;


    @Override
    public void start(Stage primaryStage) throws Exception{
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setHeaderText("Enter User Name and Password");


        dialog.setTitle("Log in page");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("fxml/login.fxml"));
        try{
            dialog.getDialogPane().setContent(fxmlLoader.load());
        }


        catch(IOException e){
            e.printStackTrace();
            return;
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.NEXT);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);


        while(outh) {
            Optional<ButtonType> result = dialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.NEXT) {

                try {
                    AccessDatabase.getInstance().setClass("com.mysql.jdbc.Driver");
                    LoginController loginController = fxmlLoader.getController();

                    if (loginController.proccessLogin()) {
                        flag = true;
                        outh=false;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if(result.isPresent() && result.get()==ButtonType.CANCEL){
                outh = false;
            }
        }

        if(flag){
            Parent root = FXMLLoader.load(getClass().getResource("fxml/sample.fxml"));
            primaryStage.setTitle("Shop App");
            primaryStage.setScene(new Scene(root,1000,400));
            primaryStage.show();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
