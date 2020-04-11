package seedu.diary.model.internship.interview;

import java.time.format.DateTimeFormatter;

import seedu.diary.model.internship.Address;
import seedu.diary.model.internship.ApplicationDate;

/**
 * Represents an interview that is scheduled to be conducted online.
 * Address field will be automatically replace with an 'NA', meaning Not Applicable.
 */
public class OnlineInterview extends Interview {
    public static final Address ADDRESS_NOT_APPLICABLE = new Address("NA");

    public OnlineInterview(ApplicationDate interviewDate) {
        super(interviewDate, ADDRESS_NOT_APPLICABLE);
    }

    @Override
    public boolean getIsOnline() {
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Online Interview on: ")
            .append(getInterviewDate().format(DateTimeFormatter.ofPattern(ApplicationDate.DATE_PATTERN)));
        return builder.toString();
    }
}
