package seedu.diary.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.diary.commons.exceptions.IllegalValueException;
import seedu.diary.model.InternshipDiary;
import seedu.diary.model.ReadOnlyInternshipDiary;
import seedu.diary.model.internship.InternshipApplication;

/**
 * An Immutable InternshipDiary that is serializable to JSON format.
 */
@JsonRootName(value = "internshipDiary")
class JsonSerializableInternshipDiary {

    public static final String MESSAGE_DUPLICATE_INTERNSHIP = "Internship list contains duplicate internship(s).";

    private final List<JsonAdaptedInternship> internships = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableInternshipDiary} with the given internship applications.
     */
    @JsonCreator
    public JsonSerializableInternshipDiary(@JsonProperty("internships") List<JsonAdaptedInternship> internships) {
        this.internships.addAll(internships);
    }

    /**
     * Converts a given {@code ReadOnlyInternshipDiary} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableInternshipDiary}.
     */
    public JsonSerializableInternshipDiary(ReadOnlyInternshipDiary source) {
        internships.addAll(source.getAllInternshipList().stream()
            .map(JsonAdaptedInternship::new).collect(Collectors.toList()));
    }

    /**
     * Converts this diary book into the model's {@code InternshipDiary} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public InternshipDiary toModelType() throws IllegalValueException {
        InternshipDiary internshipDiary = new InternshipDiary();
        for (JsonAdaptedInternship jsonAdaptedInternship : internships) {
            InternshipApplication internship = jsonAdaptedInternship.toModelType();
            if (internshipDiary.hasInternship(internship)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_INTERNSHIP);
            }
            internshipDiary.loadInternshipApplication(internship);
        }
        return internshipDiary;
    }

}
