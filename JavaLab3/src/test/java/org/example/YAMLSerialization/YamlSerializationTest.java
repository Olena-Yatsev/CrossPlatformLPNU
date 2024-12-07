package org.example.YAMLSerialization;

import org.example.Models.Product;
import org.example.Models.Salesman;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class YamlSerializationTest {

    private YamlSerialization yamlSerialization;
    private String testFilename;

    @BeforeEach
    void setUp() {
        yamlSerialization = new YamlSerialization();
        testFilename = "test_products.yaml";
    }

    @AfterEach
    void tearDown() {
        // Delete test file after each test
        File file = new File(testFilename);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void handleEmptyList() {
        yamlSerialization.writeProductsToFile(new ArrayList<>(), testFilename);
        List<Product> productsRead = yamlSerialization.readProductsFromFile(testFilename);

        assertNotNull(productsRead);
        assertTrue(productsRead.isEmpty());
    }

    @Test
    void handleFileNotFound() {
        String nonExistentFile = "non_existent_products.yaml";
        List<Product> productsRead = yamlSerialization.readProductsFromFile(nonExistentFile);

        assertNull(productsRead);
    }

    @Test
    void validateSerializationOutput() {
        Salesman salesman = new Salesman("Company A", null);
        Product product = new Product("Tablet", 299.99, 10, Collections.singletonList(salesman));

        List<Product> productsToWrite = Collections.singletonList(product);

        yamlSerialization.writeProductsToFile(productsToWrite, testFilename);

        File file = new File(testFilename);
        assertTrue(file.exists());
        assertTrue(file.length() > 0);
    }

    @Test
    void validateSalesmanSerialization() {
        Salesman salesman = new Salesman("Company B", null);
        Product product = new Product("Desktop", 899.99, 3, Collections.singletonList(salesman));

        List<Product> productsToWrite = Collections.singletonList(product);

        yamlSerialization.writeProductsToFile(productsToWrite, testFilename);

        List<Product> productsRead = yamlSerialization.readProductsFromFile(testFilename);

        assertNotNull(productsRead);
        assertEquals(1, productsRead.size());

        Product readProduct = productsRead.get(0);
        assertEquals(1, readProduct.getProductSalesman().size());
        Salesman readSalesman = readProduct.getProductSalesman().get(0);
        assertEquals(salesman.getCompanyName(), readSalesman.getCompanyName());
        assertNull(readSalesman.getStartContractDate());
    }
}
