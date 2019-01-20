package com.tanveer;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class Main extends Application {

    private String name;
    private String passwrd;
    boolean flag=false;
    boolean outh = true;


    @Override
    public void start(Stage primaryStage) throws Exception{
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setHeaderText("this is used to add new Customer");
        dialog.setTitle("Add new Todo item");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("login.fxml"));
        try{
            System.out.println("couldnt load the dialogue");
            dialog.getDialogPane().setContent(fxmlLoader.load());

        }
        catch(IOException e){
            System.out.println(e);
            return;
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);


        while(outh) {
            Optional<ButtonType> result = dialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {

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
        }

        if(flag){
            Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
            primaryStage.setTitle("Shop App");
            primaryStage.setScene(new Scene(root,900,400));
            primaryStage.show();
        }



    }


    public static void main(String[] args) {
        launch(args);
    }
}
