package com.tanveer;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProductTypeDialog {
    @FXML
    private TextField id;

    @FXML
    private TextField name;

     void processData(){
        int id = -1;
        if(!this.id.getText().isEmpty()){
            id = Integer.parseInt(this.id.getText());
        }
        String query  = "INSERT INTO product_types(id,name) VALUES(?,?)";
        String query2  = "INSERT INTO product_types(name22) VALUES(?)";
        PreparedStatement ps;
        if(id != -1) {
            try {
                ps = AccessDatabase.getInstance().getConnection().prepareStatement(query);
                ps.setInt(1,id);
                ps.setString(2,name.getText());
                ps.execute();
            }
            catch(SQLException s){
                s.printStackTrace();
            }
        }
        else{
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

}
