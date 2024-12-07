package org.example.Models;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void testGetAndSetProductName() {
        Product product = new Product();
        product.setProductName("Laptop");
        assertEquals("Laptop", product.getProductName());
    }

    @Test
    void testGetAndSetProductPrice() {
        Product product = new Product();
        product.setProductPrice(999.99);
        assertEquals(999.99, product.getProductPrice());
    }

    @Test
    void testGetAndSetProductQuantity() {
        Product product = new Product();
        product.setProductQuantity(10);
        assertEquals(10, product.getProductQuantity());
    }

    @Test
    void testGetAndSetProductSalesman() {
        Salesman salesman1 = new Salesman("Company A", null);
        Salesman salesman2 = new Salesman("Company B", null);
        List<Salesman> salesmen = Arrays.asList(salesman1, salesman2);

        Product product = new Product();
        product.setProductSalesman(salesmen);
        assertEquals(salesmen, product.getProductSalesman());
    }

    @Test
    void testToString() {
        Salesman salesman = new Salesman("Company A", null);
        List<Salesman> salesmen = Collections.singletonList(salesman);

        Product product = new Product("Phone", 599.99, 5, salesmen);

        String expected = "\nProduct name: Phone" +
                "\nPrice: 599.99" +
                "\nQuantity: 5" +
                "\nSalesman: " + salesmen;

        assertEquals(expected, product.toString());
    }


    @Test
    void testConstructorWithAllFields() {
        Salesman salesman = new Salesman("Company C", null);
        List<Salesman> salesmen = Collections.singletonList(salesman);

        Product product = new Product("Tablet", 299.99, 3, salesmen);

        assertEquals("Tablet", product.getProductName());
        assertEquals(299.99, product.getProductPrice());
        assertEquals(3, product.getProductQuantity());
        assertEquals(salesmen, product.getProductSalesman());
    }

    @Test
    void testConstructorWithoutSalesman() {
        Product product = new Product("Monitor", 199.99, 7);

        assertEquals("Monitor", product.getProductName());
        assertEquals(199.99, product.getProductPrice());
        assertEquals(7, product.getProductQuantity());
        assertNull(product.getProductSalesman());
    }
}
