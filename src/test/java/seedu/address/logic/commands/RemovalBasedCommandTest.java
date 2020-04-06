package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showInternshipApplicationAtIndex;
import static seedu.address.logic.commands.CommandTestUtil.showInternshipApplicationAtIndices;
import static seedu.address.testutil.PredicateUtil.prepareCompanyPredicate;
import static seedu.address.testutil.PredicateUtil.prepareEmailPredicate;
import static seedu.address.testutil.PredicateUtil.prepareRolePredicate;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_INTERNSHIP_APPLICATION;
import static seedu.address.testutil.TypicalIndexes.INDEX_LIST_FIRST_INTERNSHIP_APPLICATION;
import static seedu.address.testutil.TypicalIndexes.INDEX_LIST_SECOND_INTERNSHIP_APPLICATION;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_INTERNSHIP_APPLICATION;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.commandexecutiontype.RemovalBasedCommandExecutionType;
import seedu.address.commons.core.index.Index;
import seedu.address.model.InternshipDiary;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.internship.InternshipApplication;
import seedu.address.testutil.InternshipApplicationBuilder;

/**
 * Contains integration tests (interaction with the Model, DeleteCommand) and unit tests for
 * {@code RemovalBasedCommand}.
 * Integration tests for ArchiveCommand and UnarchiveCommand are deemed unnecessary because they are similar in nature
 * to DeleteCommand. Testing integration with DeleteCommand will suffice.
 */
public class RemovalBasedCommandTest {

    private static final String GOOGLE = "google";
    private static final String FACEBOOK = "facebook";

    private Index index;
    private Index secondIndex;
    private List<Index> indices;
    private List<Index> secondIndices;
    private Predicate<InternshipApplication> validPredicate;
    private Predicate<InternshipApplication> secondValidPredicate;
    private Predicate<InternshipApplication> invalidPredicate;

    private RemovalBasedCommandExecutionType executionTypeIndex;
    private RemovalBasedCommandExecutionType executionTypeIndices;
    private RemovalBasedCommandExecutionType executionTypeField;

