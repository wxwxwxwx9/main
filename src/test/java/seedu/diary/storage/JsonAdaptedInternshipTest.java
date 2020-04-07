package seedu.diary.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.diary.storage.JsonAdaptedInternship.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.diary.testutil.Assert.assertThrows;
import static seedu.diary.testutil.TypicalInternshipApplications.GOOGLE;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.diary.commons.core.Messages;
import seedu.diary.commons.exceptions.IllegalValueException;
import seedu.diary.commons.util.BooleanUtil;
import seedu.diary.model.internship.Address;
import seedu.diary.model.internship.ApplicationDate;
import seedu.diary.model.internship.Company;
import seedu.diary.model.internship.Email;
import seedu.diary.model.internship.Phone;
import seedu.diary.model.internship.Priority;
import seedu.diary.model.internship.Role;
import seedu.diary.model.status.Status;

public class JsonAdaptedInternshipTest {
    private static final String INVALID_COMPANY = "";
    private static final String INVALID_ROLE = "";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_PHONE = "-651234";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_APPLICATION_DATE = "20/20/2020";
    private static final String INVALID_PRIORITY = "11";
    private static final String INVALID_STATUS = "ALIVE";
    private static final String INVALID_IS_ARCHIVED = "s";

    private static final String VALID_COMPANY = GOOGLE.getCompany().toString();
    private static final String VALID_ROLE = GOOGLE.getRole().toString();
    private static final String VALID_ADDRESS = GOOGLE.getAddress().toString();
    private static final String VALID_PHONE = GOOGLE.getPhone().toString();
    private static final String VALID_EMAIL = GOOGLE.getEmail().toString();
    private static final String VALID_APPLICATION_DATE =
        GOOGLE.getApplicationDate().toString();
    private static final String VALID_PRIORITY = GOOGLE.getPriority().toString();
    private static final String VALID_STATUS = GOOGLE.getStatus().toString();
    private static final List<JsonAdaptedInterview> VALID_INTERVIEWS = new ArrayList<>();
    private static final String VALID_IS_ARCHIVED = GOOGLE.isArchived().toString();
    private static final String VALID_LAST_STAGE = null;

    private static final Path TEST_DATA_FOLDER =
        Paths.get("src", "test", "data", "JsonAdaptedInternshipTest");
    private static final Path TYPICAL_INTERNSHIPS_FILE =
        TEST_DATA_FOLDER.resolve("typicalInterviewInternship.json");
    private static final Path INVALID_INTERNSHIP_FILE =
        TEST_DATA_FOLDER.resolve("invalidInterviewInternship.json");
    private static final Path DUPLICATE_INTERNSHIP_FILE =
        TEST_DATA_FOLDER.resolve("duplicateInterviewInternship.json");

    @Test
    public void toModelType_validInternshipDetails_returnsInternship() throws Exception {
        JsonAdaptedInternship internship = new JsonAdaptedInternship(GOOGLE);
        assertEquals(GOOGLE, internship.toModelType());
    }

