package com.tanveer;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.Property;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;

import java.io.IOException;;
import java.util.Date;
import java.util.Optional;

public class Controller {
    @FXML
    private BorderPane parent;
    @FXML
    private ListView tables;
    @FXML
    private TableView customer;
    @FXML
    private TableView productType;
    @FXML
    private TableView product;
    @FXML
    private TableView orderTable;
    @FXML
    private TableView sales;


    public  void initialize()throws Exception {
        tables.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observableValue, Object o, Object t1) {
                    customer.setVisible(false);
                    productType.setVisible(false);
                    product.setVisible(false);
                    orderTable.setVisible(false);
                    sales.setVisible(false);
                    if(tables.getSelectionModel().getSelectedItem().equals("customers")) {
                        customer.setVisible(true);
                    }
                    else if(tables.getSelectionModel().getSelectedItem().equals("product_types")){
                        productType.setVisible(true);
                    }
                    else if(tables.getSelectionModel().getSelectedItem().equals("products")){
                        product.setVisible(true);
                    }
                    else if(tables.getSelectionModel().getSelectedItem().equals("orders")){
                        orderTable.setVisible(true);
                    }
                    else if(tables.getSelectionModel().getSelectedItem().equals("sales")){
                        sales.setVisible(true);
                    }
                }
        });
            parent.setStyle("-fx-background-color:white");
            showCustomerTable();
            makeOrders();
            makeProductTypeTable();
            makeProductTable();
            makeSalesTable();

            tables.setItems(AccessDatabase.getInstance().getTables());
            customer.setVisible(false);
            productType.setVisible(false);
            product.setVisible(false);
            orderTable.setVisible(false);
            sales.setVisible(false);
            tables.getSelectionModel().selectFirst();
            System.out.println("end block");

    }


    public void showCustomerTable() throws Exception{

            TableColumn<Customer, IntegerProperty> idColumn = new TableColumn<>("ID");
            idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            TableColumn<Customer, StringProperty> fnameColumn = new TableColumn<>("First Name");
            fnameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            TableColumn<Customer, StringProperty> lnameColumn = new TableColumn<>("Last Name");
            lnameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
            TableColumn<Customer, StringProperty> pnoColumn = new TableColumn<>("Phone Number");
            pnoColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
            TableColumn<Customer, StringProperty> emailColumn = new TableColumn<>("Email");
            emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

            customer.getColumns().addAll(idColumn,fnameColumn,lnameColumn,pnoColumn,emailColumn);
            customer.getItems().addAll(AccessDatabase.getInstance().getCustomers());

    }
    public void makeProductTypeTable()throws Exception{
        TableColumn<ProductType, IntegerProperty> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<ProductType, StringProperty> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productType.getColumns().addAll(idColumn,nameColumn);
        productType.getItems().addAll(AccessDatabase.getInstance().getProductTypes());
    }

    public void makeProductTable() throws Exception{
        TableColumn<Product, IntegerProperty> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Product, StringProperty> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Product,StringProperty> productTypeColumn = new TableColumn<>("Product Type");
        productTypeColumn.setCellValueFactory(new PropertyValueFactory<>("productType"));
        TableColumn<Product, DoubleProperty> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        TableColumn<Product, IntegerProperty> instockColumn = new TableColumn<>("Quantity");
        instockColumn.setCellValueFactory(new PropertyValueFactory<>("inStock"));

        product.getColumns().addAll(idColumn,nameColumn,productTypeColumn,priceColumn,instockColumn);
        product.getItems().addAll(AccessDatabase.getInstance().getProducts());
    }

    public void makeOrders() throws Exception{
        TableColumn<Order, IntegerProperty> idColumn = new TableColumn<>("Order ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Order, IntegerProperty> quantityColumn = new TableColumn<>("Quantity");
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        TableColumn<Order, Date> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        TableColumn<Order, StringProperty> customer_nameColumn = new TableColumn<>("Customer Name");
        customer_nameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        TableColumn<Order, StringProperty> productColumn = new TableColumn<>("Product");
        productColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        TableColumn<Order, StringProperty> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("productType"));
        TableColumn<Order, IntegerProperty> priceColumn = new TableColumn<>("TotalPrice");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        orderTable.getColumns().addAll(idColumn,quantityColumn,dateColumn,customer_nameColumn,productColumn,typeColumn,priceColumn);
        orderTable.getItems().addAll(AccessDatabase.getInstance().getOrders());
    }

    public void makeSalesTable(){
        TableColumn<Sale, IntegerProperty> idColumn = new TableColumn<>("Order ID");
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

        sales.getColumns().addAll(idColumn,customer_nameColumn,productColumn,typeColumn,dateColumn,quantityColumn);
        sales.getItems().addAll(AccessDatabase.getInstance().getSales());
    }

    public void addNewCustomer() throws Exception{
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(parent.getScene().getWindow());
        dialog.setHeaderText("this is used to add new Customer");
        dialog.setTitle("Add new Todo item");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("newCustomerDialog.fxml"));
        try{
            System.out.println("couldnt load the dialogue");
            dialog.getDialogPane().setContent(fxmlLoader.load());

        }
        catch(IOException e){
            System.out.println(e);
            return;
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent()&&result.get() ==ButtonType.OK){
            System.out.println("if block");
            DialogController dialogController = fxmlLoader.getController();
            dialogController.processData();
            for(int i =0;i<customer.getItems().size();i++){
                customer.getColumns().clear();
                customer.getItems().clear();
            }
            showCustomerTable();
        }


    }

    public BorderPane getParent() {
        return parent;
    }

}



