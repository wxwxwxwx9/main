package seedu.diary.testutil;

import java.time.LocalDate;

import seedu.diary.model.internship.Address;
import seedu.diary.model.internship.ApplicationDate;
import seedu.diary.model.internship.interview.Interview;

/**
 * A utility class to help build a list of Interview objects
 */
public class InterviewBuilder {

    public static final boolean IS_NOT_ONLINE = false;
    public static final String DEFAULT_ADDRESS_1 = "66 Big Avenue, Unit 10-350";
    public static final String DEFAULT_DATE_1 = "12 03 2020";

    private boolean isOnline;
    private Address interviewAddress;
    private ApplicationDate interviewDate;

    public InterviewBuilder() {
        this.isOnline = IS_NOT_ONLINE;
        this.interviewAddress = new Address(DEFAULT_ADDRESS_1);
        this.interviewDate = new ApplicationDate(DEFAULT_DATE_1);
    }

    public InterviewBuilder(Interview toCopy) {
        this.isOnline = toCopy.getIsOnline();
        this.interviewDate = new ApplicationDate(toCopy.getInterviewDate());
        this.interviewAddress = toCopy.getInterviewAddress();
    }

    /**
     * Sets the {@code isOnline} based on a boolean.
     */
    public InterviewBuilder withIsOnline(boolean isOnline) {
        this.isOnline = isOnline;
        return this;
    }

    /**
     * Sets isOnline based on a String.
     */
    public InterviewBuilder withIsOnline(String isOnline) {
        return withIsOnline(Boolean.parseBoolean(isOnline));
    }

    /**
     * Sets the {@code interviewAddress} based on a String.
     */
    public InterviewBuilder withAddress(String address) {
        this.interviewAddress = new Address(address);
        return this;
    }

    /**
     * Sets the {@code interviewDate} based on a String.
     */
    public InterviewBuilder withDate(String date) {
        this.interviewDate = new ApplicationDate(date);
        return this;
    }

    /**
     * Overloaded method to set date from {@code LocalDate}
     */
    public InterviewBuilder withDate(LocalDate date) {
        this.interviewDate = new ApplicationDate(date);
        return this;
    }

    public Interview build() {
        return Interview.createInterview(isOnline, interviewDate, interviewAddress);
    }

}
