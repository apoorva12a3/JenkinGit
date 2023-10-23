package com.etl.cleaner;

import java.util.List;

import org.springframework.stereotype.Component;

import com.etl.model.Customer;

@Component
public class DataCleaner  {

    public List<Customer> cleanData(List<Customer> customers) {
        for (Customer customer : customers) {
        	
            // Remove leading and trailing whitespace from fields
            String cleanedFirstName = customer.getFirstName().trim();
            String cleanedLastName = customer.getLastName().trim();

            // Remove spaces within the first name and last name
            cleanedFirstName = cleanedFirstName.replaceAll("\\s+", "");
            cleanedLastName = cleanedLastName.replaceAll("\\s+", "");

            // Remove special characters from the first name and last name
            cleanedFirstName = cleanedFirstName.replaceAll("[^a-zA-Z]", "");
            cleanedLastName = cleanedLastName.replaceAll("[^a-zA-Z]", "");

            String cleanedContactNo = customer.getContactNo().trim();
            String cleanedEmailID = customer.getEmailID().trim();

            // Update the customer object with cleaned values
            customer.setFirstName(cleanedFirstName);
            customer.setLastName(cleanedLastName);
            customer.setContactNo(cleanedContactNo);
            customer.setEmailID(cleanedEmailID);

            // Clean customer data as needed
            // Convert FirstName and LastName to have the first letter capitalized
            customer.setFirstName(capitalizeFirstLetter(customer.getFirstName()));
            customer.setLastName(capitalizeFirstLetter(customer.getLastName()));

            // Convert email to lowercase
            String email = customer.getEmailID();
            if (email != null) {
                customer.setEmailID(email.toLowerCase());
                if (!email.equals(customer.getEmailID())) {
                    System.out.println("Customer " + customer.getCustomerID() + " has an email with capital letters. Corrected to lowercase.");
                }
            }

            // Add "+91" prefix to phone number
//            String phoneNumber = customer.getContactNo();
//            if (phoneNumber != null && !phoneNumber.startsWith("+91")) {
//                customer.setContactNo("+91 " + phoneNumber);
//            }
        }

        return customers;
    }

    // Helper method to capitalize the first letter of a string
    private String capitalizeFirstLetter(String str) {
        if (str != null && !str.isEmpty()) {
            return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
        }
        return str;
    }
}
