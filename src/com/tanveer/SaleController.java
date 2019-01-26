package com.tanveer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import java.sql.*;


public class SaleController {
    @FXML
    private TextField id;

    @FXML
    private ComboBox firstName;

    @FXML
    private ComboBox lastName;

    @FXML
    private ComboBox product;

    @FXML
    private ComboBox productType;

    @FXML
    private DatePicker date;

    @FXML
    private TextField quantity;

    public void setFirstName(){
        ObservableList<String> firstNameList = FXCollections.observableArrayList();
        try {
            Statement st = AccessDatabase.getInstance().getConnection().createStatement();
            ResultSet rs = st.executeQuery("SELECT first_name FROM customers");
            while(rs.next()){
                String fName = rs.getString("first_name");
                firstNameList.add(fName);

            }
            firstName.setItems(firstNameList);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void setLastName(){
        ObservableList<String> lastNameList = FXCollections.observableArrayList();
        try {
            Statement st = AccessDatabase.getInstance().getConnection().createStatement();
            ResultSet rs = st.executeQuery("SELECT last_name FROM customers");
            while(rs.next()){
                String lName = rs.getString("last_name");
                lastNameList.add(lName);

            }
            System.out.println("setfirsnam");
            lastName.setItems(lastNameList);
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

    public void setProductType(){
        ObservableList<String> productTypeList = FXCollections.observableArrayList();
        try {
            Statement st = AccessDatabase.getInstance().getConnection().createStatement();
            ResultSet rs = st.executeQuery("SELECT name FROM product_types");
            while(rs.next()) {
                String name = rs.getString("name");
                productTypeList.add(name);
            }
            productType.setItems(productTypeList);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void processData(){
        int saleId = 0;
        if(!this.id.getText().isEmpty()) {
            saleId = Integer.parseInt(this.id.getText());
        }
        int customerId = -1;
        int quantity =Integer.parseInt(this.quantity.getText());
        Date date = Date.valueOf(this.date.getValue());
        int productId = -1;
        try {
            PreparedStatement st1 = AccessDatabase.getInstance().getConnection().prepareStatement("SELECT id FROM customers WHERE first_name =?  " +
                                                                                                       "AND last_name = ? ");
            st1.setString(1,firstName.getSelectionModel().getSelectedItem().toString());
            st1.setString(2,lastName.getSelectionModel().getSelectedItem().toString());
            ResultSet rs1 = st1.executeQuery();
            if(rs1.next()){
                customerId = rs1.getInt("id");
            }

            st1 = AccessDatabase.getInstance().getConnection().prepareStatement("SELECT id FROM products where products.name = ?");
            st1.setString(1, product.getSelectionModel().getSelectedItem().toString());

            rs1 = st1.executeQuery();
            if(rs1.next()){
                productId = rs1.getInt("id");
            }
            String query ="INSERT INTO sales(id,quantity,date,product_id,customer_id) VALUES(?,?,?,?,?)";
            String query2 = "INSERT INTO sales(quantity,date,product_id,customer_id) VALUES(?,?,?,?)";


        if(saleId ==0){
            PreparedStatement statement = AccessDatabase.getInstance().getConnection().prepareStatement(query2);
            statement.setInt(1,quantity);
            statement.setDate(2,date);
            statement.setInt(3,productId);
            statement.setInt(4,customerId);

            statement.execute();

        }
        else {
            PreparedStatement statement = AccessDatabase.getInstance().getConnection().prepareStatement(query);
            statement.setInt(1, saleId);
            statement.setInt(2, quantity);
            statement.setDate(3, date);
            statement.setInt(4, productId);
            statement.setInt(5, customerId);

            statement.execute();
        }
        managingQuantity(quantity,productId);

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
