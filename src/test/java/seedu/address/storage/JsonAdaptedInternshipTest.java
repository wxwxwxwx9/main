package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedInternship.DATE_TIME_PATTERN;
import static seedu.address.storage.JsonAdaptedInternship.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalInternshipApplications.GOOGLE;

import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.internship.Address;
import seedu.address.model.internship.Company;
import seedu.address.model.internship.Email;
import seedu.address.model.internship.Phone;

public class JsonAdaptedInternshipTest {
    private static final String INVALID_COMPANY = "";
    private static final String INVALID_ROLE = "";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_APPLICATION_DATE = "20/20/2020";
    private static final String INVALID_PRIORITY = "11";
    private static final String INVALID_STATUS = "ALIVE";

    private static final String VALID_COMPANY = GOOGLE.getCompany().toString();
    private static final String VALID_ROLE = GOOGLE.getRole().toString();
    private static final String VALID_ADDRESS = GOOGLE.getAddress().toString();
    private static final String VALID_PHONE = GOOGLE.getPhone().toString();
    private static final String VALID_EMAIL = GOOGLE.getEmail().toString();
    private static final String VALID_APPLICATION_DATE =
            GOOGLE.getApplicationDate().format(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN));
    private static final String VALID_PRIORITY = GOOGLE.getPriority().toString();
    private static final String VALID_STATUS = GOOGLE.getStatus().toString();

    @Test
    public void toModelType_validInternshipDetails_returnsInternship() throws Exception {
        JsonAdaptedInternship internship = new JsonAdaptedInternship(GOOGLE);
        assertEquals(GOOGLE, internship.toModelType());
    }

    // WX: Fixed this for you Gerhean, check if the test case is as intended
     @Test
     public void toModelType_invalidCompany_throwsIllegalValueException() {
         JsonAdaptedInternship internship =
                 new JsonAdaptedInternship(INVALID_COMPANY, VALID_ROLE, VALID_ADDRESS,
                         INVALID_PHONE,
                         VALID_EMAIL, VALID_APPLICATION_DATE, VALID_PRIORITY, VALID_STATUS);
         String expectedMessage = Company.MESSAGE_CONSTRAINTS;
         assertThrows(IllegalValueException.class, expectedMessage, internship::toModelType);
     }

    // WX: Fixed this for you Gerhean, check if the test case is as intended
     @Test
     public void toModelType_nullCompany_throwsIllegalValueException() {
         JsonAdaptedInternship internship =
              new JsonAdaptedInternship(null, VALID_ROLE, VALID_ADDRESS,
                      INVALID_PHONE,
                      VALID_EMAIL, VALID_APPLICATION_DATE, VALID_PRIORITY, VALID_STATUS);
         String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Company.class.getSimpleName());
         assertThrows(IllegalValueException.class, expectedMessage, internship::toModelType);
     }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedInternship internship =
                new JsonAdaptedInternship(VALID_COMPANY, VALID_ROLE, VALID_ADDRESS,
                        INVALID_PHONE,
                        VALID_EMAIL, VALID_APPLICATION_DATE, VALID_PRIORITY, VALID_STATUS);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, internship::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedInternship internship =
                new JsonAdaptedInternship(VALID_COMPANY, VALID_ROLE, VALID_ADDRESS,
                        null,
                        VALID_EMAIL, VALID_APPLICATION_DATE, VALID_PRIORITY, VALID_STATUS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, internship::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedInternship internship =
                new JsonAdaptedInternship(VALID_COMPANY, VALID_ROLE, VALID_ADDRESS, VALID_PHONE,
                        INVALID_EMAIL,
                        VALID_APPLICATION_DATE, VALID_PRIORITY, VALID_STATUS);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, internship::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedInternship internship =
                new JsonAdaptedInternship(VALID_COMPANY, VALID_ROLE, VALID_ADDRESS, VALID_PHONE,
                        null,
                        VALID_APPLICATION_DATE, VALID_PRIORITY, VALID_STATUS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, internship::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedInternship internship = new JsonAdaptedInternship(VALID_COMPANY, VALID_ROLE,
                INVALID_ADDRESS,
                VALID_PHONE, VALID_EMAIL, VALID_APPLICATION_DATE, VALID_PRIORITY, VALID_STATUS);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, internship::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedInternship internship = new JsonAdaptedInternship(VALID_COMPANY, VALID_ROLE,
                null,
                VALID_PHONE, VALID_EMAIL, VALID_APPLICATION_DATE, VALID_PRIORITY, VALID_STATUS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, internship::toModelType);
    }


}
