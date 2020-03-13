package seedu.address.model.internship;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.util.Objects;

import seedu.address.model.status.Status;

/**
 * Represents an InternshipApplication in the internship diary.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class InternshipApplication {

    private final Company company;
    private final Role role;
    private final Address address;
    private final Phone phone;
    private final Email email;
    private final LocalDate applicationDate;
    private final Priority priority;
    private final Status status;

    /**
     * Every field must be present and not null.
     */
    public InternshipApplication(Company company, Role role, Address address, Phone phone, Email email,
            LocalDate applicationDate, Priority priority, Status status) {
        requireAllNonNull(company, phone, email, address, status);
        this.company = company;
        this.role = role;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.status = status;
        this.applicationDate = applicationDate;
        this.priority = priority;
    }

    public Company getCompany() {
        return company;
    }

    public Role getRole() {
        return role;
    }

    public seedu.address.model.internship.Address getAddress() {
        return address;
    }

    public seedu.address.model.internship.Phone getPhone() {
        return phone;
    }

    public seedu.address.model.internship.Email getEmail() {
        return email;
    }

    public LocalDate getApplicationDate() {
        return applicationDate;
    }

    public Priority getPriority() {
        return priority;
    }

    public Status getStatus() {
        return status;
    }

    /**
     * Returns true if all but priority and status fields are the same.
     * This defines a weaker notion of equality between two internship applications.
     */
    public boolean isSameInternshipApplication(InternshipApplication internshipApplication) {

        if (internshipApplication == this) {
            return true;
        }

        return internshipApplication != null
                && internshipApplication.getCompany().equals(getCompany())
                && internshipApplication.getRole().equals(getRole())
                && internshipApplication.getAddress().equals(getAddress())
                && internshipApplication.getPhone().equals(getPhone())
                && internshipApplication.getEmail().equals(getEmail())
                && internshipApplication.getApplicationDate().equals(getApplicationDate());
    }

    /**
     * Returns true if both internship application have the fields.
     * This defines a stronger notion of equality between two internship applications.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof InternshipApplication)) {
            return false;
        }

        InternshipApplication internshipApplication = (InternshipApplication) other;
        return internshipApplication.getCompany().equals(getCompany())
                && internshipApplication.getRole().equals(getRole())
                && internshipApplication.getAddress().equals(getAddress())
                && internshipApplication.getPhone().equals(getPhone())
                && internshipApplication.getEmail().equals(getEmail())
                && internshipApplication.getApplicationDate().equals(getApplicationDate())
                && internshipApplication.getPriority().equals(getPriority())
                && internshipApplication.getStatus().equals(getStatus());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(company, role, address, phone, email, applicationDate, priority, status);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getCompany())
                .append(" Role: ")
                .append(getRole())
                .append(" Address: ")
                .append(getAddress())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Application Date: ")
                .append(getApplicationDate())
                .append(" Priority: ")
                .append(getPriority())
                .append(" Status: ")
                .append(getStatus());
        return builder.toString();
    }
}

