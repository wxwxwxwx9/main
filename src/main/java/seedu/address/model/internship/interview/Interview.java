package seedu.address.model.internship.interview;

import java.time.LocalDate;

import seedu.address.model.internship.Address;
import seedu.address.model.internship.ApplicationDate;


/**
 * Represents an Interview in the Internship Diary.
 * Interviews are always tagged to an internship application.
 */
public class Interview {
    public final boolean isOnline;

    private final ApplicationDate interviewDate;
    private final Address interviewAddress;

    public Interview(boolean isOnline, ApplicationDate interviewDate, Address interviewAddress) {
        this.isOnline = isOnline;
        this.interviewDate = interviewDate;
        this.interviewAddress = interviewAddress;
    }

    public LocalDate getInterviewDate() {
        return interviewDate.fullApplicationDate;
    }

    public Address getInterviewAddress() {
        return interviewAddress;
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

}
