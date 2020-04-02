package seedu.address.commons.core.commandexecutiontype;

/**
 * Represents an enumeration of command execution type.
 * A command may want to mass execute on multiple items by available fields
 * or it may want to cherry-pick items to execute on.
 */
public enum CommandExecutionType {
     BY_INDEX, BY_INDICES, BY_FIELD,;
}
