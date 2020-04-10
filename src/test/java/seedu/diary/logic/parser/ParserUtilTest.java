package seedu.diary.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.diary.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.diary.testutil.Assert.assertThrows;
import static seedu.diary.testutil.TypicalIndexes.INDEX_FIRST_INTERNSHIP_APPLICATION;

import org.junit.jupiter.api.Test;

import seedu.diary.commons.core.interviewcode.InterviewCode;
import seedu.diary.logic.parser.exceptions.ParseException;
import seedu.diary.model.internship.Address;
import seedu.diary.model.internship.ApplicationDate;
import seedu.diary.model.internship.Company;
import seedu.diary.model.internship.Email;
import seedu.diary.model.internship.Phone;
import seedu.diary.model.internship.Priority;
import seedu.diary.model.internship.Role;
import seedu.diary.model.status.Status;

public class ParserUtilTest {
    private static final String INVALID_COMPANY = "R@chel";
    private static final String INVALID_ROLE = "R@chel";
    private static final String INVALID_PHONE = "-651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_DATE = "010120";
    private static final String INVALID_PRIORITY = "r";
    private static final String INVALID_STATUS = "status";
    private static final String INVALID_INTERVIEW_PREAMBLE_1String = "0";
    private static final String INVALID_INTERVIEW_PREAMBLE_4Strings = "this is four strings";
    private static final String INVALID_INTERVIEW_CODE = "invalid";

