package testData;

import org.openqa.selenium.WebDriver;
import pageObjects.SharedActions;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class NewCustomerData {
    SharedActions sharedActions = new SharedActions(null);
    String emailExt = sharedActions.randomString();

    public final String customerGender = "Male";
    public final String customerFirstname = "John";
    public final String customerLastname = "Dow";
    public final String customerEmail = "john.dow@"+emailExt+".com";
    public final String customerDob = "1 January 1990";
    public final String customerCompanyName = "Test Company";
    public final String customerPassword = "password";

    public NewCustomerData(){}

    public String getDay() {
        LocalDate date = parseDob();
        return String.valueOf(date.getDayOfMonth());
    }

    public String getMonth() {
        LocalDate date = parseDob();
        return date.getMonth().toString();
    }

    public String getYear() {
        LocalDate date = parseDob();
        return String.valueOf(date.getYear());
    }

    private LocalDate parseDob() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.ENGLISH);
        return LocalDate.parse(customerDob, formatter);
    }

}
