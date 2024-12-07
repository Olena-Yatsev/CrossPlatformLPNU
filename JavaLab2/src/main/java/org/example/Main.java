package org.example;

import org.example.Models.User;
import org.example.UserScheduler.UserData;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<User> users = new ArrayList<>();

        User user1 = new User("london 07:00-11:15", ZoneId.of("Europe/Kiev"));
        user1.Add_Availability(DayOfWeek.valueOf("MONDAY"),
                LocalTime.parse("09:00"), LocalTime.parse("13:15"));
        users.add(user1);

        User user2 = new User("london 11:00-12:00", ZoneId.of("Europe/Berlin"));
        user2.Add_Availability(DayOfWeek.valueOf("MONDAY"),
                LocalTime.parse("12:00"), LocalTime.parse("13:00"));
        users.add(user2);

        User user3 = new User("london truly", ZoneId.of("Europe/London"));
        user3.Add_Availability(DayOfWeek.valueOf("MONDAY"),
                LocalTime.parse("10:30"), LocalTime.parse("11:10"));
        users.add(user3);

        UserData data = new UserData(users, scanner);

        boolean continueLoop = true;
        while (continueLoop) {
            helper();
            int action = scanner.nextInt();
            scanner.nextLine();
            switch (action) {
                case 1:
                    String res = data.AddUser(users, scanner);
                    System.out.println(res);
                    break;
                case 2:
                    res = data.AddAvailability(users,scanner);
                    System.out.println(res);
                    break;
                case 3:
                    if (users.isEmpty()) {
                        System.out.println("No users in the list.");
                    } else {
                        for (User u : users) {
                            System.out.println(u);
                        }
                    }
                    break;
                case 4:
                    if (users.isEmpty()) {
                        System.out.println("No users in the list.");
                        break;
                    }
                    res = data.FindBestTimeForMeeting(users,scanner);
                    System.out.println(res);
                    break;
                case 5:
                    res = data.FindLongestCommonTime(users);
                    System.out.println(res);
                    break;
                case 6:
                    continueLoop = false;
                    System.out.println("Happy end :) ");
                    break;

                default:
                    System.out.println("Invalid action");
                    break;
            }
        }

        scanner.close();
    }

    public static void helper(){
        System.out.println("\nEnter type of action ");
        System.out.println("1. Add a new user ");
        System.out.println("2. Add a new availability to user ");
        System.out.println("3. Print availability of users ");
        System.out.println("4. Find best time for meeting ");
        System.out.println("5. Find common availability ");
        System.out.println("6. Exit ");
    }
}