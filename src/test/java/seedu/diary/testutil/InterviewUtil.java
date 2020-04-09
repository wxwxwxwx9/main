package seedu.diary.testutil;

import static seedu.diary.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.diary.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.diary.logic.parser.CliSyntax.PREFIX_IS_ONLINE;
import static seedu.diary.testutil.TypicalIndexes.INDEX_FIRST_INTERNSHIP_APPLICATION;

import java.time.format.DateTimeFormatter;

import seedu.diary.logic.commands.InterviewCommand;
import seedu.diary.logic.commands.interviewsubcommands.InterviewEditCommand;
import seedu.diary.model.internship.interview.Interview;

/**
 * A utility class for Interviews.
 */
public class InterviewUtil {

    /**
     * Returns an add command string for adding the {@code interview}.
     */
    public static String getAddCommand(Interview interview) {
        return InterviewCommand.COMMAND_WORD + " " + INDEX_FIRST_INTERNSHIP_APPLICATION.getOneBased() + " add "
            + getInterviewDetails(interview);
    }

    /**
     * Returns the part of command string for the given {@code Interview}'s details.
     */
    public static String getInterviewDetails(Interview interview) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd MM YYYY");
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_ADDRESS + interview.getInterviewAddress().value + " ");
        sb.append(PREFIX_DATE + dateFormat
            .format(interview.getInterviewDate()) + " ");
        sb.append(PREFIX_IS_ONLINE + String.valueOf(interview.getIsOnline()) + " ");
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditInterviewDescriptor}'s details.
     */
    public static String getEditInterviewApplicationDescriptorDetails(
        InterviewEditCommand.EditInterviewDescriptor descriptor) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd MM YYYY");
        StringBuilder sb = new StringBuilder();
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
        descriptor.getInterviewDate().ifPresent(date -> sb.append(PREFIX_DATE)
            .append(dateFormat.format(date.fullApplicationDate)).append(" "));
        descriptor.getIsOnline()
            .ifPresent(isOnline -> sb.append(PREFIX_IS_ONLINE).append(isOnline.toString()).append(" "));

        return sb.toString();
    }
}
