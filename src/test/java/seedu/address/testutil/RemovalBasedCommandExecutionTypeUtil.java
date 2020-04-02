package seedu.address.testutil;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ArchiveCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.UnarchiveCommand;

/**
 * A utility class for RemovalBasedCommandExecutionType.
 */
public class RemovalBasedCommandExecutionTypeUtil {

    public static final Integer OUT_OF_BOUNDS_VALUE = 999999;

    public static final String VALID_INDEX = " 1";
    public static final String VALID_INDICES = " 1, 2, 3";
    public static final String VALID_INDICES_JUMBLED = " 3, 1, 2";

    public static final String INVALID_INDEX = " a";
    public static final String INVALID_INDICES = " 1, a, 3";
    public static final String INVALID_INDICES_JUMBLED = " 1, 2, " + OUT_OF_BOUNDS_VALUE;

    public static final Index VALID_INDEX_OBJECT = Index.fromOneBased(Integer.parseInt(VALID_INDEX.trim()));
    public static final Index INVALID_INDEX_OBJECT = Index.fromOneBased(Integer.parseInt(INVALID_INDEX.trim()));
    public static final Index INVALID_INDEX_OUT_OF_BOUNDS_OBJECT = Index.fromOneBased(OUT_OF_BOUNDS_VALUE);

    // Delete
    public static final String DELETE_BY_VALID_INDEX = DeleteCommand.COMMAND_WORD + VALID_INDEX;
    public static final String DELETE_BY_VALID_INDICES = DeleteCommand.COMMAND_WORD + VALID_INDICES;
    public static final String DELETE_BY_VALID_INDICES_JUMBLED = DeleteCommand.COMMAND_WORD + VALID_INDICES_JUMBLED;

    public static final String DELETE_BY_INVALID_INDEX = DeleteCommand.COMMAND_WORD + INVALID_INDEX;
    public static final String DELETE_BY_INVALID_INDICES = DeleteCommand.COMMAND_WORD + INVALID_INDICES;
    public static final String DELETE_BY_INVALID_INDICES_OUT_OF_BOUNDS =
        DeleteCommand.COMMAND_WORD + INVALID_INDICES_JUMBLED;

    // Archive
    public static final String ARCHIVE_BY_VALID_INDEX = ArchiveCommand.COMMAND_WORD + VALID_INDEX;
    public static final String ARCHIVE_BY_VALID_INDICES = ArchiveCommand.COMMAND_WORD + VALID_INDICES;
    public static final String ARCHIVE_BY_VALID_INDICES_JUMBLED = ArchiveCommand.COMMAND_WORD + VALID_INDICES_JUMBLED;

    public static final String ARCHIVE_BY_INVALID_INDEX = ArchiveCommand.COMMAND_WORD + INVALID_INDEX;
    public static final String ARCHIVE_BY_INVALID_INDICES = ArchiveCommand.COMMAND_WORD + INVALID_INDICES;
    public static final String ARCHIVE_BY_INVALID_INDICES_OUT_OF_BOUNDS =
        ArchiveCommand.COMMAND_WORD + INVALID_INDICES_JUMBLED;

    // Unarchive
    public static final String UNARCHIVE_BY_VALID_INDEX = UnarchiveCommand.COMMAND_WORD + VALID_INDEX;
    public static final String UNARCHIVE_BY_VALID_INDICES = UnarchiveCommand.COMMAND_WORD + VALID_INDICES;
    public static final String UNARCHIVE_BY_VALID_INDICES_JUMBLED =
        UnarchiveCommand.COMMAND_WORD + VALID_INDICES_JUMBLED;

    public static final String UNARCHIVE_BY_INVALID_INDEX = UnarchiveCommand.COMMAND_WORD + INVALID_INDEX;
    public static final String UNARCHIVE_BY_INVALID_INDICES = UnarchiveCommand.COMMAND_WORD + INVALID_INDICES;
    public static final String UNARCHIVE_BY_INVALID_INDICES_OUT_OF_BOUNDS =
        UnarchiveCommand.COMMAND_WORD + INVALID_INDICES_JUMBLED;

}