    private static final String VALID_COMPANY = "Google";
    private static final String VALID_ROLE = "Software Engineer";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_DATE = "01 01 2020";
    private static final String VALID_PRIORITY = "1";
    private static final String VALID_STATUS = "Applied";
    private static final String VALID_INTERVIEW_PREAMBLE = "1 add";
    private static final String VALID_INTERVIEW_CODE = "add";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
            -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_INTERNSHIP_APPLICATION, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_INTERNSHIP_APPLICATION, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseCompany_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseCompany((String) null));
    }

    @Test
    public void parseCompany_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseCompany(INVALID_COMPANY));
    }

    @Test
    public void parseCompany_validValueWithoutWhitespace_returnsCompany() throws Exception {
        Company expectedCompany = new Company(VALID_COMPANY);
        assertEquals(expectedCompany, ParserUtil.parseCompany(VALID_COMPANY));
    }

    @Test
    public void parseCompany_validValueWithWhitespace_returnsTrimmedCompany() throws Exception {
        String companyWithWhitespace = WHITESPACE + VALID_COMPANY + WHITESPACE;
        Company expectedCompany = new Company(VALID_COMPANY);
        assertEquals(expectedCompany, ParserUtil.parseCompany(companyWithWhitespace));
    }

    @Test
    public void parseRole_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseRole((String) null));
    }

    @Test
    public void parseRole_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseRole(INVALID_ROLE));
    }

    @Test
    public void parseRole_validValueWithoutWhitespace_returnsRole() throws Exception {
        Role expectedRole = new Role(VALID_ROLE);
        assertEquals(expectedRole, ParserUtil.parseRole(VALID_ROLE));
    }

    @Test
    public void parseRole_validValueWithWhitespace_returnsTrimmedRole() throws Exception {
        String roleWithWhitespace = WHITESPACE + VALID_ROLE + WHITESPACE;
        Role expectedRole = new Role(VALID_ROLE);
        assertEquals(expectedRole, ParserUtil.parseRole(roleWithWhitespace));
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone((String) null));
    }

    @Test
    public void parsePhone_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(VALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_PHONE + WHITESPACE;
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(phoneWithWhitespace));
    }

    @Test
    public void parseAddress_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAddress((String) null));
    }

    // @Test
    // public void parseAddress_invalidValue_throwsParseException() {
    //     assertThrows(ParseException.class, () -> ParserUtil.parseAddress(INVALID_ADDRESS));
    // }

    @Test
    public void parseAddress_validValueWithoutWhitespace_returnsAddress() throws Exception {
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(VALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithWhitespace_returnsTrimmedAddress() throws Exception {
        String addressWithWhitespace = WHITESPACE + VALID_ADDRESS + WHITESPACE;
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(addressWithWhitespace));
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseEmail((String) null));
    }

    @Test
    public void parseEmail_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseEmail(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithoutWhitespace_returnsEmail() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(VALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String emailWithWhitespace = WHITESPACE + VALID_EMAIL + WHITESPACE;
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(emailWithWhitespace));
    }

    @Test
    public void parseApplicationDate_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseApplicationDate((String) null));
    }

    @Test
    public void parseApplicationDate_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseApplicationDate(INVALID_DATE));
    }

    @Test
    public void parseApplicationDate_validValueWithoutWhitespace_returnsApplicationDate() throws Exception {
        ApplicationDate expectedApplicationDate = new ApplicationDate(VALID_DATE);
        assertEquals(expectedApplicationDate, ParserUtil.parseApplicationDate(VALID_DATE));
    }

    @Test
    public void parseApplicationDate_validValueWithWhitespace_returnsTrimmedApplicationDate() throws Exception {
        String dateWithWhitespace = WHITESPACE + VALID_DATE + WHITESPACE;
        ApplicationDate expectedApplicationDate = new ApplicationDate(VALID_DATE);
        assertEquals(expectedApplicationDate, ParserUtil.parseApplicationDate(dateWithWhitespace));
    }

    @Test
    public void parseInterviewPreamble_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseInterviewPreamble((String) null));
    }

    @Test
    public void parseInterviewPreamble_validValueWithoutWhiteSpace_returnsStringArray() throws Exception {
        String preamble = VALID_INTERVIEW_PREAMBLE;
        String[] expectedArray = new String[]{"1", "add"};
        String[] resultArray = ParserUtil.parseInterviewPreamble(preamble);
        assertEquals(expectedArray[0], resultArray[0]);
        assertEquals(expectedArray[1], resultArray[1]);
    }

    @Test
    public void parseInterviewPreamble_validValueWithWhiteSpace_returnsStringArray() throws Exception {
        String preamble = WHITESPACE + VALID_INTERVIEW_PREAMBLE + WHITESPACE;
        String[] expectedArray = new String[]{"1", "add"};
        String[] resultArray = ParserUtil.parseInterviewPreamble(preamble);
        assertEquals(expectedArray[0], resultArray[0]);
        assertEquals(expectedArray[1], resultArray[1]);
    }

    @Test
    public void parseInterviewPreamble_invalidValueWithInsufficientStrings_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil
            .parseInterviewPreamble(INVALID_INTERVIEW_PREAMBLE_1String));
    }

    @Test
    public void parseInterviewPreamble_invalidValueWithExcessStrings_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil
            .parseInterviewPreamble(INVALID_INTERVIEW_PREAMBLE_4Strings));
    }

    @Test
    public void parseInterviewCode_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseInterviewCode((String) null));
    }

    @Test
    public void parseInterviewCode_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseInterviewCode(INVALID_INTERVIEW_CODE));
    }

    @Test
    public void parseInterviewCode_validValueWithoutWhiteSpace_returnsInterviewCode() throws ParseException {
        InterviewCode interviewCode = InterviewCode.ADD;
        assertEquals(interviewCode, ParserUtil.parseInterviewCode(VALID_INTERVIEW_CODE));
    }

    @Test
    public void parseInterviewCode_validValueWithWhiteSpace_returnsInterviewCode() throws ParseException {
        String codeWithWhitespace = WHITESPACE + VALID_INTERVIEW_CODE + WHITESPACE;
        InterviewCode interviewCode = InterviewCode.ADD;
        assertEquals(interviewCode, ParserUtil.parseInterviewCode(codeWithWhitespace));

    }

    @Test
    public void parseStatus_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseStatus((String) null));
    }

    @Test
    public void parseStatus_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseStatus(INVALID_STATUS));
        assertThrows(ParseException.class, () -> ParserUtil.parseStatus(VALID_STATUS.substring(1)));
    }

    @Test
    public void parseStatus_validValueWithoutWhiteSpace_returnsStatus() throws ParseException {
        Status status = Status.APPLIED;
        assertEquals(status, ParserUtil.parseStatus(VALID_STATUS));
        assertEquals(status, ParserUtil.parseStatus(VALID_STATUS.substring(0, 1)));
        assertEquals(status, ParserUtil.parseStatus(VALID_STATUS.substring(0, 3)));
    }

    @Test
    public void parseStatus_validValueWithWhiteSpace_returnsStatus() throws ParseException {
        String statusWithWhitespace = WHITESPACE + VALID_STATUS + WHITESPACE;
        Status status = Status.APPLIED;
        assertEquals(status, ParserUtil.parseStatus(statusWithWhitespace));
    }

    @Test
    public void parsePriority_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePriority((String) null));
    }

    @Test
    public void parsePriority_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePriority(INVALID_PRIORITY));
    }

    @Test
    public void parsePriority_validValueWithoutWhitespace_returnsPriority() throws Exception {
        Priority expectedPriority = new Priority(VALID_PRIORITY);
        assertEquals(expectedPriority, ParserUtil.parsePriority(VALID_PRIORITY));
    }

    @Test
    public void parsePriority_validValueWithWhitespace_returnsTrimmedPriority() throws Exception {
        String priorityWithWhitespace = WHITESPACE + VALID_PRIORITY + WHITESPACE;
        Priority expectedPriority = new Priority(VALID_PRIORITY);
        assertEquals(expectedPriority, ParserUtil.parsePriority(priorityWithWhitespace));
    }

    @Test
    public void parseStatus_validValueWithoutWhitespace_returnsStatus() throws Exception {
        Status expectedStatus = Status.APPLIED;
        assertEquals(expectedStatus, ParserUtil.parseStatus(VALID_STATUS));
    }

    @Test
    public void parseStatus_validValueWithWhitespace_returnsTrimmedStatus() throws Exception {
        String statusWithWhitespace = WHITESPACE + VALID_STATUS + WHITESPACE;
        Status expectedStatus = Status.APPLIED;
        assertEquals(expectedStatus, ParserUtil.parseStatus(statusWithWhitespace));
    }

}
