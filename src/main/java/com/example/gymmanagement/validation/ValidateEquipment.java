package com.example.gymmanagement.validation;

import java.math.BigDecimal;


public class ValidateEquipment {

    public static boolean isValidEquipmentName(String equipmentName) {
        return equipmentName != null && !equipmentName.isEmpty();
    }

    public static boolean isValidCategory(String category) {
        return category != null && !category.isEmpty();
    }

    public static boolean isValidPurchaseDate(String purchaseDate) {
        // Add your validation logic for purchase date here
        // For example, you can check if the input can be parsed as a valid date.
        return true; // Replace with your validation code
    }

    public static boolean isValidPrice(String priceStr) {
        if (priceStr == null || priceStr.isEmpty()) {
            return false;
        }

        try {
            BigDecimal price = new BigDecimal(priceStr);
            return price.compareTo(BigDecimal.ZERO) > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isValidStatus(String status) {
        // Add your validation logic for status here
        // For example, you can check if the status is one of the valid options.
        return true; // Replace with your validation code
    }

    // Add more validation methods as needed

}
