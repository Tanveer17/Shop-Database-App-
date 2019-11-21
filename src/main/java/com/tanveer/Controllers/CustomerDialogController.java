package com.tanveer.Controllers;

import com.tanveer.*;
import com.tanveer.entities.Customer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.*;
import java.util.regex.*;
import javafx.scene.control.Alert;

import javafx.collections.ObservableList;


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
        ObservableList<Customer> customers = AccessDatabase.getInstance().getCustomers();
        try {
            String fName = firstName.getText();
            String lName = lastName.getText();
            String pNumber = phoneNumber.getText();
            String eMail = email.getText();
            Pattern p = Pattern.compile("[A-Za-z]{1,15}");
            Pattern p2 = Pattern.compile("[0-9]{11}");
            Pattern p3 = Pattern.compile("[A-Za-z0-9]{1,}@[A-Za-z0-9]{1,}.com");
            Matcher matcher = p.matcher(fName);
            Matcher matcher2 = p.matcher(lName);
            Matcher matcher3 = p2.matcher(pNumber);
            Matcher matcher4 = p3.matcher(eMail);
            if (!matcher.matches()) {
                throw new InvalidInputException("First Name is invalid");
            }
            if (!matcher2.matches()) {
                throw new InvalidInputException("last name is invalid");
            }
            if (!matcher3.matches()) {
                throw new InvalidInputException("phone number is invalid");
            }
            if (!matcher4.matches()) {
                throw new InvalidInputException("Email is invalid");
            }



            String query1 = "INSERT INTO customers(first_name,last_name,phone_number,email) Values(?,?,?,?)";
            String query2 = "SELECT id FROM customers where first_name = ?";

            PreparedStatement preparedStatement;
            PreparedStatement preparedStatement1;

            try {
                preparedStatement = AccessDatabase.getInstance().getConnection().prepareStatement(query1);
                preparedStatement.setString(1, fName);
                preparedStatement.setString(2, lName);
                preparedStatement.setString(3, pNumber);
                preparedStatement.setString(4, eMail);
                preparedStatement.execute();
                preparedStatement1 = AccessDatabase.getInstance().getConnection().prepareStatement(query2);
                preparedStatement1.setString(1, fName);
                ResultSet rs = preparedStatement1.executeQuery();
                int id = 0;
                while (rs.next()) {
                    id = rs.getInt("id");
                }
                customers.add(new Customer(id, fName, lName, pNumber, eMail));
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
        catch(InvalidInputException e){
            Alert invalid = new Alert(Alert.AlertType.ERROR);
            invalid.setHeaderText("Invalid input");
            invalid.setContentText(e.getMessage());
            invalid.showAndWait();
           System.out.println(e.getMessage());
        }



        }


    }

