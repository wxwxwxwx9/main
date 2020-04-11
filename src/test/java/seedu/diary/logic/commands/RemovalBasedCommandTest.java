package seedu.diary.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.diary.commons.core.commandexecutiontype.RemovalBasedCommandExecutionType.BY_FIELD;
import static seedu.diary.commons.core.commandexecutiontype.RemovalBasedCommandExecutionType.BY_INDEX;
import static seedu.diary.commons.core.commandexecutiontype.RemovalBasedCommandExecutionType.BY_INDICES;
import static seedu.diary.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.diary.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.diary.logic.commands.CommandTestUtil.showInternshipApplicationAtIndex;
import static seedu.diary.logic.commands.CommandTestUtil.showInternshipApplicationAtIndices;
import static seedu.diary.testutil.PredicateUtil.prepareEmailPredicate;
import static seedu.diary.testutil.PredicateUtil.prepareStatusPredicate;
import static seedu.diary.testutil.TypicalIndexes.INDEX_FIRST_INTERNSHIP_APPLICATION;
import static seedu.diary.testutil.TypicalIndexes.INDEX_LIST_FIRST_INTERNSHIP_APPLICATION;
import static seedu.diary.testutil.TypicalIndexes.INDEX_LIST_SECOND_INTERNSHIP_APPLICATION;
import static seedu.diary.testutil.TypicalIndexes.INDEX_SECOND_INTERNSHIP_APPLICATION;
import static seedu.diary.testutil.TypicalInternshipApplications.getTypicalInternshipDiary;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.diary.commons.core.Messages;
import seedu.diary.commons.core.index.Index;
import seedu.diary.model.Model;
import seedu.diary.model.ModelManager;
import seedu.diary.model.UserPrefs;
import seedu.diary.model.internship.InternshipApplication;
import seedu.diary.model.status.Status;

/**
 * Contains integration tests (interaction with the Model, DeleteCommand) and unit tests for
 * {@code RemovalBasedCommand}.
 * Integration tests for ArchiveCommand and UnarchiveCommand are deemed unnecessary because they are similar in nature
 * to DeleteCommand. Testing integration with DeleteCommand will suffice.
 */
public class RemovalBasedCommandTest {

    private final Predicate<InternshipApplication> validPredicate = prepareStatusPredicate(Status.APPLIED.toString());
    private final Predicate<InternshipApplication> secondValidPredicate =
        prepareStatusPredicate(Status.WISHLIST.toString());
    private final Predicate<InternshipApplication> invalidPredicate = prepareEmailPredicate(Status.APPLIED.toString());

    private List<Index> indices;
    private List<Index> secondIndices;

    private final String commandWord = DeleteCommand.COMMAND_WORD;

