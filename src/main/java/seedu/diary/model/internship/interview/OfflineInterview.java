package seedu.diary.model.internship.interview;

import java.time.format.DateTimeFormatter;

import seedu.diary.model.internship.Address;
import seedu.diary.model.internship.ApplicationDate;

/**
 * Represents an interview that is scheduled to be conducted offline.
 * Address field is compulsory when creating this Interview object.
 */
public class OfflineInterview extends Interview {

    public OfflineInterview(ApplicationDate interviewDate, Address interviewAddress) {
        super(interviewDate, interviewAddress);
    }

    public boolean getIsOnline() {
        return false;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Offline Interview on: ")
            .append(getInterviewDate().format(DateTimeFormatter.ofPattern(ApplicationDate.DATE_PATTERN)))
            .append(" at: ")
            .append(getInterviewAddress());
        return builder.toString();
    }
}
