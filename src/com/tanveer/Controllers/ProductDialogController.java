package com.tanveer.Controllers;

import com.tanveer.AccessDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ProductDialogController {
    @FXML
    private TextField name;

    @FXML
    private ComboBox productType;

    @FXML
    private TextField price;

    @FXML
    private TextField quantity;

    public void initialize(){
        setProductType();
    }


    public void setProductType(){
        ObservableList<String> productTypes = FXCollections.observableArrayList();
        try {
            Statement st = AccessDatabase.getInstance().getConnection().createStatement();
            ResultSet rs = st.executeQuery("SELECT name FROM product_types");
            while(rs.next()){
                String productName = rs.getString("name");

                productTypes.add(productName);
            }


        }
        catch (SQLException e){
            e.printStackTrace();
        }

        productType.setItems(productTypes);
    }

    public void processData(){
        int productTypeId = -1;
        String que = "SELECT id FROM product_types WHERE name = ?";
        PreparedStatement ps;
        try {
           ps = AccessDatabase.getInstance().getConnection().prepareStatement(que);
            ps.setString(1, productType.getSelectionModel().getSelectedItem().toString());
            ResultSet resultSet = ps.executeQuery();
            if(resultSet.next()){
                productTypeId = resultSet.getInt("id");
            }

        }
        catch (SQLException e){
            e.printStackTrace();
        }


        String query = "INSERT INTO products(name,instock,price,product_type_id) VALUES(?,?,?,?)";
        try {
            ps = AccessDatabase.getInstance().getConnection().prepareStatement(query);
            ps.setString(1,name.getText());
            ps.setInt(2,Integer.parseInt(quantity.getText()));
            ps.setDouble(3,Double.parseDouble(price.getText()));
            ps.setInt(4,productTypeId);
            ps.execute();
        }
        catch (SQLException s){
            s.printStackTrace();
        }

    }

}
