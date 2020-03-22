package seedu.address.model.internship.interview;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import seedu.address.model.internship.Address;
import seedu.address.model.internship.ApplicationDate;


/**
 * Represents an Interview in the Internship Diary.
 * Interviews are always tagged to an internship application.
 */
public class Interview {
    public static final String ADDRESS_NOT_APPLICABLE = "NA";
    public static final String INVALID_IS_ONLINE = "IsOnline should be either true or false";

    public final boolean isOnline;

    private final ApplicationDate interviewDate;
    private final Address interviewAddress;

    public Interview(boolean isOnline, ApplicationDate interviewDate, Address interviewAddress) {
        this.isOnline = isOnline;
        this.interviewDate = interviewDate;
        this.interviewAddress = interviewAddress;
    }

    public Interview(boolean isOnline, ApplicationDate interviewDate) {
        this.isOnline = isOnline;
        this.interviewDate = interviewDate;
        this.interviewAddress = new Address(ADDRESS_NOT_APPLICABLE);
    }

    /**
     * Returns the interview date in local date format.
     */
    public LocalDate getInterviewDate() {
        return interviewDate.fullApplicationDate;
    }

    public ApplicationDate getDate() {
        return interviewDate;
    }

    public Address getInterviewAddress() {
        return interviewAddress;
    }

    /**
     * Checks if the interview is valid. If it is an online interview, it should not have an address.
     * Otherwise, any valid address is fine.
     */
    public boolean isValid() {
        if (isOnline) {
            return interviewAddress.equals(new Address(ADDRESS_NOT_APPLICABLE));
        }
        return true;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Interview)) {
            return false;
        }

        Interview interview = (Interview) other;
        return interview.getInterviewDate().equals(getInterviewDate())
                && interview.getInterviewAddress().equals(getInterviewAddress())
                && interview.isOnline == isOnline;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        if (isOnline) {
            builder.append("Online Interview on: ")
                    .append(getInterviewDate().format(DateTimeFormatter.ofPattern(ApplicationDate.DATE_PATTERN)));
        } else {
            builder.append("Interview on: ")
                    .append(getInterviewDate().format(DateTimeFormatter.ofPattern(ApplicationDate.DATE_PATTERN)))
                    .append(" at: ")
                    .append(getInterviewAddress());
        }
        return builder.toString();
    }
}
