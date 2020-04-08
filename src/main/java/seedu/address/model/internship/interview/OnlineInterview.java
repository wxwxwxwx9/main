package seedu.address.model.internship.interview;

import java.time.format.DateTimeFormatter;

import seedu.address.model.internship.Address;
import seedu.address.model.internship.ApplicationDate;

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
