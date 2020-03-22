package seedu.address.logic.commands.interviewsubcommands;

import org.junit.jupiter.api.Test;
import seedu.address.logic.commands.EditCommand;
import seedu.address.testutil.EditInternshipDescriptorBuilder;
import seedu.address.testutil.EditInterviewDescriptorBuilder;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.DESC_NUS;
import static seedu.address.logic.commands.CommandTestUtil.DESC_ONLINE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_ONLINE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_ONLINE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_IS_ONLINE_ONLINE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;

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

        // different address -> returns false
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
