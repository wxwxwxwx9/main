package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showInternshipApplicationAtIndex;
import static seedu.address.logic.commands.CommandTestUtil.showInternshipApplicationAtIndices;
import static seedu.address.testutil.PredicateUtil.prepareCompanyPredicate;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_INTERNSHIP_APPLICATION;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_INTERNSHIP_APPLICATION;
import static seedu.address.testutil.TypicalIndexes.INDEX_SET_FIRST_INTERNSHIP_APPLICATION;
import static seedu.address.testutil.TypicalIndexes.INDEX_SET_SECOND_INTERNSHIP_APPLICATION;
import static seedu.address.testutil.TypicalInternshipApplications.getTypicalInternshipDiary;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.commandexecutiontype.CommandExecutionType;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.internship.InternshipApplication;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalInternshipDiary(), new UserPrefs());

    @Test
    public void execute_byIndexValidIndexUnfilteredList_success() {
        InternshipApplication internshipApplicationToDelete =
                model.getFilteredInternshipApplicationList().get(INDEX_FIRST_INTERNSHIP_APPLICATION.getZeroBased());
        DeleteCommand deleteCommand =
                new DeleteCommand(INDEX_FIRST_INTERNSHIP_APPLICATION, CommandExecutionType.BY_INDEX);

        String expectedMessage =
            String.format(DeleteCommand.MESSAGE_DELETE_INTERNSHIP_SUCCESS, internshipApplicationToDelete);

        ModelManager expectedModel = new ModelManager(model.getInternshipDiary(), new UserPrefs());
        expectedModel.deleteInternshipApplication(internshipApplicationToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_byIndexInvalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredInternshipApplicationList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex, CommandExecutionType.BY_INDEX);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_INTERNSHIP_DISPLAYED_INDEX);
    }

    @Test
    public void execute_byIndexValidIndexFilteredList_success() {
        showInternshipApplicationAtIndex(model, INDEX_FIRST_INTERNSHIP_APPLICATION);

        InternshipApplication internshipApplicationToDelete =
                model.getFilteredInternshipApplicationList().get(INDEX_FIRST_INTERNSHIP_APPLICATION.getZeroBased());
        DeleteCommand deleteCommand =
                new DeleteCommand(INDEX_FIRST_INTERNSHIP_APPLICATION, CommandExecutionType.BY_INDEX);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_INTERNSHIP_SUCCESS,
            internshipApplicationToDelete);

        Model expectedModel = new ModelManager(model.getInternshipDiary(), new UserPrefs());
        expectedModel.deleteInternshipApplication(internshipApplicationToDelete);
        showNoInternshipApplication(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_byIndexInvalidIndexFilteredList_throwsCommandException() {
        showInternshipApplicationAtIndex(model, INDEX_FIRST_INTERNSHIP_APPLICATION);

        Index outOfBoundIndex = INDEX_SECOND_INTERNSHIP_APPLICATION;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getInternshipDiary().getDisplayedInternshipList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex, CommandExecutionType.BY_INDEX);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_INTERNSHIP_DISPLAYED_INDEX);
    }

    @Test
    public void execute_byIndicesValidIndicesUnfilteredList_success() {
        // prepare internship applications to delete
        List<InternshipApplication> internshipApplicationsToDelete = new ArrayList<>();

        // create expected model and delete the appropriate internship applications
        ModelManager expectedModel = new ModelManager(model.getInternshipDiary(), new UserPrefs());
        for (Index index: INDEX_SET_FIRST_INTERNSHIP_APPLICATION) {
            InternshipApplication internshipApplicationToDelete =
                model.getFilteredInternshipApplicationList().get(index.getZeroBased());
            expectedModel.deleteInternshipApplication(internshipApplicationToDelete);
            internshipApplicationsToDelete.add(internshipApplicationToDelete);
        }

        // create command
        DeleteCommand deleteCommand =
            new DeleteCommand(INDEX_SET_FIRST_INTERNSHIP_APPLICATION, CommandExecutionType.BY_INDICES);

        String expectedMessage =
            String.format(DeleteCommand.MESSAGE_DELETE_INTERNSHIP_SUCCESS, internshipApplicationsToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_byIndicesInvalidIndicesUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredInternshipApplicationList().size() + 1);
        List<Index> mockIndexes = new ArrayList<>(INDEX_SET_FIRST_INTERNSHIP_APPLICATION);
        mockIndexes.add(outOfBoundIndex);

        DeleteCommand deleteCommand =
            new DeleteCommand(mockIndexes, CommandExecutionType.BY_INDICES);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_INTERNSHIP_DISPLAYED_INDEX);
    }

    @Test
    public void execute_byIndicesValidIndexFilteredList_success() {
        showInternshipApplicationAtIndices(model, INDEX_SET_FIRST_INTERNSHIP_APPLICATION);

        // prepare internship applications to delete
        List<InternshipApplication> internshipApplicationsToDelete = new ArrayList<>();

        // create expected model and delete the appropriate internship applications
        ModelManager expectedModel = new ModelManager(model.getInternshipDiary(), new UserPrefs());
        for (Index index: INDEX_SET_FIRST_INTERNSHIP_APPLICATION) {
            InternshipApplication internshipApplicationToDelete =
                model.getFilteredInternshipApplicationList().get(index.getZeroBased());
            expectedModel.deleteInternshipApplication(internshipApplicationToDelete);
            internshipApplicationsToDelete.add(internshipApplicationToDelete);
        }

        // create command
        DeleteCommand deleteCommand =
            new DeleteCommand(INDEX_SET_FIRST_INTERNSHIP_APPLICATION, CommandExecutionType.BY_INDICES);

        String expectedMessage =
            String.format(DeleteCommand.MESSAGE_DELETE_INTERNSHIP_SUCCESS, internshipApplicationsToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_byIndicesInvalidIndicesFilteredList_throwsCommandException() {
        showInternshipApplicationAtIndices(model, INDEX_SET_FIRST_INTERNSHIP_APPLICATION);

        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredInternshipApplicationList().size() + 1);
        List<Index> mockIndexes = new ArrayList<>(INDEX_SET_FIRST_INTERNSHIP_APPLICATION);
        mockIndexes.add(outOfBoundIndex);

        DeleteCommand deleteCommand =
            new DeleteCommand(mockIndexes, CommandExecutionType.BY_INDICES);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_INTERNSHIP_DISPLAYED_INDEX);
    }

    @Test
    public void execute_byFieldValidFieldUnfilteredList_success() {
        // company field predicate
        Predicate<InternshipApplication> validCompanyField = prepareCompanyPredicate("google");

        // create expected model and filter and get the appropriate internship applications to delete
        ModelManager expectedModel = new ModelManager(model.getInternshipDiary(), new UserPrefs());
        expectedModel.updateFilteredInternshipApplicationList(validCompanyField);
        List<InternshipApplication> internshipApplicationsToDelete = new ArrayList<>();
        for (InternshipApplication toDelete : expectedModel.getFilteredInternshipApplicationList()) {
            internshipApplicationsToDelete.add(toDelete);
        }
        expectedModel.updateFilteredInternshipApplicationList(Model.PREDICATE_SHOW_ALL_INTERNSHIPS);

        // delete the filtered internship applications
        for (InternshipApplication toDelete : internshipApplicationsToDelete) {
            expectedModel.deleteInternshipApplication(toDelete);
        }

        // create command
        DeleteCommand deleteCommand =
            new DeleteCommand(validCompanyField, CommandExecutionType.BY_FIELD);

        String expectedMessage =
            String.format(DeleteCommand.MESSAGE_DELETE_INTERNSHIP_SUCCESS, internshipApplicationsToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_byFieldValidFieldFilteredList_success() {
        showInternshipApplicationAtIndices(model, INDEX_SET_FIRST_INTERNSHIP_APPLICATION);

        // company field predicate
        Predicate<InternshipApplication> validCompanyField = prepareCompanyPredicate("google");

        // create expected model and filter and get the appropriate internship applications to delete
        ModelManager expectedModel = new ModelManager(model.getInternshipDiary(), new UserPrefs());
        expectedModel.updateFilteredInternshipApplicationList(validCompanyField);
        List<InternshipApplication> internshipApplicationsToDelete = new ArrayList<>();
        for (InternshipApplication toDelete : expectedModel.getFilteredInternshipApplicationList()) {
            internshipApplicationsToDelete.add(toDelete);
        }
        expectedModel.updateFilteredInternshipApplicationList(Model.PREDICATE_SHOW_ALL_INTERNSHIPS);

        // delete the filtered internship applications
        for (InternshipApplication toDelete : internshipApplicationsToDelete) {
            expectedModel.deleteInternshipApplication(toDelete);
        }

        // create command
        DeleteCommand deleteCommand =
            new DeleteCommand(validCompanyField, CommandExecutionType.BY_FIELD);

        String expectedMessage =
            String.format(DeleteCommand.MESSAGE_DELETE_INTERNSHIP_SUCCESS, internshipApplicationsToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);

    }


    @Test
    public void equals() {

        // BY INDEX
        DeleteCommand deleteFirstCommandByIndex =
                new DeleteCommand(INDEX_FIRST_INTERNSHIP_APPLICATION, CommandExecutionType.BY_INDEX);
        DeleteCommand deleteSecondCommandByIndex =
                new DeleteCommand(INDEX_SECOND_INTERNSHIP_APPLICATION, CommandExecutionType.BY_INDEX);

        // same object -> returns true
        assertTrue(deleteFirstCommandByIndex.equals(deleteFirstCommandByIndex));

        // same values -> returns true
        DeleteCommand deleteFirstCommandByIndexCopy =
                new DeleteCommand(INDEX_FIRST_INTERNSHIP_APPLICATION, CommandExecutionType.BY_INDEX);
        assertTrue(deleteFirstCommandByIndex.equals(deleteFirstCommandByIndexCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommandByIndex.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommandByIndex.equals(null));

        // different internship application index -> returns false
        assertFalse(deleteFirstCommandByIndex.equals(deleteSecondCommandByIndex));


        // BY INDICES
        DeleteCommand deleteFirstCommandByIndices =
            new DeleteCommand(INDEX_SET_FIRST_INTERNSHIP_APPLICATION, CommandExecutionType.BY_INDICES);
        DeleteCommand deleteSecondCommandByIndices =
            new DeleteCommand(INDEX_SET_SECOND_INTERNSHIP_APPLICATION, CommandExecutionType.BY_INDICES);

        // same object -> returns true
        assertTrue(deleteFirstCommandByIndices.equals(deleteFirstCommandByIndices));

        // same values -> returns true
        DeleteCommand deleteFirstCommandByIndicesCopy =
            new DeleteCommand(INDEX_SET_FIRST_INTERNSHIP_APPLICATION, CommandExecutionType.BY_INDICES);
        assertTrue(deleteFirstCommandByIndices.equals(deleteFirstCommandByIndicesCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommandByIndices.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommandByIndices.equals(null));

        // different internship application index -> returns false
        assertFalse(deleteFirstCommandByIndices.equals(deleteSecondCommandByIndices));


        // BY FIELD
        DeleteCommand deleteFirstCommandByField =
            new DeleteCommand(prepareCompanyPredicate("google"), CommandExecutionType.BY_FIELD);
        DeleteCommand deleteSecondCommandByField =
            new DeleteCommand(prepareCompanyPredicate("facebook"), CommandExecutionType.BY_FIELD);

        // same object -> returns true
        assertTrue(deleteFirstCommandByField.equals(deleteFirstCommandByField));

        // same values -> returns true
        DeleteCommand deleteFirstCommandByFieldCopy =
            new DeleteCommand(prepareCompanyPredicate("google"), CommandExecutionType.BY_FIELD);
        assertTrue(deleteFirstCommandByField.equals(deleteFirstCommandByFieldCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommandByField.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommandByField.equals(null));

        // different internship application index -> returns false
        assertFalse(deleteFirstCommandByField.equals(deleteSecondCommandByField));

    }

    /**
     * Updates {@code model}'s filtered list to show no applications.
     */
    private void showNoInternshipApplication(Model model) {
        model.updateFilteredInternshipApplicationList(p -> false);

        assertTrue(model.getFilteredInternshipApplicationList().isEmpty());
    }

}
