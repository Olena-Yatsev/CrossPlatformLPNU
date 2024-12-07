package org.example.JSONSerializer;

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

class JsonSerializerTest {

    private JsonSerializer jsonSerializer;
    private String testFilename;

    @BeforeEach
    void setUp() {
        jsonSerializer = new JsonSerializer();
        testFilename = "test_products.json";
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

        Product product1 = new Product("Phone", 599.99, 5, salesmen);
        Product product2 = new Product("Laptop", 1299.49, 2, salesmen);

        List<Product> productsToWrite = new ArrayList<>();
        productsToWrite.add(product1);
        productsToWrite.add(product2);

        jsonSerializer.writeProductsToFile(productsToWrite, testFilename);

        List<Product> productsRead = jsonSerializer.readProductsFromFile(testFilename);

        assertNotNull(productsRead);
        assertEquals(productsToWrite.size(), productsRead.size());

        Product readProduct1 = productsRead.get(0);
        Product readProduct2 = productsRead.get(1);

        assertEquals(product1.getProductName(), readProduct1.getProductName());
        assertEquals(product1.getProductQuantity(), readProduct1.getProductQuantity());
        assertNull(readProduct1.getProductPrice());

        assertEquals(product2.getProductName(), readProduct2.getProductName());
        assertEquals(product2.getProductQuantity(), readProduct2.getProductQuantity());
        assertNull(readProduct2.getProductPrice());
    }

    @Test
    void handleEmptyList() {
        jsonSerializer.writeProductsToFile(new ArrayList<>(), testFilename);

        List<Product> productsRead = jsonSerializer.readProductsFromFile(testFilename);

        assertNotNull(productsRead);
        assertTrue(productsRead.isEmpty());
    }

    @Test
    void handleFileNotFound() {
        String nonExistentFile = "non_existent_products.json";
        List<Product> productsRead = jsonSerializer.readProductsFromFile(nonExistentFile);

        assertNull(productsRead);
    }

    @Test
    void validateSerializationFormat() {
        Product product = new Product("Tablet", 299.99, 10);
        List<Product> productsToWrite = Collections.singletonList(product);

        jsonSerializer.writeProductsToFile(productsToWrite, testFilename);

        File file = new File(testFilename);
        assertTrue(file.exists());
        assertTrue(file.length() > 0);
    }
}
