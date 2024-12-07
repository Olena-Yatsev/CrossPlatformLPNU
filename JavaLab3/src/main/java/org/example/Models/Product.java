package org.example.Models;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Product implements Serializable {
    private String productName;

    @JsonIgnore
    private transient Double productPrice;

    private Integer productQuantity;

    private transient List<Salesman> productSalesman;

    public Product(){
    }

    public Product(String productName, Double productPrice, Integer productQuantity, List<Salesman> productSalesman) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.productSalesman = productSalesman;
    }
    public Product(String productName, Double productPrice, Integer productQuantity) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public Double getProductPrice() {
        return productPrice;
    }
    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }
    public Integer getProductQuantity() {
        return productQuantity;
    }
    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }
    public List<Salesman> getProductSalesman() {
        return productSalesman;
    }
    public void setProductSalesman(List<Salesman> productSalesman) {
        this.productSalesman = productSalesman;
    }

    @Override
    public String toString() {
        return "\nProduct name: " + productName +
                "\nPrice: " + productPrice +
                "\nQuantity: " + productQuantity +
                "\nSalesman: " + productSalesman;
    }
}
