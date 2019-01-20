package com.tanveer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.*;
import java.time.LocalDate;

public class AccessDatabase {
    private Connection connection;
    private ObservableList<String> tables;
    private static AccessDatabase instance = new AccessDatabase();


    public static AccessDatabase getInstance() {
        System.out.println("ASd");
        return instance;

    }

    public void setClass(String driverClass) throws Exception {
        Class.forName(driverClass);
    }

    public boolean login(String userName, String password) {
        try {
            instance.connection = DriverManager.getConnection("jdbc:mysql://localhost/shop_app", userName, password);
            return true;
        } catch (SQLException s) {
            Alert invalid = new Alert(Alert.AlertType.ERROR);
            invalid.setHeaderText("Invalid Password Or User Name");
            invalid.setContentText("please enter the right password or user Name");
            invalid.showAndWait();
            return false;

        }
    }

    public ObservableList<String> getTables() throws Exception {
        DatabaseMetaData metaData = connection.getMetaData();
        Statement st = connection.createStatement();
        ResultSet rs1 = metaData.getTables(null, null, null, new String[]{"TABLE"});
        ;
        tables = FXCollections.observableArrayList();
        while (rs1.next()) {
            tables.addAll(rs1.getString("TABLE_NAME"));
        }
        return tables;
    }

    public Connection getConnection() {
        return connection;
    }

    public ObservableList<Customer> getCustomers() throws Exception {
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM customers");
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        while (rs.next()) {
            int id = rs.getInt("id");
            String fname = rs.getString("first_name");
            String lname = rs.getString("last_name");
            String pno = rs.getString("phone_number");
            String email = rs.getString("email");
            customers.add(new Customer(id, fname, lname, pno, email));
        }
        return customers;
    }

    public ObservableList<ProductType> getProductTypes() throws Exception {
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM product_types");
        ObservableList<ProductType> productTypes = FXCollections.observableArrayList();
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            productTypes.add(new ProductType(id, name));
        }
        return productTypes;
    }

    public ObservableList<Product> getProducts() throws Exception {
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery("SELECT products.id,products.name,product_types.name,price,instock FROM products INNER JOIN product_types ON products.product_type_id = product_types.id");
        ObservableList<Product> products = FXCollections.observableArrayList();
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String productName = rs.getString("product_types.name");
            double price = rs.getDouble("price");
            int inStock = rs.getInt("instock");

            products.add(new Product(id, name, productName, price, inStock));
        }

        return products;
    }

    public ObservableList<Order> getOrders() throws Exception {
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery("SELECT orders.id, quantity,date,CONCAT(customers.first_name,' ' ,customers.last_name) AS 'customer_name', products.name, product_types.name, quantity*products.price AS 'price' " +
                "FROM orders INNER JOIN products ON orders.product_id=products.id " +
                "INNER JOIN customers ON orders.customer_id=customers.id " +
                "INNER JOIN product_types ON product_types.id=products.product_type_id");
        ObservableList<Order> orders = FXCollections.observableArrayList();
        while (rs.next()) {
            int id = rs.getInt("orders.id");
            int quantity = rs.getInt("quantity");
            Date date = rs.getDate("date");
            String customerName = rs.getString("customer_name");
            String productName = rs.getString("products.name");
            String productType = rs.getString("product_types.name");
            int price = rs.getInt("price");
            orders.add(new Order(id, quantity, date, productName, customerName, productType, price));

        }
        return orders;
    }

    public ObservableList<Sale> getSales() {
        ObservableList sales = FXCollections.observableArrayList();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT sales.id,CONCAT(customers.first_name,' ',customers.last_name) AS 'customer_name',products.name,product_types.name,sales.date,sales.quantity" +
                    " FROM sales INNER JOIN customers ON sales.customer_id = customers.id" +
                    " INNER JOIN products ON sales.product_id = products.id " +
                    " INNER JOIN product_types ON products.product_type_id =product_types.id");


            while (rs.next()) {
                int id = rs.getInt("sales.id");
                String customer = rs.getString("customer_name");
                String product = rs.getString("products.name");
                String type = rs.getString("product_types.name");
                Date date = rs.getDate("saless.date");
                int quantity = rs.getInt("sales.quanity");

                sales.add(new Sale(id, customer, product, type, date, quantity));
                return sales;

            }
        } catch (SQLException s) {
            s.printStackTrace();

        }
        return sales;

    }
}
