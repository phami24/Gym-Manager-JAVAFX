package com.example.gymmanagement.validation;

public class ValidateGymClass {

    public static boolean isValidClassName(String className) {
        return className != null && !className.trim().isEmpty();
    }

    public static boolean isValidInstructor(String instructorName) {
        return instructorName != null && !instructorName.trim().isEmpty();
    }

    public static boolean isValidSchedule(String schedule) {
        return schedule != null && !schedule.trim().isEmpty();
    }

    public static boolean isValidCapacity(String capacityStr) {
        if (capacityStr == null || capacityStr.trim().isEmpty()) {
            return false;
        }
        try {
            int capacity = Integer.parseInt(capacityStr);
            return capacity > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
