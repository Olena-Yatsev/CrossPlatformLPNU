package org.example;

import org.example.JSONSerializer.JsonSerializer;
import org.example.YAMLSerialization.YamlSerialization;
import org.example.JavaNativeSerializer.ProductSerializer;
import org.example.Models.Product;
import org.example.Models.Salesman;

import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Salesman> salesmen = createSalesmen();
        List<Product> products = createProducts(salesmen);

        handleNativeSerialization(products, "products.dat");
        handleJsonSerialization(products, "products.json");
        handleYamlSerialization(products, "products.yaml");
    }

    private static List<Salesman> createSalesmen() {
        return List.of(
                new Salesman("Company A", LocalDate.parse("2000-02-04")),
                new Salesman("Company B", LocalDate.parse("2024-02-04"))
        );
    }

    private static List<Product> createProducts(List<Salesman> salesmen) {
        return List.of(
                new Product("Product A", 100.0, 10, salesmen),
                new Product("Product B", 200.0, 5, salesmen)
        );
    }

    private static void handleNativeSerialization(List<Product> products, String filename) {
        ProductSerializer productSerializer = new ProductSerializer();
        productSerializer.writeProductsToFile(products, filename);

        List<Product> deserializedProducts = productSerializer.readProductsFromFile(filename);
        System.out.println("Native Serialized Products:");
        deserializedProducts.forEach(System.out::println);
    }

    private static void handleJsonSerialization(List<Product> products, String filename) {
        JsonSerializer jsonSerializer = new JsonSerializer();
        jsonSerializer.writeProductsToFile(products, filename);

        List<Product> deserializedProducts = jsonSerializer.readProductsFromFile(filename);
        System.out.println("JSON Serialized Products:");
        deserializedProducts.forEach(System.out::println);
    }

    private static void handleYamlSerialization(List<Product> products, String filename) {
        YamlSerialization yamlSerializer = new YamlSerialization();
        yamlSerializer.writeProductsToFile(products, filename);

        List<Product> deserializedProducts = yamlSerializer.readProductsFromFile(filename);
        if (deserializedProducts != null) {
            System.out.println("YAML Serialized Products:");
            deserializedProducts.forEach(System.out::println);
        }
    }
}
