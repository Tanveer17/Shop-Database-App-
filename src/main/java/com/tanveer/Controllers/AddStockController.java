package com.tanveer.Controllers;

import com.tanveer.AccessDatabase;
import com.tanveer.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class AddStockController {
    @FXML
    private ComboBox product;
    @FXML
    private TextField quantity;

    public void initialize() {
        setProduct();
    }

    public void setProduct() {
        ObservableList<String> productList = FXCollections.observableArrayList();
        try {
            Statement st = AccessDatabase.getInstance().getConnection().createStatement();
            ResultSet rs = st.executeQuery("SELECT name FROM products");
            while (rs.next()) {
                String pName = rs.getString("name");
                productList.add(pName);

            }
            product.setItems(productList);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void processData() {
        int qua = Integer.parseInt(quantity.getText());
        String prodct = product.getSelectionModel().getSelectedItem().toString();
        String query = "UPDATE products SET instock = instock +? WHERE name = ? ";

        try {
            PreparedStatement ps = AccessDatabase.getInstance().getConnection().prepareStatement(query);
            ps.setInt(1, qua);
            ps.setString(2, prodct);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

