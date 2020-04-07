package seedu.diary.testutil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import seedu.diary.model.internship.Address;
import seedu.diary.model.internship.ApplicationDate;
import seedu.diary.model.internship.Company;
import seedu.diary.model.internship.Email;
import seedu.diary.model.internship.InternshipApplication;
import seedu.diary.model.internship.Phone;
import seedu.diary.model.internship.Priority;
import seedu.diary.model.internship.Role;
import seedu.diary.model.internship.interview.Interview;
import seedu.diary.model.status.Status;

/**
 * A utility class to help with building Internship Application objects.
 */
public class InternshipApplicationBuilder {

    public static final String DEFAULT_COMPANY = "Google";
    public static final String DEFAULT_ROLE = "Product Manager";
    public static final String DEFAULT_ADDRESS = "1600 Amphitheatre Parkway";
    public static final String DEFAULT_PHONE = "99999999";
    public static final String DEFAULT_EMAIL = "richardma@gmail.com";
    public static final String DEFAULT_APPLICATION_DATE = "12 03 2020";
    public static final Integer DEFAULT_PRIORITY = 10;
    public static final Status DEFAULT_STATUS = Status.APPLIED;
    public static final Boolean DEFAULT_IS_GHOSTED_OR_REJECTED = false;
    public static final Status DEFAULT_LAST_STAGE = null;

    private Company company;
    private Role role;
    private Address address;
    private Phone phone;
    private Email email;
    private ApplicationDate applicationDate;
    private Priority priority;
    private Status status;
    private Boolean isGhostedOrRejected;
    private Status lastStage;
    private List<Interview> interviews;

    public InternshipApplicationBuilder() {
        company = new Company(DEFAULT_COMPANY);
        role = new Role(DEFAULT_ROLE);
        address = new Address(DEFAULT_ADDRESS);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        try {
            applicationDate = new ApplicationDate(LocalDate.parse(DEFAULT_APPLICATION_DATE,
                DateTimeFormatter.ofPattern("dd MM yyyy")));
        } catch (DateTimeParseException e) {
            e.printStackTrace();
        }
        priority = new Priority(DEFAULT_PRIORITY);
        status = DEFAULT_STATUS;
        isGhostedOrRejected = DEFAULT_IS_GHOSTED_OR_REJECTED;
        lastStage = DEFAULT_LAST_STAGE;
        //default interviews is nil
        interviews = new ArrayList<>();
    }

    /**
     * Initializes the InternshipApplicationBuilder with the data of {@code toCopy}.
     */
    public InternshipApplicationBuilder(InternshipApplication toCopy) {
        company = toCopy.getCompany();
        role = toCopy.getRole();
        address = toCopy.getAddress();
        phone = toCopy.getPhone();
        email = toCopy.getEmail();
        applicationDate = toCopy.getApplicationDate();
        priority = toCopy.getPriority();
        status = toCopy.getStatus();
        isGhostedOrRejected = toCopy.getIsGhostedOrRejected();
        lastStage = toCopy.getLastStage();
        interviews = toCopy.getInterviews();
    }

    /**
     * Sets the {@code Company} of the {@code InternshipApplication} that we are building.
     */
    public InternshipApplicationBuilder withCompany(String company) {
        this.company = new Company(company);
        return this;
    }

    /**
     * Sets the {@code Role} of the {@code InternshipApplication} that we are building.
     */
    public InternshipApplicationBuilder withRole(String role) {
        this.role = new Role(role);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code InternshipApplication} that we are building.
     */
    public InternshipApplicationBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code InternshipApplication} that we are building.
     */
    public InternshipApplicationBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code InternshipApplication} that we are building.
     */
    public InternshipApplicationBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Priority} of the {@code InternshipApplication} that we are building.
     */
    public InternshipApplicationBuilder withPriority(Integer priority) {
        this.priority = new Priority(priority);
        return this;
    }

    /**
     * Overloaded withPriority method to set priority from String.
     */
    public InternshipApplicationBuilder withPriority(String priority) {
        return withPriority(Integer.parseInt(priority));
    }

    /**
     * Sets the {@code ApplicationDate} of the {@code InternshipApplication} that we are building.
     */
    public InternshipApplicationBuilder withApplicationDate(ApplicationDate applicationDate) {
        this.applicationDate = applicationDate;
        return this;
    }

    /**
     * Overloaded withApplicationDate method to set date from String.
     */
    public InternshipApplicationBuilder withApplicationDate(String applicationDate) {
        try {
            this.applicationDate = new ApplicationDate(LocalDate.parse(applicationDate, DateTimeFormatter.ofPattern(
                "dd MM yyyy")));
            return this;
        } catch (DateTimeParseException e) {
            System.err.println("error in parsing date");
            return this;
        }
    }

    /**
     * Returns the {@code ApplicationDate} of the {@code InternshipApplication} that we are building.
     */
    public ApplicationDate getApplicationDate() {
        return applicationDate;
    }

    /**
     * Sets the {@code Status} of the {@code InternshipApplication} that we are building.
     */
    public InternshipApplicationBuilder withStatus(Status status) {
        this.status = status;
        return this;
    }

    /**
     * Overloaded withStatus method to set status from String.
     */
    public InternshipApplicationBuilder withStatus(String status) {
        return withStatus(Status.valueOf(status));
    }

    /**
     * Sets the {@code lastStage} of the {@code InternshipApplication} that we are building.
     */
    public InternshipApplicationBuilder withLastStage(Status lastStage) {
        this.lastStage = lastStage;
        return this;
    }

    /**
     * Overloaded withStatus method to set lastStage from String.
     */
    public InternshipApplicationBuilder withLastStage(String lastStage) {
        return withLastStage(Status.valueOf(lastStage));
    }

    /**
     * Adds an Interview object into the array list of interviews.
     */
    public InternshipApplicationBuilder withInterview(Interview interview) {
        this.interviews.add(interview);
        return this;
    }

    /**
     * Returns the {@code interviews} of the {@code InternshipApplication} we are building.
     *
     * @return list of interviews
     */
    public List<Interview> getInterview() {
        return interviews;
    }

    /**
     * Builds an internship application with specified fields.
     *
     * @return InternshipApplication built.
     */
    public InternshipApplication build() {
        InternshipApplication internshipApplication = new InternshipApplication(company, role, address, phone, email,
            applicationDate, priority, status);
        internshipApplication = internshipApplication.setLastStage(lastStage);
        return internshipApplication;
    }

    /**
     * Builds the Internship Application object with interviews.
     */
    public InternshipApplication buildWithInterviews() {
        InternshipApplication internshipApplication =
            new InternshipApplication(company, role, address, phone, email, applicationDate, priority, status);
        for (Interview interview : interviews) {
            internshipApplication.addInterview(interview);
        }
        return internshipApplication;
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
}
