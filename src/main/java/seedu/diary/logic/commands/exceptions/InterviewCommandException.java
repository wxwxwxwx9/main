package seedu.diary.logic.commands.exceptions;

/**
 * Represents an exception caused by an execution of an interview sub-command.
 */
public class InterviewCommandException extends CommandException {
    /**
     * Constructs a new {@code InterviewCommandException} with the specified detail {@code message} and {@code cause}.
     */
    public InterviewCommandException(String message) {
        super(message);
    }
}
