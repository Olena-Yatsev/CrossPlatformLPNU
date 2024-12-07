package org.example.Models;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class SalesmanTest {

    @Test
    void getCompanyName() {
        Salesman salesman = new Salesman("Company A", LocalDate.of(2023, 5, 20));
        assertEquals("Company A", salesman.getCompanyName());
    }

    @Test
    void setCompanyName() {
        Salesman salesman = new Salesman();
        salesman.setCompanyName("Company B");
        assertEquals("Company B", salesman.getCompanyName());
    }

    @Test
    void getStartContractDate() {
        LocalDate date = LocalDate.of(2022, 12, 1);
        Salesman salesman = new Salesman("Company A", date);
        assertEquals(date, salesman.getStartContractDate());
    }

    @Test
    void setStartContractDate() {
        Salesman salesman = new Salesman();
        LocalDate date = LocalDate.of(2024, 1, 1);
        salesman.setStartContractDate(date);
        assertEquals(date, salesman.getStartContractDate());
    }

    @Test
    void testToString() {
        LocalDate date = LocalDate.of(2023, 3, 15);
        Salesman salesman = new Salesman("Company C", date);
        String expected = "CompanyName: Company C, \nStartContractDate: 2023-03-15\n";
        assertEquals(expected, salesman.toString());
    }
}
