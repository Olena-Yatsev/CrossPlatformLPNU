package org.example;

import org.example.Models.Product;

import java.util.List;

public interface ISerialize {
    public void writeProductsToFile(List<Product> products, String filename);
    public List<Product> readProductsFromFile(String filename);
}
