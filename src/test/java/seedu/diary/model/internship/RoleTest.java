package seedu.diary.model.internship;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.diary.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class RoleTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Role(null));
    }

    @Test
    public void constructor_invalidRole_throwsIllegalArgumentException() {
        String invalidRole = "";
        assertThrows(IllegalArgumentException.class, () -> new Role(invalidRole));
    }

    @Test
    public void isValidRole() {
        // null role
        assertThrows(NullPointerException.class, () -> Role.isValidRole(null));

        // invalid role
        assertFalse(Role.isValidRole("")); // empty string
        assertFalse(Role.isValidRole(" ")); // spaces only
        assertFalse(Role.isValidRole("^")); // only non-alphanumeric characters
        assertFalse(Role.isValidRole("cleaner*")); // contains non-alphanumeric characters

        // valid role
        assertTrue(Role.isValidRole("data scientist")); // alphabets only
        assertTrue(Role.isValidRole("12345")); // numbers only
        assertTrue(Role.isValidRole("3d artist")); // alphanumeric characters
        assertTrue(Role.isValidRole("Software Engineer")); // with capital letters
        assertTrue(Role.isValidRole("3D Open Environment and Backdrop Artist")); // long roles
    }
}
