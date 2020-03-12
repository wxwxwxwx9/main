package seedu.address.testutil;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditInternshipDescriptor;
import seedu.address.model.internship.Company;
import seedu.address.model.internship.InternshipApplication;
import seedu.address.model.internship.Address;
import seedu.address.model.internship.Email;
import seedu.address.model.internship.Phone;
import seedu.address.model.internship.Priority;
import seedu.address.model.internship.Role;
import seedu.address.model.status.Status;

/**
 * A utility class to help with building EditPersonDescriptor objects.
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
     * Returns an {@code EditPersonDescriptor} with fields containing {@code internshipApplication}'s details
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
    }

    /**
     * Sets the {@code Name} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditInternshipDescriptorBuilder withCompany(String company) {
        descriptor.setCompany(new Company(company));
        return this;
    }

    /**
     * Sets the {@code Role} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditInternshipDescriptorBuilder withRole(String role) {
        descriptor.setRole(new Role(role));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditInternshipDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditInternshipDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditInternshipDescriptorBuilder withDate(String date) {
        try {
            descriptor.setDate(new SimpleDateFormat("dd MM yyyy").parse(date));
            return this;
        } catch (ParseException e) {
            return this;
        }
    }

    /**
     * Sets the {@code Priority} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditInternshipDescriptorBuilder withPriority(String priority) {
        descriptor.setPriority(new Priority(priority));
        return this;
    }

    /**
     * Sets the {@code Status} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditInternshipDescriptorBuilder withStatus(String status) {
        descriptor.setStatus(Status.valueOf(status));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditInternshipDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    public EditCommand.EditInternshipDescriptor build() {
        return descriptor;
    }
}
