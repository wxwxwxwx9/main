package seedu.address.commons.core.interviewcode;

/**
 * Represents an enumeration of interview command code. This code should be either "add", "edit", or "delete",
 * which will be used in interview command to manipulate interviews in an {@code Internship Application}.
 *
 * InterviewCode should come right after {@code Index} when inputting interview command.
 */
public enum InterviewCode {
    LIST, ADD, EDIT, DELETE;

    public static final String MESSAGE_CONSTRAINTS = "Interview should have an add, edit or delete after index.";

    /**
     * Returns true is the given {@code code} is a valid {@code InterviewCode}.
     */
    public static boolean isValidCode(String code) {
        String upperCaseCode = code.toUpperCase();
        return upperCaseCode.equals("ADD") || upperCaseCode.equals("EDIT")
                || upperCaseCode.equals("DELETE") || upperCaseCode.equals("LIST");
    }
}
