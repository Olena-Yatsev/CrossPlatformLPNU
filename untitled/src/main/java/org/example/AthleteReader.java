package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AthleteReader {

    private static List<Athlete> readAthletesFromFile(String fileName) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(fileName));

        return lines.stream()
                .map(line -> {
                    String[] parts = line.split(",");

                    String name = parts[0];
                    String surname = parts[1];

                    List<String> sports = Arrays.asList(parts[2].split(";"));
                    int medals = Integer.parseInt(parts[3]);
                    int age = Integer.parseInt(parts[4]);

                    return new Athlete(name, surname, sports, medals, age);
                }).collect(Collectors.toList());
    }

    public static List<Athlete> getArrayListAthletes(String fileName) throws IOException {
        return readAthletesFromFile(fileName);
    }

    public static Set<Athlete> getHashSetAthletes(String fileName) throws IOException {
        return new HashSet<>(readAthletesFromFile(fileName));
    }
}


