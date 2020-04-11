package seedu.diary.logic.parser;

import static seedu.diary.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.diary.logic.parser.CliSyntax.ALL_PREFIXES;
import static seedu.diary.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.diary.logic.util.PrefixPredicateUtil.getFieldPredicate;
import static seedu.diary.logic.util.PrefixUtil.areAnyPrefixesPresent;

import java.util.List;
import java.util.function.Predicate;

import seedu.diary.commons.core.commandexecutiontype.RemovalBasedCommandExecutionType;
import seedu.diary.commons.core.index.Index;
import seedu.diary.logic.commands.RemovalBasedCommand;
import seedu.diary.logic.parser.exceptions.ParseException;
import seedu.diary.model.internship.InternshipApplication;

/**
 * Parses input arguments and creates a new RemovalBasedCommand based on the command execution type.
 * Mainly checks whether the input is of a valid format and whether it conforms to any of the
 * command execution type formats.
 */
public class RemovalBasedCommandExecutionTypeParser implements Parser<RemovalBasedCommand> {

    /**
     * Prefixes that are accepted for execution by field in RemovalBasedCommand.
     */
    private static final Prefix[] acceptedPrefixes = {PREFIX_STATUS};

    /**
     * Holds the specific command string that will be used to create the command.
     */
    private final String commandWord;

    public RemovalBasedCommandExecutionTypeParser(String commandWord) {
        this.commandWord = commandWord;
    }

    /**
     * Tokenizes the given {@code String} of arguments in the context of the RemovalBasedCommand
     * and retrieves the appropriate execution type based on the content of the string.
     *
     * @return the appropriate RemovalBasedCommand based the the execution type.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public RemovalBasedCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, ALL_PREFIXES);
        RemovalBasedCommandExecutionType executionType =
            RemovalBasedCommandExecutionType.getExecutionType(args, argMultimap);

        switch (executionType) {
        case BY_INDEX:
            return commandByIndex(args);
        case BY_INDICES:
            return commandByIndices(args);
        case BY_FIELD:
            return commandByField(args);
        default:
            // this should never happen
            assert false;
            throw new IllegalStateException("unreachable");
        }

    }

    /**
     * Parses the given {@code String} of arguments in the context of a RemovalBasedCommand
     * that is to be executed by index and returns a RemovalBasedCommand for execution.
     *
     * @param args the argument to be parsed into an Index object.
     * @throws ParseException if the user input does not conform the expected format.
     */
    private RemovalBasedCommand commandByIndex(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new RemovalBasedCommand(index, RemovalBasedCommandExecutionType.BY_INDEX, commandWord);
        } catch (ParseException pe) {
            String exceptionMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RemovalBasedCommand.MESSAGE_USAGE_BY_INDEX.apply(commandWord));
            throw new ParseException(exceptionMessage, pe);
        }
    }

    /**
     * Parses the given {@code String} of arguments in the context of the RemovalBasedCommand
     * that is to be executed by multiple indexes and returns a RemovalBasedCommand for execution.
     *
     * @param args the argument to be parsed into an a set of Index object.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    private RemovalBasedCommand commandByIndices(String args) throws ParseException {
        try {
            List<Index> indicesList = ParserUtil.parseIndices(args, RemovalBasedCommandExecutionType.INDICES_DELIMITER);
            return new RemovalBasedCommand(indicesList, RemovalBasedCommandExecutionType.BY_INDICES, commandWord);
        } catch (ParseException pe) {
            String exceptionMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RemovalBasedCommand.MESSAGE_USAGE_BY_INDICES.apply(commandWord));
            throw new ParseException(exceptionMessage, pe);
        }
    }

    /**
     * Parses the given {@code String} of arguments in the context of the RemovalBasedCommand
     * that is to be executed by accepted fields/prefixes and returns a RemovalBasedCommand for execution.
     *
     * @param args the argument to be parsed into a predicate.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    private RemovalBasedCommand commandByField(String args) throws ParseException {
        Predicate<InternshipApplication> predicate = generatePredicate(args);
        return new RemovalBasedCommand(predicate, RemovalBasedCommandExecutionType.BY_FIELD, commandWord);
    }

    /**
     * Creates the appropriate predicate according to the argument.
     *
     * @param args the argument to be parsed into a predicate.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    private Predicate<InternshipApplication> generatePredicate(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, acceptedPrefixes);
        boolean isValidField = checkValidField(argMultimap);
        if (!isValidField) {
            String exceptionMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RemovalBasedCommand.MESSAGE_USAGE_BY_FIELD.apply(commandWord));
            throw new ParseException(exceptionMessage);
        }
        return getFieldPredicate(argMultimap, acceptedPrefixes);
    }

    private boolean checkValidField(ArgumentMultimap argMultimap) {
        boolean hasOnlyOneField = (argMultimap.getSize() - 1) == 1;
        boolean hasAcceptedPrefixesPresent = areAnyPrefixesPresent(argMultimap, acceptedPrefixes);
        return hasOnlyOneField & hasAcceptedPrefixesPresent;
    }

}
