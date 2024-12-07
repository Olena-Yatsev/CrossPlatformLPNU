package org.example.JavaNativeSerializer;

import org.example.Models.Product;
import org.example.Models.Salesman;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductSerializerTest {

    private ProductSerializer serializer;
    private String testFilename;

    @BeforeEach
    void setUp() {
        serializer = new ProductSerializer();
        testFilename = "test_products.ser";
    }

    @AfterEach
    void tearDown() {
        File file = new File(testFilename);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void writeProductsToFileAndReadProductsFromFile() {
        Salesman salesman = new Salesman("Company A", LocalDate.of(2023, 5, 20));
        List<Salesman> salesmen = Collections.singletonList(salesman);

        Product product1 = new Product("Laptop", 999.99, 10, salesmen);
        Product product2 = new Product("Smartphone", 499.99, 20, salesmen);

        List<Product> productsToWrite = new ArrayList<>();
        productsToWrite.add(product1);
        productsToWrite.add(product2);

        serializer.writeProductsToFile(productsToWrite, testFilename);

        List<Product> productsRead = serializer.readProductsFromFile(testFilename);

        assertNotNull(productsRead);
        assertEquals(productsToWrite.size(), productsRead.size());
        assertEquals(productsToWrite.get(0).getProductName(), productsRead.get(0).getProductName());
        assertEquals(productsToWrite.get(1).getProductQuantity(), productsRead.get(1).getProductQuantity());
    }

    @Test
    void handleEmptyFile() {
        serializer.writeProductsToFile(new ArrayList<>(), testFilename);
        List<Product> productsRead = serializer.readProductsFromFile(testFilename);

        assertNotNull(productsRead);
        assertTrue(productsRead.isEmpty());
    }

    @Test
    void handleFileNotFound() {
        String nonExistentFile = "non_existent_file.ser";
        List<Product> productsRead = serializer.readProductsFromFile(nonExistentFile);

        assertNotNull(productsRead);
        assertTrue(productsRead.isEmpty());
    }
}