    private final Model model = new ModelManager(getTypicalInternshipDiary(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalInternshipDiary(), new UserPrefs());

    @BeforeEach
    public void setUp() {
        // to prevent leakage between test cases, where the list is mutated
        indices = new ArrayList<>(INDEX_LIST_FIRST_INTERNSHIP_APPLICATION);
        secondIndices = new ArrayList<>(INDEX_LIST_SECOND_INTERNSHIP_APPLICATION);
    }

    @Test
    public void execute_byIndexValidIndexUnfilteredListDeleteCommand_success() {

        RemovalBasedCommand removalBasedDeleteCommand =
            new RemovalBasedCommand(INDEX_FIRST_INTERNSHIP_APPLICATION, BY_INDEX, commandWord);

        InternshipApplication internshipApplicationToExecuteOn =
            model.getFilteredInternshipApplicationList().get(INDEX_FIRST_INTERNSHIP_APPLICATION.getZeroBased());

        String expectedMessage = String.format(
            RemovalBasedCommand.MESSAGE_COMMAND_INTERNSHIP_SUCCESS.apply(commandWord),
            internshipApplicationToExecuteOn + "\n"
        );

        expectedModel.deleteInternshipApplication(internshipApplicationToExecuteOn);

        assertCommandSuccess(removalBasedDeleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_byIndexInvalidIndexUnfilteredListDeleteCommand_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredInternshipApplicationList().size() + 1);

        RemovalBasedCommand removalBasedDeleteCommand =
            new RemovalBasedCommand(outOfBoundIndex, BY_INDEX, commandWord);

        assertCommandFailure(removalBasedDeleteCommand, model, Messages.MESSAGE_INVALID_INTERNSHIP_DISPLAYED_INDEX);
    }

    @Test
    public void execute_byIndexValidIndexFilteredListDeleteCommand_success() {
        showInternshipApplicationAtIndex(model, INDEX_FIRST_INTERNSHIP_APPLICATION);

        RemovalBasedCommand removalBasedDeleteCommand =
            new RemovalBasedCommand(INDEX_FIRST_INTERNSHIP_APPLICATION, BY_INDEX, commandWord);

        InternshipApplication internshipApplicationToExecuteOn =
            model.getFilteredInternshipApplicationList().get(INDEX_FIRST_INTERNSHIP_APPLICATION.getZeroBased());

        String expectedMessage = String.format(
            RemovalBasedCommand.MESSAGE_COMMAND_INTERNSHIP_SUCCESS.apply(commandWord),
            internshipApplicationToExecuteOn + "\n"
        );

        showInternshipApplicationAtIndex(expectedModel, INDEX_FIRST_INTERNSHIP_APPLICATION);
        expectedModel.deleteInternshipApplication(internshipApplicationToExecuteOn);

        assertCommandSuccess(removalBasedDeleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_byIndexInvalidIndexFilteredListDeleteCommand_throwsCommandException() {
        showInternshipApplicationAtIndex(model, INDEX_FIRST_INTERNSHIP_APPLICATION);

        Index outOfBoundIndex = INDEX_SECOND_INTERNSHIP_APPLICATION;
        // ensures that outOfBoundIndex is still in bounds of unfiltered diary book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getInternshipDiary().getDisplayedInternshipList().size());

        RemovalBasedCommand removalBasedDeleteCommand =
            new RemovalBasedCommand(outOfBoundIndex, BY_INDEX, commandWord);

        assertCommandFailure(removalBasedDeleteCommand, model, Messages.MESSAGE_INVALID_INTERNSHIP_DISPLAYED_INDEX);
    }

    @Test
    public void execute_byIndicesValidIndicesUnfilteredListDeleteCommand_success() {

        String deletedInternshipApplications = "";

        // create expected model and delete the appropriate internship applications
        for (Index index : indices) {
            InternshipApplication internshipApplicationToDelete =
                model.getFilteredInternshipApplicationList().get(index.getZeroBased());
            expectedModel.deleteInternshipApplication(internshipApplicationToDelete);
            deletedInternshipApplications += internshipApplicationToDelete + "\n\n";
        }

        String expectedMessage = String.format(
            RemovalBasedCommand.MESSAGE_COMMAND_INTERNSHIP_SUCCESS.apply(commandWord),
            deletedInternshipApplications);

        // create command
        RemovalBasedCommand removalBasedDeleteCommand =
            new RemovalBasedCommand(indices, BY_INDICES, commandWord);

        assertCommandSuccess(removalBasedDeleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_byIndicesInvalidIndicesUnfilteredListDeleteCommand_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredInternshipApplicationList().size() + 1);
        List<Index> mockIndexes = new ArrayList<>(INDEX_LIST_FIRST_INTERNSHIP_APPLICATION);
        mockIndexes.add(outOfBoundIndex);

        RemovalBasedCommand removalBasedDeleteCommand =
            new RemovalBasedCommand(mockIndexes, BY_INDICES, commandWord);

        String expectedMessage =
            Messages.MESSAGE_INVALID_INTERNSHIP_DISPLAYED_INDEX + ": " + List.of(outOfBoundIndex.getOneBased());

        assertCommandFailure(removalBasedDeleteCommand, model, expectedMessage);
    }

    @Test
    public void execute_byIndicesValidIndexFilteredListDeleteCommand_success() {
        showInternshipApplicationAtIndices(model, indices);
        showInternshipApplicationAtIndices(expectedModel, indices);

        String deletedInternshipApplications = "";

        List<InternshipApplication> toDelete = new ArrayList<>();

        // get internship applications to delete
        for (Index index : indices) {
            InternshipApplication internshipApplicationToDelete =
                expectedModel.getFilteredInternshipApplicationList().get(index.getZeroBased());
            toDelete.add(internshipApplicationToDelete);
            deletedInternshipApplications += internshipApplicationToDelete + "\n\n";
        }

        for (InternshipApplication internshipApplication : toDelete) {
            expectedModel.deleteInternshipApplication(internshipApplication);
        }

        String expectedMessage = String.format(
            RemovalBasedCommand.MESSAGE_COMMAND_INTERNSHIP_SUCCESS.apply(commandWord),
            deletedInternshipApplications);

        RemovalBasedCommand removalBasedDeleteCommand =
            new RemovalBasedCommand(indices, BY_INDICES, commandWord);

        assertCommandSuccess(removalBasedDeleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_byIndicesInvalidIndicesFilteredListDeleteCommand_throwsCommandException() {
        showInternshipApplicationAtIndices(model, INDEX_LIST_FIRST_INTERNSHIP_APPLICATION);

        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredInternshipApplicationList().size() + 1);
        List<Index> mockIndexes = new ArrayList<>(INDEX_LIST_FIRST_INTERNSHIP_APPLICATION);
        mockIndexes.add(outOfBoundIndex);

        RemovalBasedCommand removalBasedDeleteCommand =
            new RemovalBasedCommand(mockIndexes, BY_INDICES, commandWord);

        String expectedMessage =
            Messages.MESSAGE_INVALID_INTERNSHIP_DISPLAYED_INDEX + ": " + List.of(outOfBoundIndex.getOneBased());

        assertCommandFailure(removalBasedDeleteCommand, model, expectedMessage);
    }

    @Test
    public void execute_byFieldValidFieldUnfilteredListDeleteCommand_success() {

        // create expected model and filter and get the appropriate internship applications to delete
        expectedModel.updateFilteredInternshipApplicationList(validPredicate);
        List<InternshipApplication> internshipApplicationsToDelete = new ArrayList<>();

        for (InternshipApplication toDelete : expectedModel.getFilteredInternshipApplicationList()) {
            internshipApplicationsToDelete.add(toDelete);
        }
        expectedModel.updateFilteredInternshipApplicationList(Model.PREDICATE_SHOW_ALL_INTERNSHIPS);

        String deletedInternshipApplications = "";

        // delete the filtered internship applications
        for (InternshipApplication toDelete : internshipApplicationsToDelete) {
            expectedModel.deleteInternshipApplication(toDelete);
            deletedInternshipApplications += toDelete + "\n\n";
        }

        RemovalBasedCommand removalBasedDeleteCommand =
            new RemovalBasedCommand(validPredicate, BY_FIELD, commandWord);

        String expectedMessage = String.format(
            RemovalBasedCommand.MESSAGE_COMMAND_INTERNSHIP_SUCCESS.apply(commandWord),
            deletedInternshipApplications);

        assertCommandSuccess(removalBasedDeleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_byFieldValidFieldFilteredListDeleteCommand_success() {
        showInternshipApplicationAtIndices(model, INDEX_LIST_FIRST_INTERNSHIP_APPLICATION);
        showInternshipApplicationAtIndices(expectedModel, INDEX_LIST_FIRST_INTERNSHIP_APPLICATION);

        // create expected model and filter and get the appropriate internship applications to delete
        expectedModel.updateFilteredInternshipApplicationList(validPredicate);
        List<InternshipApplication> internshipApplicationsToDelete = new ArrayList<>();

        for (InternshipApplication toDelete : expectedModel.getFilteredInternshipApplicationList()) {
            internshipApplicationsToDelete.add(toDelete);
        }
        expectedModel.updateFilteredInternshipApplicationList(Model.PREDICATE_SHOW_ALL_INTERNSHIPS);

        String deletedInternshipApplications = "";

        // delete the filtered internship applications
        for (InternshipApplication toDelete : internshipApplicationsToDelete) {
            expectedModel.deleteInternshipApplication(toDelete);
            deletedInternshipApplications += toDelete + "\n\n";
        }

        RemovalBasedCommand removalBasedDeleteCommand =
            new RemovalBasedCommand(validPredicate, BY_FIELD, commandWord);

        String expectedMessage = String.format(
            RemovalBasedCommand.MESSAGE_COMMAND_INTERNSHIP_SUCCESS.apply(commandWord),
            deletedInternshipApplications);

        assertCommandSuccess(removalBasedDeleteCommand, model, expectedMessage, expectedModel);

    }

    @Test
    public void equals() {

        // BY INDEX
        RemovalBasedCommand firstRemovalBasedDeleteCommandByIndex =
            new RemovalBasedCommand(INDEX_FIRST_INTERNSHIP_APPLICATION, BY_INDEX, commandWord);
        RemovalBasedCommand secondRemovalBasedDeleteCommandByIndex =
            new RemovalBasedCommand(INDEX_SECOND_INTERNSHIP_APPLICATION, BY_INDEX, commandWord);

        // same object -> returns true
        assertTrue(firstRemovalBasedDeleteCommandByIndex.equals(firstRemovalBasedDeleteCommandByIndex));

        // same values -> returns true
        RemovalBasedCommand firstRemovalBasedDeleteCommandByIndexCopy =
            new RemovalBasedCommand(INDEX_FIRST_INTERNSHIP_APPLICATION, BY_INDEX, commandWord);
        assertTrue(firstRemovalBasedDeleteCommandByIndex.equals(firstRemovalBasedDeleteCommandByIndexCopy));

        // different types -> returns false
        assertFalse(firstRemovalBasedDeleteCommandByIndex.equals(1));

        // null -> returns false
        assertFalse(firstRemovalBasedDeleteCommandByIndex.equals(null));

        // different internship application index -> returns false
        assertFalse(firstRemovalBasedDeleteCommandByIndex.equals(secondRemovalBasedDeleteCommandByIndex));


        // BY INDICES
        RemovalBasedCommand firstRemovalBasedDeleteCommandByIndices =
            new RemovalBasedCommand(indices, BY_INDICES, commandWord);
        RemovalBasedCommand secondRemovalBasedDeleteCommandByIndices =
            new RemovalBasedCommand(secondIndices, BY_INDICES, commandWord);

        // same object -> returns true
        assertTrue(firstRemovalBasedDeleteCommandByIndices.equals(firstRemovalBasedDeleteCommandByIndices));

        // same values -> returns true
        RemovalBasedCommand firstRemovalBasedDeleteCommandByIndicesCopy =
            new RemovalBasedCommand(indices, BY_INDICES, commandWord);
        assertTrue(firstRemovalBasedDeleteCommandByIndices.equals(firstRemovalBasedDeleteCommandByIndicesCopy));

        // different types -> returns false
        assertFalse(firstRemovalBasedDeleteCommandByIndices.equals(1));

        // null -> returns false
        assertFalse(firstRemovalBasedDeleteCommandByIndices.equals(null));

        // different internship application indices -> returns false
        assertFalse(firstRemovalBasedDeleteCommandByIndices.equals(secondRemovalBasedDeleteCommandByIndices));


        // BY FIELD
        RemovalBasedCommand firstRemovalBasedDeleteCommandByField =
            new RemovalBasedCommand(validPredicate, BY_FIELD, commandWord);
        RemovalBasedCommand secondRemovalBasedDeleteCommandByField =
            new RemovalBasedCommand(secondValidPredicate, BY_FIELD, commandWord);
        RemovalBasedCommand invalidRemovalBasedDeleteCommandByField =
            new RemovalBasedCommand(invalidPredicate, BY_FIELD, commandWord);

        // same object -> returns true
        assertTrue(firstRemovalBasedDeleteCommandByField.equals(firstRemovalBasedDeleteCommandByField));

        // same values -> returns true
        RemovalBasedCommand firstRemovalBasedDeleteCommandByFieldCopy =
            new RemovalBasedCommand(validPredicate, BY_FIELD, commandWord);
        assertTrue(firstRemovalBasedDeleteCommandByField.equals(firstRemovalBasedDeleteCommandByFieldCopy));

        // different types -> returns false
        assertFalse(firstRemovalBasedDeleteCommandByField.equals(1));

        // null -> returns false
        assertFalse(firstRemovalBasedDeleteCommandByField.equals(null));

        // different internship application predicate -> returns false
        assertFalse(firstRemovalBasedDeleteCommandByField.equals(secondRemovalBasedDeleteCommandByField));

        // different internship application predicate (invalid predicate) -> returns false
        assertFalse(firstRemovalBasedDeleteCommandByField.equals(invalidRemovalBasedDeleteCommandByField));

    }

}
