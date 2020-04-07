package seedu.diary.testutil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import seedu.diary.logic.commands.EditCommand;
import seedu.diary.logic.commands.EditCommand.EditInternshipDescriptor;
import seedu.diary.model.internship.Address;
import seedu.diary.model.internship.ApplicationDate;
import seedu.diary.model.internship.Company;
import seedu.diary.model.internship.Email;
import seedu.diary.model.internship.InternshipApplication;
import seedu.diary.model.internship.Phone;
import seedu.diary.model.internship.Priority;
import seedu.diary.model.internship.Role;
import seedu.diary.model.status.Status;

/**
 * A utility class to help with building EditInternshipDescriptor objects.
 */
public class EditInternshipDescriptorBuilder {

    private EditCommand.EditInternshipDescriptor descriptor;

    public EditInternshipDescriptorBuilder() {
        descriptor = new EditCommand.EditInternshipDescriptor();
    }

    public EditInternshipDescriptorBuilder(EditCommand.EditInternshipDescriptor descriptor) {
        this.descriptor = new EditInternshipDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditInternshipDescriptor} with fields containing {@code internshipApplication}'s details
     */
    public EditInternshipDescriptorBuilder(InternshipApplication internshipApplication) {
        descriptor = new EditCommand.EditInternshipDescriptor();
        descriptor.setCompany(internshipApplication.getCompany());
        descriptor.setRole(internshipApplication.getRole());
        descriptor.setPhone(internshipApplication.getPhone());
        descriptor.setEmail(internshipApplication.getEmail());
        descriptor.setAddress(internshipApplication.getAddress());
        descriptor.setDate(internshipApplication.getApplicationDate());
        descriptor.setPriority(internshipApplication.getPriority());
        descriptor.setStatus(internshipApplication.getStatus());
        descriptor.setIsGhostedOrRejected(internshipApplication.getIsGhostedOrRejected());
        descriptor.setLastStage(internshipApplication.getLastStage());
    }

    /**
     * Sets the {@code Company} of the {@code EditInternshipDescriptor} that we are building.
     */
    public EditInternshipDescriptorBuilder withCompany(String company) {
        descriptor.setCompany(new Company(company));
        return this;
    }

    /**
     * Sets the {@code Role} of the {@code EditInternshipDescriptor} that we are building.
     */
    public EditInternshipDescriptorBuilder withRole(String role) {
        descriptor.setRole(new Role(role));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditInternshipDescriptor} that we are building.
     */
    public EditInternshipDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditInternshipDescriptor} that we are building.
     */
    public EditInternshipDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code ApplicationDate} of the {@code EditInternshipDescriptor} that we are building.
     */
    public EditInternshipDescriptorBuilder withApplicationDate(String date) {
        try {
            descriptor.setDate(new ApplicationDate(LocalDate.parse(date, DateTimeFormatter.ofPattern("dd MM yyyy"))));
            return this;
        } catch (DateTimeParseException e) {
            return this;
        }
    }

    /**
     * Sets the {@code Priority} of the {@code EditInternshipDescriptor} that we are building.
     */
    public EditInternshipDescriptorBuilder withPriority(String priority) {
        descriptor.setPriority(new Priority(priority));
        return this;
    }

    /**
     * Sets the {@code Status} of the {@code EditInternshipDescriptor} that we are building.
     */
    public EditInternshipDescriptorBuilder withStatus(String status) {
        descriptor.setStatus(Status.valueOf(status));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditInternshipDescriptor} that we are building.
     */
    public EditInternshipDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    public EditCommand.EditInternshipDescriptor build() {
        return descriptor;
    }

}
