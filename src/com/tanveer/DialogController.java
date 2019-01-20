package com.tanveer;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import java.sql.PreparedStatement;
;

public class DialogController {
    @FXML
    private TextField id;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField phoneNumber;
    @FXML
    private TextField email;

    public void processData()throws Exception{
        String query = "INSERT INTO customers(id,first_name,last_name,phone_number,email) Values(?,?,?,?,?)";
        String fName = firstName.getText();
        int iD=0;
        if(!id.getText().isEmpty()) {
            iD = Integer.parseInt(id.getText());
        }
        String lName  = lastName.getText();
        String pNumber = phoneNumber.getText();
        String eMail = email.getText();

        FXMLLoader fxmlLoader = new FXMLLoader();


        fxmlLoader.setLocation(getClass().getResource("sample.fxml"));
        Parent root = fxmlLoader.load();
        Controller controller = fxmlLoader.getController();
        String query2 = "INSERT INTO customers(first_name,last_name,phone_number,email) Values(?,?,?,?)";

        PreparedStatement preparedStatement;
        if(this.id.getText().isEmpty()){
            try {
                preparedStatement = AccessDatabase.getInstance().getConnection().prepareStatement(query2);
                preparedStatement.setString(1,fName);
                preparedStatement.setString(2,lName);
                preparedStatement.setString(3,pNumber);
                preparedStatement.setString(4,eMail);
            }
            catch(Exception e){
                System.out.println(e);

            }

        }
        else {
            try {
                System.out.println("prepared");

                preparedStatement = AccessDatabase.getInstance().getConnection().prepareStatement(query);
                preparedStatement.setInt(1,iD);
                preparedStatement.setString(2,fName);
                preparedStatement.setString(3,lName);
                preparedStatement.setString(4,pNumber);
                preparedStatement.setString(5,eMail);
                preparedStatement.execute();
            }
            catch(Exception e){
                System.out.println(e);

            }

        }


    }
}
