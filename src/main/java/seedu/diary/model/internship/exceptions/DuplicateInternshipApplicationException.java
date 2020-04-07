package seedu.diary.model.internship.exceptions;

/**
 * Signals that the operation will result in duplicate Internship Application (Internship Application are considered
 * duplicates if they have the same value in all but priority and status fields).
 */
public class DuplicateInternshipApplicationException extends RuntimeException {
    public DuplicateInternshipApplicationException() {
        super("Operation would result in duplicate internship application");
    }
}
