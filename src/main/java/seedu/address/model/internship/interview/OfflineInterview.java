package seedu.address.model.internship.interview;

import java.time.format.DateTimeFormatter;

import seedu.address.model.internship.Address;
import seedu.address.model.internship.ApplicationDate;

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
