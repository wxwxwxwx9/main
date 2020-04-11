package seedu.diary.model.internship;

import static java.util.Objects.requireNonNull;
import static seedu.diary.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.diary.model.ListenerPropertyType.DISPLAYED_INTERVIEWS;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.diary.commons.core.index.Index;
import seedu.diary.model.ListenerPropertyType;
import seedu.diary.model.internship.interview.Interview;
import seedu.diary.model.status.Status;

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
    private final Boolean isArchived;
    private Boolean isGhostedOrRejected;
    private final Status lastStage;

    private final PropertyChangeSupport changes = new PropertyChangeSupport(this);


    private List<Interview> interviews;

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
        this.isGhostedOrRejected = false;
        this.lastStage = status;
        interviews = new ArrayList<>();
    }

    /**
     * Overloaded constructor to set isArchived, lastStage and interviews fields.
     */
    public InternshipApplication(Company company, Role role, Address address, Phone phone, Email email,
        ApplicationDate applicationDate, Priority priority, Status status, Boolean isArchived, Status lastStage,
        List<Interview> interviews) {
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
        this.isGhostedOrRejected = false;
        this.lastStage = lastStage;
        this.interviews = interviews;
    }

    /**
     * Overloaded constructor to set isArchived, lastStage and interviews fields.
     */
    public InternshipApplication(Company company, Role role, Address address, Phone phone, Email email,
        ApplicationDate applicationDate, Priority priority, Status status, Boolean isArchived,
        List<Interview> interviews) {
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
        this.isGhostedOrRejected = false;
        this.lastStage = status;
        this.interviews = interviews;
    }

    /**
     * Overloaded constructor to set lastStage and interviews field.
     */
    public InternshipApplication(Company company, Role role, Address address, Phone phone, Email email,
        ApplicationDate applicationDate, Priority priority, Status status,
        Status lastStage, List<Interview> interviews) {
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
        this.isGhostedOrRejected = false;
        this.lastStage = lastStage;
        this.interviews = interviews;
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

    /**
     * Sets variable 'isGhostedOrRejected' to true to keep track of whether the last stage before the internship
     * application failed (ghosted/ rejected) needs to be stored.
     */
    public void setIsGhostedOrRejected(Boolean bool) {
        this.isGhostedOrRejected = bool;
    }

    public Boolean getIsGhostedOrRejected() {
        return isGhostedOrRejected;
    }

    /**
     * Returns an InternshipApplication updated with last stage.
     *
     * @param lastStage where the application failed (APPLIED/ INTERVIEW/ OFFERED).
     * @return InternshipApplication with lastStage specified.
     */
    public InternshipApplication setLastStage(Status lastStage) {
        return new InternshipApplication(company, role, address, phone, email, applicationDate, priority, status,
            lastStage, interviews);
    }

    /**
     * Returns the last stage before the status of an internship application was updated to be ghosted/ rejected.
     *
     * @return an enum of Status (APPLIED/ OFFERED/ INTERVIEW).
     */
    public Status getLastStage() {
        return lastStage;
    }

    /**
     * Returns the last stage failed.
     *
     * @return message of the last stage failed, else an empty string.
     */
    public String getLastStageMessage() {
        if (status == Status.GHOSTED || status == Status.REJECTED) {
            return " [You failed at " + lastStage.toString() + ":(]";
        } else {
            return "";
        }
    }

    public Boolean isArchived() {
        return isArchived;
    }

    /**
     * Returns the earliest interview from today in the list of interviews of the application.
     *
     * @param todayDate The current date today.
     * @return an Optional of LocalDate. Will return empty if there are no interviews after today's date.
     */
    public Optional<Interview> getEarliestInterview(LocalDate todayDate) {
        if (interviews.size() <= 0) {
            return Optional.empty();
        }

        Interview earliestInterview = interviews.get(0);
        for (Interview currentInterview : interviews) {
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

    /**
     * Adds given interview into interview list. Will fire property change event
     */
    public void addInterview(Interview interview) {
        interviews.add(interview);
        firePropertyChange(DISPLAYED_INTERVIEWS, interviews);
    }

    /**
     * Deletes given interview from interview list. Will fire property change event
     */
    public void deleteInterview(Interview interview) {
        interviews.remove(interview);
        firePropertyChange(DISPLAYED_INTERVIEWS, interviews);
    }

    /**
     * Sets given interview into specified index in the interview list. Will fire property change event
     */
    public void setInterview(Index index, Interview interview) {
        interviews.set(index.getZeroBased(), interview);
        firePropertyChange(DISPLAYED_INTERVIEWS, interviews);
    }

    public Interview getInterview(int index) {
        return interviews.get(index);
    }

    public void setInterviews(List<Interview> interviews) {
        requireNonNull(interviews);
        this.interviews.addAll(interviews);
        firePropertyChange(DISPLAYED_INTERVIEWS, this.interviews);
    }

    public List<Interview> getInterviews() {
        return interviews;
    }

    public boolean hasInterview(Interview interview) {
        return interviews.contains(interview);
    }

    /**
     * Returns a deep, archived copy of this internship application (isArchived field is marked true).
     * The rationale behind this is to uphold immutability.
     */
    public InternshipApplication archive() {
        return new InternshipApplication(
            this.company,
            this.role,
            this.address,
            this.phone,
            this.email,
            this.applicationDate,
            this.priority,
            this.status,
            true,
            this.lastStage,
            this.interviews
        );
    }

    /**
     * Returns a deep, unarchived copy of this internship application (isArchived field is marked false).
     * The rationale behind this is to uphold immutability.
     */
    public InternshipApplication unarchive() {
        return new InternshipApplication(
            this.company,
            this.role,
            this.address,
            this.phone,
            this.email,
            this.applicationDate,
            this.priority,
            this.status,
            false,
            this.lastStage,
            this.interviews
        );
    }

    /**
     * Returns application date or the earliest interview date scheduled, whichever is closer to current date.
     *
     * @return earliest date from current date.
     */
    public ApplicationDate getEarliestApplicationOrInterviewDate() {
        LocalDate currentDate = LocalDate.now();
        Optional<Interview> earliestInterview = getEarliestInterview(currentDate);
        if (applicationDate.fullApplicationDate.compareTo(currentDate) < 0) { // application date before current date
            if (earliestInterview.isPresent()) {
                return earliestInterview.get().getDate();
            } else {
                return applicationDate;
            }
        }
        if (earliestInterview.isPresent()) { // there are interviews after current date
            ApplicationDate earliestInterviewDate = earliestInterview.get().getDate();
            return applicationDate.compareTo(earliestInterviewDate) >= 0 ? earliestInterviewDate : applicationDate;
        } else { // there are no interviews after current date
            return applicationDate;
        }
    }

    //=========== PropertyChanges ======================================================================

    public void addPropertyChangeListener(ListenerPropertyType propertyType, PropertyChangeListener l) {
        changes.addPropertyChangeListener(propertyType.toString(), l);
    }

    /**
     * Removes all property change listeners from Internship Application.
     */
    public void removeAllPropertyChangeListener() {
        PropertyChangeListener[] propertyChangeListeners = changes.getPropertyChangeListeners();
        for (PropertyChangeListener pcl : propertyChangeListeners) {
            changes.removePropertyChangeListener(pcl);
        }
    }

    private void firePropertyChange(ListenerPropertyType propertyType, Object newValue) {
        changes.firePropertyChange(propertyType.toString(), null, newValue);
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
            && internshipApplication.getApplicationDate().equals(getApplicationDate())
            && internshipApplication.isArchived().equals(isArchived())
            && internshipApplication.getInterviews().equals(getInterviews());
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
            && internshipApplication.isArchived().equals(isArchived())
            && internshipApplication.getInterviews().equals(getInterviews());
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
            .append(getLastStageMessage())
            .append(" Archived: ")
            .append(isArchived());
        return builder.toString();
    }

}

