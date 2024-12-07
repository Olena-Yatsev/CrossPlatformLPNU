package org.example.JavaNativeSerializer;

import org.example.Models.Salesman;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SalesmanSerializerTest {

    private SalesmanSerializer serializer;
    private String testFilename;

    @BeforeEach
    void setUp() {
        serializer = new SalesmanSerializer();
        testFilename = "test_salesmen.dat";
    }

    @AfterEach
    void tearDown() {
        File file = new File(testFilename);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void writeSalesmenToFileAndReadSalesmenFromFile() {
        Salesman salesman1 = new Salesman("Company A", LocalDate.of(2023, 5, 20));
        Salesman salesman2 = new Salesman("Company B", LocalDate.of(2022, 8, 15));

        List<Salesman> salesmenToWrite = new ArrayList<>();
        salesmenToWrite.add(salesman1);
        salesmenToWrite.add(salesman2);

        serializer.writeSalesmenToFile(salesmenToWrite, testFilename);

        List<Salesman> salesmenRead = serializer.readSalesmenFromFile(testFilename, salesmenToWrite.size());

        assertNotNull(salesmenRead);
        assertEquals(salesmenToWrite.size(), salesmenRead.size());

        Salesman readSalesman1 = salesmenRead.get(0);
        Salesman readSalesman2 = salesmenRead.get(1);

        assertEquals(salesman1.getCompanyName(), readSalesman1.getCompanyName());
        assertEquals(salesman1.getStartContractDate(), readSalesman1.getStartContractDate());

        assertEquals(salesman2.getCompanyName(), readSalesman2.getCompanyName());
        assertEquals(salesman2.getStartContractDate(), readSalesman2.getStartContractDate());
    }

    @Test
    void handleEmptyFile() {
        serializer.writeSalesmenToFile(new ArrayList<>(), testFilename);

        List<Salesman> salesmenRead = serializer.readSalesmenFromFile(testFilename, 0);

        assertNotNull(salesmenRead);
        assertTrue(salesmenRead.isEmpty());
    }

    @Test
    void handleFileNotFound() {
        String nonExistentFile = "non_existent_salesmen.dat";
        List<Salesman> salesmenRead = serializer.readSalesmenFromFile(nonExistentFile, 1);

        assertNotNull(salesmenRead);
        assertTrue(salesmenRead.isEmpty());
    }

    @Test
    void handleIncorrectCount() {
        Salesman salesman = new Salesman("Company A", LocalDate.of(2023, 5, 20));
        List<Salesman> salesmenToWrite = new ArrayList<>();
        salesmenToWrite.add(salesman);

        serializer.writeSalesmenToFile(salesmenToWrite, testFilename);

        List<Salesman> salesmenRead = serializer.readSalesmenFromFile(testFilename, 2);

        assertNotNull(salesmenRead);
        assertEquals(1, salesmenRead.size());
        assertEquals(salesman.getCompanyName(), salesmenRead.get(0).getCompanyName());
        assertEquals(salesman.getStartContractDate(), salesmenRead.get(0).getStartContractDate());
    }
}
