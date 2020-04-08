package seedu.diary.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.diary.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.diary.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.diary.logic.commands.CommandTestUtil.VALID_COMPANY_BOB;
import static seedu.diary.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.diary.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.diary.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.diary.logic.commands.CommandTestUtil.showInternshipApplicationAtIndex;
import static seedu.diary.testutil.TypicalIndexes.INDEX_FIRST_INTERNSHIP_APPLICATION;
import static seedu.diary.testutil.TypicalIndexes.INDEX_SECOND_INTERNSHIP_APPLICATION;
import static seedu.diary.testutil.TypicalInternshipApplications.getTypicalInternshipDiary;

import org.junit.jupiter.api.Test;

import seedu.diary.commons.core.Messages;
import seedu.diary.commons.core.index.Index;
import seedu.diary.logic.commands.EditCommand.EditInternshipDescriptor;
import seedu.diary.model.InternshipDiary;
import seedu.diary.model.Model;
import seedu.diary.model.ModelManager;
import seedu.diary.model.UserPrefs;
import seedu.diary.model.internship.InternshipApplication;
import seedu.diary.testutil.EditInternshipDescriptorBuilder;
import seedu.diary.testutil.InternshipApplicationBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalInternshipDiary(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        InternshipApplication editedInternshipApplication = new InternshipApplicationBuilder().build();
        EditInternshipDescriptor descriptor =
            new EditInternshipDescriptorBuilder(editedInternshipApplication).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_INTERNSHIP_APPLICATION, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_INTERNSHIP_SUCCESS,
            editedInternshipApplication);

        Model expectedModel = new ModelManager(new InternshipDiary(model.getInternshipDiary()), new UserPrefs());
        expectedModel.setInternshipApplication(model.getFilteredInternshipApplicationList().get(0),
            editedInternshipApplication);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel, editedInternshipApplication);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastInternship = Index.fromOneBased(model.getFilteredInternshipApplicationList().size());
        InternshipApplication lastInternshipApplication =
            model.getFilteredInternshipApplicationList().get(indexLastInternship.getZeroBased());

        InternshipApplicationBuilder internshipApplicationInList =
            new InternshipApplicationBuilder(lastInternshipApplication);
        InternshipApplication editedInternshipApplication = internshipApplicationInList
            .withCompany(VALID_COMPANY_BOB).withPhone(VALID_PHONE_BOB)
            .build();

        EditInternshipDescriptor descriptor = new EditInternshipDescriptorBuilder().withCompany(VALID_COMPANY_BOB)
            .withPhone(VALID_PHONE_BOB).build();
        EditCommand editCommand = new EditCommand(indexLastInternship, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_INTERNSHIP_SUCCESS,
            editedInternshipApplication);

        Model expectedModel = new ModelManager(new InternshipDiary(model.getInternshipDiary()), new UserPrefs());
        expectedModel.setInternshipApplication(lastInternshipApplication, editedInternshipApplication);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel, editedInternshipApplication);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_INTERNSHIP_APPLICATION, new EditInternshipDescriptor());
        InternshipApplication editedInternshipApplication =
            model.getFilteredInternshipApplicationList().get(INDEX_FIRST_INTERNSHIP_APPLICATION.getZeroBased());

        String expectedMessage =
            String.format(EditCommand.MESSAGE_EDIT_INTERNSHIP_SUCCESS, editedInternshipApplication);

        Model expectedModel = new ModelManager(new InternshipDiary(model.getInternshipDiary()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel, editedInternshipApplication);
    }

    @Test
    public void execute_filteredList_success() {
        showInternshipApplicationAtIndex(model, INDEX_FIRST_INTERNSHIP_APPLICATION);
        model.viewUnarchivedInternshipApplicationList();

        InternshipApplication internshipApplicationInFilteredList =
            model.getFilteredInternshipApplicationList().get(INDEX_FIRST_INTERNSHIP_APPLICATION.getZeroBased());
        InternshipApplication editedInternshipApplication =
            new InternshipApplicationBuilder(internshipApplicationInFilteredList)
                .withCompany(VALID_COMPANY_BOB).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_INTERNSHIP_APPLICATION,
            new EditInternshipDescriptorBuilder().withCompany(VALID_COMPANY_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_INTERNSHIP_SUCCESS,
            editedInternshipApplication);

        Model expectedModel = new ModelManager(new InternshipDiary(model.getInternshipDiary()), new UserPrefs());
        expectedModel.setInternshipApplication(model.getFilteredInternshipApplicationList().get(0),
            editedInternshipApplication);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel, editedInternshipApplication);
    }

    @Test
    public void execute_duplicateInternshipApplicationUnfilteredList_failure() {
        InternshipApplication firstInternshipApplication =
            model.getFilteredInternshipApplicationList().get(INDEX_FIRST_INTERNSHIP_APPLICATION.getZeroBased());
        EditInternshipDescriptor descriptor = new EditInternshipDescriptorBuilder(firstInternshipApplication).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_INTERNSHIP_APPLICATION, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_INTERNSHIP);
    }

    @Test
    public void execute_duplicateInternshipApplicationFilteredList_failure() {
        showInternshipApplicationAtIndex(model, INDEX_FIRST_INTERNSHIP_APPLICATION);

        // edit internship application in filtered list into a duplicate in diary book
        InternshipApplication internshipApplicationInList =
            model.getInternshipDiary().getDisplayedInternshipList()
                .get(INDEX_SECOND_INTERNSHIP_APPLICATION.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_INTERNSHIP_APPLICATION,
            new EditInternshipDescriptorBuilder(internshipApplicationInList).build());
        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_INTERNSHIP);
    }

    @Test
    public void execute_invalidInternshipApplicationIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredInternshipApplicationList().size() + 1);
        EditCommand.EditInternshipDescriptor descriptor =
            new EditInternshipDescriptorBuilder().withCompany(VALID_COMPANY_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_INTERNSHIP_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of diary book
     */
    @Test
    public void execute_invalidInternshipApplicationIndexFilteredList_failure() {
        showInternshipApplicationAtIndex(model, INDEX_FIRST_INTERNSHIP_APPLICATION);
        Index outOfBoundIndex = INDEX_SECOND_INTERNSHIP_APPLICATION;
        // ensures that outOfBoundIndex is still in bounds of diary book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getInternshipDiary().getDisplayedInternshipList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
            new EditInternshipDescriptorBuilder().withCompany(VALID_COMPANY_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_INTERNSHIP_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_INTERNSHIP_APPLICATION, DESC_AMY);

        // same values -> returns true
        EditInternshipDescriptor copyDescriptor = new EditInternshipDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_INTERNSHIP_APPLICATION, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_INTERNSHIP_APPLICATION, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_INTERNSHIP_APPLICATION, DESC_BOB)));
    }

}
