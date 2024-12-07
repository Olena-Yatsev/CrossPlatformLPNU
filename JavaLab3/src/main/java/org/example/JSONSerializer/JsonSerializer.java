package org.example.JSONSerializer;

import org.example.ISerialize;
import org.example.Models.Product;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.yaml.snakeyaml.serializer.Serializer;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonSerializer implements ISerialize {
    private final ObjectMapper objectMapper;

    public JsonSerializer() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    @Override
    public void writeProductsToFile(List<Product> products, String filename) {
        try {
            objectMapper.writeValue(new File(filename), products);
            System.out.println("\nProducts successfully written to " + filename + "\n");
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    @Override
    public List<Product> readProductsFromFile(String filename) {
        List<Product> products = null;
        try {
            products = objectMapper.readValue(new File(filename),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Product.class));
            System.out.println("\nProducts successfully read from " + filename + "\n");
        } catch (IOException e) {
            System.err.println("Error reading from file: " + e.getMessage());
        }
        return products;
    }
}
