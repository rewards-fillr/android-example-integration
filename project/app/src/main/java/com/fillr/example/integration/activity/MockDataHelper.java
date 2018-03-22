package com.fillr.example.integration.activity;

import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.List;

public class MockDataHelper {

    @NonNull
    public static HashMap<String, String> assignMockProfileData(List<String> fields) {
        HashMap<String, String> profileData = new HashMap<>();
        HashMap<String, String> mockData = buildProfile();

        for (String field : fields) {
            if (mockData.containsKey(field)) {
                profileData.put(field, mockData.get(field));
            }
        }
        return profileData;
    }

    private static HashMap<String, String> buildProfile() {
        HashMap<String, String> profileData = new HashMap<>();
        profileData.put("PersonalDetails.Honorific", "Mr.");
        profileData.put("PersonalDetails.FirstName", "John");
        profileData.put("PersonalDetails.LastName", "Wick");

        profileData.put("Passwords.Password.Password", "password");

        profileData.put("PersonalDetails.Gender", "Male");

        profileData.put("PersonalDetails.BirthDate.Day", "01");
        profileData.put("PersonalDetails.BirthDate.Month", "11");
        profileData.put("PersonalDetails.BirthDate.Year", "1990");

        profileData.put("ContactDetails.CellPhones.CellPhone.Number", "0416414225");
        profileData.put("ContactDetails.Emails.Email.Address", "test@gmail.com");
        profileData.put("CreditCards.CreditCard.NameOnCard", "John Wick");

        profileData.put("CreditCards.CreditCard.Number", "411111111111111111");
        profileData.put("CreditCards.CreditCard.Type", "VISA");
        profileData.put("CreditCards.CreditCard.Expiry.Month", "19");
        profileData.put("CreditCards.CreditCard.Expiry.Year", "2020");
        profileData.put("CreditCards.CreditCard.CCV", "123");

        profileData.put("AddressDetails.HomeAddress.AddressLine1", "245 Chapel Street");
        profileData.put("AddressDetails.HomeAddress.AddressLine2", "Level 6");

        profileData.put("AddressDetails.HomeAddress.Suburb", "Prahran");
        profileData.put("AddressDetails.HomeAddress.PostalCode", "3181");
        profileData.put("AddressDetails.HomeAddress.AdministrativeArea", "Glen Iris");

        profileData.put("AddressDetails.BillingAddress.AddressLine1", "119 Atkinson Street");
        profileData.put("AddressDetails.BillingAddress.AddressLine2", "Unit 18");
        profileData.put("AddressDetails.BillingAddress.Suburb", "Oakleigh");
        profileData.put("AddressDetails.BillingAddress.PostalCode", "3166");
        profileData.put("AddressDetails.BillingAddress.AdministrativeArea", "Monash");

        profileData.put("AddressDetails.PostalAddress.AddressLine1", "501 Blackburn Road");
        profileData.put("AddressDetails.PostalAddress.AddressLine2", "This is addy 2");
        profileData.put("AddressDetails.PostalAddress.PostalCode", "3149");
        profileData.put("AddressDetails.PostalAddress.Suburb", "Mount Waverley");
        profileData.put("AddressDetails.PostalAddress.AdministrativeArea", "Monash City");

        return profileData;
    }
}
