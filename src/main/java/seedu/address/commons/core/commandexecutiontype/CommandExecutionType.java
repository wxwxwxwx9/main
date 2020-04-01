package seedu.address.commons.core.commandexecutiontype;

import static seedu.address.logic.parser.CliSyntax.ALL_PREFIXES;
import static seedu.address.logic.util.PrefixUtil.areAnyPrefixesPresent;

import seedu.address.logic.parser.ArgumentMultimap;

/**
 * Represents an enumeration of command execution type.
 * A command may want to mass execute on multiple items by available fields
 * or it may want to cherry-pick items to execute on.
 */
public enum CommandExecutionType {

     BY_INDEX, BY_INDICES, BY_FIELD;

     public static final String INDICES_DELIMITER = ",";

     /**
      * Retrieves the appropriate command execution type based on the argument.
      *
      * @return the appropriate command execution type.
      */
     public static CommandExecutionType getExecutionType(String args, ArgumentMultimap argMultimap) {
          boolean hasPrefixes = areAnyPrefixesPresent(argMultimap, ALL_PREFIXES);
          boolean hasDelimiter = args.contains(CommandExecutionType.INDICES_DELIMITER);
          return hasPrefixes
              ? CommandExecutionType.BY_FIELD
              : hasDelimiter
              ? CommandExecutionType.BY_INDICES
              : CommandExecutionType.BY_INDEX;
     }

}
