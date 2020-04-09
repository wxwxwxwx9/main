package seedu.diary.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.diary.commons.core.Messages;
import seedu.diary.commons.exceptions.IllegalValueException;
import seedu.diary.commons.util.BooleanUtil;
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
 * Jackson-friendly version of {@link InternshipApplication}.
 */
class JsonAdaptedInternship {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Internship's %s field is missing!";
    public static final String MESSAGE_DUPLICATE_INTERVIEW = "Interviews list contains duplicate interview!";

    private final String company;
    private final String role;
    private final String address;
    private final String phone;
    private final String email;
    private final String applicationDate;
    private final String priority;
    private final String status;
    private final List<JsonAdaptedInterview> interviews = new ArrayList<>();
    private final String isArchived;
    private final String lastStage;

    /**
     * Constructs a {@code JsonAdaptedInternship} with the given internship application details.
     */
    @JsonCreator
    public JsonAdaptedInternship(@JsonProperty("company") String company, @JsonProperty("role") String role,
        @JsonProperty("diary") String address, @JsonProperty("phone") String phone,
        @JsonProperty("email") String email, @JsonProperty("applicationDate") String applicationDate,
        @JsonProperty("priority") String priority, @JsonProperty("status") String status,
        @JsonProperty("interviews") List<JsonAdaptedInterview> interviews,
        @JsonProperty("isArchived") String isArchived, @JsonProperty("lastStage") String lastStage) {
        this.company = company;
        this.role = role;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.applicationDate = applicationDate;
        this.priority = priority;
        this.status = status;
        this.interviews.addAll(interviews);
        this.isArchived = isArchived;
        this.lastStage = lastStage;
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
        applicationDate = source.getApplicationDate().toString();
        priority = Integer.toString(source.getPriority().fullPriority);
        status = source.getStatus().name();
        interviews.addAll(source.getInterviews()
            .stream().map(JsonAdaptedInterview::new).collect(Collectors.toList()));
        isArchived = source.isArchived().toString();
        lastStage = source.getLastStage().toString();
    }

    /**
     * Converts this Jackson-friendly adapted internship application object
     * into the model's {@code InternshipApplication} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted internship application
     * object.
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

        ApplicationDate modelDate;
        if (applicationDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                ApplicationDate.class.getSimpleName()));
        }
        if (!ApplicationDate.isValidApplicationDate(applicationDate)) {
            throw new IllegalValueException(ApplicationDate.MESSAGE_CONSTRAINTS);
        }
        modelDate = new ApplicationDate(applicationDate);

        if (priority == null) {
            throw new IllegalValueException(
                String.format(MISSING_FIELD_MESSAGE_FORMAT, Priority.class.getSimpleName()));
        }
        if (!Priority.isValidPriority(priority)) {
            throw new IllegalValueException(Priority.MESSAGE_CONSTRAINTS);
        }
        final Priority modelPriority = new Priority(priority);

        if (status == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Status.class.getSimpleName()));
        }
        if (!Status.isValidStatus(status)) {
            throw new IllegalValueException(Status.MESSAGE_CONSTRAINTS);
        }
        final Status modelStatus = Status.valueOf(status);

        if (isArchived == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Messages.IS_ARCHIVED));
        }
        if (!BooleanUtil.isValidBoolean(isArchived)) {
            throw new IllegalValueException(BooleanUtil.INVALID_BOOLEAN);
        }
        final Boolean modelIsArchived = Boolean.valueOf(isArchived);

        InternshipApplication internshipApplication = new InternshipApplication(modelCompany, modelRole, modelAddress,
            modelPhone, modelEmail, modelDate, modelPriority, modelStatus);
        final Status modelLastStage = Status.valueOf(lastStage);
        internshipApplication = internshipApplication.setLastStage(modelLastStage);

        for (JsonAdaptedInterview jsonAdaptedInterview : interviews) {
            Interview interview = jsonAdaptedInterview.toModelType();
            if (internshipApplication.hasInterview(interview)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_INTERVIEW);
            }
            internshipApplication.addInterview(interview);
        }

        if (modelIsArchived) {
            internshipApplication = internshipApplication.archive();
        }

        return internshipApplication;
    }

}
