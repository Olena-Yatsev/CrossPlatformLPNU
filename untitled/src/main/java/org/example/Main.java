package org.example;

import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        String fileName = "athletes.txt";

        List<Athlete> athletesList = AthleteReader.getArrayListAthletes(fileName);
        Set<Athlete> athletesSet = AthleteReader.getHashSetAthletes(fileName);

        AthleteOperations withoutStreams = new AthleteHelperWithoutStreams();
        AthleteOperations withStreams = new AthleteHelperWithStreams();

        Scanner scanner = new Scanner(System.in);
        int taskNumber;

        do {
            PrintMenu();

            taskNumber = scanner.nextInt();
            scanner.nextLine();

            switch (taskNumber) {
                case 1:
                    // Task 1: Divide by sport representation
                    System.out.println("\n\nWithout Streams:");
                    List<Athlete> multipleSportsWithoutStreams = withoutStreams.divideByRepresent(athletesList);
                    multipleSportsWithoutStreams.forEach(System.out::println);

                    System.out.println("\nWith Streams:");
                    List<Athlete> multipleSportsWithStreams = withStreams.divideByRepresent(athletesSet);
                    multipleSportsWithStreams.forEach(System.out::println);
                    break;

                case 2:
                    // Task 2: Group by sport
                    System.out.println("\n\nWithout Streams:");
                    Map<String, List<Athlete>> groupedWithoutStreams = withoutStreams.groupBySport(athletesList);
                    groupedWithoutStreams.forEach((sport, athleteList) -> {
                        System.out.print("\n" + sport + " - ");
                        for (Athlete athlete : athleteList) {
                            System.out.print(athlete.getName() + " " + athlete.getSurname() + ", ");
                        }
                    });

                    System.out.println("\nWith Streams:");
                    Map<String, List<Athlete>> groupedWithStreams = withStreams.groupBySport(athletesSet);
                    groupedWithStreams.forEach((sport, athleteList) -> {
                        System.out.print("\n" + sport + " - ");
                        athleteList.forEach(athlete -> System.out.print(athlete.getName()
                                + " " + athlete.getSurname() + ", "));
                    });
                    break;

                case 3:
                    // Task 3: Filter athletes with more than 5 medals
                    System.out.println("\n\nWithout Streams:");
                    List<Athlete> athletesMoreThan5MedalsWithoutStreams = withoutStreams.medalsMoreThan5(athletesList);
                    athletesMoreThan5MedalsWithoutStreams.forEach(System.out::println);

                    System.out.println("\nWith Streams:");
                    List<Athlete> athletesMoreThan5MedalsWithStreams = withStreams.medalsMoreThan5(athletesSet);
                    athletesMoreThan5MedalsWithStreams.forEach(System.out::println);
                    break;

                case 4:
                    // Task 4: Sort by medals and age
                    System.out.println("\n\nWithout Streams:");
                    List<Athlete> sortedWithoutStreams = withoutStreams.sortByMedalsAndAge(athletesList);
                    sortedWithoutStreams.forEach(System.out::println);

                    System.out.println("\nWith Streams:");
                    List<Athlete> sortedWithStreams = withStreams.sortByMedalsAndAge(athletesSet);
                    sortedWithStreams.forEach(System.out::println);
                    break;

                case 5:
                    // Task 5: Find unique type of sport
                    System.out.println("\n\nWithout Streams:");
                    Set<String> uniqueSportsWithoutStreams = withoutStreams.uniqueTypeOfSport(athletesList);
                    uniqueSportsWithoutStreams.forEach(System.out::println);

                    System.out.println("\nWith Streams:");
                    Set<String> uniqueSportsWithStreams = withStreams.uniqueTypeOfSport(athletesSet);
                    uniqueSportsWithStreams.forEach(System.out::println);
                    break;

                case 6:
                    // Task 6: Find the best athlete
                    System.out.print("\n\nEnter your favorite sport: ");
                    String favoriteSport = scanner.nextLine();

                    System.out.println("\n\nWithout Streams:");
                    Optional<Athlete> bestAthleteWithoutStreams = withoutStreams.findBestAthlete(athletesList, favoriteSport);
                    bestAthleteWithoutStreams.ifPresentOrElse(
                            athlete -> System.out.println("Best athlete for " + favoriteSport + ": " + athlete.getName() + " " + athlete.getSurname()),
                            () -> System.out.println("No athlete found for " + favoriteSport)
                    );

                    System.out.println("\nWith Streams:");
                    Optional<Athlete> bestAthleteWithStreams = withStreams.findBestAthlete(athletesSet, favoriteSport);
                    bestAthleteWithStreams.ifPresentOrElse(
                            athlete -> System.out.println("Best athlete for " + favoriteSport + ": " + athlete.getName() + " " + athlete.getSurname()),
                            () -> System.out.println("No athlete found for " + favoriteSport)
                    );
                    break;

                case 0:
                    System.out.println("\n\nSuccess. End of program.");
                    break;

                default:
                    System.out.println("\n\nSomething went wrong. Try again.");
                    break;
            }

            System.out.println("\n");
        } while (taskNumber != 0);

        scanner.close();
    }

    private static void PrintMenu(){
        System.out.println("\ntask 1: Divide by sport representation");
        System.out.println("task 2: Group by sport");
        System.out.println("task 3: Filter athletes with more than 5 medals");
        System.out.println("task 4: Sort by medals and age");
        System.out.println("task 5: Find unique type of sport");
        System.out.println("task 6: Find the best athlete");
        System.out.println("Choose num of task or type '0' to close program:");
    }
}
