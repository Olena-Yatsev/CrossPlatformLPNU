package org.example;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class AthleteHelperWithStreamsTest {

    private AthleteHelperWithStreams athleteHelper;
    private Collection<Athlete> athletes;

    @Before
    public void setUp() {
        athleteHelper = new AthleteHelperWithStreams();

        athletes = Arrays.asList(
                new Athlete("John", "Doe", Arrays.asList("Basketball", "Football"), 10, 25),
                new Athlete("Mike", "Smith", Arrays.asList("Basketball", "Football"), 5, 30),
                new Athlete("Steve", "Johnson", Arrays.asList("Tennis"), 7, 28),
                new Athlete("Bob", "Brown", Arrays.asList("Tennis"), 2, 22),
                new Athlete("Alice", "Davis", Arrays.asList("Swimming"), 6, 20)
        );
    }

    @Test
    public void testDivideByRepresentDifferentSize() {
        List<Athlete> result = athleteHelper.divideByRepresent(athletes);
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getName());
    }

    @Test
    public void testGroupBySportDifferentApproach() {
        Map<String, List<Athlete>> result = athleteHelper.groupBySport(athletes);
        assertFalse(result.isEmpty());
        assertTrue(result.containsKey("Basketball"));
        assertTrue(result.get("Football").stream().anyMatch(a -> a.getName().equals("Mike")));
    }

    @Test
    public void testMedalsMoreThan5DifferentAssert() {
        List<Athlete> result = athleteHelper.medalsMoreThan5(athletes);
        assertEquals(3, result.size());
        assertTrue(result.contains(result.get(0)));
    }

    @Test
    public void testSortByMedalsAndAgeDescending() {
        List<Athlete> result = athleteHelper.sortByMedalsAndAge(athletes);
        assertEquals(5, result.size());
        assertEquals("John", result.get(0).getName());
        assertEquals("Bob", result.get(4).getName());
    }

    @Test
    public void testUniqueTypeOfSportDifferentCondition() {
        Set<String> result = athleteHelper.uniqueTypeOfSport(athletes);
        assertEquals(1, result.size());
        assertTrue(result.contains("Swimming"));
    }

    @Test
    public void testFindBestAthleteCustomCheck() {
        Optional<Athlete> result = athleteHelper.findBestAthlete(athletes, "Basketball");
        assertTrue(result.isPresent());
        assertEquals("John", result.get().getName());
        assertFalse(result.get().getName().equals("Mike"));
    }
}
