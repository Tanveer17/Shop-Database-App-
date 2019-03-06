package com.tanveer.Controllers;

import com.tanveer.AccessDatabase;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import java.sql.PreparedStatement;
;

public class CustomerDialogController {
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField phoneNumber;
    @FXML
    private TextField email;

    public void processData()throws Exception{
        String fName = firstName.getText();
        String lName  = lastName.getText();
        String pNumber = phoneNumber.getText();
        String eMail = email.getText();

        String query2 = "INSERT INTO customers(first_name,last_name,phone_number,email) Values(?,?,?,?)";

        PreparedStatement preparedStatement;

            try {
                preparedStatement = AccessDatabase.getInstance().getConnection().prepareStatement(query2);
                preparedStatement.setString(1,fName);
                preparedStatement.setString(2,lName);
                preparedStatement.setString(3,pNumber);
                preparedStatement.setString(4,eMail);
                preparedStatement.execute();
            }
            catch(Exception e){
                System.out.println(e);

            }



        }


    }

