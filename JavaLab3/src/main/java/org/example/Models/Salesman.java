package org.example.Models;

//import org.yaml.snakeyaml.extensions.annotation.YamlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.time.LocalDate;

public class Salesman implements Serializable {

    private String CompanyName;

    //@JsonIgnore
    private LocalDate StartContractDate;

    public Salesman() {
    }
    public Salesman(String CompanyName, LocalDate StartContractDate) {
        this.CompanyName = CompanyName;
        this.StartContractDate = StartContractDate;
    }

    public String getCompanyName() {
        return CompanyName;
    }
    public void setCompanyName(String CompanyName) {
        this.CompanyName = CompanyName;
    }
    public LocalDate getStartContractDate() {
        return StartContractDate;
    }
    public void setStartContractDate(LocalDate StartContractDate) {
        this.StartContractDate = StartContractDate;
    }

    @Override
    public String toString() {
        return "CompanyName: " + CompanyName +
                ", \nStartContractDate: " + StartContractDate + "\n";
    }
}
