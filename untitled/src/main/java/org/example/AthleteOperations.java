package org.example;

import java.util.*;

public interface AthleteOperations {
    List<Athlete> divideByRepresent(Collection<Athlete> athletes);
    Map<String, List<Athlete>> groupBySport(Collection<Athlete> athletes);
    List<Athlete> medalsMoreThan5(Collection<Athlete> athletes);
    List<Athlete> sortByMedalsAndAge(Collection<Athlete> athletes);
    Set<String> uniqueTypeOfSport(Collection<Athlete> athletes);
    Optional<Athlete> findBestAthlete(Collection<Athlete> athletes, String sport);
}
