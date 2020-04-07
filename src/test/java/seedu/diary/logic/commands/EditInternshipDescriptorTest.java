package seedu.diary.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.diary.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.diary.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.diary.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.diary.logic.commands.CommandTestUtil.VALID_COMPANY_BOB;
import static seedu.diary.logic.commands.CommandTestUtil.VALID_DATE_BOB;
import static seedu.diary.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.diary.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.diary.logic.commands.CommandTestUtil.VALID_PRIORITY_BOB;
import static seedu.diary.logic.commands.CommandTestUtil.VALID_ROLE_BOB;
import static seedu.diary.logic.commands.CommandTestUtil.VALID_STATUS_BOB;

import org.junit.jupiter.api.Test;

import seedu.diary.testutil.EditInternshipDescriptorBuilder;

public class EditInternshipDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditCommand.EditInternshipDescriptor descriptorWithSameValues =
            new EditCommand.EditInternshipDescriptor(DESC_AMY);
        assertTrue(DESC_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_AMY.equals(DESC_AMY));

        // null -> returns false
        assertFalse(DESC_AMY.equals(null));

        // different types -> returns false
        assertFalse(DESC_AMY.equals(5));

        // different values -> returns false
        assertFalse(DESC_AMY.equals(DESC_BOB));

        // different company -> returns false
        EditCommand.EditInternshipDescriptor editedAmy =
            new EditInternshipDescriptorBuilder(DESC_AMY).withCompany(VALID_COMPANY_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different phone -> returns false
        editedAmy = new EditInternshipDescriptorBuilder(DESC_AMY).withPhone(VALID_PHONE_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different email -> returns false
        editedAmy = new EditInternshipDescriptorBuilder(DESC_AMY).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different diary -> returns false
        editedAmy = new EditInternshipDescriptorBuilder(DESC_AMY).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        //different date -> returns false
        editedAmy = new EditInternshipDescriptorBuilder(DESC_AMY).withApplicationDate(VALID_DATE_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        //different role -> returns false
        editedAmy = new EditInternshipDescriptorBuilder(DESC_AMY).withRole(VALID_ROLE_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        //different priority -> returns false
        editedAmy = new EditInternshipDescriptorBuilder(DESC_AMY).withPriority(VALID_PRIORITY_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        //different status -> returns false
        editedAmy = new EditInternshipDescriptorBuilder(DESC_AMY).withStatus(VALID_STATUS_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));
    }
}
