package seedu.address.testutil;

import java.util.Date;

import seedu.address.model.internship.Address;
import seedu.address.model.internship.Company;
import seedu.address.model.internship.Email;
import seedu.address.model.internship.InternshipApplication;
import seedu.address.model.internship.Phone;
import seedu.address.model.internship.Priority;
import seedu.address.model.internship.Role;
import seedu.address.model.status.Status;

/**
 * A utility class to help with building Internship Application objects.
 */
public class InternshipApplicationBuilder {

    public static final String DEFAULT_COMPANY = "Google";
    public static final String DEFAULT_ROLE = "Product Manager";
    public static final String DEFAULT_ADDRESS = "1600 Amphitheatre Parkway";
    public static final String DEFAULT_PHONE = "99999999";
    public static final String DEFAULT_EMAIL = "richardma@gmail.com";
    public static final Date DEFAULT_APPLICATION_DATE = new Date(2020, 01, 01);
    public static final Integer DEFAULT_PRIORITY = 10;
    public static final Status DEFAULT_STATUS = Status.APPLICATION_DONE;

    private Company company;
    private Role role;
    private Address address;
    private Phone phone;
    private Email email;
    private Date applicationDate;
    private Priority priority;
    private Status status;

    public InternshipApplicationBuilder() {
        company = new Company(DEFAULT_COMPANY);
        role = new Role(DEFAULT_ROLE);
        address = new Address(DEFAULT_ADDRESS);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        applicationDate = DEFAULT_APPLICATION_DATE;
        priority = new Priority(DEFAULT_PRIORITY);
        status = DEFAULT_STATUS;
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
     * Sets the {@code ApplicationDate} of the {@code InternshipApplication} that we are building.
     */
    public InternshipApplicationBuilder withApplicationDate(Date applicationDate) {
        this.applicationDate = applicationDate;
        return this;
    }

    /**
     * Sets the {@code Status} of the {@code InternshipApplication} that we are building.
     */
    public InternshipApplicationBuilder withStatus(Status status) {
        this.status = status;
        return this;
    }

    public InternshipApplication build() {
        return new InternshipApplication(company, role, address, phone, email, applicationDate, priority, status);
    }

}
