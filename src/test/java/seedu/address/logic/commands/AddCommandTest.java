package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.InternshipDiary;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyInternshipDiary;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.internship.InternshipApplication;
import seedu.address.model.statistics.Statistics;
import seedu.address.testutil.InternshipApplicationBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullInternshipApplication_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_internshipApplicationAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingInternshipAdded modelStub = new ModelStubAcceptingInternshipAdded();
        InternshipApplication validInternshipApplication = new InternshipApplicationBuilder().build();

        CommandResult commandResult = new AddCommand(validInternshipApplication).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validInternshipApplication),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validInternshipApplication), modelStub.internshipsAdded);
    }

    @Test
    public void execute_duplicateInternshipApplication_throwsCommandException() {
        InternshipApplication validInternshipApplication = new InternshipApplicationBuilder().build();
        AddCommand addCommand = new AddCommand(validInternshipApplication);
        ModelStub modelStub = new ModelStubWithInternshipApplication(validInternshipApplication);

        assertThrows(CommandException.class,
                AddCommand.MESSAGE_DUPLICATE_INTERNSHIP, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        InternshipApplication nus = new InternshipApplicationBuilder().withCompany("NUS").build();
        InternshipApplication ntu = new InternshipApplicationBuilder().withCompany("NTU").build();
        AddCommand addNusCommand = new AddCommand(nus);
        AddCommand addNtuCommand = new AddCommand(ntu);

        // same object -> returns true
        assertTrue(addNusCommand.equals(addNusCommand));

        // same values -> returns true
        AddCommand addNUsCommandCopy = new AddCommand(nus);
        assertTrue(addNusCommand.equals(addNUsCommandCopy));

        // different types -> returns false
        assertFalse(addNusCommand.equals(1));

        // null -> returns false
        assertFalse(addNusCommand.equals(null));

        // different person -> returns false
        assertFalse(addNusCommand.equals(addNtuCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getInternshipDiaryFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setInternshipDiaryFilePath(Path internshipDiaryFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setInternshipDiary(ReadOnlyInternshipDiary internshipDiary) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyInternshipDiary getInternshipDiary() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasInternshipApplication(InternshipApplication internshipApplication) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteInternshipApplication(InternshipApplication target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addInternshipApplication(InternshipApplication internshipApplication) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setInternshipApplication(InternshipApplication target, InternshipApplication editedInternship) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<InternshipApplication> getFilteredInternshipApplicationList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredInternshipApplicationList(Predicate<InternshipApplication> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredInternshipApplicationList(Comparator<InternshipApplication> comparator) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Statistics getStatistics() {
            throw new AssertionError("This method should not be called.");
        }

    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithInternshipApplication extends ModelStub {
        private final InternshipApplication internshipApplication;

        ModelStubWithInternshipApplication(InternshipApplication internshipApplication) {
            requireNonNull(internshipApplication);
            this.internshipApplication = internshipApplication;
        }

        public boolean hasInternshipApplication(InternshipApplication internshipApplication) {
            requireNonNull(internshipApplication);
            return this.internshipApplication.isSameInternshipApplication(internshipApplication);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingInternshipAdded extends ModelStub {
        final ArrayList<InternshipApplication> internshipsAdded = new ArrayList<>();

        @Override
        public boolean hasInternshipApplication(InternshipApplication internshipApplication) {
            requireNonNull(internshipApplication);
            return internshipsAdded.stream().anyMatch(internshipApplication::isSameInternshipApplication);
        }

        @Override
        public void addInternshipApplication(InternshipApplication internshipApplication) {
            requireNonNull(internshipApplication);
            internshipsAdded.add(internshipApplication);
        }

        @Override
        public ReadOnlyInternshipDiary getInternshipDiary() {
            return new InternshipDiary();
        }
    }

}
