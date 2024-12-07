package org.example.YAMLSerialization;

import org.example.ISerialize;
import org.example.Models.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.*;
import java.util.List;

import org.example.Models.Salesman;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YamlSerialization implements ISerialize {

    private final Yaml yaml;

    public YamlSerialization() {
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK); // Для читабельності
        this.yaml = new Yaml(options);
    }

    @Override
    public void writeProductsToFile(List<Product> products, String filename) {
        List<Map<String, Object>> yamlProducts = new ArrayList<>();
        for (Product product : products) {
            Map<String, Object> yamlProduct = new HashMap<>();
            yamlProduct.put("name", product.getProductName());
            yamlProduct.put("price", product.getProductPrice());
            yamlProduct.put("quantity", product.getProductQuantity());

            List<Map<String, Object>> salesmanList = new ArrayList<>();
            for (Salesman salesman : product.getProductSalesman()) {
                Map<String, Object> yamlSalesman = new HashMap<>();
                yamlSalesman.put("companyName", salesman.getCompanyName());
                salesmanList.add(yamlSalesman);
            }
            yamlProduct.put("salesman", salesmanList);
            yamlProducts.add(yamlProduct);
        }

        try (FileWriter writer = new FileWriter(new File(filename))) {
            yaml.dump(yamlProducts, writer);
            System.out.println("\nProducts successfully written to " + filename + " in YAML format.\n");
        } catch (IOException e) {
            System.err.println("Error writing to YAML file: " + e.getMessage());
        }
    }

    @Override
    public List<Product> readProductsFromFile(String filename) {
        List<Product> products = null;
        try (FileReader reader = new FileReader(new File(filename))) {
            List<Map<String, Object>> yamlProducts = yaml.loadAs(reader, List.class);
            products = new ArrayList<>();

            for (Map<String, Object> yamlProduct : yamlProducts) {
                String name = (String) yamlProduct.get("name");
                Double price = (Double) yamlProduct.get("price");
                Integer quantity = (Integer) yamlProduct.get("quantity");

                List<Salesman> salesmen = new ArrayList<>();
                List<Map<String, Object>> yamlSalesmen = (List<Map<String, Object>>) yamlProduct.get("salesman");
                for (Map<String, Object> yamlSalesman : yamlSalesmen) {
                    String companyName = (String) yamlSalesman.get("companyName");
                    salesmen.add(new Salesman(companyName, null));
                }

                products.add(new Product(name, price, quantity, salesmen));
            }
            System.out.println("\nProducts successfully read from " + filename + " in YAML format.\n");
        } catch (IOException e) {
            System.err.println("Error reading from YAML file: " + e.getMessage());
        }
        return products;
    }
}
