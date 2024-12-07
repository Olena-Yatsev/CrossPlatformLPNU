package org.example.JavaNativeSerializer;

import org.example.Models.Salesman;

import javax.lang.model.type.NullType;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SalesmanSerializer {

    public void writeSalesmenToFile(List<Salesman> salesmen, String filename) {
        try (FileOutputStream file = new FileOutputStream(filename);
             BufferedOutputStream buffer = new BufferedOutputStream(file);
             DataOutputStream out = new DataOutputStream(buffer)) {

            for (Salesman salesman : salesmen) {
                out.writeUTF(salesman.getCompanyName());
                out.writeUTF(salesman.getStartContractDate().toString());
            }
        } catch (IOException e) {
            System.err.println("Error writing salesmen to file: " + e.getMessage());
        }
    }

    public List<Salesman> readSalesmenFromFile(String filename, int count) {
        List<Salesman> salesmen = new ArrayList<>();
        try (FileInputStream file = new FileInputStream(filename);
             BufferedInputStream buffer = new BufferedInputStream(file);
             DataInputStream in = new DataInputStream(buffer)) {

            for (int i = 0; i < count; i++) {
                String companyName = in.readUTF();
                LocalDate startDate = LocalDate.parse(in.readUTF());
                salesmen.add(new Salesman(companyName, startDate));
            }
        } catch (IOException e) {
            System.err.println("Error reading salesmen from file: " + e.getMessage());
        }
        return salesmen;
    }
}
