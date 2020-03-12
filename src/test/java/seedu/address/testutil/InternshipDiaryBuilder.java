package seedu.address.testutil;

import seedu.address.model.InternshipDiary;
import seedu.address.model.internship.InternshipApplication;

/**
 * A utility class to help with building InternshipDiary objects.
 * Example usage: <br>
 *     {@code InternshipDiary diary =
 *     new InternshipDiaryBuilder().withInternshipApplication(new InternshipApplication("Google", ...)).build();}
 */
public class InternshipDiaryBuilder {

    private InternshipDiary internshipDiary;

    public InternshipDiaryBuilder() {
        internshipDiary = new InternshipDiary();
    }

    public InternshipDiaryBuilder(InternshipDiary internshipDiary) {
        this.internshipDiary = internshipDiary;
    }

    /**
     * Adds a new {@code InternshipApplication} to the {@code InternshipDiary} that we are building.
     */
    public InternshipDiaryBuilder withInternshipApplication(InternshipApplication application) {
        internshipDiary.addInternshipApplication(application);
        return this;
    }

    public InternshipDiary build() {
        return internshipDiary;
    }
}
