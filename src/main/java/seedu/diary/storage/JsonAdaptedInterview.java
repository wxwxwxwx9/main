package seedu.diary.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.diary.commons.exceptions.IllegalValueException;
import seedu.diary.commons.util.BooleanUtil;
import seedu.diary.model.internship.Address;
import seedu.diary.model.internship.ApplicationDate;
import seedu.diary.model.internship.interview.Interview;

/**
 * Jackson-friendly version of {@code Interview}.
 */
public class JsonAdaptedInterview {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Interview's %s field is missing!";

    private final String isOnline;
    private final String interviewAddress;
    private final String interviewDate;

    /**
     * Constructs a {@code JsonAdaptedInterview} with the given interview details.
     */
    @JsonCreator
    public JsonAdaptedInterview(@JsonProperty("interviewAddress") String interviewAddress,
        @JsonProperty("isOnline") String isOnline,
        @JsonProperty("interviewDate") String interviewDate) {
        this.isOnline = isOnline;
        this.interviewAddress = interviewAddress;
        this.interviewDate = interviewDate;
    }

    /**
     * Converts a given {@code Interview} into this class for Jackson use.
     */
    public JsonAdaptedInterview(Interview source) {
        interviewAddress = source.getInterviewAddress().value;
        interviewDate = source.getDate().toString();
        isOnline = Boolean.toString(source.getIsOnline());
    }

    /**
     * Converts this Jackson-friendly adapted interview object into the model's {@code Interview} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted interview.
     */
    public Interview toModelType() throws IllegalValueException {
        if (interviewAddress == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(interviewAddress)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(interviewAddress);

        ApplicationDate modelDate;
        if (interviewDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                ApplicationDate.class.getSimpleName()));
        }
        if (!ApplicationDate.isValidApplicationDate(interviewDate)) {
            throw new IllegalValueException(ApplicationDate.MESSAGE_CONSTRAINTS);
        }
        modelDate = new ApplicationDate(interviewDate);

        if (isOnline == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Boolean.class.getSimpleName()));
        }
        if (!BooleanUtil.isValidBoolean(isOnline)) {
            throw new IllegalValueException(BooleanUtil.INVALID_BOOLEAN);
        }
        Boolean modelIsOnline = Boolean.parseBoolean(isOnline);

        return Interview.createInterview(modelIsOnline, modelDate, modelAddress);
    }
}
