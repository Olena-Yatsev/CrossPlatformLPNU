package org.example;

import java.util.*;

public class AthleteHelperWithoutStreams implements AthleteOperations {

    @Override
    public List<Athlete> divideByRepresent(Collection<Athlete> athletes) {
        List<Athlete> multipleSports = new ArrayList<>();
        for (Athlete athlete : athletes) {
            if (athlete.getSports().size() > 1) {
                multipleSports.add(athlete);
            }
        }
        return multipleSports;
    }

    @Override
    public Map<String, List<Athlete>> groupBySport(Collection<Athlete> athletes) {
        Map<String, List<Athlete>> sportsMap = new HashMap<>();
        for (Athlete athlete : athletes) {
            for (String sport : athlete.getSports()) {
                sportsMap.computeIfAbsent(sport, k -> new ArrayList<>()).add(athlete);
            }
        }
        return sportsMap;
    }

    @Override
    public List<Athlete> medalsMoreThan5(Collection<Athlete> athletes) {
        List<Athlete> result = new ArrayList<>();
        for (Athlete athlete : athletes) {
            if (athlete.getMedals() > 5) {
                result.add(athlete);
            }
        }
        return result;
    }

    @Override
    public List<Athlete> sortByMedalsAndAge(Collection<Athlete> athletes) {
        List<Athlete> athleteList = new ArrayList<>(athletes);
        for (int i = 0; i < athleteList.size(); i++) {
            for (int j = i + 1; j < athleteList.size(); j++) {
                if (athleteList.get(i).getMedals() < athleteList.get(j).getMedals()) {
                    Collections.swap(athleteList, i, j);
                } else if (athleteList.get(i).getMedals() == athleteList.get(j).getMedals() &&
                        athleteList.get(i).getAge() < athleteList.get(j).getAge()) {
                    Collections.swap(athleteList, i, j);
                }
            }
        }
        return athleteList;
    }

    @Override
    public Set<String> uniqueTypeOfSport(Collection<Athlete> athletes) {
        Map<String, Integer> sportCount = new HashMap<>();

        for (Athlete athlete : athletes) {
            for (String sport : athlete.getSports()) {
                sportCount.put(sport, sportCount.getOrDefault(sport, 0) + 1);
            }
        }

        Set<String> uniqueSports = new HashSet<>();
        for (Map.Entry<String, Integer> entry : sportCount.entrySet()) {
            if (entry.getValue() == 1) {
                uniqueSports.add(entry.getKey());
            }
        }

        return uniqueSports;
    }


    @Override
    public Optional<Athlete> findBestAthlete(Collection<Athlete> athletes, String sport) {
        Athlete bestAthlete = null;
        for (Athlete athlete : athletes) {
            for (String athleteSport : athlete.getSports()) {
                if (athleteSport.equalsIgnoreCase(sport)) {
                    if (bestAthlete == null || athlete.getMedals() > bestAthlete.getMedals()) {
                        bestAthlete = athlete;
                    }
                }
            }
        }
        return Optional.ofNullable(bestAthlete);
    }

}
