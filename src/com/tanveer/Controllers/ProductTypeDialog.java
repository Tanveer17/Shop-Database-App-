package com.tanveer.Controllers;

import com.tanveer.AccessDatabase;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProductTypeDialog {
    @FXML
    private TextField name;

    public void processData(){
        String query2  = "INSERT INTO product_types(name) VALUES(?)";
        PreparedStatement ps;
        try{
                ps = AccessDatabase.getInstance().getConnection().prepareStatement(query2);
                ps.setString(1,name.getText());
                ps.execute();
            }
            catch (SQLException s){
                s.printStackTrace();
            }
        }
    }


