package seedu.diary.testutil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import seedu.diary.logic.commands.interviewsubcommands.InterviewEditCommand;
import seedu.diary.model.internship.Address;
import seedu.diary.model.internship.ApplicationDate;
import seedu.diary.model.internship.interview.Interview;

/**
 * A utility class to help build EditInterviewDescriptor objects.
 */
public class EditInterviewDescriptorBuilder {

    private InterviewEditCommand.EditInterviewDescriptor descriptor;

    public EditInterviewDescriptorBuilder() {
        descriptor = new InterviewEditCommand.EditInterviewDescriptor();
    }

    public EditInterviewDescriptorBuilder(InterviewEditCommand.EditInterviewDescriptor descriptor) {
        this.descriptor = new InterviewEditCommand.EditInterviewDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditInterviewDescriptor} with fields containing {@code interview}'s details
     */
    public EditInterviewDescriptorBuilder(Interview interview) {
        descriptor = new InterviewEditCommand.EditInterviewDescriptor();
        descriptor.setAddress(interview.getInterviewAddress());
        descriptor.setDate(interview.getDate());
        descriptor.setOnline(interview.getIsOnline());
    }


    /**
     * Sets the {@code ApplicationDate} of the {@code EditInternshipDescriptor} that we are building.
     */
    public EditInterviewDescriptorBuilder withInterviewDate(String date) {
        try {
            descriptor.setDate(new ApplicationDate(LocalDate.parse(date, DateTimeFormatter.ofPattern("dd MM yyyy"))));
            return this;
        } catch (DateTimeParseException e) {
            return this;
        }
    }

    /**
     * Overloaded interviewDate setter that uses a LocalDate instead of a String.
     */
    public EditInterviewDescriptorBuilder withInterviewDate(LocalDate date) {
        try {
            descriptor.setDate(new ApplicationDate(date));
            return this;
        } catch (DateTimeParseException e) {
            return this;
        }
    }

    /**
     * Sets the {@code Address} of the {@code EditInternshipDescriptor} that we are building.
     */
    public EditInterviewDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditInternshipDescriptor} that we are building.
     */
    public EditInterviewDescriptorBuilder withIsOnline(String isOnline) {
        descriptor.setOnline(Boolean.parseBoolean(isOnline));
        return this;
    }

    public InterviewEditCommand.EditInterviewDescriptor build() {
        return descriptor;
    }
}