    @Test
    public void toModelType_invalidCompany_throwsIllegalValueException() {
        JsonAdaptedInternship internship =
            new JsonAdaptedInternship(INVALID_COMPANY,
                VALID_ROLE, VALID_ADDRESS, VALID_PHONE, VALID_EMAIL,
                VALID_APPLICATION_DATE, VALID_PRIORITY, VALID_STATUS, VALID_INTERVIEWS, VALID_IS_ARCHIVED,
                VALID_LAST_STAGE);
        String expectedMessage = Company.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, internship::toModelType);
    }

    @Test
    public void toModelType_nullCompany_throwsIllegalValueException() {
        JsonAdaptedInternship internship =
            new JsonAdaptedInternship(null,
                VALID_ROLE, VALID_ADDRESS, VALID_PHONE, VALID_EMAIL,
                VALID_APPLICATION_DATE, VALID_PRIORITY, VALID_STATUS, VALID_INTERVIEWS, VALID_IS_ARCHIVED,
                VALID_LAST_STAGE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Company.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, internship::toModelType);
    }

    @Test
    public void toModelType_invalidRole_throwsIllegalValueException() {
        JsonAdaptedInternship internship =
            new JsonAdaptedInternship(VALID_COMPANY,
                INVALID_ROLE,
                VALID_ADDRESS, VALID_PHONE, VALID_EMAIL,
                VALID_APPLICATION_DATE, VALID_PRIORITY, VALID_STATUS, VALID_INTERVIEWS, VALID_IS_ARCHIVED,
                VALID_LAST_STAGE);
        String expectedMessage = Role.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, internship::toModelType);
    }

    @Test
    public void toModelType_nullRole_throwsIllegalValueException() {
        JsonAdaptedInternship internship =
            new JsonAdaptedInternship(VALID_COMPANY,
                null, VALID_ADDRESS, VALID_PHONE, VALID_EMAIL,
                VALID_APPLICATION_DATE, VALID_PRIORITY, VALID_STATUS, VALID_INTERVIEWS, VALID_IS_ARCHIVED,
                VALID_LAST_STAGE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Role.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, internship::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedInternship internship =
            new JsonAdaptedInternship(VALID_COMPANY, VALID_ROLE, VALID_ADDRESS,
                INVALID_PHONE,
                VALID_EMAIL, VALID_APPLICATION_DATE,
                VALID_PRIORITY, VALID_STATUS, VALID_INTERVIEWS, VALID_IS_ARCHIVED, VALID_LAST_STAGE);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, internship::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedInternship internship =
            new JsonAdaptedInternship(VALID_COMPANY, VALID_ROLE, VALID_ADDRESS,
                null,
                VALID_EMAIL, VALID_APPLICATION_DATE,
                VALID_PRIORITY, VALID_STATUS, VALID_INTERVIEWS, VALID_IS_ARCHIVED, VALID_LAST_STAGE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, internship::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedInternship internship =
            new JsonAdaptedInternship(VALID_COMPANY, VALID_ROLE, VALID_ADDRESS, VALID_PHONE,
                INVALID_EMAIL,
                VALID_APPLICATION_DATE, VALID_PRIORITY, VALID_STATUS, VALID_INTERVIEWS, VALID_IS_ARCHIVED,
                VALID_LAST_STAGE);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, internship::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedInternship internship =
            new JsonAdaptedInternship(VALID_COMPANY, VALID_ROLE, VALID_ADDRESS, VALID_PHONE,
                null,
                VALID_APPLICATION_DATE, VALID_PRIORITY, VALID_STATUS, VALID_INTERVIEWS, VALID_IS_ARCHIVED,
                VALID_LAST_STAGE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, internship::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedInternship internship = new JsonAdaptedInternship(VALID_COMPANY, VALID_ROLE,
            INVALID_ADDRESS,
            VALID_PHONE, VALID_EMAIL, VALID_APPLICATION_DATE,
            VALID_PRIORITY, VALID_STATUS, VALID_INTERVIEWS, VALID_IS_ARCHIVED, VALID_LAST_STAGE);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, internship::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedInternship internship = new JsonAdaptedInternship(VALID_COMPANY, VALID_ROLE,
            null,
            VALID_PHONE, VALID_EMAIL, VALID_APPLICATION_DATE,
            VALID_PRIORITY, VALID_STATUS, VALID_INTERVIEWS, VALID_IS_ARCHIVED, VALID_LAST_STAGE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, internship::toModelType);
    }

    @Test
    public void toModelType_invalidApplicationDate_throwsIllegalValueException() {
        JsonAdaptedInternship internship = new JsonAdaptedInternship(VALID_COMPANY, VALID_ROLE,
            VALID_ADDRESS, VALID_PHONE, VALID_EMAIL,
            INVALID_APPLICATION_DATE,
            VALID_PRIORITY, VALID_STATUS, VALID_INTERVIEWS, VALID_IS_ARCHIVED, VALID_LAST_STAGE);
        String expectedMessage = ApplicationDate.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, internship::toModelType);
    }

    @Test
    public void toModelType_nullApplicationDate_throwsIllegalValueException() {
        JsonAdaptedInternship internship = new JsonAdaptedInternship(VALID_COMPANY, VALID_ROLE,
            VALID_ADDRESS, VALID_PHONE, VALID_EMAIL,
            null,
            VALID_PRIORITY, VALID_STATUS, VALID_INTERVIEWS, VALID_IS_ARCHIVED, VALID_LAST_STAGE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, ApplicationDate.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, internship::toModelType);
    }

    @Test
    public void toModelType_invalidPriority_throwsIllegalValueException() {
        JsonAdaptedInternship internship = new JsonAdaptedInternship(VALID_COMPANY, VALID_ROLE,
            VALID_ADDRESS, VALID_PHONE, VALID_EMAIL, VALID_APPLICATION_DATE,
            INVALID_PRIORITY,
            VALID_STATUS, VALID_INTERVIEWS, VALID_IS_ARCHIVED, VALID_LAST_STAGE);
        String expectedMessage = Priority.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, internship::toModelType);
    }

    @Test
    public void toModelType_nullPriority_throwsIllegalValueException() {
        JsonAdaptedInternship internship = new JsonAdaptedInternship(VALID_COMPANY, VALID_ROLE,
            VALID_ADDRESS, VALID_PHONE, VALID_EMAIL, VALID_APPLICATION_DATE,
            null,
            VALID_STATUS, VALID_INTERVIEWS, VALID_IS_ARCHIVED, VALID_LAST_STAGE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Priority.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, internship::toModelType);
    }

    @Test
    public void toModelType_invalidStatus_throwsIllegalValueException() {
        JsonAdaptedInternship internship = new JsonAdaptedInternship(VALID_COMPANY, VALID_ROLE,
            VALID_ADDRESS, VALID_PHONE, VALID_EMAIL, VALID_APPLICATION_DATE, VALID_PRIORITY,
            INVALID_STATUS,
            VALID_INTERVIEWS, VALID_IS_ARCHIVED, VALID_LAST_STAGE);
        String expectedMessage = Status.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, internship::toModelType);
    }

    @Test
    public void toModelType_nullStatus_throwsIllegalValueException() {
        JsonAdaptedInternship internship = new JsonAdaptedInternship(VALID_COMPANY, VALID_ROLE,
            VALID_ADDRESS, VALID_PHONE, VALID_EMAIL, VALID_APPLICATION_DATE, VALID_PRIORITY,
            null,
            VALID_INTERVIEWS, VALID_IS_ARCHIVED, VALID_LAST_STAGE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Status.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, internship::toModelType);
    }

    @Test
    public void toModelType_invalidIsArchived_throwsIllegalValueException() {
        JsonAdaptedInternship internship = new JsonAdaptedInternship(VALID_COMPANY, VALID_ROLE,
            VALID_ADDRESS, VALID_PHONE, VALID_EMAIL, VALID_APPLICATION_DATE, VALID_PRIORITY,
            VALID_STATUS, VALID_INTERVIEWS,
            INVALID_IS_ARCHIVED, VALID_LAST_STAGE);
        String expectedMessage = BooleanUtil.INVALID_BOOLEAN;
        assertThrows(IllegalValueException.class, expectedMessage, internship::toModelType);
    }

    @Test
    public void toModelType_nullIsArchived_throwsIllegalValueException() {
        JsonAdaptedInternship internship = new JsonAdaptedInternship(VALID_COMPANY, VALID_ROLE,
            VALID_ADDRESS, VALID_PHONE, VALID_EMAIL, VALID_APPLICATION_DATE, VALID_PRIORITY,
            VALID_STATUS, VALID_INTERVIEWS, null, VALID_LAST_STAGE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Messages.IS_ARCHIVED);
        assertThrows(IllegalValueException.class, expectedMessage, internship::toModelType);
    }
}
