package seedu.diary.model.internship.interview;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;

import seedu.diary.model.internship.Address;
import seedu.diary.model.internship.ApplicationDate;

/**
 * Represents an Interview in the Internship Diary.
 * Interviews are always tagged to an internship application.
 */
public abstract class Interview {
    protected final ApplicationDate interviewDate;
    protected final Address interviewAddress;

    protected Interview(ApplicationDate interviewDate, Address interviewAddress) {
        requireNonNull(interviewDate);
        requireNonNull(interviewAddress);
        this.interviewDate = interviewDate;
        this.interviewAddress = interviewAddress;
    }

    /**
     * Constructs a new online or offline interview based on the parameters given.
     * This is the default static constructor to create an interview object.
     */
    public static Interview createInterview(boolean isOnline,
        ApplicationDate interviewDate, Address interviewAddress) {
        if (isOnline) {
            return new OnlineInterview(interviewDate);
        } else {
            return new OfflineInterview(interviewDate, interviewAddress);
        }
    }

    /**
     * Constructs a new online interview.
     * This constructor is only for adding a new online interview.
     */
    public static Interview createOnlineInterview(ApplicationDate interviewDate) {
        return new OnlineInterview(interviewDate);
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
     * Returns a boolean on whether the interview is to be conducted online.
     */
    public abstract boolean getIsOnline();

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
            && this.getIsOnline() == interview.getIsOnline();
    }
}
