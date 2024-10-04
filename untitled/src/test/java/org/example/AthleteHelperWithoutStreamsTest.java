package org.example;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.Optional;
import java.util.*;

import static org.junit.Assert.*;

public class AthleteHelperWithoutStreamsTest {

    private AthleteHelperWithoutStreams athleteHelper;
    private Collection<Athlete> athletes;

    @Before
    public void setUp() {
        athleteHelper = new AthleteHelperWithoutStreams();

        athletes = Arrays.asList(
                new Athlete("John", "Doe", Arrays.asList("Basketball", "Football"), 10, 25),
                new Athlete("Mike", "Smith", Arrays.asList("Basketball", "Football"), 5, 30),
                new Athlete("Steve", "Johnson", Arrays.asList("Tennis"), 7, 28),
                new Athlete("Bob", "Brown", Collections.singletonList("Tennis"), 2, 22),
                new Athlete("Alice", "Davis", Collections.singletonList("Swimming"), 6, 20)
        );
    }

    @Test
    public void testDivideByRepresent() {
        List<Athlete> result = athleteHelper.divideByRepresent(athletes);
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(a -> a.getName().equals("John") && a.getSurname().equals("Doe")));
        assertTrue(result.stream().anyMatch(a -> a.getName().equals("Mike") && a.getSurname().equals("Smith")));
    }

    @Test
    public void testGroupBySport() {
        Map<String, List<Athlete>> result = athleteHelper.groupBySport(athletes);
        assertEquals(2, result.get("Basketball").size());
        assertEquals(2, result.get("Football").size());
        assertEquals(2, result.get("Tennis").size());
        assertEquals(1, result.get("Swimming").size());
    }

    @Test
    public void testMedalsMoreThan5() {
        List<Athlete> result = athleteHelper.medalsMoreThan5(athletes);
        assertEquals(3, result.size());
        assertTrue(result.stream().anyMatch(a -> a.getName().equals("John") && a.getSurname().equals("Doe")));
        assertTrue(result.stream().anyMatch(a -> a.getName().equals("Steve") && a.getSurname().equals("Johnson")));
        assertTrue(result.stream().anyMatch(a -> a.getName().equals("Alice") && a.getSurname().equals("Davis")));
    }

    @Test
    public void testSortByMedalsAndAge() {
        List<Athlete> result = athleteHelper.sortByMedalsAndAge(athletes);
        assertEquals("John", result.get(0).getName());
        assertEquals("Steve", result.get(1).getName());
        assertEquals("Alice", result.get(2).getName());

    }

    @Test
    public void testUniqueTypeOfSport() {
        Set<String> result = athleteHelper.uniqueTypeOfSport(athletes);
        assertEquals(1, result.size());
        assertTrue(result.contains("Swimming"));
    }

    @Test
    public void testFindBestAthlete() {
        Optional<Athlete> result = athleteHelper.findBestAthlete(athletes, "Basketball");
        assertTrue(result.isPresent());
        assertEquals("John", result.get().getName());
        assertEquals("Doe", result.get().getSurname());

        result = athleteHelper.findBestAthlete(athletes, "Tennis");
        assertTrue(result.isPresent());
        assertEquals("Steve", result.get().getName());
        assertEquals("Johnson", result.get().getSurname());
    }
}
