package org.example.JavaNativeSerializer;

import org.example.ISerialize;
import org.example.Models.Product;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProductSerializer implements ISerialize {

    @Override
    public void writeProductsToFile(List<Product> products, String filename) {
        try (FileOutputStream file = new FileOutputStream(filename);
             ObjectOutputStream out = new ObjectOutputStream(file)) {

            for (Product product : products) {
                out.writeObject(product);
            }
        } catch (IOException e) {
            System.err.println("Error writing products to file: " + e.getMessage());
        }
    }

    @Override
    public List<Product> readProductsFromFile(String filename) {
        List<Product> products = new ArrayList<>();
        try (FileInputStream file = new FileInputStream(filename);
             ObjectInputStream in = new ObjectInputStream(file)) {

            while (true) {
                try {
                    Product product = (Product) in.readObject();
                    products.add(product);
                } catch (EOFException e) {
                    System.out.println("End of file reached");
                    break;
                } catch (ClassNotFoundException e) {
                    System.err.println("Class not found during deserialization: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading products from file: " + e.getMessage());
        }
        return products;
    }
}
