package org.example.UserScheduler;

import org.example.Models.Meeting;
import org.example.Models.User;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.*;

public class UserData {
    private List<User> users;
    private Scanner scanner;

    public UserData(List<User> userss, Scanner scanner) {
        users = userss;
        this.scanner = scanner;
    }

    public String AddUser(List<User> users, Scanner scanner) {
        System.out.print("Enter user name: ");
        String userName = scanner.nextLine().trim();

        if (userName.isEmpty()) {
            return "Error: User name cannot be empty.";
        }

        System.out.print("Enter user's time zone (e.g., UTC, Europe/Kiev): ");
        String timeZoneInput = scanner.nextLine().trim();

        ZoneId userTimeZone;
        try {
            userTimeZone = ZoneId.of(timeZoneInput);
        } catch (DateTimeException e) {
            return "Error: Invalid time zone. Please enter a valid time zone (e.g., UTC, Europe/Kiev).";
        }

        User user = new User(userName, userTimeZone);
        users.add(user);
        return "Successfully added user " + userName;
    }

    public String AddAvailability(List<User> users, Scanner scanner) {
        if (users.isEmpty()) {
            return "Error: No users available to add availability.";
        }

        System.out.println("Choose user from list: ");
        for (int i = 0; i < users.size(); i++) {
            System.out.println((i + 1) + " " + users.get(i).toString());
        }

        System.out.print("Enter the user number: ");
        int numOfUser;
        try {
            numOfUser = scanner.nextInt();
            scanner.nextLine();
            if (numOfUser < 1 || numOfUser > users.size()) {
                return "Error: User number out of range.";
            }
        } catch (InputMismatchException e) {
            scanner.nextLine();
            return "Error: Please enter a valid number for user selection.";
        }

        System.out.print("Choose the day of the week (e.g., Monday): ");
        DayOfWeek dayOfWeek;
        SelectDayOfWeek();
        int day = Integer.valueOf(scanner.nextLine().trim());
        switch (day) {
            case 1:
                dayOfWeek = DayOfWeek.MONDAY;
                break;
            case 2:
                dayOfWeek = DayOfWeek.TUESDAY;
                break;
            case 3:
                dayOfWeek = DayOfWeek.WEDNESDAY;
                break;
            case 4:
                dayOfWeek = DayOfWeek.THURSDAY;
                break;
            case 5:
                dayOfWeek = DayOfWeek.FRIDAY;
                break;
            case 6:
                dayOfWeek = DayOfWeek.SATURDAY;
                break;
            case 7:
                dayOfWeek = DayOfWeek.SUNDAY;
                break;
            default:
                System.out.println("Invalid day of the week. Please enter a valid day");
                return "Error: Please enter a valid day.";
        }

        System.out.print("Enter a start time (e.g., 12:00): ");
        LocalTime startTime;
        try {
            startTime = LocalTime.parse(scanner.nextLine().trim());
        } catch (DateTimeParseException e) {
            return "Error: Invalid time format for start time! Please use HH:mm.";
        }

        System.out.print("Enter an end time (e.g., 22:00): ");
        LocalTime endTime;
        try {
            endTime = LocalTime.parse(scanner.nextLine().trim());
        } catch (DateTimeParseException e) {
            return "Error: Invalid time format for end time! Please use HH:mm.";
        }

        if (!endTime.isAfter(startTime)) {
            return "Error: End time must be after start time.";
        }

        users.get(numOfUser - 1).Add_Availability(dayOfWeek, startTime, endTime);
        return "Successfully added availability!";
    }


    public String FindBestTimeForMeeting(List<User> users, Scanner scanner) {
        System.out.print("Enter required meeting duration in min: ");
        int duration = scanner.nextInt();
        scanner.nextLine();

        MeetingScheduler meetingScheduler = new MeetingScheduler(users);
        String str = meetingScheduler.findBestTimeForMeeting(duration);
        return str;
    }

    public String FindLongestCommonTime(List<User> users) {
        MeetingScheduler meetingScheduler = new MeetingScheduler(users);
        Map<DayOfWeek, Long> longestCommonTimes = meetingScheduler.calculateLongestCommonAvailability(users);

        StringBuilder result = new StringBuilder();

        if (longestCommonTimes.isEmpty()) {
            result.append("No common available time found.");
        } else {
            for (Map.Entry<DayOfWeek, Long> entry : longestCommonTimes.entrySet()) {
                DayOfWeek day = entry.getKey();
                long commonTime = entry.getValue();
                result.append(day)
                        .append(": ")
                        .append(commonTime)
                        .append(" minutes\n");
            }
        }

        return result.toString();
    }
    public static void SelectDayOfWeek(){
        System.out.println("Choose day of week: ");
        System.out.println("1 - Monday");
        System.out.println("2 - Tuesday");
        System.out.println("3 - Wednesday");
        System.out.println("4 - Thursday");
        System.out.println("5 - Friday");
        System.out.println("6 - Saturday");
        System.out.println("7 - Sunday");
    }

}
