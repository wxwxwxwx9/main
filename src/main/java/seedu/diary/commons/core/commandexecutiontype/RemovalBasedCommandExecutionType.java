package seedu.diary.commons.core.commandexecutiontype;

import static seedu.diary.logic.parser.CliSyntax.ALL_PREFIXES;
import static seedu.diary.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.diary.logic.util.PrefixUtil.areAnyPrefixesPresent;

import seedu.diary.logic.parser.ArgumentMultimap;
import seedu.diary.logic.parser.exceptions.ParseException;

/**
 * Represents an enumeration of command execution type for removal-based commands.
 * A removal-based command may want to mass execute on multiple items by available fields
 * or it may want to cherry-pick items to execute on.
 */
public enum RemovalBasedCommandExecutionType {

    BY_INDEX, BY_INDICES, BY_FIELD;

    public static final String INDICES_DELIMITER = ",";

    public static final String MESSAGE_USAGE_EXECUTION_TYPE =
        "You may only execute either by INDEX, multiple INDEX-es, or by STATUS.\n"
        + "If you wish to execute by INDEX, please do not include '" + PREFIX_STATUS + "' in your input.\n"
        + "If you wish to execute by STATUS, please do not include '" + INDICES_DELIMITER + "' in your input";

    /**
     * Retrieves the appropriate command execution type based on the argument.
     *
     * @return the appropriate command execution type.
     */
    public static RemovalBasedCommandExecutionType getExecutionType(String args, ArgumentMultimap argMultimap)
        throws ParseException {
        boolean hasPrefixes = areAnyPrefixesPresent(argMultimap, ALL_PREFIXES);
        boolean hasDelimiter = args.contains(RemovalBasedCommandExecutionType.INDICES_DELIMITER);
        if (hasPrefixes && hasDelimiter) {
            throw new ParseException(MESSAGE_USAGE_EXECUTION_TYPE);
        }
        return hasPrefixes
            ? RemovalBasedCommandExecutionType.BY_FIELD
            : hasDelimiter
            ? RemovalBasedCommandExecutionType.BY_INDICES
            : RemovalBasedCommandExecutionType.BY_INDEX;
    }

}
