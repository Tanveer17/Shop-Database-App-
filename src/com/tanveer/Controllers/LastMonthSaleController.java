package com.tanveer.Controllers;

import com.tanveer.AccessDatabase;
import com.tanveer.entities.Sale;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class LastMonthSaleController {
    @FXML
    private TableView saleOfLastMonth;

    public void initialize(){
        makeSalesTable();

    }

    private void makeSalesTable(){
        TableColumn<Sale, IntegerProperty> idColumn = new TableColumn<>("Sale ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Sale, StringProperty> customer_nameColumn = new TableColumn<>("Customer Name");
        customer_nameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        TableColumn<Sale, StringProperty> productColumn = new TableColumn<>("Product");
        productColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        TableColumn<Sale, StringProperty> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("productType"));
        TableColumn<Sale, Date> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        TableColumn<Sale, IntegerProperty> quantityColumn = new TableColumn<>("Quantity");
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        saleOfLastMonth.getColumns().addAll(idColumn,customer_nameColumn,productColumn,typeColumn,dateColumn,quantityColumn);
        saleOfLastMonth.getItems().addAll(getSales());
    }


    public ObservableList<Sale> getSales()
    {
        ObservableList sales = FXCollections.observableArrayList();
        try
        {
            Statement st = AccessDatabase.getInstance().getConnection().createStatement();
            ResultSet rs = st.executeQuery("SELECT sales.id,CONCAT(customers.first_name,' ',customers.last_name) AS 'customer_name',products.name,product_types.name,sales.date,sales.quantity" +
                    " FROM sales INNER JOIN customers ON sales.customer_id = customers.id" +
                    " INNER JOIN products ON sales.product_id = products.id " +
                    " INNER JOIN product_types ON products.product_type_id =product_types.id WHERE MONTH(sales.date) = MONTH(now())-1");


            while (rs.next()) {
                int id = rs.getInt("sales.id");
                String customer = rs.getString("customer_name");
                String product = rs.getString("products.name");
                String type = rs.getString("product_types.name");
                java.sql.Date date = rs.getDate("sales.date");
                int quantity = rs.getInt("sales.quantity");

                sales.add(new Sale(id, customer, product, type, date, quantity));
            }
            return sales;
        } catch (SQLException s)
        {
            s.printStackTrace();

        }
        return null;

    }
}
