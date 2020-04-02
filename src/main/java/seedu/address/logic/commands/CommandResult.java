package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.address.model.internship.InternshipApplication;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /**
     * Help information should be shown to the user.
     */
    private final boolean showHelp;

    /**
     * Statistics should be shown to the user.
     */
    private final boolean showStatistics;

    /**
     * The application should exit.
     */
    private final boolean exit;

    /**
     * Details of an internship application should be shown to the user based on the index provided.
     */
    private final boolean showInternshipApplication;

    /**
     * Index of the internship application to display.
     */
    private final InternshipApplication internshipApplicationToShow;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean showStatistics, boolean exit) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.showStatistics = showStatistics;
        this.exit = exit;
        showInternshipApplication = false;
        internshipApplicationToShow = null;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false, false);
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * also instructs window to display an internship application at specified {@code internshipApplicationIndex}.
     */
    public CommandResult(String feedbackToUser, InternshipApplication internshipApplicationToShow) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showInternshipApplication = true;
        this.internshipApplicationToShow = requireNonNull(internshipApplicationToShow);
        showHelp = false;
        showStatistics = false;
        exit = false;
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isShowStatistics() {
        return showStatistics;
    }

    public boolean isExit() {
        return exit;
    }

    public boolean isShowInternshipApplication() {
        return showInternshipApplication;
    }

    public InternshipApplication getInternshipApplicationToShow() {
        return internshipApplicationToShow;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;

        boolean isIndexEquals = true;

        if (showInternshipApplication && otherCommandResult.showInternshipApplication) {
            isIndexEquals = internshipApplicationToShow.equals(otherCommandResult.internshipApplicationToShow);
        }

        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
            && showHelp == otherCommandResult.showHelp
            && showStatistics == otherCommandResult.showStatistics
            && exit == otherCommandResult.exit
            && isIndexEquals
            && showInternshipApplication == otherCommandResult.showInternshipApplication;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, showStatistics, exit);
    }

}
