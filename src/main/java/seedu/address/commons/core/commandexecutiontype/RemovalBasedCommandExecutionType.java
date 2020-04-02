package seedu.address.commons.core.commandexecutiontype;

import static seedu.address.logic.parser.CliSyntax.ALL_PREFIXES;
import static seedu.address.logic.util.PrefixUtil.areAnyPrefixesPresent;

import seedu.address.logic.parser.ArgumentMultimap;

/**
 * Represents an enumeration of command execution type for removal-based commands.
 * A removal-based command may want to mass execute on multiple items by available fields
 * or it may want to cherry-pick items to execute on.
 */
public enum RemovalBasedCommandExecutionType {

    BY_INDEX, BY_INDICES, BY_FIELD;

    public static final String INDICES_DELIMITER = ",";

    /**
     * Retrieves the appropriate command execution type based on the argument.
     *
     * @return the appropriate command execution type.
     */
    public static RemovalBasedCommandExecutionType getExecutionType(String args, ArgumentMultimap argMultimap) {
        boolean hasPrefixes = areAnyPrefixesPresent(argMultimap, ALL_PREFIXES);
        boolean hasDelimiter = args.contains(RemovalBasedCommandExecutionType.INDICES_DELIMITER);
        return hasPrefixes
            ? RemovalBasedCommandExecutionType.BY_FIELD
            : hasDelimiter
            ? RemovalBasedCommandExecutionType.BY_INDICES
            : RemovalBasedCommandExecutionType.BY_INDEX;
    }

}