    private String commandWord = DeleteCommand.COMMAND_WORD;

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getNewInternshipDiary(), new UserPrefs());
        expectedModel = new ModelManager(getNewInternshipDiary(), new UserPrefs());
        index = INDEX_FIRST_INTERNSHIP_APPLICATION;
        secondIndex = INDEX_SECOND_INTERNSHIP_APPLICATION;
        indices = new ArrayList<>(INDEX_LIST_FIRST_INTERNSHIP_APPLICATION);
        secondIndices = new ArrayList<>(INDEX_LIST_SECOND_INTERNSHIP_APPLICATION);
        validPredicate = prepareCompanyPredicate(GOOGLE);
        secondValidPredicate = prepareRolePredicate(GOOGLE);
        invalidPredicate = prepareEmailPredicate(GOOGLE);
        executionTypeIndex = RemovalBasedCommandExecutionType.BY_INDEX;
        executionTypeIndices = RemovalBasedCommandExecutionType.BY_INDICES;
        executionTypeField = RemovalBasedCommandExecutionType.BY_FIELD;
    }

    private InternshipDiary getNewInternshipDiary() {
        InternshipDiary mockInternshipDiary = new InternshipDiary();
        mockInternshipDiary.loadInternshipApplication(new InternshipApplicationBuilder().withCompany(GOOGLE).build());
        mockInternshipDiary.loadInternshipApplication(new InternshipApplicationBuilder().withCompany(FACEBOOK).build());
        return mockInternshipDiary;
    }

    @Test
    public void execute_byIndexValidIndexUnfilteredListDeleteCommand_success() {

        RemovalBasedCommand removalBasedDeleteCommand =
            new RemovalBasedCommand(index, executionTypeIndex, commandWord);

        InternshipApplication internshipApplicationToExecuteOn =
                model.getFilteredInternshipApplicationList().get(INDEX_FIRST_INTERNSHIP_APPLICATION.getZeroBased());

        String expectedMessage = String.format(
            RemovalBasedCommand.MESSAGE_COMMAND_INTERNSHIP_SUCCESS.apply(commandWord),
            internshipApplicationToExecuteOn + "\n");

        expectedModel.deleteInternshipApplication(internshipApplicationToExecuteOn);

        assertCommandSuccess(removalBasedDeleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_byIndexInvalidIndexUnfilteredListDeleteCommand_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredInternshipApplicationList().size() + 1);

        RemovalBasedCommand removalBasedDeleteCommand =
            new RemovalBasedCommand(outOfBoundIndex, executionTypeIndex, commandWord);

        assertCommandFailure(removalBasedDeleteCommand, model, Messages.MESSAGE_INVALID_INTERNSHIP_DISPLAYED_INDEX);
    }

    @Test
    public void execute_byIndexValidIndexFilteredListDeleteCommand_success() {
        showInternshipApplicationAtIndex(model, INDEX_FIRST_INTERNSHIP_APPLICATION);

        RemovalBasedCommand removalBasedDeleteCommand =
            new RemovalBasedCommand(index, executionTypeIndex, commandWord);

        InternshipApplication internshipApplicationToExecuteOn =
            model.getFilteredInternshipApplicationList().get(INDEX_FIRST_INTERNSHIP_APPLICATION.getZeroBased());

        String expectedMessage = String.format(
            RemovalBasedCommand.MESSAGE_COMMAND_INTERNSHIP_SUCCESS.apply(commandWord),
            internshipApplicationToExecuteOn + "\n");

        showInternshipApplicationAtIndex(expectedModel, INDEX_FIRST_INTERNSHIP_APPLICATION);
        expectedModel.deleteInternshipApplication(internshipApplicationToExecuteOn);

        assertCommandSuccess(removalBasedDeleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_byIndexInvalidIndexFilteredListDeleteCommand_throwsCommandException() {
        showInternshipApplicationAtIndex(model, INDEX_FIRST_INTERNSHIP_APPLICATION);

        Index outOfBoundIndex = INDEX_SECOND_INTERNSHIP_APPLICATION;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getInternshipDiary().getDisplayedInternshipList().size());

        RemovalBasedCommand removalBasedDeleteCommand =
            new RemovalBasedCommand(outOfBoundIndex, executionTypeIndex, commandWord);

        assertCommandFailure(removalBasedDeleteCommand, model, Messages.MESSAGE_INVALID_INTERNSHIP_DISPLAYED_INDEX);
    }

    @Test
    public void execute_byIndicesValidIndicesUnfilteredListDeleteCommand_success() {

        String deletedInternshipApplications = "";

        // create expected model and delete the appropriate internship applications
        for (Index index: indices) {
            InternshipApplication internshipApplicationToDelete =
                model.getFilteredInternshipApplicationList().get(index.getZeroBased());
            expectedModel.deleteInternshipApplication(internshipApplicationToDelete);
            deletedInternshipApplications += internshipApplicationToDelete + "\n";
        }

        String expectedMessage = String.format(
            RemovalBasedCommand.MESSAGE_COMMAND_INTERNSHIP_SUCCESS.apply(commandWord),
            deletedInternshipApplications);

        // create command
        RemovalBasedCommand removalBasedDeleteCommand =
            new RemovalBasedCommand(indices, executionTypeIndices, commandWord);

        assertCommandSuccess(removalBasedDeleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_byIndicesInvalidIndicesUnfilteredListDeleteCommand_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredInternshipApplicationList().size() + 1);
        List<Index> mockIndexes = new ArrayList<>(INDEX_LIST_FIRST_INTERNSHIP_APPLICATION);
        mockIndexes.add(outOfBoundIndex);

        RemovalBasedCommand removalBasedDeleteCommand =
            new RemovalBasedCommand(mockIndexes, executionTypeIndices, commandWord);

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
        for (Index index: indices) {
            InternshipApplication internshipApplicationToDelete =
                expectedModel.getFilteredInternshipApplicationList().get(index.getZeroBased());
            toDelete.add(internshipApplicationToDelete);
            deletedInternshipApplications += internshipApplicationToDelete + "\n";
        }

        for (InternshipApplication internshipApplication : toDelete) {
            expectedModel.deleteInternshipApplication(internshipApplication);
        }

        String expectedMessage = String.format(
            RemovalBasedCommand.MESSAGE_COMMAND_INTERNSHIP_SUCCESS.apply(commandWord),
            deletedInternshipApplications);

        RemovalBasedCommand removalBasedDeleteCommand =
            new RemovalBasedCommand(indices, executionTypeIndices, commandWord);

        assertCommandSuccess(removalBasedDeleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_byIndicesInvalidIndicesFilteredListDeleteCommand_throwsCommandException() {
        showInternshipApplicationAtIndices(model, INDEX_LIST_FIRST_INTERNSHIP_APPLICATION);

        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredInternshipApplicationList().size() + 1);
        List<Index> mockIndexes = new ArrayList<>(INDEX_LIST_FIRST_INTERNSHIP_APPLICATION);
        mockIndexes.add(outOfBoundIndex);

        RemovalBasedCommand removalBasedDeleteCommand =
            new RemovalBasedCommand(mockIndexes, executionTypeIndices, commandWord);

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
            deletedInternshipApplications += toDelete + "\n";
        }

        RemovalBasedCommand removalBasedDeleteCommand =
            new RemovalBasedCommand(validPredicate, executionTypeField, commandWord);

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
            deletedInternshipApplications += toDelete + "\n";
        }

        RemovalBasedCommand removalBasedDeleteCommand =
            new RemovalBasedCommand(validPredicate, executionTypeField, commandWord);

        String expectedMessage = String.format(
            RemovalBasedCommand.MESSAGE_COMMAND_INTERNSHIP_SUCCESS.apply(commandWord),
            deletedInternshipApplications);

        assertCommandSuccess(removalBasedDeleteCommand, model, expectedMessage, expectedModel);

    }

    @Test
    public void equals() {

        // BY INDEX
        RemovalBasedCommand firstRemovalBasedDeleteCommandByIndex =
                new RemovalBasedCommand(index, executionTypeIndex, commandWord);
        RemovalBasedCommand secondRemovalBasedDeleteCommandByIndex =
            new RemovalBasedCommand(secondIndex, executionTypeIndex, commandWord);

        // same object -> returns true
        assertTrue(firstRemovalBasedDeleteCommandByIndex.equals(firstRemovalBasedDeleteCommandByIndex));

        // same values -> returns true
        RemovalBasedCommand firstRemovalBasedDeleteCommandByIndexCopy =
            new RemovalBasedCommand(index, executionTypeIndex, commandWord);
        assertTrue(firstRemovalBasedDeleteCommandByIndex.equals(firstRemovalBasedDeleteCommandByIndexCopy));

        // different types -> returns false
        assertFalse(firstRemovalBasedDeleteCommandByIndex.equals(1));

        // null -> returns false
        assertFalse(firstRemovalBasedDeleteCommandByIndex.equals(null));

        // different internship application index -> returns false
        assertFalse(firstRemovalBasedDeleteCommandByIndex.equals(secondRemovalBasedDeleteCommandByIndex));


        // BY INDICES
        RemovalBasedCommand firstRemovalBasedDeleteCommandByIndices =
            new RemovalBasedCommand(indices, executionTypeIndices, commandWord);
        RemovalBasedCommand secondRemovalBasedDeleteCommandByIndices =
            new RemovalBasedCommand(secondIndices, executionTypeIndices, commandWord);

        // same object -> returns true
        assertTrue(firstRemovalBasedDeleteCommandByIndices.equals(firstRemovalBasedDeleteCommandByIndices));

        // same values -> returns true
        RemovalBasedCommand firstRemovalBasedDeleteCommandByIndicesCopy =
            new RemovalBasedCommand(indices, executionTypeIndices, commandWord);
        assertTrue(firstRemovalBasedDeleteCommandByIndices.equals(firstRemovalBasedDeleteCommandByIndicesCopy));

        // different types -> returns false
        assertFalse(firstRemovalBasedDeleteCommandByIndices.equals(1));

        // null -> returns false
        assertFalse(firstRemovalBasedDeleteCommandByIndices.equals(null));

        // different internship application index -> returns false
        assertFalse(firstRemovalBasedDeleteCommandByIndices.equals(secondRemovalBasedDeleteCommandByIndices));


        // BY FIELD
        RemovalBasedCommand firstRemovalBasedDeleteCommandByField =
            new RemovalBasedCommand(validPredicate, executionTypeField, commandWord);
        RemovalBasedCommand secondRemovalBasedDeleteCommandByField =
            new RemovalBasedCommand(secondValidPredicate, executionTypeField, commandWord);

        // same object -> returns true
        assertTrue(firstRemovalBasedDeleteCommandByField.equals(firstRemovalBasedDeleteCommandByField));

        // same values -> returns true
        RemovalBasedCommand firstRemovalBasedDeleteCommandByFieldCopy =
            new RemovalBasedCommand(validPredicate, executionTypeField, commandWord);
        assertTrue(firstRemovalBasedDeleteCommandByField.equals(firstRemovalBasedDeleteCommandByFieldCopy));

        // different types -> returns false
        assertFalse(firstRemovalBasedDeleteCommandByField.equals(1));

        // null -> returns false
        assertFalse(firstRemovalBasedDeleteCommandByField.equals(null));

        // different internship application index -> returns false
        assertFalse(firstRemovalBasedDeleteCommandByField.equals(secondRemovalBasedDeleteCommandByField));

    }

    /**
     * Updates {@code model}'s filtered list to show no applications.
     */
    private void showNoInternshipApplication(Model model) {
        model.updateFilteredInternshipApplicationList(p -> false);

        assertTrue(model.getFilteredInternshipApplicationList().isEmpty());
    }

}
