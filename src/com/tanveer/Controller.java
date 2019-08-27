package com.tanveer;

import com.tanveer.Controllers.*;
import com.tanveer.entities.*;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
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


    public void initialize() throws Exception {
        tables.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                customer.setVisible(false);
                productType.setVisible(false);
                product.setVisible(false);
                orderTable.setVisible(false);
                sales.setVisible(false);
                if (tables.getSelectionModel().getSelectedItem().equals("customers".toUpperCase())) {
                    customer.setVisible(true);
                } else if (tables.getSelectionModel().getSelectedItem().equals("product_types".toUpperCase())) {
                    productType.setVisible(true);
                } else if (tables.getSelectionModel().getSelectedItem().equals("products".toUpperCase())) {
                    product.setVisible(true);
                } else if (tables.getSelectionModel().getSelectedItem().equals("orders".toUpperCase())) {
                    orderTable.setVisible(true);
                } else if (tables.getSelectionModel().getSelectedItem().equals("sales".toUpperCase())) {
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

        new Thread (() ->{
            try {
                tables.setItems(AccessDatabase.getInstance().getTables());
            }
            catch (Exception e){

            }
        }).start();

       // customer.setVisible(false);
        productType.setVisible(false);
        product.setVisible(false);
        orderTable.setVisible(false);
        sales.setVisible(false);
        tables.getSelectionModel().selectFirst();
        System.out.println("end block");

    }


    private void showCustomerTable() throws Exception {
        ObservableList<Customer> list;

        TableColumn<Customer, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Customer, StringProperty> fnameColumn = new TableColumn<>("First Name");
        fnameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        TableColumn<Customer, StringProperty> lnameColumn = new TableColumn<>("Last Name");
        lnameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        TableColumn<Customer, StringProperty> pnoColumn = new TableColumn<>("Phone Number");
        pnoColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        TableColumn<Customer, StringProperty> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        AccessDatabase.getInstance().makeCustomersList();
        list  = AccessDatabase.getInstance().getCustomers();

        customer.getColumns().addAll(idColumn, fnameColumn, lnameColumn, pnoColumn, emailColumn);
        customer.getItems().addAll(list);

        list.addListener(new ListChangeListener() {
            @Override
            public void onChanged(ListChangeListener.Change change){
            customer.getItems().add(list.get(list.size() - 1));
        }
        });


//        task = new Task<ObservableList<Customer>>() {
//            @Override
//            protected ObservableList<Customer> call() throws Exception {
//                System.out.println(Thread.currentThread());
//                return AccessDatabase.getInstance().getCustomers();
//            }
//        };
//
//       customer.itemsProperty().bind(task.valueProperty());
//       new Thread(task,"customer").start();


    }

    private void makeProductTypeTable() throws Exception {
        TableColumn<ProductType, IntegerProperty> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<ProductType, StringProperty> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productType.getColumns().addAll(idColumn, nameColumn);
        productType.getItems().addAll(AccessDatabase.getInstance().getProductTypes());
    }

    private void makeProductTable() throws Exception {
        TableColumn<Product, IntegerProperty> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Product, StringProperty> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Product, StringProperty> productTypeColumn = new TableColumn<>("Product Type");
        productTypeColumn.setCellValueFactory(new PropertyValueFactory<>("productType"));
        TableColumn<Product, DoubleProperty> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        TableColumn<Product, IntegerProperty> instockColumn = new TableColumn<>("Quantity");
        instockColumn.setCellValueFactory(new PropertyValueFactory<>("inStock"));

        product.getColumns().addAll(idColumn, nameColumn, productTypeColumn, priceColumn, instockColumn);
        product.getItems().addAll(AccessDatabase.getInstance().getProducts());
    }

    private void makeOrders() throws Exception {
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

        orderTable.getColumns().addAll(idColumn, quantityColumn, dateColumn, customer_nameColumn, productColumn, typeColumn, priceColumn);
        orderTable.getItems().addAll(AccessDatabase.getInstance().getOrders());
        System.out.println(Thread.currentThread());
    }

    private void makeSalesTable() {
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

        sales.getColumns().addAll(idColumn, customer_nameColumn, productColumn, typeColumn, dateColumn, quantityColumn);
        sales.getItems().addAll(AccessDatabase.getInstance().getSales());
    }

    @FXML
    public void addNewCustomer() throws Exception {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(parent.getScene().getWindow());
        dialog.setHeaderText("Enter the Customer Data");
        dialog.setTitle("Add new Customer");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("fxml/newCustomerDialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());

        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        CustomerDialogController customerDialogController = fxmlLoader.getController();
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.out.println("if block");
            customerDialogController.processData();

           // customer.getColumns().clear();
//            for (int i = 0; i < customer.getItems().size(); i++) {
//
//                customer.getItems().clear();
//            }
//            showCustomerTable();
        }
        System.out.println("end of customr add");


    }

    @FXML
    public void makeSale() throws Exception {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(parent.getScene().getWindow());
        dialog.setHeaderText("This is used to Make sales");
        dialog.setTitle("Sale an item");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("fxml/saleDialog.fxml"));
        try {

            dialog.getDialogPane().setContent(fxmlLoader.load());

        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            SaleController dialogController = fxmlLoader.getController();
            dialogController.processData();
            for (int i = 0; i < sales.getItems().size(); i++) {
                sales.getItems().clear();

            }
            for (int i = 0; i < product.getItems().size(); i++) {
                product.getItems().clear();
            }

            sales.getItems().addAll(AccessDatabase.getInstance().getSales());
            product.getItems().addAll(AccessDatabase.getInstance().getProducts());

        }


    }

    @FXML
    public void addProductType() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(parent.getScene().getWindow());
        dialog.setHeaderText("Enter Product Type");
        dialog.setTitle("Add new Product Type");

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("fxml/productTypeDialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException i) {
            i.printStackTrace();
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            ProductTypeDialog productTypeDialog = fxmlLoader.getController();
            productTypeDialog.processData();
            productType.getColumns().clear();
            for (int i = 0; i < productType.getItems().size(); i++) {
                productType.getItems().clear();

            }
            try {
                makeProductTypeTable();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    @FXML
    public void lastMonthSale() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("fxml/lastmonthsale.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root, 700, 400));
            LocalDate lastMonth = LocalDate.now().minusMonths(1);
            String month = lastMonth.getMonth().toString();
            int year = lastMonth.getYear();
            stage.setTitle(month + " " + year + " Sales");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void addProduct() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(parent.getScene().getWindow());
        dialog.setHeaderText("Enter new Product data ");
        dialog.setTitle("Add new Product");

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("fxml/addProductDialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException i) {
            i.printStackTrace();
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            ProductDialogController productTypeDialog = fxmlLoader.getController();
            productTypeDialog.processData();


        }
        for (int i = 0; i < product.getItems().size(); i++) {
            product.getItems().clear();
        }
        try {
            product.getItems().addAll(AccessDatabase.getInstance().getProducts());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @FXML
    public void addStock() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(parent.getScene().getWindow());
        dialog.setHeaderText("Enter Product name And Quantity ");
        dialog.setTitle("Add Quantity");

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("fxml/addStockDialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException i) {
            i.printStackTrace();
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            AddStockController addStockController = fxmlLoader.getController();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    addStockController.processData();


                    for (int i = 0; i < product.getItems().size(); i++) {
                        product.getItems().clear();
                    }
                    try {
                        product.getItems().addAll(AccessDatabase.getInstance().getProducts());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }).start();

        }
    }
}



