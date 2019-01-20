package com.tanveer;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Date;

public class Sale {
    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty customerName = new SimpleStringProperty();
    private StringProperty productName = new SimpleStringProperty();
    private StringProperty productType = new SimpleStringProperty();
    private Date date;
    private IntegerProperty quantity = new SimpleIntegerProperty();

  
    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getCustomerName() {
        return customerName.get();
    }

    public StringProperty customerNameProperty() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName.set(customerName);
    }

    public String getProductName() {
        return productName.get();
    }

    public StringProperty productNameProperty() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName.set(productName);
    }

    public String getProductType() {
        return productType.get();
    }

    public StringProperty productTypeProperty() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType.set(productType);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getquantity() {
        return quantity.get();
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }

    public void setquantity(int quantity) {
        this.quantity.set(quantity);
    }

    public Sale(int id, String customerName, String productName, String productType, Date date, Integer quantity) {
        setId(id);
        setCustomerName(customerName);
        setProductName(productName);
        setProductType(productType);
        setDate(date);
        setquantity(quantity);
    }

}
