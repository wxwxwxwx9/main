package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showInternshipApplicationAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand.EditInternshipDescriptor;
import seedu.address.model.InternshipDiary;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.internship.InternshipApplication;
import seedu.address.testutil.EditInternshipDescriptorBuilder;
import seedu.address.testutil.InternshipApplicationBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        InternshipApplication editedInternshipApplication = new InternshipApplicationBuilder().build();
        EditInternshipDescriptor descriptor =
                new EditInternshipDescriptorBuilder(editedInternshipApplication).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_INTERNSHIP_SUCCESS, editedInternshipApplication);

        Model expectedModel = new ModelManager(new InternshipDiary(model.getInternshipDiary()), new UserPrefs());
        expectedModel.setInternshipApplication(model.getFilteredInternshipApplicationList().get(0),
                editedInternshipApplication);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
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

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_INTERNSHIP_SUCCESS, editedInternshipApplication);

        Model expectedModel = new ModelManager(new InternshipDiary(model.getInternshipDiary()), new UserPrefs());
        expectedModel.setInternshipApplication(lastInternshipApplication, editedInternshipApplication);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, new EditInternshipDescriptor());
        InternshipApplication editedInternshipApplication =
                model.getFilteredInternshipApplicationList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage =
                String.format(EditCommand.MESSAGE_EDIT_INTERNSHIP_SUCCESS, editedInternshipApplication);

        Model expectedModel = new ModelManager(new InternshipDiary(model.getInternshipDiary()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showInternshipApplicationAtIndex(model, INDEX_FIRST_PERSON);


        InternshipApplication internshipApplicationInFilteredList =
                model.getFilteredInternshipApplicationList().get(INDEX_FIRST_PERSON.getZeroBased());
        InternshipApplication editedInternshipApplication =
                new InternshipApplicationBuilder(internshipApplicationInFilteredList)
                        .withCompany(VALID_COMPANY_BOB).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON,
                new EditInternshipDescriptorBuilder().withCompany(VALID_COMPANY_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_INTERNSHIP_SUCCESS, editedInternshipApplication);

        Model expectedModel = new ModelManager(new InternshipDiary(model.getInternshipDiary()), new UserPrefs());
        expectedModel.setInternshipApplication(model.getFilteredInternshipApplicationList().get(0),
                editedInternshipApplication);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePersonUnfilteredList_failure() {
        InternshipApplication firstInternshipApplication =
                model.getFilteredInternshipApplicationList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditInternshipDescriptor descriptor = new EditInternshipDescriptorBuilder(firstInternshipApplication).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_PERSON, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_INTERNSHIP);
    }

    @Test
    public void execute_duplicatePersonFilteredList_failure() {
        showInternshipApplicationAtIndex(model, INDEX_FIRST_PERSON);

        // edit person in filtered list into a duplicate in address book
        InternshipApplication internshipApplicationInList =
                model.getInternshipDiary().getInternshipList().get(INDEX_SECOND_PERSON.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON,
                new EditInternshipDescriptorBuilder(internshipApplicationInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_INTERNSHIP);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        EditCommand.EditInternshipDescriptor descriptor =
                new EditInternshipDescriptorBuilder().withCompany(VALID_COMPANY_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_INTERNSHIP_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showInternshipApplicationAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditInternshipDescriptorBuilder().withCompany(VALID_COMPANY_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_INTERNSHIP_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_PERSON, DESC_AMY);

        // same values -> returns true
        EditInternshipDescriptor copyDescriptor = new EditInternshipDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_PERSON, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_PERSON, DESC_BOB)));
    }

}
