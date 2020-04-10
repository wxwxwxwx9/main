package seedu.diary.testutil;

import seedu.diary.model.InternshipDiary;
import seedu.diary.model.internship.InternshipApplication;

/**
 * A utility class to help with building InternshipDiary objects.
 * Example usage: <br>
 * {@code InternshipDiary diary =
 * new InternshipDiaryBuilder().withInternshipApplication(new InternshipApplication("Google", ...)).build();}
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
        internshipDiary.loadInternshipApplication(application);
        return this;
    }

    public InternshipDiary build() {
        return internshipDiary;
    }
}
