package org.example;

import java.util.List;

public class Athlete {

    public Athlete(String name, String surname, List<String> sports, int medals, int age) {
        this.name = name;
        this.surname = surname;
        this.sports = sports;
        this.medals = medals;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public List<String> getSports() {
        return sports;
    }

    public int getMedals() {
        return medals;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return name + " " + surname + " - " + sports + ", Medals: " + medals + ", Age: " + age;
    }

    private String name;
    private String surname;
    private List<String> sports;
    private int medals;
    private int age;
}
