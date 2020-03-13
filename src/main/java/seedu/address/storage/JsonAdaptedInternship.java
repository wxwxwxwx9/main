package seedu.address.storage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.internship.Address;
import seedu.address.model.internship.Company;
import seedu.address.model.internship.Email;
import seedu.address.model.internship.InternshipApplication;
import seedu.address.model.internship.Phone;
import seedu.address.model.internship.Priority;
import seedu.address.model.internship.Role;
import seedu.address.model.status.Status;

/**
 * Jackson-friendly version of {@link InternshipApplication}.
 */
class JsonAdaptedInternship {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Internship's %s field is missing!";
    private static final String DATE_TIME_PATTERN = "dd/MM/yyyy";
    private static final String ERROR_MESSAGE_PLACEHOLDER = "Error message.";

    private final String company;
    private final String role;
    private final String address;
    private final String phone;
    private final String email;
    private final String applicationDate;
    private final String priority;
    private final String status;

    // Company company, Role role, Address address, Phone phone, Email email,
    // Date applicationDate, Priority priority, Status status

    /**
     * Constructs a {@code JsonAdaptedInternship} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedInternship(@JsonProperty("company") String company, @JsonProperty("role") String role,
            @JsonProperty("address") String address, @JsonProperty("phone") String phone,
            @JsonProperty("email") String email, @JsonProperty("applicationDate") String applicationDate,
            @JsonProperty("priority") String priority, @JsonProperty("status") String status) {
        this.company = company;
        this.role = role;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.applicationDate = applicationDate;
        this.priority = priority;
        this.status = status;
    }

    /**
     * Converts a given {@code InternshipApplication} into this class for Jackson use.
     */
    public JsonAdaptedInternship(InternshipApplication source) {
        company = source.getCompany().fullCompany;
        role = source.getRole().fullRole;
        address = source.getAddress().value;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        applicationDate = (new SimpleDateFormat(DATE_TIME_PATTERN)).format(source.getApplicationDate());
        priority = Integer.toString(source.getPriority().fullPriority);
        status = source.getStatus().name();
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Internship} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public InternshipApplication toModelType() throws IllegalValueException {
        if (company == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Company.class.getSimpleName()));
        }
        if (!Company.isValidCompany(company)) {
            throw new IllegalValueException(Company.MESSAGE_CONSTRAINTS);
        }
        final Company modelCompany = new Company(company);

        if (role == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Role.class.getSimpleName()));
        }
        if (!Role.isValidRole(role)) {
            throw new IllegalValueException(Role.MESSAGE_CONSTRAINTS);
        }
        final Role modelRole = new Role(role);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        Date modelDate = null;
        if (applicationDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_TIME_PATTERN);
        try {
            modelDate = dateFormat.parse(applicationDate);
        } catch (ParseException e) {
            throw new IllegalValueException(ERROR_MESSAGE_PLACEHOLDER);
        }
        if (modelDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }

        if (priority == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Priority.class.getSimpleName()));
        }
        if (!priority.matches("-?(0|[1-9]\\d*)")) { // Check if integer.
            throw new IllegalValueException(Priority.MESSAGE_CONSTRAINTS);
        }
        final int intPriority = Integer.parseInt(priority);
        if (!Priority.isValidPriority(intPriority)) {
            throw new IllegalValueException(Priority.MESSAGE_CONSTRAINTS);
        }
        final Priority modelPriority = new Priority(intPriority);

        if (status == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Status.class.getSimpleName()));
        }
        try {
            Status.valueOf(status);
        } catch (IllegalArgumentException e) {
            throw new IllegalValueException(ERROR_MESSAGE_PLACEHOLDER);
        }
        final Status modelStatus = Status.valueOf(status);

        return new InternshipApplication(modelCompany, modelRole, modelAddress,
                modelPhone, modelEmail, modelDate, modelPriority, modelStatus);
    }

}
