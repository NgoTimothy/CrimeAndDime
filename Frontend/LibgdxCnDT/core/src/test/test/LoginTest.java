package test;

import org.junit.Test;

import java.util.regex.Pattern;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LoginTest {

    @Test
    public void checkValidPhoneNumberContainsOnlyDigits() {
        Pattern pattern = Pattern.compile(".*[^0-9].*");
        String phoneNumber = "8401236487";
        assertTrue(!pattern.matcher(phoneNumber).matches());
    }

    @Test
    public void checkInvalidPhoneNumber() {
        Pattern pattern = Pattern.compile(".*[^0-9].*");
        String phoneNumber = "840d1236487";
        assertTrue(pattern.matcher(phoneNumber).matches());
    }
}
