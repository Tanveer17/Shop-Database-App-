package com.tanveer.Controllers;

import com.tanveer.AccessDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import java.sql.*;
import java.time.LocalDate;


public class SaleController {
    @FXML
    private ComboBox name;

    @FXML
    private ComboBox product;

    @FXML
    private TextField quantity;

    public void initialize(){
        setName();
        setProductName();

    }

    public void setName(){
        ObservableList<String> nameList = FXCollections.observableArrayList();
        try {
            Statement st = AccessDatabase.getInstance().getConnection().createStatement();
            ResultSet rs = st.executeQuery("SELECT concat(first_name,' ',last_name) AS name FROM customers");
            while(rs.next()){
                String fName = rs.getString("name");
                nameList.add(fName);

            }
            name.setItems(nameList);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }




    public void setProductName(){
        ObservableList<String> productNameList = FXCollections.observableArrayList();
        try {
            Statement st = AccessDatabase.getInstance().getConnection().createStatement();
            ResultSet rs = st.executeQuery("SELECT name FROM products");
            while(rs.next()){
                String name = rs.getString("name");
                productNameList.add(name);

            }
            product.setItems(productNameList);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void processData(){
        int customerId = -1;
        int instock = 0;
        int quantity =Integer.parseInt(this.quantity.getText());
        Date date = Date.valueOf(LocalDate.now());
        int productId = -1;
        try {
            PreparedStatement st1 = AccessDatabase.getInstance().getConnection().prepareStatement("SELECT id FROM customers WHERE first_name =?  " +
                                                                                                       "AND last_name = ? ");
            String[] fullName = name.getSelectionModel().getSelectedItem().toString().split(" ");
            st1.setString(1,fullName[0]);
            st1.setString(2,fullName[1]);
            ResultSet rs1 = st1.executeQuery();
            if(rs1.next()){
                customerId = rs1.getInt("id");
            }

            st1 = AccessDatabase.getInstance().getConnection().prepareStatement("SELECT id,instock FROM products where products.name = ?");
            st1.setString(1, product.getSelectionModel().getSelectedItem().toString());


            rs1 = st1.executeQuery();
            if(rs1.next()){
                productId = rs1.getInt("id");
                instock = rs1.getInt("instock");
            }
            String query2 = "INSERT INTO sales(quantity,sale_date,product_id,customer_id) VALUES(?,?,?,?)";

        if(instock>0 && instock - quantity >=0) {
            PreparedStatement statement = AccessDatabase.getInstance().getConnection().prepareStatement(query2);
            statement.setInt(1, quantity);
            statement.setDate(2, date);
            statement.setInt(3, productId);
            statement.setInt(4, customerId);

            statement.execute();


            managingQuantity(quantity, productId);
        }
        else{
            Alert invalid = new Alert(Alert.AlertType.WARNING);
            invalid.setHeaderText("Out Of Stock");
            invalid.setContentText(product.getSelectionModel().getSelectedItem().toString()+" is out of Stock");
            invalid.showAndWait();
        }

        }
        catch (SQLException s){
            s.printStackTrace();
        }


    }

    private void managingQuantity(int quantity, int productId) {
        System.out.println(productId);
        if (productId != -1) {
            System.out.println("managing");
            String query = "UPDATE products SET instock = instock-? WHERE id = ?";
            try {
                PreparedStatement p = AccessDatabase.getInstance().getConnection().prepareStatement(query);
                p.setInt(1, quantity);
                p.setInt(2, productId);
                p.execute();
            } catch (SQLException s) {
                s.printStackTrace();
            }
        }
    }
}
