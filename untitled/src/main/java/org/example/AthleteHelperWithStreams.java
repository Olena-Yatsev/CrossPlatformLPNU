package org.example;

import java.util.*;
import java.util.stream.Collectors;

public class AthleteHelperWithStreams implements AthleteOperations {

    @Override
    public List<Athlete> divideByRepresent(Collection<Athlete> athletes) {
        return athletes.stream()
                .filter(athlete -> athlete.getSports().size() > 1)
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, List<Athlete>> groupBySport(Collection<Athlete> athletes) {
        return athletes.stream()
                .flatMap(athlete -> athlete.getSports().stream().map(sport -> new AbstractMap.SimpleEntry<>(sport, athlete)))
                .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.mapping(Map.Entry::getValue, Collectors.toList())));
    }

    @Override
    public List<Athlete> medalsMoreThan5(Collection<Athlete> athletes) {
        return athletes.stream()
                .filter(athlete -> athlete.getMedals() > 5)
                .collect(Collectors.toList());
    }

    @Override
    public List<Athlete> sortByMedalsAndAge(Collection<Athlete> athletes) {
        return athletes.stream()
                .sorted(Comparator.comparingInt(Athlete::getMedals).thenComparingInt(Athlete::getAge).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public Set<String> uniqueTypeOfSport(Collection<Athlete> athletes) {
        Map<String, Long> sportCount = athletes.stream()
                .flatMap(athlete -> athlete.getSports().stream())
                .collect(Collectors.groupingBy(sport -> sport, Collectors.counting()));

        Set<String> uniqueSportsSet = sportCount.entrySet().stream()
                .filter(entry -> entry.getValue() == 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());

        return uniqueSportsSet;
    }

    @Override
    public Optional<Athlete> findBestAthlete(Collection<Athlete> athletes, String sport) {
        return athletes.stream()
                .filter(athlete -> athlete.getSports().stream()
                        .anyMatch(s -> s.equalsIgnoreCase(sport)))
                .max(Comparator.comparingInt(Athlete::getMedals));
    }

}
