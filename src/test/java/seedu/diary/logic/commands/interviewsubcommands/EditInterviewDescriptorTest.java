package seedu.diary.logic.commands.interviewsubcommands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.diary.logic.commands.CommandTestUtil.DESC_NUS;
import static seedu.diary.logic.commands.CommandTestUtil.DESC_ONLINE;
import static seedu.diary.logic.commands.CommandTestUtil.VALID_ADDRESS_ONLINE;
import static seedu.diary.logic.commands.CommandTestUtil.VALID_DATE_ONLINE;
import static seedu.diary.logic.commands.CommandTestUtil.VALID_IS_ONLINE_ONLINE;

import org.junit.jupiter.api.Test;

import seedu.diary.testutil.EditInterviewDescriptorBuilder;

public class EditInterviewDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        InterviewEditCommand.EditInterviewDescriptor descriptorWithSameValues =
            new InterviewEditCommand.EditInterviewDescriptor(DESC_NUS);
        assertTrue(DESC_NUS.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_NUS.equals(DESC_NUS));

        // null -> returns false
        assertFalse(DESC_NUS.equals(null));

        // different types -> returns false
        assertFalse(DESC_NUS.equals(5));

        // different values -> returns false
        assertFalse(DESC_NUS.equals(DESC_ONLINE));

        // different diary -> returns false
        InterviewEditCommand.EditInterviewDescriptor editedNus =
            new EditInterviewDescriptorBuilder(DESC_NUS).withAddress(VALID_ADDRESS_ONLINE).build();
        assertFalse(DESC_NUS.equals(editedNus));

        //different date -> returns false
        editedNus = new EditInterviewDescriptorBuilder(DESC_NUS).withInterviewDate(VALID_DATE_ONLINE).build();
        assertFalse(DESC_NUS.equals(editedNus));

        //different isOnline -> returns false
        editedNus = new EditInterviewDescriptorBuilder(DESC_NUS).withIsOnline(VALID_IS_ONLINE_ONLINE).build();
        assertFalse(DESC_NUS.equals(editedNus));
    }
}
