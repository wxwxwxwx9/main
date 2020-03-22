package seedu.address.model.internship;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

import seedu.address.model.internship.interview.Interview;
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
    private final ApplicationDate applicationDate;
    private final Priority priority;
    private final Status status;
    private final ArrayList<Interview> interviews;
    private boolean isArchived;

    /**
     * Every field must be present and not null.
     */
    public InternshipApplication(Company company, Role role, Address address, Phone phone, Email email,
            ApplicationDate applicationDate, Priority priority, Status status) {
        requireAllNonNull(company, phone, email, address, status);
        this.company = company;
        this.role = role;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.status = status;
        this.applicationDate = applicationDate;
        this.priority = priority;
        this.isArchived = false;
        interviews = new ArrayList<>();
    }

    /**
     * Overloaded constructor to set isArchived field (probably not needed).
     */
    public InternshipApplication(Company company, Role role, Address address, Phone phone, Email email,
             ApplicationDate applicationDate, Priority priority, Status status, boolean isArchived) {
        requireAllNonNull(company, phone, email, address, status);
        this.company = company;
        this.role = role;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.status = status;
        this.applicationDate = applicationDate;
        this.priority = priority;
        this.isArchived = isArchived;
        interviews = new ArrayList<>();
    }

    public Company getCompany() {
        return company;
    }

    public Role getRole() {
        return role;
    }

    public Address getAddress() {
        return address;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public ApplicationDate getApplicationDate() {
        return applicationDate;
    }

    public Priority getPriority() {
        return priority;
    }

    public Status getStatus() {
        return status;
    }

    public boolean isArchived() {
        return isArchived;
    }

    /**
     * Returns the earliest interview from today in the list of interviews of the application.
     * @param todayDate The current date today.
     * @return an Optional of LocalDate. Will return empty if there are no interviews after today's date.
     */
    public Optional<Interview> getEarliestInterview(LocalDate todayDate) {
        if (interviews.size() <= 0) {
            return Optional.empty();
        }

        Interview earliestInterview = interviews.get(0);
        for (Interview currentInterview: interviews) {
            LocalDate earliestDate = earliestInterview.getInterviewDate();
            LocalDate currentDate = currentInterview.getInterviewDate();
            if ((currentDate.compareTo(earliestDate) <= 0 || earliestDate.compareTo(todayDate) < 0)
                    && currentDate.compareTo(todayDate) >= 0) {
                earliestInterview = currentInterview;
            }
        }
        return earliestInterview.getInterviewDate().compareTo(todayDate) >= 0
                ? Optional.of(earliestInterview) : Optional.empty();
    }

    public void addInterview(Interview interview) {
        interviews.add(interview);
    }

    public ArrayList<Interview> getInterviews() {
        return interviews;
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
                && internshipApplication.getStatus().equals(getStatus())
                && internshipApplication.getStatus().equals(isArchived());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(company, role, address, phone, email, applicationDate, priority, status, isArchived);
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
                .append(getStatus())
                .append(" Archived: ")
                .append(isArchived());
        return builder.toString();
    }
}

