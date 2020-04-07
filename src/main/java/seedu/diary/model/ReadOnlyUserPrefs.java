package seedu.diary.model;

import java.nio.file.Path;

import seedu.diary.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getInternshipDiaryFilePath();
}
